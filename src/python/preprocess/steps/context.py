"""
Context processing pipeline step.

Wraps the ContextProcessor to integrate it into the pipeline.
"""

from ..pipeline import ProcessingContext, ProcessingStep
from ..contextprocess import ContextProcessor


class ContextStep(ProcessingStep):
    """Wraps ContextProcessor.run() as a pipeline step.
    
    The ContextProcessor handles synonym replacement (via AltRuleEngine),
    parameter syntax processing, and symbol normalization.
    """
    
    def __init__(self):
        self._processor = None
    
    def _get_processor(self) -> ContextProcessor:
        """Get or create the ContextProcessor instance."""
        if self._processor is None:
            self._processor = ContextProcessor()
        return self._processor
    
    def process(self, sent: str, context: ProcessingContext) -> str:
        """Run the context processor on the sentence.
        
        Args:
            sent: The sentence to process
            context: Processing context (not modified by this step directly)
            
        Returns:
            The processed sentence
        """
        cp = self._get_processor()
        return cp.run(sent)
