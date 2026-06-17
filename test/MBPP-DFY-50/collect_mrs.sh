#!/bin/bash
cd /mnt/e/Post_Phd_work/Software/HAFIS
for f in $(find test/MBPP-DFY-50/tmp -name '*.mr' | sort); do
    echo "=== $f ==="
    cat "$f"
    echo
done
