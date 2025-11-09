import numpy as np
import pandas as pd
from scipy.spatial.distance import pdist, squareform

# Step 1: Define your own dataset (4 instances, 2 attributes)
data = np.array([
    [1.0, 2.0],   # Instance A
    [2.0, 3.0],   # Instance B
    [3.0, 5.0],   # Instance C
    [5.0, 8.0]    # Instance D
])

# Step 2: Compute pairwise Euclidean distances
# pdist - is used to compute pair wise distance(Math.sqrt((x2-x1)^2 - (y2-y1)^2)
# pdist is seen from one instance to all other available instances E.g: From A , We calculate dist to A,B,C and D
# In general pdist() produces a condesed_matrix(1D array),
# For better visualization and understanding, we convert it into square matrix using squareform()
# Why Eucledian ? -- It’s intuitive (straight-line distance) and works well for continuous numerical data.
dissimilarity_matrix = squareform(pdist(data, metric='euclidean'))

# Step 3: Convert to a labeled DataFrame for readability
labels = ['A', 'B', 'C', 'D']
df = pd.DataFrame(dissimilarity_matrix, index=labels, columns=labels)  #index refers to rows

# Step 4: Display the dissimilarity matrix
print("Dissimilarity Matrix (Euclidean Distance):")
print(df)
