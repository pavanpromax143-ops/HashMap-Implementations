import java.util.*;

public class solution {

    // n-gram -> documents containing it
    static HashMap<String, Set<String>> ngramIndex = new HashMap<>();

    static int N = 5; // 5-gram

    // Generate n-grams
    public static List<String> generateNgrams(String text) {
        String[] words = text.split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder gram = new StringBuilder();
            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }
            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }

    // Store document in index
    public static void addDocument(String docName, String text) {
        List<String> grams = generateNgrams(text);

        for (String gram : grams) {
            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docName);
        }
    }

    // Analyze document for plagiarism
    public static void analyzeDocument(String docName, String text) {

        List<String> grams = generateNgrams(text);
        System.out.println("Extracted " + grams.size() + " n-grams");

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : grams) {

            if (ngramIndex.containsKey(gram)) {

                for (String doc : ngramIndex.get(gram)) {
                    matchCount.put(doc, matchCount.getOrDefault(doc, 0) + 1);
                }

            }
        }

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);
            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Found " + matches + " matching n-grams with \"" + doc + "\"");

            System.out.printf("Similarity: %.1f%% ", similarity);

            if (similarity > 50)
                System.out.println("(PLAGIARISM DETECTED)");
            else if (similarity > 10)
                System.out.println("(suspicious)");
            else
                System.out.println("(safe)");
        }
    }

    public static void main(String[] args) {

        // existing documents
        String essay1 = "machine learning improves systems by learning from data automatically";
        String essay2 = "learning from data automatically improves machine intelligence systems";

        addDocument("essay_089.txt", essay1);
        addDocument("essay_092.txt", essay2);

        // new essay
        String newEssay = "machine learning improves systems by learning from data automatically and helps automation";

        analyzeDocument("essay_123.txt", newEssay);
    }
}