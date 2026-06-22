import sys
import openai
import time
import re
import os

KEY = '/Users/chrissleong/Documents/Phd_Studies/openai.key'


def get_key() -> str:
    key = None
    with open(KEY, 'r') as fp:
        key = fp.read()
    return key


def qwen_send_prompt(prompt: str) -> str:
    """Send prompt to qwen3.7 model via OpenAI-compatible API."""
    s = None
    while True:
        try:
            print('Sending prompt to qwen3.7....')
            response = openai.ChatCompletion.create(
                            model="qwen3.7",
                            messages=[
                                {"role": "user", "content": prompt}
                            ],
                            temperature=0
                        )
            s = response.choices[0].message.content
            print('Message received from qwen3.7...')
        except openai.error.APIError:
            print('API Error. Retry...')
            continue
        except openai.error.ServiceUnavailableError:
            print('Server overloaded...Retry after 120 seconds...')
            time.sleep(120)
            continue
        else:
            break
    return s


def load_prompt_template():
    """Load the RNL prompt template from the v7 prompt file."""
    script_dir = os.path.dirname(os.path.abspath(__file__))
    project_root = os.path.abspath(os.path.join(script_dir, '..', '..'))
    prompt_path = os.path.join(project_root, 'qwen3.7', 'prompt_readme_to_rnl_v7.md')
    with open(prompt_path, 'r') as fp:
        return fp.read()


def extract_method_name(signature):
    """Extract the method name from a Java-like method signature.
    E.g., 'public int[] twoSum(int[] numbers, int target)' -> 'twoSum'
    """
    match = re.search(r'(\w+)\s*\(', signature)
    if match:
        return match.group(1)
    return None


def extract_method_info(signature):
    """Extract method name and parameter info from a Java-like method signature.
    Returns a dict with method_name, params, return_type.
    """
    info = {'method_name': None, 'params': [], 'return_type': 'void'}
    match = re.search(r'(\w+)\s*\(([^)]*)\)', signature)
    if not match:
        return info
    info['method_name'] = match.group(1)
    param_str = match.group(2).strip()
    # Extract return type from text before method name
    before_method = signature[:signature.rfind(match.group(1))].strip()
    words = before_method.split()
    if words:
        info['return_type'] = words[-1].replace('[]', '')
    # Parse parameters
    if param_str:
        for p in param_str.split(','):
            p = p.strip()
            pm = re.match(r'(\w+(?:<[^>]+>)?(?:\[\])?)\s+(\w+)', p)
            if pm:
                ptype = pm.group(1)
                pname = pm.group(2)
                is_array = '[]' in ptype or ptype.lower() in ('list', 'array')
                info['params'].append({
                    'type': ptype.replace('[]', ''),
                    'name': pname,
                    'is_array': is_array
                })
    return info


def build_prompt(readme_content, signature):
    """Build the full prompt by combining the template with the problem-specific content."""
    template = load_prompt_template()

    # Clean up HTML artifacts from readme
    readme_content = readme_content.replace('<sup>', '^').replace('</sup>', '')

    # Append the problem-specific input section
    prompt = template + """

---

## PROBLEM INPUT

**readme.md**:
```
%s
```

**Method signature**: %s

Now, generate the rnl.txt output following the instructions above.
Remember: the first line MUST be `// method: %s` with the method name extracted from the signature.
""" % (readme_content, signature, extract_method_name(signature) or 'unknown')

    return prompt


def list_method_conditions(rnl_content):
    """Parse rnl.txt content and return a list of (method_name, [conditions]) tuples.
    This is used by the instrumentation script to map conditions to methods.
    """
    methods = []
    current_method = None
    current_conditions = []

    for line in rnl_content.split('\n'):
        stripped = line.strip()
        # Check for method header
        method_match = re.match(r'^//\s*method:\s*(\w+)', stripped)
        if method_match:
            if current_method is not None:
                methods.append((current_method, current_conditions))
            current_method = method_match.group(1)
            current_conditions = []
            continue
        # Collect conditions
        if stripped.startswith('- '):
            current_conditions.append(stripped)

    # Don't forget the last method
    if current_method is not None:
        methods.append((current_method, current_conditions))

    return methods


def main(filename, mode=None):
    openai.api_key = get_key().strip()

    sentence = None
    with open(filename, 'r') as fp:
        sentence = fp.read()

    if not sentence.strip():
        print('Warning: %s is empty...Nothing to be done...' % filename)
        exit(-1)

    _arr = filename.split('/')
    folder = '/'.join(_arr[:-1])

    signature = None
    with open('%s/methodsignature' % folder, 'r') as fp:
        signature = fp.read()
    signature = signature.strip()
    if not signature:
        print('error in getting signature of ' % folder)
        exit(-1)

    # Extract method info for the prompt
    method_info = extract_method_info(signature)
    method_name = method_info['method_name']
    print('Method: %s' % method_name)
    print('Params: %s' % ', '.join([p['name'] for p in method_info['params']]))
    print('Return type: %s' % method_info['return_type'])

    # Build prompt using the template-based approach
    prompt = build_prompt(sentence, signature)

    # Compute qwen3.7 output directory based on project root
    script_dir = os.path.dirname(os.path.abspath(__file__))
    project_root = os.path.abspath(os.path.join(script_dir, '..', '..'))
    problem_name = os.path.basename(folder)
    qwen_folder = os.path.join(project_root, 'qwen3.7', problem_name)

    if mode and mode.strip() == 'q':
        s = qwen_send_prompt(prompt)

        # Ensure qwen3.7 output directory exists
        if not os.path.exists(qwen_folder):
            os.makedirs(qwen_folder)

        # Write rnl.txt
        rnl_path = os.path.join(qwen_folder, 'rnl.txt')
        print('Writing qwen3.7 result to %s...' % rnl_path)
        with open(rnl_path, 'w') as fp:
            fp.write(s)

        print(s)

        # List method-condition mapping
        methods = list_method_conditions(s)
        for m_name, conditions in methods:
            print('Method %s: %d conditions' % (m_name, len(conditions)))

    elif mode and mode.strip() == 's':
        # Stdout only - don't send to API, just print the prompt
        print(prompt)
    elif mode and mode.strip() == 'l':
        # List mode - parse existing rnl.txt and list method-condition mapping
        rnl_path = os.path.join(qwen_folder, 'rnl.txt')
        if os.path.exists(rnl_path):
            with open(rnl_path, 'r') as fp:
                content = fp.read()
            methods = list_method_conditions(content)
            if not methods:
                print('No method headers found in %s' % rnl_path)
            else:
                for m_name, conditions in methods:
                    print('Method: %s (%d conditions)' % (m_name, len(conditions)))
                    for cond in conditions:
                        print('  %s' % cond)
        else:
            print('rnl.txt not found at %s' % rnl_path)
    else:
        # Default: send to qwen3.7 and write output
        s = qwen_send_prompt(prompt)

        # Ensure qwen3.7 output directory exists
        if not os.path.exists(qwen_folder):
            os.makedirs(qwen_folder)

        # Write rnl.txt
        rnl_path = os.path.join(qwen_folder, 'rnl.txt')
        print('Writing qwen3.7 result to %s...' % rnl_path)
        with open(rnl_path, 'w') as fp:
            fp.write(s)

        print(s)

        # List method-condition mapping
        methods = list_method_conditions(s)
        for m_name, conditions in methods:
            print('Method %s: %d conditions' % (m_name, len(conditions)))


if __name__ == "__main__":
    if len(sys.argv) == 3:
        main(filename=sys.argv[1], mode=sys.argv[2])
    else:
        main(filename=sys.argv[1])
