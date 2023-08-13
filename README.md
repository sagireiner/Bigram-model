# Bigram-model
The Bigram Model is a Java program that implements a language model based on bigram probabilities. This model is commonly used in natural language processing to predict the next word in a sequence of words based on the previous word. It's a simple yet effective way to capture some aspects of the language's structure and relationships between words.

A bigram model is a probabilistic language model that calculates the probability of a word occurring given its preceding word. It simplifies language modeling by considering only the most recent word in a sequence, assuming that the occurrence of a word depends only on the previous word. While this is a basic assumption, bigram models can provide reasonable approximations for various language-related tasks, such as text generation, completion, and analysis.

In this implementation, the model's vocabulary and bigram probabilities are built from a text corpus. The model uses counts of word pairs (bigrams) to estimate the likelihood of a word following another word. The bigram probabilities enable the model to assess the coherence and likelihood of sentences and sequences of words. Additionally, cosine similarity is employed to measure the similarity between words based on their co-occurrence patterns in the text corpus.

Overall, the Bigram Model offers a simple yet versatile approach to understanding language patterns and relationships, making it a fundamental tool in natural language processing and computational linguistics.

# Features

Initialization: Initialize the language model by providing a text corpus file. The model builds a vocabulary index and calculates bigram probabilities to be used for various tasks.
Word Indexing: Map words to their corresponding indices in the vocabulary, enabling efficient access and manipulation of vocabulary-related data.
Bigram Counts: Count the occurrences of word pairs (bigrams) in the text corpus. These counts provide insights into the frequency of word pairs appearing together.
Bigram Probabilities: Calculate bigram probabilities for word pairs based on their counts. These probabilities indicate the likelihood of a word appearing after another word, allowing the model to make predictions.
Sentence Probability: Compute the probability of a sentence using bigram probabilities. This is useful for evaluating the likelihood of a given sentence in the context of the model.
Most Frequent Proceeding Word: Identify the most frequent word that follows a given word. This feature can help predict the next word in a sequence.
Legal Sentence Check: Validate whether a sentence is probable according to the model. This helps filter out sentences that are unlikely to occur based on the bigram probabilities.
Cosine Similarity: Calculate the cosine similarity between two word vectors. This metric assesses the similarity between words based on their occurrence patterns.
Closest Word: Find the word most similar to a given word using cosine similarity. This feature helps identify words with similar contextual usage.
Model Saving and Loading: Save and load the model's vocabulary, bigram counts, and probabilities to/from files. This facilitates reusability and prevents the need for retraining the model.

Initialization: Create an instance of BigramModel and initialize it using a text corpus file. This initializes the model's vocabulary and bigram probabilities for analysis.
Calculate Probability: Estimate the probability of a sentence using bigram probabilities. This can help assess the likelihood of a sentence occurring in the given text corpus.
Most Frequent Proceeding Word: Determine the most frequently occurring word following a given word. This is valuable for word prediction tasks.
Cosine Similarity: Compute the cosine similarity between two word vectors. Use this to quantify the similarity of words based on their contextual usage.
Closest Word: Identify the word most similar to a given word using cosine similarity. This can be used to suggest synonyms or related words.
Save and Load Model: Save and load the model's data to/from files. This facilitates model persistence and sharing.
Clear Model: Clear the model's data when it's no longer needed. This helps free up memory resources.
