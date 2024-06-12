# Selenium WebDriver & Rest Assured

## Framework Setup: Gradle / Cucumber / Selenium / RestAssured / TestNG

This framework is designed with Gradle, Selenium WebDriver, Cucumber, and RestAssured for automated testing.

### Installation Steps:

1. Install Java
2. Install Gradle
3. Run the following command in the terminal:
    ```sh
    ./gradlew build
    ```

### Project Structure:

The project follows the Page Object Model (POM) design pattern, ensuring better maintainability and reusability of code. Test cases are organized under:

```src
│
└── test
├── resources
│ └── features
│  └── AliExpress.feature
```


### Running Tests:

To execute Selenium WebDriver test cases, run the following command with the name of the Cucumber feature file:

```
./gradlew cucumber -Pfeature=AliExpress.feature
```

