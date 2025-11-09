# Import necessary libraries
import pandas as pd                      # For handling tabular data
from sklearn.datasets import load_iris   # For loading the Iris dataset
from sklearn.cluster import KMeans       # For applying K-Means clustering
import matplotlib.pyplot as plt          # For plotting the clusters


# Step 1: Load the Iris dataset
iris = load_iris()

# Step 2: Convert the data into a DataFrame for easier handling
data = pd.DataFrame(iris.data, columns=iris.feature_names)

# Step 3: Apply K-Means clustering with 3 clusters
# We choose 3 because the Iris dataset has 3 species
kmeans = KMeans(n_clusters=3, random_state=42)

# Step 4: Fit the model and assign cluster labels to each data point
data['Cluster'] = kmeans.fit_predict(data)

# Step 5: Visualize the clusters using Sepal Length and Sepal Width
plt.figure(figsize=(6, 6))  # Set the plot size
plt.scatter(
    data['sepal length (cm)'],
    data['sepal width (cm)'],
    c=data['Cluster'],
    cmap='viridis',
    s=50
)

# Step 6: Add plot details
plt.title('K-Means Clustering of Iris Data')
plt.xlabel('Sepal Length')
plt.ylabel('Sepal Width')
plt.grid(True)
plt.show()
