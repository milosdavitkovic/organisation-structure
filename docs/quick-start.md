# Quick Start Guide

Get the Organizational Structure Analysis application up and running in minutes!

## 🚀 Prerequisites

Before you begin, ensure you have:
- **Java 21** or later installed
- **Maven 3.6+** installed
- Basic familiarity with command-line tools

### Check Your Installation

```bash
# Verify Java version
java -version

# Verify Maven version
mvn -version
```

## 📦 Installation

### Option 1: Build from Source (Recommended)

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd organisation-structure
   ```

2. **Build the application**
   ```bash
   mvn clean package
   ```

3. **Verify the build**
   ```bash
   ls target/exercise-0.0.1-SNAPSHOT.jar
   ```

### Option 2: Download Pre-built JAR

If a pre-built JAR is available, download it directly.

## 📊 Prepare Your Data

Create a CSV file with your employee data in this format:

```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300
```

**Important Notes:**
- First line is the header (will be automatically skipped)
- CEO has empty `managerId`
- All other employees must have a valid `managerId`
- `salary` must be a valid number
- `Id` values must be unique

## 🎯 Run Your First Analysis

### Basic Usage

```bash
java -jar target/exercise-0.0.1-SNAPSHOT.jar your-employees.csv
```

### Example with Sample Data

The project includes sample data files:

```bash
# Use the basic example
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv

# Use the complex example
java -jar target/exercise-0.0.1-SNAPSHOT.jar example-complex.csv
```

## 📋 Understanding the Output

The application provides three main types of analysis:

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

### 2. Manager Salary Analysis
```
=== MANAGER SALARY ANALYSIS ===
⚠ UNDERPAID MANAGERS:
  - Martin Chekov (ID: 124): Underpaid by $9600.00

⚠ OVERPAID MANAGERS:
  - Alice Hasacat (ID: 300): Overpaid by $2000.00
```

### 3. Reporting Line Analysis
```
=== REPORTING LINE ANALYSIS ===
⚠ EMPLOYEES WITH TOO LONG REPORTING LINES:
  - Amanda White (ID: 314): 1 levels too deep (Level 5)
```

## 🔧 Common Commands

### Get Help
```bash
java -jar target/exercise-0.0.1-SNAPSHOT.jar
```

### Run Tests
```bash
mvn test
```

### Clean Build
```bash
mvn clean package
```

## 🚨 Troubleshooting

### Common Issues

**"No such file or directory"**
- Ensure the CSV file exists in the specified path
- Check file permissions

**"Invalid line format"**
- Verify CSV format matches the required structure
- Check for missing commas or invalid characters

**"Manager not found"**
- Ensure all `managerId` values reference existing employee IDs
- Check for typos in employee IDs

**"Error parsing salary"**
- Ensure salary values are valid numbers
- Check for currency symbols or formatting issues

### Getting Help

- Check the [Troubleshooting Guide](troubleshooting.md) for detailed solutions
- Review the [User Guide](user-guide.md) for advanced usage
- Consult the [FAQ](faq.md) for common questions

## 🎉 Next Steps

Now that you're up and running:

1. **Analyze your data**: Run the application with your employee CSV file
2. **Review results**: Examine the analysis output for insights
3. **Take action**: Use the findings to improve your organizational structure
4. **Explore features**: Check out the [User Guide](user-guide.md) for advanced usage

## 📚 Additional Resources

- **[User Guide](user-guide.md)** - Detailed usage instructions
- **[Business Logic](business-logic.md)** - Understanding the analysis rules
- **[Data Models](data-models.md)** - CSV format specifications
- **[Architecture](architecture.md)** - Technical implementation details

---

*Need help? Check the [Troubleshooting Guide](troubleshooting.md) or create an issue in the repository.*
