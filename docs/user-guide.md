# User Guide

Complete guide to using the Organizational Structure Analysis application effectively.

## 📋 Table of Contents

1. [Getting Started](#getting-started)
2. [CSV Data Format](#csv-data-format)
3. [Running Analysis](#running-analysis)
4. [Understanding Results](#understanding-results)
5. [Advanced Usage](#advanced-usage)
6. [Best Practices](#best-practices)
7. [Examples](#examples)

## 🚀 Getting Started

### Prerequisites
- Java 21 or later
- Maven 3.6+ (for building from source)
- CSV file with employee data

### Installation
```bash
# Build the application
mvn clean package

# Verify installation
java -jar target/exercise-0.0.1-SNAPSHOT.jar
```

## 📊 CSV Data Format

### Required Structure
Your CSV file must follow this exact format:

```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300
```

### Field Descriptions

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `Id` | String | Yes | Unique identifier for each employee |
| `firstName` | String | Yes | Employee's first name |
| `lastName` | String | Yes | Employee's last name |
| `salary` | Number | Yes | Annual salary (no currency symbols) |
| `managerId` | String | No | ID of direct manager (empty for CEO) |

### Data Validation Rules

1. **Header Row**: First line must be the exact header shown above
2. **CEO Identification**: CEO has empty `managerId` field
3. **Manager References**: All `managerId` values must reference existing employee IDs
4. **Unique IDs**: Each employee ID must be unique
5. **Valid Salaries**: Salary must be a positive number
6. **No Circular References**: Employee cannot manage themselves

### Common Data Issues

**❌ Invalid Examples:**
```csv
# Missing header
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123

# Invalid salary format
Id,firstName,lastName,salary,managerId
123,Joe,Doe,$60000,
124,Martin,Chekov,45000,123

# Circular reference
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,123

# Missing manager reference
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,999
```

**✅ Valid Examples:**
```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
```

## 🎯 Running Analysis

### Basic Command
```bash
java -jar target/exercise-0.0.1-SNAPSHOT.jar <path-to-csv-file>
```

### Examples
```bash
# Analyze your employee data
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv

# Use relative path
java -jar target/exercise-0.0.1-SNAPSHOT.jar ./data/company-employees.csv

# Use absolute path
java -jar target/exercise-0.0.1-SNAPSHOT.jar /path/to/your/employees.csv
```

### Command Options
Currently, the application supports:
- Single CSV file path as argument
- Help display when no arguments provided

## 📈 Understanding Results

The application provides three main analysis sections:

### 1. Organizational Summary

```
=== ORGANIZATIONAL SUMMARY ===
CEO: Joe Doe (ID: 123)
Total Employees: 15
Managers: 8
Individual Contributors: 7
Total Salary Budget: $625000.00
Average Salary: $41666.67
```

**What it shows:**
- **CEO**: Name and ID of the chief executive
- **Total Employees**: Complete headcount
- **Managers**: Employees with direct reports
- **Individual Contributors**: Employees without direct reports
- **Total Salary Budget**: Sum of all salaries
- **Average Salary**: Mean salary across all employees

### 2. Manager Salary Analysis

```
=== MANAGER SALARY ANALYSIS ===
⚠ UNDERPAID MANAGERS:
  - Martin Chekov (ID: 124): Underpaid by $9600.00
  - Bob Ronstad (ID: 125): Underpaid by $1600.00

⚠ OVERPAID MANAGERS:
  - Alice Hasacat (ID: 300): Overpaid by $2000.00
```

**Analysis Rules:**
- **Underpaid**: Manager earns < 120% of average subordinate salary
- **Overpaid**: Manager earns > 150% of average subordinate salary
- **Fair Pay**: Manager earns 120-150% of average subordinate salary

**Calculation Method:**
1. Calculate average salary of direct subordinates
2. Determine salary ratio: `manager_salary / average_subordinate_salary`
3. Identify underpaid (ratio < 1.2) or overpaid (ratio > 1.5) managers
4. Calculate exact amount: `(target_salary - actual_salary)` or `(actual_salary - target_salary)`

### 3. Reporting Line Analysis

```
=== REPORTING LINE ANALYSIS ===
⚠ EMPLOYEES WITH TOO LONG REPORTING LINES:
  - Amanda White (ID: 314): 1 levels too deep (Level 5)
  - Daniel Harris (ID: 315): 1 levels too deep (Level 5)
```

**Analysis Rules:**
- **Maximum Levels**: 4 levels between employee and CEO
- **Level 0**: CEO
- **Level 1**: Direct reports to CEO
- **Level 2**: Reports to Level 1 managers
- **Level 3**: Reports to Level 2 managers
- **Level 4**: Reports to Level 3 managers
- **Level 5+**: Too deep (violation)

**Calculation Method:**
1. Start from CEO (level 0)
2. Recursively assign levels to all subordinates
3. Identify employees at level 5 or higher
4. Report excess levels beyond the 4-level limit

## 🔧 Advanced Usage

### Batch Processing
```bash
# Analyze multiple files
for file in *.csv; do
    echo "Analyzing $file..."
    java -jar target/exercise-0.0.1-SNAPSHOT.jar "$file"
    echo "---"
done
```

### Output Redirection
```bash
# Save results to file
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv > analysis-results.txt

# Append to existing file
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv >> analysis-results.txt
```

### Error Handling
```bash
# Capture errors
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv 2> errors.log

# Full output capture
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv > output.log 2>&1
```

## 📋 Best Practices

### Data Preparation
1. **Validate your CSV** before running analysis
2. **Use consistent ID formats** (numbers or strings, but be consistent)
3. **Ensure salary accuracy** - use annual figures, no currency symbols
4. **Check manager references** - verify all manager IDs exist
5. **Include all employees** - don't skip anyone in the hierarchy

### Analysis Workflow
1. **Start with sample data** to verify setup
2. **Run analysis on your data**
3. **Review organizational summary** for data quality
4. **Examine salary analysis** for compensation insights
5. **Check reporting lines** for structural issues
6. **Take action** based on findings

### Interpreting Results
1. **Context matters** - consider industry standards and company size
2. **Look for patterns** - multiple underpaid managers might indicate systemic issues
3. **Prioritize by impact** - focus on largest salary gaps first
4. **Consider reporting depth** - long chains may indicate organizational bloat

## 📚 Examples

### Example 1: Small Company Analysis
**Input (small-company.csv):**
```csv
Id,firstName,lastName,salary,managerId
1,John,CEO,80000,
2,Alice,Manager,60000,1
3,Bob,Employee,45000,2
4,Carol,Employee,47000,2
```

**Output:**
```
=== ORGANIZATIONAL SUMMARY ===
CEO: John CEO (ID: 1)
Total Employees: 4
Managers: 2
Individual Contributors: 2
Total Salary Budget: $232000.00
Average Salary: $58000.00

=== MANAGER SALARY ANALYSIS ===
⚠ UNDERPAID MANAGERS:
  - Alice Manager (ID: 2): Underpaid by $6000.00

=== REPORTING LINE ANALYSIS ===
✓ No employees with too long reporting lines found.
```

### Example 2: Complex Organization
**Input (complex-org.csv):**
```csv
Id,firstName,lastName,salary,managerId
100,CEO,Executive,150000,
101,VP1,Manager,120000,100
102,VP2,Manager,110000,100
103,Dir1,Manager,90000,101
104,Dir2,Manager,85000,101
105,Emp1,Employee,60000,103
106,Emp2,Employee,65000,103
107,Emp3,Employee,55000,104
108,Emp4,Employee,58000,104
109,Emp5,Employee,52000,102
110,Emp6,Employee,54000,102
```

**Output:**
```
=== ORGANIZATIONAL SUMMARY ===
CEO: CEO Executive (ID: 100)
Total Employees: 10
Managers: 4
Individual Contributors: 6
Total Salary Budget: $849000.00
Average Salary: $84900.00

=== MANAGER SALARY ANALYSIS ===
⚠ OVERPAID MANAGERS:
  - VP1 Manager (ID: 101): Overpaid by $15000.00
  - VP2 Manager (ID: 102): Overpaid by $25000.00

=== REPORTING LINE ANALYSIS ===
✓ No employees with too long reporting lines found.
```

## 🚨 Troubleshooting

### Common Issues and Solutions

**"No CEO found in the organization!"**
- Ensure one employee has empty `managerId`
- Check for extra spaces or hidden characters

**"Manager not found for employee: [Name]"**
- Verify all `managerId` values reference existing employee IDs
- Check for typos or case sensitivity issues

**"Invalid line format: [line]"**
- Ensure CSV format matches required structure
- Check for missing commas or extra fields

**"Error parsing salary in line: [line]"**
- Remove currency symbols from salary values
- Ensure salary is a valid number

### Getting Help
- Check the [Troubleshooting Guide](troubleshooting.md) for detailed solutions
- Review the [FAQ](faq.md) for common questions
- Create an issue in the repository for bugs

---

*For technical details, see the [Architecture](architecture.md) and [API Reference](api-reference.md) documentation.*
