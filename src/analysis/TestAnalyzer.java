package analysis;

import models.TestResult;
import models.User;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import enums.DifficultyLevel;

public abstract class TestAnalyzer {
    // Abstract methods to be implemented by subclasses
    public abstract String generatePerformanceReport(User user);
    public abstract String analyzeRealTime(String original, String typed);
    public abstract Map<Character, Integer> findMostCommonErrors(List<TestResult> results);
    // Common method for all analyzers
    protected double calculateAverage(List<Double> values) {
        if (values.isEmpty()) return 0.0;
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
    
    // Calculate improvement percentage between first and last test
    protected double calculateImprovement(List<TestResult> results) {
        if (results.size() < 2) return 0.0;
        
        Collections.sort(results, (r1, r2) -> r1.getTimestamp().compareTo(r2.getTimestamp()));
        //sorting done in the basis of timestamp in ascending order
        double firstWPM = results.get(0).getWpm();//now first element is the oldest one
        double lastWPM = results.get(results.size() - 1).getWpm();//now last element is the latest one
        
        if (firstWPM == 0) return 0.0;
        return ((lastWPM - firstWPM) / firstWPM) * 100.0;
    }
    
    // Get most recent difficulty level else returns default level
    protected DifficultyLevel getMostRecentDifficultyLevel(List<TestResult> results) {
        if (results.isEmpty()) return DifficultyLevel.MEDIUM;
        return results.get(results.size() - 1).getDifficultyLevel();
    }
}
