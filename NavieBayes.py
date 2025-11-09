# Import necessary libraries
from sklearn.datasets import load_iris                      # Loads the Iris dataset
from sklearn.model_selection import train_test_split        # Splits data into training and testing sets
from sklearn.naive_bayes import GaussianNB                  # Imports Gaussian Naive Bayes classifier
from sklearn.metrics import accuracy_score, classification_report  # For evaluating model performance

# Step 1: Load the Iris dataset
iris = load_iris()              # iris.data contains features, iris.target contains labels

# Step 2: Separate features and labels
X = iris.data                   # Features: sepal length, sepal width, petal length, petal width
y = iris.target                 # Labels: 0 = setosa, 1 = versicolor, 2 = virginica

# Step 3: Split the dataset into training and testing sets
# 70% training, 30% testing; random_state ensures reproducibility
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Step 4: Create and train the Naive Bayes model
model = GaussianNB()            # Initialize Gaussian Naive Bayes classifier
model.fit(X_train, y_train)     # Train the model using training data

# Step 5: Make predictions on the test set
y_pred = model.predict(X_test) # Predict labels for test data

# Step 6: Evaluate the model
#(y_pred,y_test) ❌       (y_test, y_pred)✔️
print("Accuracy:", accuracy_score(y_test, y_pred))          # Print accuracy score
print("\nClassification Report:\n", classification_report(y_test, y_pred))  # Detailed performance report
