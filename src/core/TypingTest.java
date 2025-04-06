package core;

import java.util.Scanner;
import enums.DifficultyLevel;
import exceptions.InvalidInputException;
import models.*;
import services.*;
import analysis.*;

public class TypingTest {
    private WordGenerator wordGenerator;
    private Timer timer;
    private User currentUser;
    private ScoringStrategy scoringStrategy;
    private DifficultyManager difficultyManager;
    private TestAnalyzer analyzer;
    private Scanner scanner;
    
    public TypingTest() {
        this.wordGenerator = new WordGenerator();
        this.timer = new Timer();
        this.scoringStrategy = new StandardScoring();
        this.difficultyManager = new DifficultyManager(wordGenerator);
        this.analyzer = new StandardAnalyzer();
        this.scanner = new Scanner(System.in);
    }
    
    public void setDifficulty(DifficultyLevel level) {
        difficultyManager.setDifficulty(level);
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public void runTest() {
        if (currentUser == null) {
            System.out.println("No user set. Please create a user first.");
            return;
        }
        
        String testSentence = wordGenerator.getRandomSentence();
        
        // Display formatting
        System.out.println("\n================================================");
        System.out.println("Type the following sentence:");
        System.out.println("------------------------------------------------");
        System.out.println(testSentence);
        System.out.println("------------------------------------------------\n");
        System.out.println("Press Enter when ready to start typing...");
        scanner.nextLine(); // Wait for Enter key
        
        System.out.print("Start typing: ");
        timer.start();
        
        // Get user input after they've finished typing
        String userInput = scanner.nextLine();
        timer.stop();
        
        // Calculate prompt length to align analysis output
        String prompt = "Start typing: ";
        
        // Perform analysis after typing is complete
        System.out.println("\nAnalysis:");
        String analysisResult = analyzer.analyzeRealTime(testSentence, userInput);
        
        // If not "Perfect!", show legend and print with the correct alignment
        if (!analysisResult.equals("Perfect!")) {
            System.out.print(" ".repeat(prompt.length())); // Add spaces to align with the prompt
            System.out.println(analysisResult);
            System.out.println("Legend: (space) = correct, ^ = error, ~ = missing letter, # = extra letter");
        } else {
            System.out.println(analysisResult);
        }
        
        double timeElapsed = timer.getElapsedTimeInSeconds();
        double wpm = scoringStrategy.calculateWPM(testSentence, timeElapsed);
        double accuracy = scoringStrategy.calculateAccuracy(testSentence, userInput);
        
        // Create test result and add to user history
        TestResult result = new TestResult(wpm, accuracy, timeElapsed, testSentence, userInput, difficultyManager.getCurrentDifficulty());
        currentUser.addTestResult(result);
        
        // Results display
        System.out.println("\n================================================");
        System.out.println("Results:");
        System.out.println("------------------------------------------------");
        System.out.println("Time taken: " + timeElapsed + " seconds");
        System.out.println("WPM: " + wpm);
        System.out.println("Accuracy: " + accuracy + "%");
        System.out.println("Average WPM: " + currentUser.getAverageWPM());
        System.out.println("Average Accuracy: " + currentUser.getAverageAccuracy() + "%");
        System.out.println("================================================");
    }
    
    public void showPerformanceReport() {
        if (currentUser == null || currentUser.getHistory().isEmpty()) {
            System.out.println("No test results available for report generation.");
            return;
        }
        
        System.out.println(analyzer.generatePerformanceReport(currentUser));
    }
    
    public static void main(String[] args) {
        System.out.println("Welcome to the Typing Speed Tester!");
        Scanner scanner = new Scanner(System.in);
        TypingTest test = new TypingTest();
        
        try {
            // Create user with error handling for username
            String username = "";
            boolean validUsername = false;
            while (!validUsername) {
                System.out.print("Enter your username: ");
                username = scanner.nextLine().trim();
                
                if (username.isEmpty()) {
                    System.out.println("Username cannot be empty. Please try again.");
                } else if (!username.matches("^[a-zA-Z]+$")) {
                    System.out.println("Username must contain only letters. Please try again.");
                } else {
                    validUsername = true;
                }
            }
            
            User user = new User(username);
            test.setCurrentUser(user);
            
            // Optional: Choose difficulty with error handling
            System.out.println("\nSelect difficulty level:");
            System.out.println("1. Easy");
            System.out.println("2. Medium (default)");
            System.out.println("3. Hard");
            
            boolean validDifficulty = false;
            while (!validDifficulty) {
                System.out.print("Your choice (1-3): ");
                String difficultyChoice = scanner.nextLine().trim();
                
                switch (difficultyChoice) {
                    case "1":
                        test.setDifficulty(DifficultyLevel.EASY);
                        validDifficulty = true;
                        break;
                    case "2":
                    case "":  // Empty input defaults to medium
                        test.setDifficulty(DifficultyLevel.MEDIUM);
                        validDifficulty = true;
                        break;
                    case "3":
                        test.setDifficulty(DifficultyLevel.HARD);
                        validDifficulty = true;
                        break;
                    default:
                        System.out.println("Invalid selection. Please enter 1, 2, or 3.");
                }
            }
            
            // Main test loop
            boolean continueTest;
            do {
                // Run the typing test
                test.runTest();
                
                // Only offer performance report option if there are at least 2 results
                if (user.getHistory().size() >= 2) {
                    boolean validChoice = false;
                    while (!validChoice) {
                        System.out.print("\nWould you like to see a performance report? (y/n): ");
                        String reportChoice = scanner.nextLine().trim().toLowerCase();
                        
                        if (reportChoice.equals("y") || reportChoice.equals("yes")) {
                            test.showPerformanceReport();
                            validChoice = true;
                        } else if (reportChoice.equals("n") || reportChoice.equals("no")) {
                            validChoice = true;
                        } else {
                            System.out.println("Invalid input. Please enter 'y' or 'n'.");
                        }
                    }
                }
                
                // Ask if user wants to try again with error handling
                boolean validChoice = false;
                continueTest = false;
                
                while (!validChoice) {
                    System.out.print("\nWould you like to try again? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    
                    if (choice.equals("y") || choice.equals("yes")) {
                        continueTest = true;
                        validChoice = true;
                    } else if (choice.equals("n") || choice.equals("no")) {
                        continueTest = false;
                        validChoice = true;
                    } else {
                        System.out.println("Invalid input. Please enter 'y' or 'n'.");
                    }
                }
            } while (continueTest);
            
            // Show final report
            if (user.getHistory().size() > 0) {
                System.out.println("\nFinal Performance Report:");
                test.showPerformanceReport();
            }
            
            System.out.println("Thank you for using the Typing Speed Tester!");
            
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
