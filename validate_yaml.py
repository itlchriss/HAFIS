import yaml
import sys

try:
    with open('specs/si/typed_si.yml', 'r') as f:
        data = yaml.safe_load(f)
    print(f"YAML is valid. Loaded {len(data)} entries.")
except yaml.YAMLError as e:
    print(f"YAML error: {e}")
    sys.exit(1)
