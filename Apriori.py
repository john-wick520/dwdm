# Import necessary libraries
import pandas as pd
from mlxtend.preprocessing import TransactionEncoder
from mlxtend.frequent_patterns import apriori, association_rules

# Step 1: Define the transaction data
# Each sublist represents items bought together in one transaction
transactions = [
    ["milk", 'bread', 'eggs'],
    ['milk', 'bread'],
    ['milk', 'eggs'],
    ['bread', 'eggs'],
    ['milk', 'bread', 'eggs', 'butter'],
    ['bread', 'butter']
]

# #These transactions are used in exp-12, to compare that values we are just verifying using this code.
# transactions = [
#     ["milk", "bread"],
#     ["milk", "diaper"],
#     ["bread", "diaper"],
#     ["milk", "bread", "diaper"]
# ]

# Step 2: Encode the transaction data into a format suitable for Apriori
# TransactionEncoder converts the list of lists into a boolean DataFrame
te = TransactionEncoder()
te_ary = te.fit(transactions).transform(transactions)

# Step 3: Create a DataFrame from the encoded array
# Each column is an item, each row is a transaction, values are True/False
df = pd.DataFrame(te_ary, columns=te.columns_)

# Step 4: Apply the Apriori algorithm
# min_support=0.5 means itemsets must appear in at least 50% of transactions
frequent_itemsets = apriori(df, min_support=0.5, use_colnames=True)

# Step 5: Generate association rules from the frequent itemsets
# metric="confidence" means we filter rules based on confidence level
# min_threshold=0.7 means we only keep rules with confidence ≥ 70%
rules = association_rules(frequent_itemsets, metric="confidence", min_threshold=0.7) #0.6 for exp-12 example

# Step 6: Display the results
print("Frequent Itemsets:")
print(frequent_itemsets)

print("\nAssociation Rules:")
# Show key metrics: antecedents, consequents, support, confidence, lift
print(rules[["antecedents", 'consequents', 'support', 'confidence', 'lift']])
