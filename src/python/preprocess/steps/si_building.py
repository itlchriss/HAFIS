"""
SI (Software Interface) building pipeline step.

Constructs the final dynamic_si dictionary entries with full type information,
interpretations, and syntax annotations. This was previously embedded in
the runengine() function.
"""

from ..pipeline import ProcessingContext, ProcessingStep


# SI templates for sequence types
_SEQUENCE_SI_TEMPLATES = {
    '_integer_sequence_': {
        'primitive_type': 'integer',
        'reference_type': 'array',
    },
    '_character_sequence_': {
        'primitive_type': 'character',
        'reference_type': 'array',
    },
    '_string_sequence_': {
        'primitive_type': 'undefined',
        'reference_type': 'string_array',
    },
}

# SI templates for Dafny extension types
_DAFNY_SI_TEMPLATES = {
    # Set types
    '_set_': {
        'primitive_type': 'any',
        'reference_type': 'set',
    },
    '_integer_set_': {
        'primitive_type': 'integer',
        'reference_type': 'set',
    },
    '_character_set_': {
        'primitive_type': 'character',
        'reference_type': 'set',
    },
    # Multiset types
    '_multiset_': {
        'primitive_type': 'any',
        'reference_type': 'multiset',
    },
    '_integer_multiset_': {
        'primitive_type': 'integer',
        'reference_type': 'multiset',
    },
    '_character_multiset_': {
        'primitive_type': 'character',
        'reference_type': 'multiset',
    },
    # Map types
    '_map_': {
        'primitive_type': 'any',
        'reference_type': 'map',
    },
    '_integer-to-integer_map_': {
        'primitive_type': 'integer',
        'reference_type': 'map',
    },
    # Sequence types (Dafny seq)
    '_seq_': {
        'primitive_type': 'any',
        'reference_type': 'seq',
    },
    # Ghost/proof types
    '_ghost_': {
        'primitive_type': 'any',
        'reference_type': 'ghost',
    },
}


class SIBuildingStep(ProcessingStep):
    """Constructs full SI dictionary entries from accumulated dynamic_si data.
    
    Takes the raw dynamic_si values accumulated during earlier pipeline steps
    and builds the complete SI dictionaries with term, syntax, arguments,
    synthesised_datatype, and interpretation fields.
    """
    
    def process(self, sent: str, context: ProcessingContext) -> str:
        """Build SI entries for all accumulated dynamic_si keys.
        
        Args:
            sent: The sentence (ensured to end with '.')
            context: Processing context (dynamic_si is transformed in-place)
            
        Returns:
            The sentence (with trailing '.' ensured)
        """
        # Ensure sentence ends with period
        if sent and sent[-1] != '.':
            sent += '.'
        
        words = sent.split(' ')
        
        # Process each dynamic_si entry
        for key in list(context.dynamic_si.keys()):
            value = context.dynamic_si[key]
            
            # Skip if already a full SI dictionary
            if isinstance(value, dict) and 'term' in value:
                continue
            
            context.dynamic_si[key] = self._build_si_entry(
                key, value, words
            )
        
        return sent
    
    def _build_si_entry(self, key: str, value, words: list) -> dict:
        """Build a single SI dictionary entry.
        
        Args:
            key: The SI key name
            value: The raw value (interpretation string or dict)
            words: Tokenized sentence for context-based type inference
            
        Returns:
            Complete SI dictionary
        """
        # Check for sequence types first
        for seq_suffix, type_info in _SEQUENCE_SI_TEMPLATES.items():
            if seq_suffix in key:
                return {
                    'term': key,
                    'syntax': ['NN'],
                    'arguments': [{
                        'symbol': '*',
                        'primitive_type': 'any',
                        'reference_type': 'any'
                    }],
                    'synthesised_datatype': [{
                        'primitive_type': type_info['primitive_type'],
                        'reference_type': type_info['reference_type']
                    }],
                    'interpretation': value
                }
        
        # Check for Dafny extension types
        for dafny_suffix, type_info in _DAFNY_SI_TEMPLATES.items():
            if dafny_suffix in key:
                return {
                    'term': key,
                    'syntax': ['NN'],
                    'arguments': [{
                        'symbol': '*',
                        'primitive_type': 'any',
                        'reference_type': 'any'
                    }],
                    'synthesised_datatype': [{
                        'primitive_type': type_info['primitive_type'],
                        'reference_type': type_info['reference_type']
                    }],
                    'interpretation': value
                }
        
        # Determine types based on key prefix and context
        return self._build_general_si_entry(key, value, words)
    
    def _build_general_si_entry(self, key: str, value, words: list) -> dict:
        """Build SI entry for non-sequence keys using context-based inference.
        
        Args:
            key: The SI key name
            value: The raw value
            words: Tokenized sentence for context
            
        Returns:
            Complete SI dictionary
        """
        # Default types
        primitive_type = 'any'
        reference_type = 'any'
        
        # Find key position in sentence for context
        index = -1
        if key in words:
            index = words.index(key)
        
        # Check if preceded by 'type_integer_' -> integer type
        if index > 0 and words[index - 1] == 'type_integer_':
            sp = 'integer'
            sr = 'undefined'
            interpretation = str(value).replace('`', '')
        elif 'chr' in key:
            sp = 'character'
            sr = 'undefined'
            interpretation = str(value)
        elif 'str_seq' in key:
            sp = 'string'
            sr = 'string_array'
            interpretation = str(value)
        elif 'arr_' not in key:
            sp = 'undefined'
            sr = 'string'
            interpretation = str(value).replace('`', '')
        else:
            sp = 'integer'
            sr = 'array'
            interpretation = '%s' % str(value).replace(' ', '')
        
        # Handle param_ prefix
        term = key
        if term.startswith('param_'):
            term = term.replace('param_', '')
            interpretation = interpretation.replace('param_', '').replace('_', '')
            if term and term[-1] == '_':
                term = term[:-1]
        
        return {
            'term': term,
            'syntax': ['NN'],
            'arguments': [{
                'symbol': '*',
                'primitive_type': primitive_type,
                'reference_type': reference_type
            }],
            'synthesised_datatype': [{
                'primitive_type': sp,
                'reference_type': sr
            }],
            'interpretation': interpretation
        }
