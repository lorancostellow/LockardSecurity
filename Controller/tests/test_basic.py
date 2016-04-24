# -*- coding: utf-8 -*-

from .context import picom

import unittest


class BasicTestSuite(unittest.TestCase):
    """Basic test cases."""

    def test_absolute_truth_and_meaning(self):
        print(picom.PayloadDO.BLANK_FIELD)


if __name__ == '__main__':
    unittest.main()