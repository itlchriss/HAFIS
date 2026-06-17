#!/bin/bash
cd /mnt/e/Post_Phd_work/Software/HAFIS
grep -ohP '_\w+\{[^}]+\}' test/MBPP-DFY-50/tmp/*/tmp/*.mr | sort -u
