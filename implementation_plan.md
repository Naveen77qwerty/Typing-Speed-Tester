# Terminal User Interface (TUI) Conversion Plan

This plan details the process of converting the current command-line Typing Speed Tester into an interactive Terminal User Interface (TUI) application.

## User Review Required

> [!IMPORTANT]
> The current C++ program uses standard Input/Output (`std::cin` and `std::cout`) which requires you to press Enter to submit a full sentence. A true typing tester (like Monkeytype) requires reading input character-by-character in real-time.

To achieve this real-time TUI, I propose using **Linux's `<termios.h>` for non-canonical input mode** along with **ANSI escape codes** for rich text formatting (colors, moving the cursor). 
- **Pros:** No external libraries required (like `ncurses`), works entirely in standard Linux terminals, single executable.
- **Cons:** Only supported on Unix/Linux/macOS (which matches your OS profile).

Are you okay with this dependency-free approach using `<termios.h>` and ANSI codes, or would you prefer using a UI library like `ncurses`? 

## Proposed Changes

### Core Logic & UI

#### [MODIFY] [C++.cpp](file:///home/renegader/Documents/Repos/Typing-Speed-Tester/C++.cpp)
- **Real-Time Input:** Implement a non-blocking character reading loop using `<termios.h>` to capture keystrokes instantly without waiting for Enter.
- **Dynamic Rendering:** Use ANSI escape sequences to clear the screen `\033[2J` and move the cursor `\033[H`.
- **Live Feedback:** Format the output string in real-time:
  - Correctly typed characters will be colored **Green**.
  - Incorrectly typed characters will be colored **Red**.
  - The current character to be typed will be highlighted.
- **Live WPM:** Update Word Per Minute calculations instantly on the screen as you type.

## Open Questions

1. Should I rename `C++.cpp` to `Typing_Speed_Tester_C++.cpp` to match your prompt?
2. Are you comfortable with a dark-mode optimized layout (using bright terminal colors: Green/Red/Yellow)?

## Verification Plan

### Manual Verification
- Compile the updated code (`g++ -std=c++14 C++.cpp -o tui_tester`).
- Run the executable and verify that the screen clears and renders the text dynamically.
- Type through the paragraphs, confirming correct colorization of individual characters and live WPM updates.
