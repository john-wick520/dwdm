import java.util.*;
import java.io.*;

public class SimulatedDatasetGeneratorARFF {
    public static void main(String[] args) {
        int numInstances = 100;
        String[] categories = {"Red", "Green", "Blue"};

        Set<String> uniqueInstances = new HashSet<>();
        Random rand = new Random();

        try (PrintWriter writer = new PrintWriter(new File("simulated_dataset.arff"))) {
            // ARFF Header
            writer.println("@RELATION simulated_data");
            writer.println("@ATTRIBUTE ID STRING");
            writer.println("@ATTRIBUTE Feature1 NUMERIC");
            writer.println("@ATTRIBUTE Feature2 NUMERIC");
            writer.println("@ATTRIBUTE Label {" + String.join(",", categories) + "}");
            writer.println();
            writer.println("@DATA");

            int count = 0;
            while (count < numInstances) {
                String id = "ID" + (count + 1);
                double feature1 = Math.round(rand.nextDouble() * 1000.0) / 10.0;
                double feature2 = Math.round(rand.nextDouble() * 1000.0) / 10.0;
                String label = categories[rand.nextInt(categories.length)];

                String instance = "\"" + id + "\"," + feature1 + "," + feature2 + "," + label;

                if (!uniqueInstances.contains(instance)) {
                    uniqueInstances.add(instance);
                    writer.println(instance);
                    count++;
                }
            }

            System.out.println("ARFF dataset created with " + numInstances + " unique instances.");
        } catch (IOException e) {
            System.err.println("Error writing ARFF file: " + e.getMessage());
        }
    }
}
