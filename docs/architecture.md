# Architecture Overview

This document provides a comprehensive overview of the Organizational Structure Analysis application architecture, design patterns, and technical implementation details.

## 🏗️ System Architecture

### High-Level Overview

The application follows a **layered architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              Command Line Interface                  │   │
│  │              (ExerciseApplication)                  │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                     Service Layer                           │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │ Organizational  │  │   CsvReader     │  │Organizational│ │
│  │   Analysis      │  │    Service      │  │  Analyzer    │ │
│  │   Service       │  │                 │  │  Service    │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                     Domain Layer                            │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                  Employee Model                     │   │
│  │              (Business Logic)                       │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                    Data Access Layer                        │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                 CSV File System                     │   │
│  │              (File I/O Operations)                  │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

## 📦 Component Details

### 1. Presentation Layer

#### ExerciseApplication
**Location**: `src/main/java/organisation/structure/exercise/ExerciseApplication.java`

**Responsibilities**:
- Application entry point
- Command-line argument parsing
- User interface and output formatting
- Error handling for user interactions

**Key Features**:
- Implements `CommandLineRunner` for Spring Boot CLI
- Provides usage instructions when no arguments provided
- Delegates analysis to service layer

```java
@SpringBootApplication
public class ExerciseApplication implements CommandLineRunner {
    @Autowired
    private OrganizationalAnalysisService analysisService;
    
    @Override
    public void run(String... args) throws Exception {
        // Handle command line arguments and delegate to service
    }
}
```

### 2. Service Layer

#### OrganizationalAnalysisService
**Location**: `src/main/java/organisation/structure/exercise/service/OrganizationalAnalysisService.java`

**Responsibilities**:
- Orchestrates the entire analysis process
- Coordinates between different services
- Handles high-level error scenarios
- Provides user-friendly error messages

**Dependencies**:
- `CsvReaderService`
- `OrganizationalAnalyzerService`

#### CsvReaderService
**Location**: `src/main/java/organisation/structure/exercise/service/CsvReaderService.java`

**Responsibilities**:
- CSV file parsing and validation
- Employee data extraction
- Error handling for file operations
- Data format validation

**Key Methods**:
- `readEmployeesFromCsv(String filePath)`: Main parsing method
- `parseEmployeeLine(String line)`: Individual line parsing

#### OrganizationalAnalyzerService
**Location**: `src/main/java/organisation/structure/exercise/service/OrganizationalAnalyzerService.java`

**Responsibilities**:
- Building organizational hierarchy
- Calculating reporting levels
- Performing salary analysis
- Generating analysis reports

**Key Methods**:
- `analyzeOrganizationalStructure(List<Employee> employees)`: Main analysis method
- `buildReportingRelationships(Map<String, Employee> employeeMap)`: Hierarchy building
- `calculateReportingLevels(Employee employee, int level)`: Level calculation
- `analyzeManagerSalaries(List<Employee> employees)`: Salary analysis
- `analyzeReportingLines(List<Employee> employees)`: Reporting line analysis

### 3. Domain Layer

#### Employee Model
**Location**: `src/main/java/organisation/structure/exercise/model/Employee.java`

**Responsibilities**:
- Core business entity representation
- Business logic implementation
- Salary calculation methods
- Reporting level management

**Key Features**:
- **Data Fields**: Basic employee information (id, name, salary, managerId)
- **Derived Fields**: Subordinates list, reporting level, CEO flag
- **Business Methods**: Salary analysis, reporting line validation
- **Validation Logic**: Self-contained business rules

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    // Core data fields
    private String id;
    private String firstName;
    private String lastName;
    private double salary;
    private String managerId;
    
    // Derived fields
    private List<Employee> directSubordinates = new ArrayList<>();
    private int reportingLevel = 0;
    private boolean isCEO = false;
    
    // Business logic methods
    public boolean isUnderpaid() { /* implementation */ }
    public boolean isOverpaid() { /* implementation */ }
    public boolean hasTooLongReportingLine() { /* implementation */ }
}
```

## 🔄 Data Flow

### 1. Application Startup
```
ExerciseApplication.main() 
    ↓
Spring Boot Context Initialization
    ↓
Dependency Injection (Services)
    ↓
CommandLineRunner.run()
```

### 2. Analysis Process
```
User Input (CSV file path)
    ↓
OrganizationalAnalysisService.analyzeOrganizationFromCsv()
    ↓
CsvReaderService.readEmployeesFromCsv()
    ↓
OrganizationalAnalyzerService.analyzeOrganizationalStructure()
    ↓
1. Build employee map
2. Find CEO
3. Build reporting relationships
4. Calculate reporting levels
5. Analyze manager salaries
6. Analyze reporting lines
    ↓
Output results to console
```

### 3. Data Transformation
```
CSV File → Employee Objects → Hierarchical Structure → Analysis Results
```

## 🎯 Design Patterns

### 1. Dependency Injection
- **Spring Boot Autowiring**: Automatic dependency management
- **Service Layer Pattern**: Clear separation of business logic
- **Interface Segregation**: Focused service responsibilities

### 2. Builder Pattern (Implicit)
- **Hierarchical Structure Building**: Step-by-step organization construction
- **Analysis Pipeline**: Sequential processing of different analysis types

### 3. Strategy Pattern
- **Analysis Strategies**: Different types of analysis (salary, reporting lines)
- **Extensible Design**: Easy to add new analysis types

### 4. Template Method Pattern
- **Analysis Workflow**: Standardized analysis process
- **Consistent Output**: Uniform reporting format

## 🔧 Technical Implementation

### Data Structures

#### Employee Hierarchy
```java
Employee {
    String id;
    String firstName;
    String lastName;
    double salary;
    String managerId;
    List<Employee> directSubordinates;  // Hierarchical relationship
    int reportingLevel;                 // Calculated depth
    boolean isCEO;                      // CEO identification
}
```

#### Employee Lookup
```java
Map<String, Employee> employeeMap;  // O(1) employee lookup by ID
```

#### Organizational Tree
```java
Employee ceo;  // Root of the organizational tree
// Each employee has a list of direct subordinates
// Hierarchical traversal possible from CEO down
```

### Algorithms

#### 1. Hierarchy Building Algorithm
**Time Complexity**: O(n)
**Space Complexity**: O(n)

```java
for (Employee employee : employees) {
    if (!employee.isCEO() && employee.getManagerId() != null) {
        Employee manager = employeeMap.get(employee.getManagerId());
        if (manager != null) {
            manager.getDirectSubordinates().add(employee);
        }
    }
}
```

#### 2. Reporting Level Calculation Algorithm
**Time Complexity**: O(n)
**Space Complexity**: O(h) where h is the height of the tree

```java
private void calculateReportingLevels(Employee employee, int level) {
    employee.setReportingLevel(level);
    for (Employee subordinate : employee.getDirectSubordinates()) {
        calculateReportingLevels(subordinate, level + 1);
    }
}
```

#### 3. Salary Analysis Algorithm
**Time Complexity**: O(n)
**Space Complexity**: O(1) per manager

```java
for (Employee manager : managers) {
    double avgSubordinateSalary = manager.getAverageSubordinateSalary();
    double ratio = manager.getSalary() / avgSubordinateSalary;
    
    if (ratio < 1.2) {
        // Underpaid
    } else if (ratio > 1.5) {
        // Overpaid
    }
}
```

### Error Handling Strategy

#### 1. Input Validation
- **CSV Format Validation**: Structure and field validation
- **Data Integrity Checks**: Manager references, salary format
- **Business Rule Validation**: CEO identification, circular references

#### 2. Graceful Degradation
- **Missing Data Handling**: Skip invalid records with warnings
- **Partial Analysis**: Continue processing even with some errors
- **User-Friendly Messages**: Clear error descriptions

#### 3. Logging and Reporting
- **Error Logging**: Detailed error information for debugging
- **User Feedback**: Actionable error messages
- **Progress Reporting**: Analysis status updates

## 🔒 Security Considerations

### Input Validation
- **File Path Validation**: Prevent directory traversal attacks
- **Data Sanitization**: Clean input data to prevent injection
- **Size Limits**: Prevent memory exhaustion with large files

### Error Information
- **Information Disclosure**: Avoid exposing sensitive system information
- **User Data Protection**: Don't log sensitive employee information
- **Safe Error Messages**: Provide helpful but secure error details

## 📈 Performance Characteristics

### Scalability Metrics
- **Employee Count**: Designed for up to 1,000 employees
- **Memory Usage**: Linear scaling with employee count
- **Processing Time**: O(n) complexity for all operations

### Optimization Techniques
- **Efficient Data Structures**: HashMap for O(1) lookups
- **Stream Processing**: Functional programming for data operations
- **Minimal Object Creation**: Reuse objects where possible

### Performance Monitoring
- **Memory Usage**: Monitor heap consumption
- **Processing Time**: Track analysis duration
- **Error Rates**: Monitor data quality issues

## 🔄 Extensibility

### Adding New Analysis Types
1. **Create Analysis Service**: Implement new analysis logic
2. **Extend Employee Model**: Add new business methods if needed
3. **Update Main Service**: Integrate new analysis into workflow
4. **Add Tests**: Ensure new functionality is properly tested

### Configuration Options
- **Salary Thresholds**: Configurable underpaid/overpaid percentages
- **Reporting Limits**: Adjustable maximum reporting levels
- **Output Formats**: Extensible reporting options

### Integration Points
- **Database Integration**: Replace CSV with database storage
- **Web Interface**: Add REST API endpoints
- **Export Formats**: Support for PDF, Excel, JSON output

## 🧪 Testing Architecture

### Test Structure
```
src/test/java/
├── ExerciseApplicationTests.java          # Application context tests
└── OrganizationalAnalysisTest.java        # Comprehensive functionality tests
```

### Testing Strategy
- **Unit Tests**: Individual component testing
- **Integration Tests**: Service interaction testing
- **End-to-End Tests**: Complete workflow testing
- **Error Scenario Tests**: Edge case and error handling

### Test Coverage
- **Employee Model**: 100% business logic coverage
- **Service Layer**: All public methods tested
- **Error Handling**: All error scenarios covered
- **Data Validation**: Complete input validation testing

---

*For implementation details, see the [API Reference](api-reference.md) and [Data Models](data-models.md) documentation.*
