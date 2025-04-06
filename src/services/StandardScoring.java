package services;

// services/StandardScoring.java
public class StandardScoring implements ScoringStrategy {
    @Override
    public double calculateWPM(String text, double time) {
        int words = text.split("\\s+").length;
        return (words / time) * 60;
    }
    
    @Override
    public double calculateAccuracy(String original, String typed) {
        int matches = 0;
        int totalChars = Math.max(original.length(), typed.length());
        
        // Count matching characters
        for (int i = 0; i < Math.min(original.length(), typed.length()); i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matches++;
            }
        }
        
        // If typed text is longer than original, penalize extra characters
        if (typed.length() > original.length()) {
            matches -= (typed.length() - original.length());
        }
        
        // Prevent negative accuracy
        matches = Math.max(0, matches);
        
        return (double) matches / totalChars * 100.0;
    }
}

// services/AdvancedScoring.java
