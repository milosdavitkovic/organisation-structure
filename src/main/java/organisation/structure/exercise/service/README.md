# Service Package

This package contains the service layer components responsible for business logic and data processing in the Organizational Structure Analyzer.

## Overview

The service layer follows the Interface Segregation Principle and provides focused, single-responsibility services for different aspects of organizational analysis.

## Package Structure

```
service/
├── ICsvReaderService.java              # CSV reading interface
├── CsvReaderService.java               # CSV reading implementation
├── csv/
│   └── impl/
│       └── DefaultCsvReaderService.java # Optimized CSV reader
├── analysis/
│   └── impl/
│       └── DefaultOrganizationalAnalyzerService.java # Analysis engine
└── facade/
    └── impl/
        └── DefaultOrganizationalAnalysisService.java # Main orchestration
```

## Services

### ICsvReaderService

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

### CsvReaderService

**Purpose**: Default implementation of CSV reading functionality.

**Optimizations**:
- Batch processing for large datasets
- Memory estimation before processing
- Graceful error handling with logging
- Efficient string parsing

### DefaultCsvReaderService

**Purpose**: Optimized implementation for production use.

**Advanced Features**:
- Concurrent processing capabilities
- Memory leak prevention
- Performance monitoring
- Advanced validation rules

## Design Principles

### Interface Segregation

Each service interface focuses on a specific responsibility:

- **CSV Operations**: File reading, validation, and parsing
- **Analysis Operations**: Organizational structure analysis
- **Facade Operations**: High-level orchestration

### Dependency Injection

Services use Spring's `@Autowired` annotation for dependency injection:

```java
@Service
public class CsvReaderService implements ICsvReaderService {
    @Autowired
    private ValidationService validationService;
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
     log.error("\n [Organization Analyzes] Operation failed: {}", e.getMessage(), e);
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

## Testing

### Test Structure

Each service has corresponding test classes:

- `DefaultCsvReaderServiceTest.java`
- `DefaultOrganizationalAnalyzerServiceTest.java`
- `DefaultOrganizationalAnalysisServiceTest.java`

### Test Guidelines

1. **Unit Tests**: Test individual methods in isolation
2. **Integration Tests**: Test service interactions
3. **Performance Tests**: Test with large datasets
4. **Error Scenarios**: Test error handling and edge cases

### Example Test

```java
@ExtendWith(MockitoExtension.class)
class DefaultExampleServiceTest {
    
    @InjectMocks
    private DefaultExampleService service;
    
    @Test
    void testPerformOperation_Success() {
        // Given
        InputType input = createTestInput();
        
        // When
        ResultType result = service.performOperation(input);
        
        // Then
        assertNotNull(result);
        assertEquals(expectedValue, result.getValue());
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
```

## Best Practices

1. **Single Responsibility**: Each service has one clear purpose
2. **Interface First**: Define interfaces before implementations
3. **Error Handling**: Comprehensive error handling with meaningful messages
4. **Logging**: Appropriate logging levels for debugging and monitoring
5. **Testing**: High test coverage with meaningful test cases
6. **Documentation**: Clear JavaDoc for all public methods
7. **Performance**: Optimize for large datasets and memory efficiency

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
