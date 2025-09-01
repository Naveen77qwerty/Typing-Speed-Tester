package services;

public class AdvancedScoring implements ScoringStrategy{
    @Override
    public double calculateWPM(String text, double timeInSeconds) {
        int wordCount = text.split("\\s+").length;
        return (wordCount / timeInSeconds) * 60.0;
    }
    

    public double calculateAccuracy(String original, String typed) {
        double deductionPerIncorrectLetter = 15.0; // Deduct 15% per incorrect letter
        double maxScore = 100.0;

        int incorrectLetters = countIncorrectLetters(original, typed);

        double totalDeduction = incorrectLetters * deductionPerIncorrectLetter;
        double finalScore = maxScore - totalDeduction;

        // Ensure the score is within 0 to 100
        return Math.max(0, Math.min(finalScore, maxScore));
    }

    private int countIncorrectLetters(String original, String typed) {
        int incorrectCount = 0;
        int minLength = Math.min(original.length(), typed.length());

        for (int i = 0; i < minLength; i++) {
            if (original.charAt(i) != typed.charAt(i)) {
                incorrectCount++;
            }
        }

        // Count remaining characters in the longer string as incorrect
        incorrectCount += Math.abs(original.length() - typed.length());

        return incorrectCount;
    }
}


