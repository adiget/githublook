# githublook
This is a sample android application using github apis built to demonstrate use of Compose + Clean Architecture.

<img src="screenshot/all-screens.png" alt="Splash Screen" width="480" />


## About

It loads repositories and pull requests from github for a user. It is implemented by following standard clean architecture principle.
Dependencies injection using Hilt.

S.O.L.I.D principle followed for more understandable, flexible and maintainable.

## Built With

### 1. Kotlin:-
First class and official programming language for Android development.
### 2. Compose:-
Jetpack Compose UI  
### 3. Coroutines:-
For composing asynchronous programming.
### 4. Android Architecture Components:-
Collection of libraries that help you design robust, testable, and maintainable apps.
### 5. Flow:-
Flow and StateFlow.
### 6. ViewModel:-
Stores UI-related data that isn't destroyed on UI changes.
### 7. Hilt:-
Dependency Injection Framework
### 8. Retrofit:-
A type-safe HTTP client for Android and Java.
### 9. Coil:-
image loading framework for Android
### 10. Unit and Instrumentation Test:-
Junit, mockk, Compose tests


## Layers

**1. Domain** - Would execute business logic which is independent of any layer and is just a pure kotlin/java package with no android specific dependency.

**2. Data** - Would dispense the required data for the application to the domain layer by implementing interface exposed by the domain.

**3. Presentation / UI** - Would include both domain and data layer and is android specific which executes the UI logic.
