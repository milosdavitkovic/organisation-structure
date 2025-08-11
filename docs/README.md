# Organizational Structure Analysis - Documentation

Welcome to the comprehensive documentation for the Organizational Structure Analysis project. This application helps companies analyze their organizational structure to identify potential improvements in management salary distribution and reporting line efficiency.

## 📚 Documentation Structure

### 🚀 Getting Started
- **[Quick Start Guide](quick-start.md)** - Get up and running in minutes
- **[Installation Guide](installation.md)** - Detailed setup instructions
- **[User Guide](user-guide.md)** - How to use the application

### 📖 Technical Documentation
- **[Architecture Overview](architecture.md)** - System design and components
- **[API Reference](api-reference.md)** - Detailed API documentation
- **[Data Models](data-models.md)** - Employee and organizational data structures
- **[Business Logic](business-logic.md)** - Salary analysis and reporting line rules

### 🛠️ Development
- **[Development Setup](development.md)** - Setting up the development environment
- **[Contributing Guidelines](contributing.md)** - How to contribute to the project
- **[Testing Guide](testing.md)** - Running tests and writing new ones
- **[Deployment Guide](deployment.md)** - Production deployment instructions

### 📋 Reference
- **[Configuration](configuration.md)** - Application configuration options
- **[Troubleshooting](troubleshooting.md)** - Common issues and solutions
- **[FAQ](faq.md)** - Frequently asked questions
- **[Changelog](changelog.md)** - Version history and changes

## 🎯 Project Overview

### Purpose
This application analyzes organizational structures to help companies:
- Ensure fair manager compensation relative to their teams
- Identify and optimize reporting line efficiency
- Maintain healthy organizational hierarchies

### Key Features
- **Manager Salary Analysis**: Identifies underpaid and overpaid managers
- **Reporting Line Analysis**: Finds employees with too long reporting chains
- **Organizational Summary**: Provides overview statistics
- **CSV Data Import**: Easy data import from standard CSV format
- **Comprehensive Reporting**: Detailed analysis with actionable insights

### Technology Stack
- **Java 21** - Core programming language
- **Spring Boot 3.5.4** - Application framework
- **Maven** - Build and dependency management
- **JUnit 5** - Testing framework
- **Lombok** - Code generation utilities

## 📊 Quick Example

```bash
# Run analysis on employee data
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
```

**Sample Output:**
```
=== MANAGER SALARY ANALYSIS ===
⚠ UNDERPAID MANAGERS:
  - Martin Chekov (ID: 124): Underpaid by $9600.00
  - Bob Ronstad (ID: 125): Underpaid by $1600.00

⚠ OVERPAID MANAGERS:
  - Alice Hasacat (ID: 300): Overpaid by $2000.00

=== REPORTING LINE ANALYSIS ===
⚠ EMPLOYEES WITH TOO LONG REPORTING LINES:
  - Amanda White (ID: 314): 1 levels too deep (Level 5)
```

## 🔗 Quick Links

- **[Source Code](../src/)** - Application source code
- **[Tests](../src/test/)** - Test suite
- **[Sample Data](../employees.csv)** - Example CSV files
- **[Issues](../../issues)** - Bug reports and feature requests
- **[Releases](../../releases)** - Download latest version

## 📞 Support

- **Documentation Issues**: Create an issue in this repository
- **Application Bugs**: Report in the main repository
- **Feature Requests**: Submit through GitHub issues

---

*Last updated: August 2024*
