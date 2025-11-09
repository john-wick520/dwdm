import java.util.*;

public class SimpleApriori {
    private List<Set<String>> transactions;
    private double minSupport;
    private double minConfidence;

    public SimpleApriori(List<Set<String>> transactions, double minSupport, double minConfidence) {
        this.transactions = transactions;
        this.minSupport = minSupport;
        this.minConfidence = minConfidence;
    }

    public Map<Set<String>, Integer> getFrequentItemsets() {
        // Final map to store all frequent itemsets and their counts
        Map<Set<String>, Integer> frequentItemsets = new LinkedHashMap<>();

        // Step 1: Collect all unique items from the transactions
        Set<String> allItems = new HashSet<>();
        // For your example:
        // Transactions:
        // T1: [milk, bread]
        // T2: [milk, diaper]
        // T3: [bread, diaper]
        // T4: [milk, bread, diaper]
        // Output: allItems = [milk, bread, diaper]
        for (Set<String> t : transactions) allItems.addAll(t);

        // Step 2: Generate initial candidate itemsets of size 1
        List<Set<String>> candidates = new ArrayList<>();
        // Output: candidates = [[milk], [bread], [diaper]]
        for (String item : allItems) candidates.add(Set.of(item));

        // Step 3: Iteratively find frequent itemsets
        while (!candidates.isEmpty()) {
            // Map to count support of each candidate
            Map<Set<String>, Integer> counts = new HashMap<>();

            // Count how many transactions contain each candidate
            for (Set<String> candidate : candidates) {
                for (Set<String> t : transactions) {
                    if (t.containsAll(candidate)) {
                        counts.put(candidate, counts.getOrDefault(candidate, 0) + 1);
                    }
                }
            }
            // Example output after first pass:
            // counts = [[milk]:3, [bread]:3, [diaper]:3]

            candidates.clear(); // Prepare for next level

            // Filter candidates by support threshold
            //We cannot use counts.forEach loop as it requires candidates to be a final variable
            for (Map.Entry<Set<String>, Integer> entry : counts.entrySet()) {
                double support = entry.getValue() / (double) transactions.size();
                // For minSupport = 0.5 and 4 transactions, threshold = 2
                if (support >= minSupport) {
                    // Add to final frequent itemsets
                    frequentItemsets.put(entry.getKey(), entry.getValue());
                    // Add to next-level candidates
                    candidates.add(entry.getKey());
                }
            }


            // After filtering:
            // frequentItemsets = [[milk]:3, [bread]:3, [diaper]:3]
            // candidates = same as above

            // Step 4: Generate next-level candidates (size k+1)
            candidates = generateNextCandidates(candidates);
            // Output: candidates = [[milk, bread], [milk, diaper], [bread, diaper]]
        }

        // Final output:
        // frequentItemsets = 
        // [[milk]:3, [bread]:3, [diaper]:3, [milk, bread]:2, [milk, diaper]:2, [bread, diaper]:2]
        return frequentItemsets;
    }

    private List<Set<String>> generateNextCandidates(List<Set<String>> prevLevel) {
        // Use a Set to avoid duplicate candidates
        Set<Set<String>> nextLevel = new HashSet<>();

        // Join step: combine each pair of itemsets
        for (int i = 0; i < prevLevel.size(); i++) {
            for (int j = i + 1; j < prevLevel.size(); j++) {
                Set<String> union = new HashSet<>(prevLevel.get(i)); //Creates a hashSet that has elements of ith index of prevLevel
                union.addAll(prevLevel.get(j));

                // Only keep itemsets that grow by one item
                if (union.size() == prevLevel.get(i).size() + 1) {
                    nextLevel.add(union); // e.g., [milk, bread], [milk, diaper], [bread, diaper]
                }
            }
        }

        // Convert to list and return
        return new ArrayList<>(nextLevel);
    }

    public List<String> generateAssociationRules(Map<Set<String>, Integer> freqItemsets) {
        List<String> rules = new ArrayList<>();
        int total = transactions.size();

        for (Set<String> itemset : freqItemsets.keySet()) {
            if (itemset.size() < 2) continue;

            List<Set<String>> subsets = getSubsets(itemset);
            for (Set<String> antecedent : subsets) {
                Set<String> consequent = new HashSet<>(itemset);
                consequent.removeAll(antecedent);
                if (consequent.isEmpty()) continue;

                Integer itemsetCount = freqItemsets.get(itemset);
                Integer antecedentCount = freqItemsets.get(antecedent);
                Integer consequentCount = freqItemsets.get(consequent);
                if (antecedentCount == null || consequentCount == null) continue;

                double confidence = itemsetCount / (double) antecedentCount;
                double lift = confidence / (consequentCount / (double) total);

                if (confidence >= minConfidence) {
                    double support = itemsetCount / (double) total;
                    rules.add(String.format("%s -> %s (Support: %.2f, Confidence: %.2f, Lift: %.2f)",
                            antecedent, consequent, support, confidence, lift));
                }
            }
        }

        return rules;
    }

    private List<Set<String>> getSubsets(Set<String> set) {
        List<Set<String>> subsets = new ArrayList<>();
        List<String> items = new ArrayList<>(set);
        int n = items.size();

        // Generate subsets of size 1 to n-1 (proper subsets) -- excludes emptyset and fullset
        for (int size = 1; size < n; size++) {
            generateCombinations(items, size, 0, new LinkedList<>(), subsets);
        }

        return subsets;

    }

    private void generateCombinations(List<String> items, int size, int index,
                                  LinkedList<String> current, List<Set<String>> result) {
        if (current.size() == size) {
            result.add(new HashSet<>(current));
            return;
        }

        for (int i = index; i < items.size(); i++) {
            current.add(items.get(i));  // Choose item
            generateCombinations(items, size, i + 1, current, result); // Recurse
            current.removeLast(); // Backtrack

        }
    }
    /**
     * Generates all proper subsets (excluding empty set and full set) of a given Set<String>.
     *
     * Logic:
     * - Convert the input Set to a List for index-based access.
     * - For each subset size from 1 to n-1:
     *     - Use recursive backtracking to generate all combinations of that size.
     *     - Store each valid subset in the result list.
     *
     * Example Trace: Input Set = {"milk", "bread", "diaper"}
     *
     * Generating subsets of size 1:
     * - Start with empty list []
     * - Add "milk" → ["milk"] → ✅ Save this
     *   - Backtrack: remove "milk"
     * - Add "bread" → ["bread"] → ✅ Save this
     *   - Backtrack: remove "bread"
     * - Add "diaper" → ["diaper"] → ✅ Save this
     *   - Backtrack: remove "diaper"
     *
     * Final subsets of size 1: [milk], [bread], [diaper]
     *
     * Generating subsets of size 2:
     * - Start with empty list []
     * - Add "milk" → ["milk"]
     *   - Add "bread" → ["milk", "bread"] → ✅ Save this
     *   - Backtrack: remove "bread" → ["milk"]
     *   - Add "diaper" → ["milk", "diaper"] → ✅ Save this
     *   - Backtrack: remove "diaper", remove "milk"
     * - Add "bread" → ["bread"]
     *   - Add "diaper" → ["bread", "diaper"] → ✅ Save this
     *   - Backtrack: remove "diaper", remove "bread"
     *
     * Final subsets of size 2: [milk, bread], [milk, diaper], [bread, diaper]
     *
     * Note:
     * - These subsets are useful for association rule mining (e.g., Apriori algorithm).
     * - This method can be applied to each transaction to extract candidate itemsets.
     */


    public static void main(String[] args) {
        List<Set<String>> transactions = Arrays.asList(
            Set.of("milk", "bread"),
            Set.of("milk", "diaper"),
            Set.of("bread", "diaper"),
            Set.of("milk", "bread", "diaper")
        );


        //EXP - 9 Example
        // List<Set<String>> transactions = Arrays.asList(
        //     Set.of("milk", "bread", "eggs"),
        //     Set.of("milk", "bread"),
        //     Set.of("milk", "eggs"),
        //     Set.of("bread", "eggs"),
        //     Set.of("milk", "bread", "eggs", "butter"),
        //     Set.of("bread", "butter")
        // );

        SimpleApriori apriori = new SimpleApriori(transactions, 0.5, 0.6);
        Map<Set<String>, Integer> itemsets = apriori.getFrequentItemsets();

        //Set - Is nothing but current item set
        System.out.println("Frequent Itemsets:");
        itemsets.forEach((set, count) -> {
            double support = count / (double) transactions.size();
            System.out.printf("%s : %d (Support: %.2f)\n", set, count, support);
        });

        System.out.println("\nAssociation Rules:");
        List<String> rules = apriori.generateAssociationRules(itemsets);
        rules.forEach(System.out::println);
    }
}
