import matplotlib.pyplot as plt
from collections import Counter

# Sample transaction data
transactions = [
    {"milk", "bread"},
    {"milk", "diaper"},
    {"milk","bread", "diaper"},
    {"milk", "bread"}
]

# Flatten items
# Flattening is the process of taking a list of lists (or any nested structure) and turning it into a single list that contains all the elements.
all_items = []
for transaction in transactions:
    for item in transaction:
        all_items.append(item)
# After all_items = ['milk', 'bread', 'bread', 'butter', 'milk', 'eggs']

# count frequencies
# creates a (Key,Value) pairs , Key = item_name and Value = frequency_count
item_counts = Counter(all_items)




# 1️⃣ Bar Chart: Frequency of each item
plt.figure(figsize=(6, 4))
plt.bar(item_counts.keys(), item_counts.values(),color="skyblue")
plt.title("Item Frequency Bar Chart")
plt.xlabel("Items")
plt.ylabel("Count")
plt.show()


# 2️⃣ Pie Chart: Proportion of each item
plt.figure(figsize=(6, 6))

# Syntax : plt.pie(x, labels=None, autopct=None, startangle=0)
plt.pie(item_counts.values(), labels=item_counts.keys(), autopct='%1.1f%%', startangle=90)  #autopct - to format String for displaying percentage
plt.title("Item Distribution Pie Chart")
plt.show()


# 3️⃣ Histogram: Number of items per transaction
item_per_transaction = [len(t) for t in transactions]
plt.figure(figsize=(6, 4))
plt.hist(item_per_transaction, bins=[1, 2, 3, 4], edgecolor='black', color='lightgreen')
plt.title("Histogram of Items per Transaction")
plt.xlabel("Number of Items(Bins)")
plt.ylabel("Frequency")
plt.show()


# 4️⃣ Box Plot: Spread of item counts per transaction
plt.figure(figsize=(6, 4))
plt.boxplot(item_per_transaction, vert=False)
plt.title("Box Plot of Items per Transaction")
plt.xlabel("Number of Items")
plt.show()
