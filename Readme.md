
# Kafka File Processing Demo

This project demonstrates how to process files concurrently in a distributed environment using Java Spring Boot, Docker Compose, and Kafka from Conduktor.
  
## Youtube Tutorial  
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/04ebTCxZoQs/0.jpg)](https://www.youtube.com/watch?v=04ebTCxZoQs&ab_channel=MohammedHattab)
  
## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Conduktor Kafka

## Project Structure

- `src/main/java`: Contains the Java source code for the Spring Boot application.
- `kafka/docker-compose.yml`: Docker Compose file to set up Kafka and other necessary services.
- `README.md`: Project documentation.

## Setup

1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/kafka-file-processing-demo.git
   cd kafka-file-processing-demo
   ```

2. **Start the Docker containers:**
   ```sh
   cd kafka/docker-compose.yml
   docker-compose up 
   ```

3. **Build and run the Spring Boot application:**
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

## Usage

1. **Call the api with the files which needs to be processed.**
2. The Spring Boot application will read the files, process them concurrently, and send the results to Kafka topics.
3. Monitor the Kafka topics using Conduktor.

## Configuration

- **Kafka Configuration:** Modify the `application.properties` file to configure Kafka brokers, topics, and other settings.
- **Docker Compose:** Update the `docker-compose.yml` file to change service configurations. For more details please check https://conduktor.io/get-started

## Contributing

Contributions are welcome!


