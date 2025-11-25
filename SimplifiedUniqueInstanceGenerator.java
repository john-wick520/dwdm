import java.util.*;

public class SimplifiedUniqueInstanceGenerator {
    public static void main(String[] args) {
        int numInstances = 10;
        String[] categories = {"Red", "Green", "Blue"};

        Set<String> uniqueInstances = new HashSet<>();
        Random rand = new Random();


            int count = 0;
            while (count < numInstances) {
                String id = "ID" + (count + 1);
                double feature1 = Math.round(rand.nextDouble() * 1000.0) / 10.0;
                double feature2 = Math.round(rand.nextDouble() * 1000.0) / 10.0;
                String label = categories[rand.nextInt(categories.length)];

                String instance = "[" + id + "," + feature1 + "," + feature2 + "," + label+"]";

                if (!uniqueInstances.contains(instance)) {
                    uniqueInstances.add(instance);
                    System.out.println(instance);
                    count++;
                }
            }
    }
}
