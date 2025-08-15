# Organizational Structure Analysis - Solution Summary

## Problem Statement

The challenge was to create a program that analyzes a company's organizational structure from a CSV file and identifies:

1. **Underpaid Managers**: Managers earning less than 20% more than their direct subordinates' average salary
2. **Overpaid Managers**: Managers earning more than 50% more than their direct subordinates' average salary  
3. **Long Reporting Lines**: Employees with more than 4 managers between them and the CEO

## Solution Architecture

The solution is built using Spring Boot with a clean, modular architecture:

### Core Components

1. **Employee Model** (`src/main/java/organisation/structure/exercise/model/Employee.java`)
   - Represents employee data with business logic
   - Calculates salary ratios and reporting levels
   - Determines if managers are underpaid/overpaid
   - Identifies employees with too long reporting lines

2. **CsvReaderService** (`src/main/java/organisation/structure/exercise/service/CsvReaderService.java`)
   - Parses CSV files with employee data
   - Handles error cases (invalid format, missing data)
   - Supports the required CSV format: `Id,firstName,lastName,salary,managerId`

3. **OrganizationalAnalyzerService** (`src/main/java/organisation/structure/exercise/service/OrganizationalAnalyzerService.java`)
   - Builds organizational hierarchy from flat employee list
   - Calculates reporting levels for all employees
   - Performs salary analysis for managers
   - Identifies reporting line issues

4. **OrganizationalAnalysisService** (`src/main/java/organisation/structure/exercise/service/OrganizationalAnalysisService.java`)
   - Orchestrates the entire analysis process
   - Handles errors and provides user-friendly output

5. **Main Application** (`src/main/java/organisation/structure/exercise/ExerciseApplication.java`)
   - Spring Boot application with command-line interface
   - Accepts CSV file path as command-line argument
   - Provides usage instructions when run without arguments

## Key Features

### Business Logic Implementation

**Manager Salary Analysis:**
- Calculates average salary of direct subordinates for each manager
- Determines salary ratio: `manager_salary / average_subordinate_salary`
- Identifies underpaid managers (ratio < 1.2) and overpaid managers (ratio > 1.5)
- Calculates exact underpayment/overpayment amounts

**Reporting Line Analysis:**
- Builds hierarchical structure starting from CEO (level 0)
- Calculates reporting level for each employee recursively
- Identifies employees at level 5+ as having too long reporting lines
- Reports excess levels beyond the 4-level limit

### Error Handling

- Invalid CSV format detection
- Missing manager references
- Invalid salary values
- File not found scenarios
- Graceful error messages with helpful information

### Performance Considerations

- O(n) time complexity for building hierarchy
- O(n) time complexity for salary analysis
- In-memory processing suitable for up to 1000 employees
- Efficient data structures using Java Streams

## Usage Examples

### Basic Usage
```bash
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
```

### Sample Output
```
Reading employee data from: employees.csv
Successfully loaded 15 employees.

=== ORGANIZATIONAL SUMMARY ===
CEO: Joe Doe (ID: 123)
Total Employees: 15
Managers: 8
Individual Contributors: 7
Total Salary Budget: $625000.00
Average Salary: $41666.67

=== MANAGER SALARY ANALYSIS ===
⚠ UNDERPAID MANAGERS:
  - Martin Chekov (ID: 124): Underpaid by $9600.00
  - Bob Ronstad (ID: 125): Underpaid by $1600.00

⚠ OVERPAID MANAGERS:
  - Alice Hasacat (ID: 300): Overpaid by $2000.00

=== REPORTING LINE ANALYSIS ===
⚠ EMPLOYEES WITH TOO LONG REPORTING LINES:
  - Amanda White (ID: 314): 1 levels too deep (Level 5)
  - Daniel Harris (ID: 315): 1 levels too deep (Level 5)

=== ANALYSIS COMPLETE ===
```

## Testing

Comprehensive test suite covering:
- Employee model validation
- Organizational structure building
- Reporting level calculations
- Manager salary analysis
- CSV file reading
- Error scenarios

Run tests with: `mvn test`

## CSV Format Requirements

The CSV file must have the following structure:
```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
```

**Rules:**
- First line is header (automatically skipped)
- CEO has empty managerId
- All other employees must have a valid managerId
- Salary must be a valid number
- IDs must be unique

## Business Rules Implemented

1. **Manager Salary Rules:**
   - Managers should earn at least 20% more than average subordinate salary
   - Managers should earn no more than 50% more than average subordinate salary
   - Only managers with direct subordinates are analyzed

2. **Reporting Line Rules:**
   - Maximum 4 levels between employee and CEO
   - CEO is at level 0
   - Direct reports to CEO are at level 1
   - Each subsequent level adds one to the depth

## Technical Implementation Details

### Data Structures
- `Employee` objects with hierarchical relationships
- `Map<String, Employee>` for O(1) employee lookup
- `List<Employee>` for direct subordinates

### Algorithms
- **Hierarchy Building**: Single pass through employees, building parent-child relationships
- **Level Calculation**: Recursive depth-first traversal from CEO
- **Salary Analysis**: Stream-based calculations for averages and ratios

### Error Handling Strategy
- Graceful degradation for missing data
- Detailed error messages for debugging
- Validation at multiple levels (CSV parsing, hierarchy building, analysis)

## Scalability Considerations

- **Memory**: Linear scaling with employee count
- **Performance**: O(n) algorithms suitable for 1000+ employees
- **Extensibility**: Modular design allows easy addition of new analysis types
- **Maintainability**: Clear separation of concerns and comprehensive testing

## Future Enhancements

Potential improvements could include:
- Database integration for larger datasets
- Web interface for interactive analysis
- Export functionality (PDF, Excel reports)
- Historical analysis and trend tracking
- Department-level analysis
- Salary benchmarking against industry standards
- Visualization of organizational charts

## Conclusion

This solution provides a robust, scalable, and maintainable implementation of the organizational structure analysis requirements. The modular architecture makes it easy to extend with additional analysis types, and the comprehensive error handling ensures reliable operation with various input scenarios.
