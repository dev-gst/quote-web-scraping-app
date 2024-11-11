# Simple Web Scraping App

## Overview

This project is a simple web scraping application built with Java. It scrapes quotes from a website and stores them in a database. The application uses Jsoup for web scraping, PostgreSQL for the database, and Gradle for build automation.

## Project Structure

- `src/main/java`: Contains the main application code.
- `build.gradle`: Gradle build configuration file.
- `gradlew` and `gradlew.bat`: Gradle wrapper scripts.
- `README.md`: Project documentation.

## Dependencies

- **Jsoup**: HTML parser library.
- **PostgreSQL**: Database driver.
- **SnakeYAML**: YAML parser and emitter for Java.
- **JUnit**: Testing framework.
- **Mockito**: Mocking framework for unit tests.

## Setup

### Prerequisites

- Java 21 or higher
- PostgreSQL database

### Installation

1. Clone the repository:
    ```sh
    git git@github.com:dev-gst/quote-web-scraping-app.git
    cd quote-web-scraping-app
    ```

2. Build the project:
    ```sh
    ./gradlew build
    ```

3. Run the application:
    ```sh
    ./gradlew run
    ```

## Configuration

Configure the application by editing the `application.yml` file located in the `src/main/resources` directory. Set the database connection details and other necessary configurations.

## Usage

The application scrapes quotes from the link https://quotes.toscrape.com/ and stores them in the PostgreSQL database. It runs at a fixed interval, scraping new quotes and updating the database.

## Testing

Run the tests using Gradle:
```sh
./gradlew test