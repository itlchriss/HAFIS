"""
Narrowing pipeline step: LLM-RNL → Strict RNL.

Applies narrowing rules from rules/narrowing.yml to transform
LLM-generated RNL into strict RNL that matches the SI terms
and syntax rules used by the rest of the pipeline.

This step runs FIRST in the pipeline, before any other processing.
"""

import os
import yaml
from typing import List, Tuple, Optional
from ..pipeline import ProcessingContext, ProcessingStep


NARROWING_RULES_PATH = os.path.join('.', 'rules', 'narrowing.yml')


class NarrowingEngine:
    """
    Applies narrowing rules to transform LLM-RNL into strict RNL.
    
    Rules are loaded from rules/narrowing.yml and applied in order.
    Each rule has a pattern (string to find) and a replacement.
    """
    
    def __init__(self):
        self.rules: List[Tuple[str, str]] = []
        self._loaded = False
    
    def load_rules(self, filepath: str = NARROWING_RULES_PATH):
        """Load narrowing rules from YAML file.
        
        YAML format:
        - pattern: 'text to find'
          replacement: 'replacement text'
          description: 'what this rule does'
        """
        if not os.path.exists(filepath):
            self._loaded = False
            return
        
        with open(filepath, 'r', encoding='utf-8') as fp:
            data = yaml.safe_load(fp)
        
        if not data:
            self._loaded = False
            return
        
        for entry in data:
            if not isinstance(entry, dict):
                continue
            pattern = entry.get('pattern', '')
            replacement = entry.get('replacement', '')
            if pattern:
                self.rules.append((pattern, replacement))
        
        # Sort by pattern length (longest first) for greedy matching
        self.rules.sort(key=lambda x: -len(x[0]))
        self._loaded = True
    
    def apply(self, sentence: str) -> str:
        """Apply all narrowing rules to the sentence.
        
        Rules are applied in order (longest pattern first).
        Each rule does a simple case-insensitive string replacement.
        """
        if not self._loaded or not self.rules:
            return sentence
        
        result = sentence
        
        for pattern, replacement in self.rules:
            # Case-insensitive replacement
            import re
            # Use word boundary-aware replacement
            escaped = re.escape(pattern)
            result = re.sub(escaped, replacement, result, flags=re.IGNORECASE)
        
        return result


# Global narrowing engine instance
_narrowing_engine: Optional[NarrowingEngine] = None


def get_narrowing_engine() -> NarrowingEngine:
    """Get or create the global narrowing engine instance."""
    global _narrowing_engine
    if _narrowing_engine is None:
        _narrowing_engine = NarrowingEngine()
        _narrowing_engine.load_rules()
    return _narrowing_engine


class NarrowingStep(ProcessingStep):
    """Pipeline step that narrows LLM-RNL to strict RNL.
    
    This step should run FIRST in the pipeline, before any other
    processing steps. It applies the narrowing rules to transform
    LLM-generated RNL into a form that matches the SI terms.
    """
    
    def __init__(self):
        self._engine = get_narrowing_engine()
    
    def process(self, sent: str, context: ProcessingContext) -> str:
        """Apply narrowing rules to the sentence.
        
        Args:
            sent: The current sentence (LLM-RNL)
            context: Processing context
            
        Returns:
            The narrowed sentence (strict RNL)
        """
        return self._engine.apply(sent)
