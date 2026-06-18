"""
Preprocessing engine - orchestrates the text processing pipeline.

The runengine() function builds and runs a pipeline of processing steps
that transform natural language requirement statements into a normalized
form suitable for CCG parsing and SI generation.

The pipeline replaces the previous procedural control flow with a modular
sequence of named, reusable steps.
"""

from typing import Tuple, Optional
from .pipeline import Pipeline, ProcessingContext
from .steps import (
    NormalizationStep,
    ExpressionExtractionStep,
    RepairStep,
    ContextStep,
    SIBuildingStep,
    NarrowingStep,
)
from . import mrrepairprocess


def build_default_pipeline() -> Pipeline:
    """Construct the default preprocessing pipeline.
    
    The pipeline executes steps in the following order:
    0. Narrowing - transform LLM-RNL to strict RNL (runs first)
    1. Repair (first pass) - apply syntax rules and clause repairs
    2. Context (first pass) - synonym replacement and parameter processing
    3. Normalize - clean up repeated words and text artifacts
    4. Extract expressions - pull out quoted/bracketed expressions
    5. Repair (second pass) - re-apply rules on normalized text
    6. Context (second pass) - re-apply context processing
    7. Normalize (post) - final cleanup
    8. Extract expressions (post) - catch any remaining expressions
    9. Build SI - construct final SI dictionary entries
    
    Returns:
        A configured Pipeline instance
    """
    p = Pipeline()
    p.add_step('narrowing', NarrowingStep())
    p.add_step('repair_pre', RepairStep())
    p.add_step('context', ContextStep())
    p.add_step('normalize', NormalizationStep())
    p.add_step('extract_expr', ExpressionExtractionStep())
    p.add_step('repair_main', RepairStep())
    p.add_step('context_post', ContextStep())
    p.add_step('normalize_post', NormalizationStep())
    p.add_step('extract_expr_post', ExpressionExtractionStep())
    p.add_step('build_si', SIBuildingStep())
    return p


def runengine(sent: str, t: str, si_path: Optional[str] = None) -> Tuple[str, dict]:
    """Process a natural language requirement statement.
    
    This is the main entry point for the preprocessing engine. It builds
    a pipeline and runs it on the input sentence, producing a normalized
    sentence and a dictionary of dynamic SI entries.
    
    Args:
        sent: The requirement statement in natural language
        t: The type of requirement ('requires' or 'ensures')
        si_path: Optional path to the SI specs file. If not provided,
                 uses the default path from mrrepairprocess.
        
    Returns:
        A tuple of (processed_sentence, dynamic_si_dict)
    """
    # Set SI path if provided
    if si_path:
        mrrepairprocess.set_si_path(si_path)
    
    pipeline = build_default_pipeline()
    context = ProcessingContext(requirement_type=t)
    sent = pipeline.run(sent, context)
    return sent, context.dynamic_si
