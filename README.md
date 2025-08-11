# Organizational Structure Analysis

This application analyzes a company's organizational structure to identify potential improvements in management salary distribution and reporting line efficiency.

## 🚀 Quick Start

```bash
# Build the application
mvn clean package

# Run analysis on your employee data
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
```

## 📚 Documentation

**📖 [Complete Documentation](docs/README.md)** - Start here for comprehensive guides

### Quick Links
- **[Quick Start Guide](docs/quick-start.md)** - Get up and running in minutes
- **[User Guide](docs/user-guide.md)** - Complete usage instructions
- **[Business Logic](docs/business-logic.md)** - Understanding analysis rules
- **[Troubleshooting](docs/troubleshooting.md)** - Common issues and solutions
- **[FAQ](docs/faq.md)** - Frequently asked questions

### For Developers
- **[Architecture Overview](docs/architecture.md)** - System design and components
- **[Development Setup](docs/development.md)** - Development environment
- **[API Reference](docs/api-reference.md)** - Technical documentation

## 🎯 Features

The application performs three main analyses:

1. **Manager Salary Analysis**: Identifies managers who earn less than 20% more than their direct subordinates' average salary (underpaid) or more than 50% more than that average (overpaid).

2. **Reporting Line Analysis**: Identifies employees who have more than 4 managers between them and the CEO (reporting line too long).

3. **Organizational Summary**: Provides an overview of the company structure including total employees, managers, and salary statistics.

## 📊 CSV File Format

The CSV file should have the following structure:

```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300
```

**📖 [Detailed CSV Format Guide](docs/user-guide.md#csv-data-format)**

## 📋 Sample Output

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

## 🏗️ Architecture

The application is built using **Spring Boot 3.5.4** with a clean, layered architecture:

- **Employee Model**: Core business logic with salary analysis and reporting level calculations
- **CsvReaderService**: Robust CSV file parsing with comprehensive error handling
- **OrganizationalAnalyzerService**: Complete analysis engine for salary and reporting line analysis
- **OrganizationalAnalysisService**: Orchestrates the entire analysis process

**📖 [Detailed Architecture Documentation](docs/architecture.md)**

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
```

The comprehensive test suite covers:
- Employee model validation
- Organizational structure building
- Reporting level calculations
- Manager salary analysis
- CSV file reading
- Error handling scenarios

## 🔧 Requirements

- **Java 21** or later
- **Maven 3.6+**
- **Minimum 512MB RAM** (1GB recommended)

## 🚨 Error Handling

The application provides robust error handling for:
- Invalid CSV file format
- Missing manager references
- Invalid salary values
- File not found errors
- Organizational structure issues

**📖 [Troubleshooting Guide](docs/troubleshooting.md)**

## 📈 Performance

- **Scalability**: Designed for up to 1,000 employees
- **Memory Usage**: Linear scaling with employee count
- **Processing Time**: O(n) complexity for all operations
- **Efficient Algorithms**: Optimized data structures and algorithms

## 🔮 Future Enhancements

Planned improvements include:
- Web interface with REST API
- Export functionality (PDF, Excel, JSON)
- Configurable salary thresholds
- Database integration
- Historical analysis and trend tracking
- Department-level analysis
- Salary benchmarking against industry standards
- Visualization of organizational charts
- Docker containerization

## 🤝 Contributing

We welcome contributions! Please see our **[Contributing Guidelines](docs/contributing.md)** for details.

## 📞 Support

- **Documentation**: [Complete Documentation](docs/README.md)
- **Issues**: [GitHub Issues](https://github.com/your-repo/issues)
- **Discussions**: [GitHub Discussions](https://github.com/your-repo/discussions)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**📖 [View Complete Documentation](docs/README.md) | [Report an Issue](https://github.com/your-repo/issues) | [Ask a Question](https://github.com/your-repo/discussions)**
