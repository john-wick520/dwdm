    import java.util.*;

    public class SimpleKMeans {
        public static void main(String[] args) {
            // Step 1: Define your data points (2D)
            double[][] data = {
                {1.0, 1.0},
                {1.5, 2.0},
                {3.0, 4.0},
                {5.0, 7.0},
                {3.5, 5.0},
                {4.5, 5.0},
                {3.5, 4.5}
            };

            int k = 2; // Number of clusters
            int maxIterations = 100;
            int n = data.length;

            // Step 2: Randomly initialize centroids
            double[][] centroids = new double[k][2];
            Random rand = new Random();
            for (int i = 0; i < k; i++) {
                centroids[i] = data[rand.nextInt(n)]; // Assigning reference to the entire row (not copying values)
            }

            // labels[i] stores the index of the cluster assigned to data point i
            int[] labels = new int[n];

            // Step 3: K-Means loop
            for (int iter = 0; iter < maxIterations; iter++) {
                // Assign each point to the nearest centroid
                for (int i = 0; i < n; i++) {
                    double minDist = Double.MAX_VALUE;
                    int cluster = 0;
                    for (int j = 0; j < k; j++) {
                        double dist = distance(data[i], centroids[j]); // Passing references to 1D arrays (rows) for distance calculation
                        if (dist < minDist) {
                            minDist = dist;
                            cluster = j;
                        }
                    }
                    labels[i] = cluster;
                }

                // Update centroids
                double[][] newCentroids = new double[k][2];
                int[] counts = new int[k]; //Count of points assigned to each cluster

                //This loop is mainly used to compute the sum of each feature(e.g., x and y) in a particular cluster.
                //If we take iris, x-> sepal Length , y->Sepal Width
                for (int i = 0; i < n; i++) {
                    int cluster = labels[i];
                    newCentroids[cluster][0] += data[i][0];
                    newCentroids[cluster][1] += data[i][1];
                    counts[cluster]++;
                }
                
                //Compute the mean (new centroid) for each cluster
                for (int j = 0; j < k; j++) {
                    if (counts[j] > 0) {
                        newCentroids[j][0] /= counts[j];
                        newCentroids[j][1] /= counts[j];
                    }
                }

                centroids = newCentroids;

                //The above two loops helps to reposition the centriods of the cluster , so that the centroids lie exactly center of all assigned points.
            }

            // Step 4: Print results
            System.out.println("Cluster assignments:");
            for (int i = 0; i < n; i++) {
                System.out.printf("Point (%.1f, %.1f) -> Cluster %d%n", data[i][0], data[i][1], labels[i]);
            }

            System.out.println("\nFinal centroids:");
            for (int j = 0; j < k; j++) {
                System.out.printf("Centroid %d: (%.2f, %.2f)%n", j, centroids[j][0], centroids[j][1]);
            }
        }

        // Helper method to compute Euclidean distance
        public static double distance(double[] a, double[] b) {
            double dx = a[0] - b[0];
            double dy = a[1] - b[1];
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

/*
Why K-Means runs for multiple iterations (e.g., 100):

- K-Means is an iterative algorithm that refines cluster assignments and centroids.
- Each iteration:
    1. Assigns points to the nearest centroid.
    2. Updates centroids based on the mean of assigned points.
- Initial centroids are random, so convergence takes time.
- Multiple iterations help centroids stabilize and reduce assignment changes.
- 100 iterations is a safe upper bound to ensure convergence, especially for complex datasets.
- In practice, convergence often happens earlier, and early stopping can be used to exit sooner.
*/
