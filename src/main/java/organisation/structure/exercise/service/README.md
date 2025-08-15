# Service Package

This package contains the service layer components responsible for business logic and data processing in the Organizational Structure Analyzer.

## Overview

The service layer follows the Interface Segregation Principle and provides focused, single-responsibility services for different aspects of organizational analysis. The services are organized into logical domains with clear interfaces and implementations.

## Package Structure

```
service/
├── analysis/                        # Analysis services
│   ├── OrganizationalAnalyzerService.java    # Analysis interface
│   └── impl/
│       └── DefaultOrganizationalAnalyzerService.java # Analysis implementation
├── csv/                             # CSV processing services
│   ├── ICsvReaderService.java       # CSV reading interface
│   └── impl/
│       └── DefaultCsvReaderService.java      # CSV implementation
├── logging/                         # Logging services
│   ├── OrganizationalAnalysisLogging.java    # Logging interface
│   └── impl/
│       └── DefaultOrganizationalAnalysisLogging.java # Logging implementation
├── test/                            # Test services
│   ├── LocalTestService.java        # Local test service interface
│   └── impl/
│       └── DefaultLocalTestService.java      # Local test implementation
├── CsvReaderService.java            # Legacy CSV reader service
├── ICsvReaderService.java           # Legacy CSV reader interface
└── README.md                        # This documentation
```

## Services

### Analysis Services

#### OrganizationalAnalyzerService

**Purpose**: Handles comprehensive organizational structure analysis operations.

**Key Methods**:
- `analyzeOrganizationalStructure(List<Employee>)`: Performs complete organizational analysis
- `buildOrganizationalHierarchy(List<Employee>)`: Builds reporting relationships
- `calculateReportingLevels(List<Employee>)`: Calculates reporting levels for all employees
- `analyzeManagerSalaries(List<Employee>)`: Analyzes manager salary distributions
- `findEmployeesWithLongReportingLines(List<Employee>)`: Identifies employees with excessive reporting levels
- `generateOrganizationalSummary(List<Employee>)`: Generates organizational metrics
- `analyzeOrganizationFromCsv(String)`: Orchestrates complete analysis from CSV file
- `validateInputFile(String)`: Validates input CSV files
- `estimateMemoryRequirements(String)`: Estimates memory needs for processing

**Features**:
- Comprehensive organizational analysis
- Salary distribution analysis
- Reporting line optimization
- Memory-efficient processing
- Input validation and error handling

**Implementation**: `DefaultOrganizationalAnalyzerService`

### CSV Services

#### ICsvReaderService

**Purpose**: Handles CSV file reading and validation operations.

**Key Methods**:
- `readEmployeesFromCsv(String filePath)`: Reads and parses employee data
- `validateCsvFile(String filePath)`: Validates CSV file format and structure
- `getCsvLineCount(String filePath)`: Estimates file size for memory planning

**Features**:
- Memory-efficient processing for large files
- Comprehensive error handling
- File format validation
- Try-with-resources for automatic cleanup

**Implementation**: `DefaultCsvReaderService`

#### CsvReaderService (Legacy)

**Purpose**: Legacy implementation of CSV reading functionality.

**Optimizations**:
- Batch processing for large datasets
- Memory estimation before processing
- Graceful error handling with logging
- Efficient string parsing

### Logging Services

#### OrganizationalAnalysisLogging

**Purpose**: Provides comprehensive logging and display capabilities for analysis results.

**Key Methods**:
- `displayOrganizationalSummary(OrganizationalSummary)`: Displays organizational metrics
- `displayAnalysisResults(AnalysisResult)`: Displays complete analysis results
- `displayError(String)`: Displays error messages
- `displaySuccess(String)`: Displays success messages
- `displayInfo(String)`: Displays informational messages

**Features**:
- Structured result display
- Error and success message handling
- Informational logging
- User-friendly output formatting

**Implementation**: `DefaultOrganizationalAnalysisLogging`

### Test Services

#### LocalTestService

**Purpose**: Provides local testing and analysis capabilities for development and testing scenarios.

**Key Methods**:
- `analyzeOrganization(String csvFilePath)`: Performs analysis on test data files

**Features**:
- Local test data processing
- Integration with analysis services
- Test result handling
- Development workflow support

**Implementation**: `DefaultLocalTestService`

## Design Principles

### Interface Segregation

Each service interface focuses on a specific responsibility:

- **Analysis Operations**: Organizational structure analysis and metrics
- **CSV Operations**: File reading, validation, and parsing
- **Logging Operations**: Result display and user communication
- **Test Operations**: Local testing and development workflows

### Dependency Injection

Services use Spring's `@Autowired` annotation for dependency injection:

```java
@Service
public class DefaultOrganizationalAnalyzerService implements OrganizationalAnalyzerService {
    @Autowired
    private ICsvReaderService csvReaderService;
    
    @Autowired
    private OrganizationalAnalysisLogging loggingService;
}
```

### Memory Optimization

All service implementations are optimized for large datasets:

- **Stream Processing**: Use Java streams for efficient data processing
- **Batch Operations**: Process data in manageable chunks
- **Resource Management**: Automatic cleanup with try-with-resources
- **Memory Estimation**: Pre-calculate memory requirements

## Implementation Guidelines

### Creating New Services

1. **Define Interface**: Create a focused interface with clear responsibilities
2. **Implement Service**: Create implementation with "Default" prefix
3. **Add Tests**: Create comprehensive JUnit 5 tests
4. **Document**: Add JavaDoc comments for all public methods

### Example Service Structure

```java
public interface IExampleService {
    ResultType performOperation(InputType input);
}

@Service
public class DefaultExampleService implements IExampleService {
    
    @Override
    public ResultType performOperation(InputType input) {
        // Implementation with error handling and logging
    }
}
```

### Error Handling

All services implement comprehensive error handling:

```java
try {
    // Service operation
} catch (SpecificException e) {
     log.error("[Organization Analyzes] Operation failed: {}", e.getMessage(), e);
    throw new ServiceException("User-friendly message", e);
}
```

### Logging

Services use Lombok's `@Slf4j` for consistent logging:

```java
@Slf4j
@Service
public class DefaultExampleService {
    
    public void performOperation() {
         log.info("[Organization Analyzes] Starting operation");
        // Implementation
        log.debug("[Organization Analyzes] Operation completed successfully");
    }
}
```

## Service Integration

### Analysis Service Integration

The analysis service orchestrates other services:

```java
@Service
public class DefaultOrganizationalAnalyzerService implements OrganizationalAnalyzerService {
    
    @Autowired
    private ICsvReaderService csvReaderService;
    
    @Autowired
    private OrganizationalAnalysisLogging loggingService;
    
    @Override
    public AnalysisResult analyzeOrganizationFromCsv(String csvFilePath) {
        // Validate input
        if (!validateInputFile(csvFilePath)) {
            return AnalysisResult.failure("Invalid input file");
        }
        
        // Read data
        List<Employee> employees = csvReaderService.readEmployeesFromCsv(csvFilePath);
        
        // Perform analysis
        return analyzeOrganizationalStructure(employees);
    }
}
```

### Logging Service Integration

The logging service provides user-friendly output:

```java
@Service
public class DefaultOrganizationalAnalysisLogging implements OrganizationalAnalysisLogging {
    
    @Override
    public void displayAnalysisResults(AnalysisResult result) {
        if (result.isSuccess()) {
            displaySuccess("Analysis completed successfully");
            displayOrganizationalSummary(result.getOrganizationalSummary());
        } else {
            displayError("Analysis failed: " + result.getErrorMessage());
        }
    }
}
```

## Testing

### Test Structure

Each service has corresponding test classes:

- `DefaultOrganizationalAnalyzerServiceTest.java`
- `DefaultCsvReaderServiceTest.java`
- `DefaultOrganizationalAnalysisLoggingTest.java`
- `DefaultLocalTestServiceTest.java`

### Test Guidelines

1. **Unit Tests**: Test individual methods in isolation
2. **Integration Tests**: Test service interactions
3. **Performance Tests**: Test with large datasets
4. **Error Scenarios**: Test error handling and edge cases

### Example Test

```java
@ExtendWith(MockitoExtension.class)
class DefaultOrganizationalAnalyzerServiceTest {
    
    @Mock
    private ICsvReaderService csvReaderService;
    
    @Mock
    private OrganizationalAnalysisLogging loggingService;
    
    @InjectMocks
    private DefaultOrganizationalAnalyzerService service;
    
    @Test
    void testAnalyzeOrganizationalStructure_Success() {
        // Given
        List<Employee> employees = createTestEmployees();
        
        // When
        AnalysisResult result = service.analyzeOrganizationalStructure(employees);
        
        // Then
        assertTrue(result.isSuccess());
        assertNotNull(result.getOrganizationalSummary());
    }
}
```

## Performance Considerations

### Large Dataset Handling

- **Batch Processing**: Process data in configurable batch sizes
- **Memory Management**: Monitor and control memory usage
- **Streaming**: Use Java streams for efficient processing
- **Caching**: Implement caching where appropriate

### Configuration

Services are configurable through application properties:

```properties
# CSV Processing
app.csv.batch-size=1000
app.csv.max-file-size=10MB

# Analysis
app.analysis.underpaid-threshold=1.2
app.analysis.overpaid-threshold=1.5
app.analysis.max-reporting-levels=4

# Performance
app.performance.thread-pool-size=4
app.performance.async-processing-enabled=true
```

## Best Practices

1. **Single Responsibility**: Each service has one clear purpose
2. **Interface First**: Define interfaces before implementations
3. **Error Handling**: Comprehensive error handling with meaningful messages
4. **Logging**: Appropriate logging levels for debugging and monitoring
5. **Testing**: High test coverage with meaningful test cases
6. **Documentation**: Clear JavaDoc for all public methods
7. **Performance**: Optimize for large datasets and memory efficiency
8. **Service Integration**: Proper coordination between services

## Dependencies

### Required Dependencies

- Spring Boot Starter
- Lombok
- SLF4J (Logging)
- JUnit 5 (Testing)

### Optional Dependencies

- Spring Boot Test (Integration testing)
- Mockito (Unit testing)
- AssertJ (Assertions)

## Future Enhancements

Potential improvements for the service layer:

1. **Async Processing**: Implement async operations for better performance
2. **Caching**: Add caching for frequently accessed data
3. **Metrics**: Add performance metrics and monitoring
4. **Validation**: Enhanced input validation and sanitization
5. **Internationalization**: Support for multiple languages
6. **Plugin Architecture**: Extensible service architecture
7. **Event-Driven**: Implement event-driven service communication
8. **Circuit Breaker**: Add resilience patterns for service calls
