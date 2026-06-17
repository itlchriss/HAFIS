"""
Text normalization pipeline step.

Loads regex-based normalization rules from YAML and applies them
sequentially to clean up text before parsing.
"""

import os
import re
import yaml
from typing import List, Optional

from ..pipeline import ProcessingContext, ProcessingStep

# Default path to normalization rules
DEFAULT_RULES_PATH = os.path.join('.', 'rules', 'normalization.yml')


class NormalizationStep(ProcessingStep):
    """Applies text normalization rules loaded from a YAML file.
    
    Rules are simple regex substitutions applied in order.
    
    YAML format:
        - pattern: 'regex_pattern'
          replacement: 'replacement_string'
          description: 'optional description'
    """
    
    def __init__(self, rules_path: Optional[str] = None):
        """Initialize the normalization step.
        
        Args:
            rules_path: Path to the YAML rules file. Uses default if not specified.
        """
        self.rules_path = rules_path or DEFAULT_RULES_PATH
        self.rules: List[dict] = []
        self._loaded = False
    
    def _load_rules(self):
        """Load rules from the YAML file."""
        if self._loaded:
            return
        
        if not os.path.exists(self.rules_path):
            self._loaded = True
            return
        
        with open(self.rules_path, 'r', encoding='utf-8') as fp:
            data = yaml.safe_load(fp)
        
        if data:
            self.rules = data
        
        self._loaded = True
    
    def process(self, sent: str, context: ProcessingContext) -> str:
        """Apply all normalization rules to the sentence.
        
        Args:
            sent: The sentence to normalize
            context: Processing context (not modified by this step)
            
        Returns:
            The normalized sentence
        """
        self._load_rules()
        
        for rule in self.rules:
            pattern = rule['pattern']
            replacement = rule['replacement']
            sent = re.sub(pattern, replacement, sent)
        
        # Also apply 'the the' -> 'the' as a simple string replacement
        sent = sent.replace('the the', 'the')
        
        return sent
