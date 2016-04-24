# loads the context of the module

import sys
import os
PACKAGE_PARENT = '..'
DIR = os.path.abspath(PACKAGE_PARENT)
sys.path.insert(0, DIR)
print("sys path: %s" % DIR)

import picom