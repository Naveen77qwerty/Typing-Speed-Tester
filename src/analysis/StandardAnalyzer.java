package analysis;

import models.TestResult;
import models.User;
import enums.DifficultyLevel;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.Comparator;
import java.util.stream.Collectors;

public class StandardAnalyzer extends TestAnalyzer {
    private static final int RECENT_TESTS_COUNT = 5;//max limit of recent tests to show in report is 5
    /**
     * Generates a comprehensive performance report for a user based on their test history.
     * The report includes overall statistics, best performance, common errors, and recent tests.
     * The user for whom to generate the report
     * return A formatted performance report as a string
     */
    @Override
    public String generatePerformanceReport(User user) {
        List<TestResult> allResults = user.getHistory();  //method call form user class to get all test results to check for history
        if (allResults.isEmpty()) {
            return "No test history available for " + user.getUsername();
        }
        
        StringBuilder report = new StringBuilder();  
        /*java.lang.StringBuilder is used to create a mutable string
         which is more efficient than using String concatenation in a loop.
         so this is used ot get the performance result when asked for */
        report.append("Performance Report for ").append(user.getUsername()).append("\n");
        report.append("=========================================\n");
        
        // Sort results by timestamp
        List<TestResult> sortedResults = allResults.stream()
                .sorted(Comparator.comparing(TestResult::getTimestamp))
                .collect(Collectors.toList());
        
        // Calculate metrics
        double improvementRate = calculateImprovement(sortedResults);
        Map<Character, Integer> errorMap = findMostCommonErrors(sortedResults);
        
        // Get most recent difficulty level using the helper method
        DifficultyLevel currentDifficulty = getMostRecentDifficultyLevel(sortedResults);
        
        // Overall stats
        report.append("Overall Statistics:\n");
        report.append(String.format("Total Tests: %d\n", allResults.size()));
        report.append(String.format("Current Difficulty: %s\n", currentDifficulty));
        report.append(String.format("Average WPM: %.2f\n", user.getAverageWPM()));
        report.append(String.format("Average Accuracy: %.2f%%\n", user.getAverageAccuracy()));
        report.append(String.format("Improvement Rate: %.2f%%\n", improvementRate));
        
        // Get best performance
        TestResult bestResult = user.getBestResult();
        if (bestResult != null) {
            report.append("\nBest Performance:\n");
            report.append(String.format("WPM: %.2f\n", bestResult.getWpm()));
            report.append(String.format("Accuracy: %.2f%%\n", bestResult.getAccuracy()));
            report.append(String.format("Date: %s\n", bestResult.getTimestamp().toLocalDate()));
        }
        
        // Most common errors
        if (!errorMap.isEmpty()) {
            report.append("\nMost Common Errors:\n");
            errorMap.entrySet().stream()
                    .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                    .limit(5)
                    .forEach(entry -> report.append(String.format("'%c': %d times\n", entry.getKey(), entry.getValue())));
        }
        
        // Recent tests
        report.append("\nRecent Tests:\n");
        sortedResults.stream()
                .sorted(Comparator.comparing(TestResult::getTimestamp).reversed())
                .limit(RECENT_TESTS_COUNT)
                .forEach(result -> report.append(String.format(
                        "Time: %s, WPM: %.2f, Accuracy: %.2f%%\n",
                        result.getTimestamp().toLocalTime(),
                        result.getWpm(),
                        result.getAccuracy())));
        
        report.append("\n=========================================\n");
        return report.toString();
    }
    
    /**
     * Analyzes typed text in real-time by comparing it with the original text.
     * Returns "Perfect!" if the texts match exactly. Otherwise, produces a visual
     * representation of errors using symbols:
     *   - (space): correct character
     *   - ^: error (incorrect character)
     *   - ~: missing letter
     *   - #: extra letter
     *  The original text that should be typed
     *  The text that was actually typed by the user
     *  Analysis result as a string
     */
    @Override
    public String analyzeRealTime(String original, String typed) {
        // Check if the texts match exactly
        if (original.equals(typed)) {
            return "Perfect!";
        }
        
        StringBuilder analysis = new StringBuilder();
        
        String[] originalWords = original.split("\\s+");
        String[] typedWords = typed.split("\\s+");
        
        // Check if typed sentence has the same number of words as original
        if (typedWords.length == originalWords.length) {
            for (int i = 0; i < originalWords.length; i++) {
                // Compare words of same length
                if (originalWords[i].length() == typedWords[i].length()) {
                    for (int j = 0; j < originalWords[i].length(); j++) {
                        //checking for correct charater

                        /*if wrong retrun ^
                        * else nothing 
                        */
                        if (originalWords[i].charAt(j) == typedWords[i].charAt(j)) { 
                            analysis.append(" ");
                        } else {
                            analysis.append("^");
                        }
                    }
                }
                // Original word is longer (missing letters)
                else if (originalWords[i].length() > typedWords[i].length()) {
                    for (int j = 0; j < typedWords[i].length(); j++) {
                        if (originalWords[i].charAt(j) == typedWords[i].charAt(j)) {
                            analysis.append(" ");
                        } else {
                            analysis.append("^");
                        }
                    }
                    
                    // Mark missing letters
                    for (int j = 0; j < (originalWords[i].length() - typedWords[i].length()); j++) {
                        analysis.append("~");
                    }
                }
                // Typed word is longer (unnecessary letters)
                else {
                    for (int j = 0; j < originalWords[i].length(); j++) {
                        if (originalWords[i].charAt(j) == typedWords[i].charAt(j)) {
                            analysis.append(" ");
                        } else {
                            analysis.append("^");
                        }
                    }
                    
                    // Mark extra letters typed will return #
                    for (int j = 0; j < (typedWords[i].length() - originalWords[i].length()); j++) {
                        analysis.append("#");
                    }
                }
                
                analysis.append(" ");
            }
        }
        // Different number of words
        else if (typedWords.length < originalWords.length) {
            // Analyze the words that exist
            for (int i = 0; i < typedWords.length; i++) {
                for (int j = 0; j < Math.min(originalWords[i].length(), typedWords[i].length()); j++) {
                    if (originalWords[i].charAt(j) == typedWords[i].charAt(j)) {
                        analysis.append(" ");
                    } else {
                        analysis.append("^");
                    }
                }
                
                // If original word is longer
                if (originalWords[i].length() > typedWords[i].length()) {
                    for (int j = 0; j < (originalWords[i].length() - typedWords[i].length()); j++) {
                        analysis.append("~");
                    }
                }
                // If typed word is longer
                else if (typedWords[i].length() > originalWords[i].length()) {
                    for (int j = 0; j < (typedWords[i].length() - originalWords[i].length()); j++) {
                        analysis.append("#");
                    }
                }
                
                analysis.append(" ");
            }
            
            // Mark missing words
            for (int i = typedWords.length; i < originalWords.length; i++) {
                for (int j = 0; j < originalWords[i].length(); j++) {
                    analysis.append("~");
                }
                analysis.append(" ");
            }
        }
        // More typed words than original
        else {
            // Analyze the words that match
            for (int i = 0; i < originalWords.length; i++) {
                for (int j = 0; j < Math.min(originalWords[i].length(), typedWords[i].length()); j++) {
                    if (originalWords[i].charAt(j) == typedWords[i].charAt(j)) {
                        analysis.append(" ");
                    } else {
                        analysis.append("^");
                    }
                }
                
                // If original word is longer
                if (originalWords[i].length() > typedWords[i].length()) {
                    for (int j = 0; j < (originalWords[i].length() - typedWords[i].length()); j++) {
                        analysis.append("~");
                    }
                }
                // If typed word is longer
                else if (typedWords[i].length() > originalWords[i].length()) {
                    for (int j = 0; j < (typedWords[i].length() - originalWords[i].length()); j++) {
                        analysis.append("#");
                    }
                }
                
                analysis.append(" ");
            }
            
            // Mark extra words
            for (int i = originalWords.length; i < typedWords.length; i++) {
                for (int j = 0; j < typedWords[i].length(); j++) {
                    analysis.append("#");
                }
                analysis.append(" ");
            }
        }
        
        return analysis.toString();
    }
    
    /**
     * Identifies the most common typing errors by analyzing test results.
     * For each character that was typed incorrectly, the method counts the frequency
     * of that error across all test results.
     * 
     * @param results List of test results to analyze
     * @return Map containing characters and the frequency of errors for each
     */
    public Map<Character, Integer> findMostCommonErrors(List<TestResult> results) {
        Map<Character, Integer> errorFrequency = new HashMap<>();
        
        for (TestResult result : results) {
            String original = result.getOriginalText();
            String typed = result.getTypedText();
            
            int minLength = Math.min(original.length(), typed.length());
            for (int i = 0; i < minLength; i++) {
                if (original.charAt(i) != typed.charAt(i)) {
                    char expectedChar = original.charAt(i);
                    errorFrequency.put(expectedChar, 
                        errorFrequency.getOrDefault(expectedChar, 0) + 1);
                }
            }
        }
        
        return errorFrequency;
    }
} 