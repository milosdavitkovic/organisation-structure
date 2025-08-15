# Organizational Structure Analyzer

A Spring Boot application that analyzes organizational structures from CSV data to identify potential improvements in management salary distribution and reporting line efficiency.

## Business Requirements

BIG COMPANY wants to analyze its organizational structure and identify potential improvements:

1. **Manager Salary Analysis**: Ensure every manager earns at least 20% more than the average salary of their direct subordinates, but no more than 50% more than that average.

2. **Reporting Line Analysis**: Identify all employees who have more than 4 managers between them and the CEO.

3. **CSV Data Processing**: Handle up to 1000 employees efficiently.

## Features

- **CSV File Processing**: Read and validate employee data from CSV files
- **Organizational Hierarchy Analysis**: Build and analyze reporting relationships
- **Salary Analysis**: Identify underpaid and overpaid managers
- **Reporting Line Analysis**: Find employees with too long reporting chains
- **Memory Optimized**: Efficient processing for large datasets
- **Comprehensive Logging**: Detailed logging using Lombok
- **Facade Pattern**: Simplified interface for complex operations
- **Modular Architecture**: Clear separation of concerns with core, service, and facade layers

## Project Structure

```
src/main/java/organisation/structure/exercise/
├── core/                           # Core business logic and models
│   ├── configuration/              # Configuration and annotations
│   │   └── annotation/             # Custom annotations
│   │       ├── Facade.java         # Facade annotation
│   │       ├── Properties.java     # Properties annotation
│   │       ├── UtilClass.java      # Utility class annotation
│   │       └── ValidatorClass.java # Validator annotation
│   ├── model/                      # Data models and entities
│   │   ├── AnalysisResult.java     # Analysis results model
│   │   ├── Employee.java           # Employee entity with business logic
│   │   └── OrganizationalSummary.java # Summary model
│   └── util/                       # Core utility classes
│       ├── CsvValidationUtil.java  # CSV validation utilities
│       ├── EmployeeValidationUtil.java # Employee validation
│       └── LoggingUtil.java        # Logging utilities
├── facade/                         # Facade layer for simplified access
│   ├── test/                       # Test facade implementations
│   │   ├── impl/                   # Test facade implementations
│   │   ├── local/                  # Local test facade
│   │   │   ├── impl/               # Local test implementations
│   │   │   └── LocalTestFacade.java
│   │   └── TestFacade.java         # Test facade interface
│   └── README.md                   # Facade documentation
├── service/                        # Service layer with business logic
│   ├── analysis/                   # Analysis services
│   │   ├── impl/                   # Analysis implementations
│   │   └── OrganizationalAnalyzerService.java # Analysis interface
│   ├── csv/                        # CSV processing services
│   │   ├── impl/                   # CSV implementations
│   │   └── ICsvReaderService.java  # CSV reader interface
│   ├── logging/                    # Logging services
│   │   ├── impl/                   # Logging implementations
│   │   └── OrganizationalAnalysisLogging.java # Logging interface
│   ├── test/                       # Test services
│   │   ├── impl/                   # Test implementations
│   │   └── LocalTestService.java   # Local test service
│   ├── CsvReaderService.java       # CSV reader service
│   ├── ICsvReaderService.java      # CSV reader interface
│   └── README.md                   # Service documentation
└── ExerciseApplication.java        # Main Spring Boot application
```

## Technology Stack

- **Java 21**
- **Spring Boot 3.x**
- **Maven**
- **Lombok**
- **JUnit 5**
- **SLF4J** (Logging)

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd organisation-structure
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run -- -Dspring.profiles.active=test
```

### Usage

1. Prepare a CSV file with employee data in the following format:
```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
```

2. Run the analysis:
```bash
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
```

## Configuration

The application supports multiple environment configurations:

- `application.properties` - Common configuration
- `application-test.properties` - Test environment
- `application-int.properties` - Integration environment  
- `application-prod.properties` - Production environment

### Key Configuration Properties

```properties
# CSV Processing
app.csv.batch-size=1000
app.csv.max-file-size=10MB
app.csv.encoding=UTF-8

# Analysis Thresholds
app.analysis.underpaid-threshold=1.2
app.analysis.overpaid-threshold=1.5
app.analysis.max-reporting-levels=4

# Performance
app.performance.thread-pool-size=4
app.performance.async-processing-enabled=true
```

## Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=DefaultCsvReaderServiceTest

# Run with specific profile
mvn test -Dspring.profiles.active=test
```

### Test Data

The project includes test data files:
- `src/test/resources/test-data/employees.csv` - Sample employee data
- `src/test/resources/test-data/large-employees.csv` - Large dataset for performance testing
- `src/test/resources/test-data/empty.csv` - Empty file for edge case testing

## Architecture

### Design Patterns

- **Facade Pattern**: Simplified interface for complex operations through the facade layer
- **Layered Architecture**: Clear separation with core, service, and facade layers
- **Interface Segregation**: Focused interfaces for specific responsibilities
- **Dependency Injection**: Spring-based dependency management
- **Annotation-Driven Configuration**: Custom annotations for configuration

### Layer Architecture

The application follows a modern layered architecture:

1. **Core Layer**: Contains business models, utilities, and configuration
   - **Model**: Data entities with business rules
   - **Util**: Core utility classes for validation and logging
   - **Configuration**: Custom annotations and configuration

2. **Service Layer**: Contains business logic and data processing
   - **Analysis Services**: Organizational analysis logic
   - **CSV Services**: File processing and validation
   - **Logging Services**: Comprehensive logging capabilities
   - **Test Services**: Testing utilities and services

3. **Facade Layer**: Provides simplified interfaces for complex operations
   - **Test Facades**: Simplified testing interfaces
   - **Local Facades**: Local testing and development interfaces

### Memory Optimization

- **Batch Processing**: Process large datasets in manageable chunks
- **Try-with-Resources**: Automatic resource management
- **Stream Processing**: Efficient data processing with Java streams
- **Memory Estimation**: Pre-calculation of memory requirements

## Business Logic

### Salary Analysis

- **Underpaid Managers**: Earn less than 120% of average subordinate salary
- **Overpaid Managers**: Earn more than 150% of average subordinate salary
- **Calculation**: Based on direct subordinates only

### Reporting Line Analysis

- **Maximum Levels**: 4 levels from CEO
- **Excess Levels**: Any level beyond 4 is considered excessive
- **Identification**: All employees with reporting level > 4

## Performance Considerations

- **Large Dataset Support**: Optimized for up to 1000 employees
- **Memory Management**: Efficient memory usage with streaming
- **Error Handling**: Graceful handling of malformed data
- **Logging**: Comprehensive logging for debugging and monitoring

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For questions and support, please contact the development team or create an issue in the repository.
