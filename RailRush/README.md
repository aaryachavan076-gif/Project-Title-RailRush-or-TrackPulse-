# RailRush Project

**RailRush** is a Java‑based educational project that demonstrates 13 core Java/ web development concepts using the theme of Mumbai local and express trains.  The project is organized into separate modules, each mapping to an experiment from the provided image.

## Table of Contents

| Experiment | Module | Description |
|------------|--------|-------------|
| 1 | `Experiment01_ClassesObjects` | Basic Java classes representing trains, stations, schedules. |
| 2 | `Experiment02_Overloading` | Method and constructor overloading examples for `Train`. |
| 3 | `Experiment03_Inheritance` | Inheritance hierarchy (`ExpressTrain` extends `Train`) and custom exception handling. |
| 4 | `Experiment04_GenericsServlet` | Generic container for `Train<T>` and a simple `HttpServlet` that returns JSON. |
| 5 | `Experiment05_JSPLogin` | JSP login page with JDBC validation against the demo H2 database. |
| 6 | `Experiment06_JSPObjects` | Demonstrates implicit and explicit JSP objects (request, session, application). |
| 7 | `Experiment07_Website` | Static HTML/CSS/JavaScript site that displays train timings. |
| 8 | `Experiment08_EmailValidation` | Client‑side JavaScript email validation using RegExp. |
| 9 | `Experiment09_BGChange` | JavaScript that changes the page background color every 5 seconds. |
|10| `Experiment10_ReactHooks` | React functional component with four color buttons using `useState`. |
|11| `Experiment11_ReactSPA` | Small React single‑page app with Router, Props, and State. |
|12| `Experiment12_SpringBootMonolith` | Monolithic Spring Boot web app exposing JSP pages and a simple service layer. |
|13| `Experiment13_SpringBootREST` | Spring Boot application exposing RESTful APIs for train information. |

## How to Use

1. **Open the project in an IDE** (IntelliJ IDEA, Eclipse, VS Code with Java extensions).
2. **Import the Maven/Gradle wrapper** – each module contains its own `pom.xml` (Maven) for dependency management.
3. **Run the `setup.md` script** to initialise the demo H2 database with sample train data.
4. Follow the **individual experiment README files** inside each module for compilation, execution and expected output.

All source code is self‑contained, uses an embedded H2 database for demo purposes and does **not** fetch live train delay data.

---

[Setup Instructions](file:///c:/Users/Aarya%20Mangesh%20Chavan/Downloads/FSJ%20MIN%20PROJECT/RailRush/setup.md)
