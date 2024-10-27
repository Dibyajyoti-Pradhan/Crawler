# Web Crawler Project

## Introduction

This project implements a simple web crawler in Java. Given a starting URL, the crawler visits each URL it finds on the same domain. It prints each URL visited along with a list of links found on that page. The crawler is limited to one subdomain, meaning it will crawl all pages on the provided domain but will not follow external links.

## Project Structure

The project consists of several key classes:

- Main: Entry point of the application.
- WebCrawler: Manages the crawling process, including the coordination of tasks.
- CrawlerTask: Callable task that fetches and processes a single URL.
- DomainFilter: Ensures URLs are within the same domain.
- URLFetcher: Fetches and parses the URLs from a given web page.

## Folder Structure

Here is the directory structure of the project, outlining where the relevant files are located:

```
demo
├── pom.xml
└── src
    ├── main
    │   └── java
    │       └── com
    │           └── dibyojyoti
    │               └── demo
    │                   ├── app
    │                   │   └── Main.java
    │                   ├── crawler
    │                   │   ├── WebCrawler.java
    │                   │   └── CrawlerTask.java
    │                   ├── filter
    │                   │   ├── DomainFilter.java
    │                   │   └── Filter.java
    │                   └── fetcher
    │                       └── URLFetcher.java
    └── test
        └── java
            └── com
                └── dibyojyoti
                    └── demo
                        ├── crawler
                        │   ├── WebCrawlerTest.java
                        │   └── CrawlerTaskTest.java
                        ├── filter
                        │   └── DomainFilterTest.java
                        └── fetcher
                            └── URLFetcherTest.java

```

## Setup

To set up and run the project, follow these steps:

1. **Install VSCode for Java:**  
   Ensure that you have VSCode installed with the necessary Java extensions. For installation instructions, visit [VSCode for Java](https://code.visualstudio.com/docs/languages/java#_install-visual-studio-code-for-java).

2. **Setup Maven:**
   ```
   brew install maven
   ```
3. **Open the Project:**  
   Open the `demo` folder in VSCode.

4. **Open a Terminal in VSCode:**  
   You can open a terminal in VSCode by going to `Terminal` -> `New Terminal`.

5. **Navigate to the Directory:(Check where is the root for pom.xml)**  
   Use the terminal to navigate to the `demo` directory:

   ```bash
   cd demo/
   ```

6. **Compile and run the Java code using the following command:**

   ```bash
   mvn exec:java
   ```

   ```bash
   mvn exec:java -Dexec.args="https://monzo.com"
   ```

   Replace "https://monzo.com" with any other arguments as needed.

   Example: ![Alt text](../Screenshot%20Run.png?raw=true "Screenshot for running")

7. **Run test**
   ```bash
   mvn test
   ```
   Example: ![Alt text](../Screenshot%20Unit%20Tests.png?raw=true "Screenshot for unit test")

Tools used:

1. Maven
2. JUnit
3. Lombok
