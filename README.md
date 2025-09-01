# Typing Speed Tester (Java)

A Java-based application to test and improve typing speed and accuracy using realistic text samples and longer practice paragraphs.

---

## ✨ Features
- 📝 Random paragraph generation for practice  
- ⏱ Real-time typing speed calculation (WPM)  
- 🎯 Accurate measurement of typing precision  
- 🔢 Multiple scoring algorithms (Standard & Advanced)  
- 📊 Progress tracking across multiple attempts  
- 👤 User profiles with statistics  
- ⚡ Difficulty levels (Easy, Medium, Hard)  
- 🏗 Object-oriented design with encapsulation  

---

## 🚀 Getting Started

### 1. Compile the program
```bash
cd src
javac -d . core/App.java
````

### 2. Run the program

```bash
java core.App
```

### 3. Follow on-screen instructions

* Enter your username
* Select difficulty level
* Choose a scoring method
* A random paragraph will appear
* Press **Enter** when ready and start typing
* View detailed results including:

  * Time taken
  * Words Per Minute (WPM)
  * Accuracy percentage
  * Average performance stats

You can retry with a new paragraph or exit anytime.

---

## 📂 Project Structure

```
src/
 ├── core/              # Core functionality
 │    ├── Timer.java
 │    ├── WordGenerator.java
 │    └── App.java       # Main entry point
 │
 ├── models/            # Data models
 │    ├── User.java
 │    └── TestResult.java
 │
 ├── services/          # Service layer
 │    ├── ScoringStrategy.java
 │    ├── StandardScoring.java
 │    ├── AdvancedScoring.java
 │    └── DifficultyManager.java
 │
 ├── enums/             # Enumerated types
 │    └── DifficultyLevel.java
 │
 ├── exceptions/        # Custom exceptions
 │    └── InvalidInputException.java
 │
 └── analysis/          # Performance analysis
      ├── TestAnalyzer.java
      ├── BasicAnalyzer.java
      └── AdvancedAnalyzer.java
```

---

## 🧩 OOP Principles Applied

* **Encapsulation** → Private fields, public getters/setters
* **Abstraction** → Interface-based design for scoring strategies
* **Inheritance** → Exception hierarchy & shared implementations
* **Polymorphism** → Multiple scoring strategies under one interface

---

## 🔧 Requirements

* Java Development Kit (JDK) 8 or newer
* Command-line interface (Terminal / CMD / PowerShell)

---

## 🌱 Future Enhancements

* GUI (Graphical User Interface)
* Smarter difficulty adjustments
* Persistent user data storage
* Multi-language support
* Custom text import
* Visual performance graphs

---

## 📜 License

This project is licensed under the **MIT License**.
