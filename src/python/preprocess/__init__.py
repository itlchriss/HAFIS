"""
Preprocessing package for natural language requirement statements.

Provides a modular pipeline for transforming natural language specifications
into a normalized form suitable for CCG parsing and SI generation.
"""

from .engine import runengine, build_default_pipeline
from .pipeline import Pipeline, ProcessingContext, ProcessingStep

__all__ = [
    'runengine',
    'build_default_pipeline',
    'Pipeline',
    'ProcessingContext',
    'ProcessingStep',
]
