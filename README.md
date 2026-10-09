# Google Form UI Automation Suite

[![Java](https://img.shields.io/badge/Java-11-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium%20WebDriver-4.21.0-43B02A?style=flat&logo=selenium&logoColor=white)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.7.0-FF7F00?style=flat&logo=testng&logoColor=white)](https://testng.org/)
[![Gradle](https://img.shields.io/badge/Gradle-7.5.1-02303A?style=flat&logo=gradle&logoColor=white)](https://gradle.org/)

An automated end-to-end testing suite for complex web forms built with Google Forms. This project showcases the **Facade / Wrapper Design Pattern**, dynamic data handling via Java 8 date-time APIs, accessibility-driven XPath locators, and resilient browser event dispatching.

---

## Architecture & Design Patterns

### 1. Wrapper / Facade Pattern
Direct WebDriver method calls are encapsulated inside dedicated static utility methods (`Wrappers.java`). This separates browser synchronization, DOM polling, and exception handling from test scenarios, producing cleaner, declarative test code.

### 2. Resilient Interaction Strategies
- **Keyboard-Driven Inputs**: Employs `Keys.chord(Keys.CONTROL, "a")` prior to typing rather than `.clear()`, ensuring client-side reactive state machines (Angular/React) detect input events.
- **Two-Tier Dispatching**: Leverages JavaScript execution fallback (`arguments[0].click()`) for components obscured by CSS animations or floating layers.
- **Custom Dropdown Traversal**: Implements list-based element iteration for non-standard HTML dropdowns lacking traditional `<select>` tags.

### 3. Dynamic, Idempotent Test Data
- Avoids hardcoded timestamps and submission data.
- Dynamically computes relative date offsets ($T - 7$ days) via `java.time.LocalDate`.
- Generates unique epoch identifiers using `Instant.now().getEpochSecond()`.

---

## Project Structure

```text
├── src
│   └── test
│       ├── java
│       │   └── demo
│       │       ├── wrappers
│       │       │   └── Wrappers.java     # Reusable action wrappers & utilities
│       │       └── TestCases.java        # TestNG test scenarios & lifecycle hooks
│       └── resources
│           └── testng.xml                # TestNG execution suite definition
├── build.gradle                          # Dependency & build configuration
├── gradle.properties                     # TLS protocols and JVM args
└── README.md                             # Documentation
