# Core Package

This package contains the core business logic, data models, utilities, and configuration components that form the foundation of the Organizational Structure Analyzer.

## Overview

The core package provides the essential building blocks for the application, including domain models, business logic utilities, and configuration annotations. It follows clean architecture principles with clear separation of concerns and domain-driven design.

## Package Structure

```
core/
├── model/                           # Domain models and entities
│   ├── Employee.java                # Employee entity with business logic
│   ├── AnalysisResult.java          # Analysis results container
│   └── OrganizationalSummary.java   # Organizational metrics summary
├── util/                            # Core utility classes
│   ├── EmployeeValidationUtil.java  # Employee data validation
│   ├── CsvValidationUtil.java       # CSV file validation
│   └── LoggingUtil.java             # Logging utilities
├── configuration/                   # Configuration components
│   └── annotation/                  # Custom annotations
│       ├── Facade.java              # Facade component annotation
│       ├── Properties.java          # Properties component annotation
│       ├── UtilClass.java           # Utility class annotation
│       └── ValidatorClass.java      # Validator component annotation
└── README.md                        # This documentation
```

## Domain Models

### Employee

**Purpose**: Core entity representing an employee in the organizational structure.

**Key Features**:
- Employee identification and personal information
- Salary and compensation data
- Organizational hierarchy relationships
- Business logic for salary analysis
- Reporting level calculations

**Key Methods**:
```java
// Basic information
public String getFullName()
public boolean isCEO()
public boolean hasSubordinates()

// Salary analysis
public double getAverageSubordinateSalary()
public double getSalaryRatioToAverage()
public boolean isUnderpaid()
public boolean isOverpaid()

// Organizational structure
public int getReportingLevel()
public List<Employee> getDirectSubordinates()
```

**Business Logic**:
- **CEO Detection**: Identifies employees without managers
- **Salary Analysis**: Calculates salary ratios and identifies underpaid/overpaid managers
- **Hierarchy Management**: Manages direct subordinate relationships
- **Reporting Levels**: Tracks organizational depth

### AnalysisResult

**Purpose**: Container for organizational analysis results and findings.

**Key Features**:
- Success/failure status tracking
- Error message handling
- Comprehensive analysis data storage
- Factory methods for result creation

**Key Methods**:
```java
// Factory methods
public static AnalysisResult success(OrganizationalSummary, List<Employee>, List<Employee>, List<Employee>)
public static AnalysisResult failure(String errorMessage)

// Data access
public boolean isSuccess()
public String getErrorMessage()
public OrganizationalSummary getOrganizationalSummary()
public List<Employee> getUnderpaidManagers()
public List<Employee> getOverpaidManagers()
public List<Employee> getEmployeesWithLongReportingLines()
```

**Data Structure**:
- **Success Status**: Boolean indicating analysis success
- **Error Information**: Detailed error messages for failures
- **Organizational Summary**: High-level metrics and statistics
- **Analysis Findings**: Lists of employees with specific issues

### OrganizationalSummary

**Purpose**: Provides high-level organizational metrics and statistics.

**Key Features**:
- Employee count and distribution statistics
- Salary analysis metrics
- Organizational structure metrics
- Performance indicators

**Key Methods**:
```java
// Employee statistics
public int getTotalEmployees()
public int getManagerCount()
public int getIndividualContributorCount()

// Salary metrics
public double getAverageSalary()
public double getTotalSalaryBudget()
public double getSalaryVariance()

// Organizational metrics
public int getMaxReportingLevel()
public double getAverageReportingLevel()
public int getOrganizationalDepth()
```

## Utility Classes

### EmployeeValidationUtil

**Purpose**: Comprehensive validation utilities for employee data and organizational structure.

**Key Features**:
- Employee data integrity validation
- Organizational structure validation
- Duplicate detection
- Salary range validation
- Manager-subordinate relationship validation

**Key Methods**:
```java
// Employee validation
public static boolean validateEmployees(List<Employee>)
public static boolean isValidEmployee(Employee)
public static boolean hasDuplicateIds(List<Employee>)

// Organizational structure validation
public static boolean isValidOrganizationalStructure(List<Employee>)
public static boolean hasValidManagerReferences(List<Employee>)
public static boolean hasSingleCEO(List<Employee>)

// Salary validation
public static boolean isValidSalary(double salary)
public static boolean hasReasonableSalaryDistribution(List<Employee>)
```

**Validation Rules**:
- **Data Integrity**: Non-null, non-empty required fields
- **Salary Range**: Reasonable salary bounds (0 to $1M)
- **Unique IDs**: No duplicate employee identifiers
- **Organizational Structure**: Valid manager-subordinate relationships
- **Single CEO**: Only one employee without a manager

### CsvValidationUtil

**Purpose**: Utilities for CSV file validation and processing.

**Key Features**:
- CSV file format validation
- File structure verification
- Data type validation
- Error reporting and logging

**Key Methods**:
```java
// File validation
public static boolean isValidCsvFile(String filePath)
public static boolean hasValidHeader(String header)
public static boolean isValidCsvLine(String line)

// Data validation
public static boolean isValidEmployeeData(String[] fields)
public static boolean isValidSalary(String salaryStr)
public static boolean isValidManagerId(String managerId)
```

**Validation Rules**:
- **File Format**: Valid CSV structure with proper delimiters
- **Header Validation**: Required column headers present
- **Data Types**: Correct data types for each field
- **Required Fields**: Mandatory fields present and non-empty

### LoggingUtil

**Purpose**: Centralized logging utilities with formatting capabilities.

**Key Features**:
- Standardized logging messages
- Currency formatting (USD, CHF)
- Number formatting
- Test execution logging

**Key Methods**:
```java
// Logging
public static void logTestStartInfo()

// Formatting
public static String logDoubleValue(Double value)
public static String logDollarValue(Double value)
public static String logSwissFrankValue(Double value)
```

**Formatting Features**:
- **Double Values**: Consistent decimal formatting
- **Currency Display**: Localized currency formatting
- **Test Logging**: Standardized test execution messages
- **Internationalization**: Support for multiple locales

## Configuration Annotations

### Facade

**Purpose**: Custom annotation for facade components.

**Usage**:
```java
@Facade
public class DefaultTestFacade implements TestFacade {
    // Facade implementation
}
```

**Features**:
- Spring component registration
- Runtime visibility
- Type-level application
- Documentation support

### Properties

**Purpose**: Custom annotation for properties components.

**Usage**:
```java
@Properties
public class ApplicationProperties {
    // Properties configuration
}
```

**Features**:
- Configuration component identification
- Spring integration
- Runtime processing
- Documentation support

### UtilClass

**Purpose**: Custom annotation for utility classes.

**Usage**:
```java
@UtilClass
public class EmployeeValidationUtil {
    // Utility methods
}
```

**Features**:
- Utility class identification
- Static method support
- Documentation generation
- Code organization

### ValidatorClass

**Purpose**: Custom annotation for validator components.

**Usage**:
```java
@ValidatorClass
public class EmployeeValidator {
    // Validation logic
}
```

**Features**:
- Validator component identification
- Spring integration
- Runtime validation support
- Documentation support

## Design Principles

### Domain-Driven Design

- **Rich Domain Models**: Business logic embedded in entities
- **Ubiquitous Language**: Consistent terminology across the codebase
- **Value Objects**: Immutable data structures
- **Aggregate Roots**: Clear entity boundaries

### Clean Architecture

- **Dependency Inversion**: Core depends on abstractions
- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed Principle**: Open for extension, closed for modification
- **Interface Segregation**: Focused, cohesive interfaces

### Immutability

- **Value Objects**: Immutable data structures where appropriate
- **Builder Pattern**: For complex object construction
- **Defensive Copying**: Protection against external modification

## Usage Examples

### Employee Creation and Analysis

```java
// Create employee
Employee employee = new Employee("123", "John", "Doe", 75000.0, "456");

// Analyze salary position
if (employee.hasSubordinates()) {
    if (employee.isUnderpaid()) {
        log.warn("Employee {} is underpaid", employee.getFullName());
    }
    if (employee.isOverpaid()) {
        log.warn("Employee {} is overpaid", employee.getFullName());
    }
}
```

### Data Validation

```java
// Validate employee list
List<Employee> employees = loadEmployeesFromCsv();
if (!EmployeeValidationUtil.validateEmployees(employees)) {
    throw new ValidationException("Invalid employee data");
}

// Validate CSV file
if (!CsvValidationUtil.isValidCsvFile("employees.csv")) {
    throw new ValidationException("Invalid CSV file");
}
```

### Analysis Result Handling

```java
// Create successful result
AnalysisResult result = AnalysisResult.success(
    organizationalSummary,
    underpaidManagers,
    overpaidManagers,
    employeesWithLongReportingLines
);

// Handle result
if (result.isSuccess()) {
    displayResults(result.getOrganizationalSummary());
} else {
    handleError(result.getErrorMessage());
}
```

### Logging and Formatting

```java
// Log test execution
LoggingUtil.logTestStartInfo();

// Format currency values
String salary = LoggingUtil.logDollarValue(75000.0); // "$ 75000.00 "
String chfSalary = LoggingUtil.logSwissFrankValue(75000.0); // "CHF 75'000.00"
```

## Best Practices

1. **Model Validation**: Always validate data before processing
2. **Business Logic**: Keep business rules in domain models
3. **Error Handling**: Use meaningful error messages
4. **Logging**: Consistent logging patterns across utilities
5. **Immutability**: Prefer immutable objects where possible
6. **Documentation**: Clear JavaDoc for all public methods
7. **Testing**: Comprehensive unit tests for all utilities
8. **Performance**: Optimize for large datasets

## Dependencies

### Required Dependencies

- **Lombok**: For boilerplate code reduction
- **SLF4J**: For logging capabilities
- **Spring Framework**: For annotation processing

### Optional Dependencies

- **JUnit 5**: For unit testing
- **AssertJ**: For fluent assertions
- **Mockito**: For mocking in tests

## Testing

### Model Testing

```java
@Test
void testEmployeeSalaryAnalysis() {
    Employee manager = new Employee("1", "Manager", "Test", 80000.0, null);
    Employee subordinate = new Employee("2", "Sub", "Test", 60000.0, "1");
    
    manager.getDirectSubordinates().add(subordinate);
    
    assertTrue(manager.hasSubordinates());
    assertEquals(60000.0, manager.getAverageSubordinateSalary(), 0.01);
    assertTrue(manager.isOverpaid()); // 80000 > 60000 * 1.5
}
```

### Utility Testing

```java
@Test
void testEmployeeValidation() {
    Employee validEmployee = new Employee("1", "John", "Doe", 50000.0, null);
    assertTrue(EmployeeValidationUtil.isValidEmployee(validEmployee));
    
    Employee invalidEmployee = new Employee("", "John", "Doe", -1000.0, null);
    assertFalse(EmployeeValidationUtil.isValidEmployee(invalidEmployee));
}
```

## Future Enhancements

Potential improvements for the core package:

1. **Enhanced Validation**: More sophisticated validation rules
2. **Performance Optimization**: Caching and optimization strategies
3. **Internationalization**: Multi-language support
4. **Audit Trail**: Change tracking and history
5. **Configuration Management**: Dynamic configuration capabilities
6. **Metrics Collection**: Performance and usage metrics
7. **Plugin Architecture**: Extensible validation and processing
8. **Data Migration**: Version management for data structures


