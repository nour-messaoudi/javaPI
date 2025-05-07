package tn.esprit.test;

import tn.esprit.util.BadWordsFilter;

public class BadWordsFilterTest {
    public static void main(String[] args) {
        String[] tests = {"Hello", "damn it!", "c0n", "Ceci est normal", "mot interdit"};

        System.out.println("=== TEST DU FILTRE DE MOTS ===");
        for (String test : tests) {
            boolean result = BadWordsFilter.containsBadWords(test);
            System.out.printf("%-20s → %s%n",
                    test,
                    result ? "🔴 BLOQUÉ" : "🟢 AUTORISÉ");
        }
    }
}