

from .context import picom

import unittest


class AdvancedTestSuite(unittest.TestCase):
    """Advanced test cases."""

    def test_thoughts(self):
        picom.hmm()


if __name__ == '__main__':
    unittest.main()