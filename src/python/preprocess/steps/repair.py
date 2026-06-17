"""
Repair pipeline step.

Wraps the RepairProcessor to integrate it into the pipeline.
"""

from ..pipeline import ProcessingContext, ProcessingStep
from ..mrrepairprocess import RepairProcessor


class RepairStep(ProcessingStep):
    """Wraps RepairProcessor.run() as a pipeline step.
    
    The RepairProcessor handles syntax rule application, complex clause
    processing, and various text repairs. This step creates a fresh
    RepairProcessor instance and merges its dynamic_si output into
    the shared context.
    """
    
    def __init__(self):
        self._processor = None
    
    def _get_processor(self) -> RepairProcessor:
        """Get or create the RepairProcessor instance."""
        if self._processor is None:
            self._processor = RepairProcessor()
        return self._processor
    
    def process(self, sent: str, context: ProcessingContext) -> str:
        """Run the repair processor on the sentence.
        
        Args:
            sent: The sentence to process
            context: Processing context (dynamic_si is updated)
            
        Returns:
            The repaired sentence
        """
        rp = self._get_processor()
        
        # Pass current dynamic_si from context if available
        current_dynamic_si = context.dynamic_si if context.dynamic_si else None
        
        sent = rp.run(sent, context.requirement_type, current_dynamic_si)
        
        # Merge repair processor's dynamic_si back into context
        if rp.dynamic_si:
            for key, value in rp.dynamic_si.items():
                if key not in context.dynamic_si:
                    if isinstance(value, dict):
                        context.dynamic_si[key] = value.get('interpretation', value)
                    else:
                        context.dynamic_si[key] = value
        
        return sent
