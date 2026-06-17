"""
Pipeline infrastructure for text preprocessing.

Provides a modular, extensible pipeline where each step processes
a sentence and updates a shared context. This replaces the procedural
control flow that was previously in runengine().
"""

from abc import ABC, abstractmethod
from dataclasses import dataclass, field
from typing import Dict, List, Optional, Tuple


@dataclass
class ProcessingContext:
    """Shared mutable state passed through the pipeline.
    
    Attributes:
        requirement_type: Either 'requires' or 'ensures'
        dynamic_si: Accumulated software interface entries
        expression_store: Extracted expressions (from backticks, brackets, quotes)
    """
    requirement_type: str = 'requires'
    dynamic_si: Dict = field(default_factory=dict)
    expression_store: Dict = field(default_factory=dict)


class ProcessingStep(ABC):
    """Base class for all pipeline steps.
    
    Each step receives the current sentence and a shared context,
    processes the sentence, optionally updates the context, and
    returns the modified sentence.
    """
    
    @abstractmethod
    def process(self, sent: str, context: ProcessingContext) -> str:
        """Process the sentence and return the modified version.
        
        Args:
            sent: The current sentence being processed
            context: Shared mutable state (dynamic_si, expression_store, etc.)
            
        Returns:
            The processed sentence
        """
        pass


class Pipeline:
    """Ordered sequence of named, reusable processing steps.
    
    Steps are executed in the order they were added. Each step
    receives the output of the previous step and the shared context.
    """
    
    def __init__(self):
        self._steps: List[Tuple[str, ProcessingStep]] = []
    
    def add_step(self, name: str, step: ProcessingStep) -> 'Pipeline':
        """Add a named step to the pipeline.
        
        Args:
            name: Human-readable name for the step (for debugging/logging)
            step: The processing step instance
            
        Returns:
            self (for chaining)
        """
        self._steps.append((name, step))
        return self
    
    def run(self, sent: str, context: ProcessingContext) -> str:
        """Execute all steps in order.
        
        Args:
            sent: The initial sentence
            context: Shared mutable state
            
        Returns:
            The final processed sentence
        """
        for name, step in self._steps:
            sent = step.process(sent, context)
        return sent
    
    @property
    def step_names(self) -> List[str]:
        """Return the names of all steps in order."""
        return [name for name, _ in self._steps]
