package models;

import java.util.ArrayList;
import java.util.List;
import exceptions.InvalidInputException;

public class User {
    private String username;
    private List<TestResult> history = new ArrayList<>();

    public User(String username) throws InvalidInputException {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidInputException("Username cannot be empty");
        }
        this.username = username;
    }
    
    // Getters and methods
    public String getUsername() {
        return username;
    }
    
    public List<TestResult> getHistory() {
        return new ArrayList<>(history); // Return a copy for encapsulation
    }
    
    public void addTestResult(TestResult result) {
        if (result == null)
            throw new IllegalArgumentException("Test result cannot be null");
        history.add(result);
    }
    
    // Calculate statistics
    public double getAverageWPM() {
        if (history.isEmpty()) return 0;
        
        return history.stream()
                .mapToDouble(TestResult::getWpm)
                .average()
                .orElse(0);
    }
    
    public double getAverageAccuracy() {
        if (history.isEmpty()) return 0;
        
        return history.stream()
                .mapToDouble(TestResult::getAccuracy)
                .average()
                .orElse(0);
    }
    
    public TestResult getBestResult() {
        if (history.isEmpty()) return null;
        
        return history.stream()
                .sorted((r1, r2) -> Double.compare(r2.getWpm(), r1.getWpm()))
                .findFirst()
                .orElse(null);
    }
}