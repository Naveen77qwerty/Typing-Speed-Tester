# Typing Speed Tester (Java Implementation)

A Java program designed to test and improve your typing speed and accuracy using longer paragraphs and realistic text samples.

## Features

- Random paragraph generation for typing practice
- Real-time typing speed calculation (WPM)
- Accurate measurement of typing precision
- Multiple scoring algorithms (Standard and Advanced)
- Progress tracking across multiple attempts
- User profiles with statistics
- Difficulty levels (Easy, Medium, Hard)
- Object-oriented design with proper encapsulation

## How to Use

1. Compile the program using the Java compiler:
   ```bash
   cd src
   javac App.java
   ```

2. Run the compiled program:
   ```bash
   java App
   ```

3. Follow the on-screen instructions:
   - Enter your username
   - Select difficulty level
   - Choose scoring method
   - A random paragraph will be displayed
   - Press Enter when you're ready to start typing
   - Type the paragraph exactly as shown
   - View your detailed results including:
     - Time taken
     - Words Per Minute (WPM)
     - Accuracy percentage
     - Average performance stats
   - Choose to try another paragraph or exit

## Project Structure

The application follows a well-structured object-oriented design:

### Packages and Classes

- **core**: Core functionality
  - `Timer`: Handles precise timing functionality.
  - `WordGenerator`: Provides random text selection based on difficulty.
  - `TypingTest`: Main coordinator class with application entry point.

- **models**: Data models
  - `User`: Stores user information and maintains history of typing tests.
  - `TestResult`: Encapsulates test results and statistics.

- **services**: Service layer
  - `ScoringStrategy`: Interface for different scoring algorithms.
  - `StandardScoring`: Basic WPM and accuracy calculation.
  - `DifficultyManager`: Manages difficulty levels and sentence generation.

- **enums**: Enumerated types
  - `DifficultyLevel`: Defines difficulty levels (EASY, MEDIUM, HARD).

- **exceptions**: Custom exceptions
  - `InvalidInputException`: For handling invalid user input.

- **analysis**: Performance analysis
  - `TestAnalyzer`: Abstract class defining analysis capabilities.
  - `StandardAnalyzer`: Generates performance reports and analyzes typing results.

## OOP Principles Applied

- **Encapsulation**: Private fields with public getters/setters to protect data integrity.
- **Abstraction**: Interface-based design for scoring strategies, allowing flexibility in implementation.
- **Inheritance**: Exception hierarchy and implementation inheritance for error handling.
- **Polymorphism**: Different scoring algorithms implementing the same interface for varied scoring methods.

## Requirements

- Java Development Kit (JDK) 8 or newer
- Command-line interface or terminal

## Future Enhancements

- Graphical user interface
- More sophisticated difficulty adjustments
- Persistence of user data
- Multi-language support
- Custom text import
- Performance graphs and visualizations

## License

This project is available under the MIT License.