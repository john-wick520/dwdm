"""
Program: Chi-Square Calculator
Description: This program calculates the chi-square value using Python.
"""

import numpy as np

def calculate_chi_square(observed, expected):
    """
    Function to calculate the chi-square value.

    Parameters:
    observed (list): List of observed values.
    expected (list): List of expected values.

    Returns:
    float: The calculated chi-square value.
    """
    observed = np.array(observed)
    expected = np.array(expected)

    chi_square = np.sum(((observed - expected) ** 2) / expected)

    return chi_square

# Example usage
observed_values = [10, 15, 20, 25]
expected_values = [12, 15, 18, 25]

chi_square_value = calculate_chi_square(observed_values, expected_values)
print(f"The chi-square value is: {chi_square_value}")
