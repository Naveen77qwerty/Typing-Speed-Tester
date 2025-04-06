package models;

import java.time.LocalDateTime;
import enums.DifficultyLevel;

public class TestResult {
    private double wpm;
    private double accuracy;
    private double timeInSeconds;
    private LocalDateTime timestamp;
    private String originalText;
    private String typedText;
    private DifficultyLevel difficultyLevel;
    
    public TestResult(double wpm, double accuracy, double timeInSeconds, 
                     String originalText, String typedText, DifficultyLevel difficultyLevel) {
        this.wpm = wpm;
        this.accuracy = accuracy;
        this.timeInSeconds = timeInSeconds;
        this.originalText = originalText;
        this.typedText = typedText;
        this.difficultyLevel = difficultyLevel;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters
    public double getWpm() {
        return wpm;
    }
    
    public double getAccuracy() {
        return accuracy;
    }
    
    public double getTimeInSeconds() {
        return timeInSeconds;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getOriginalText() {
        return originalText;
    }
    
    public String getTypedText() {
        return typedText;
    }
    
    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }
    
    @Override
    public String toString() {
        return String.format("Test Result [WPM: %.2f, Accuracy: %.2f%%, Time: %.2f seconds, Difficulty: %s]", 
                            wpm, accuracy, timeInSeconds, difficultyLevel);
    }
} 