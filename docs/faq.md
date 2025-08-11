# Frequently Asked Questions (FAQ)

This FAQ addresses common questions about the Organizational Structure Analysis application.

## 🚀 Getting Started

### Q: What are the system requirements?
**A**: The application requires:
- **Java 21** or later
- **Maven 3.6+** (for building from source)
- **Minimum 512MB RAM** (1GB recommended)
- **Command-line access**

### Q: How do I install the application?
**A**: 
1. **Clone or download** the project
2. **Build with Maven**: `mvn clean package`
3. **Verify installation**: `java -jar target/exercise-0.0.1-SNAPSHOT.jar`

### Q: Can I run the application without building it?
**A**: Currently, you need to build the application from source. Pre-built JAR files may be available in future releases.

### Q: What format should my employee data be in?
**A**: Your data must be in CSV format with this exact structure:
```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
```

## 📊 Data and Input

### Q: How many employees can the application handle?
**A**: The application is designed to handle up to **1,000 employees** efficiently. Performance may degrade with larger datasets.

### Q: Can I use different currency formats in the CSV?
**A**: **No**. Use plain numbers without currency symbols, commas, or formatting:
```csv
# ❌ Wrong
123,Joe,Doe,$60,000.00,

# ✅ Correct
123,Joe,Doe,60000,
```

### Q: What if an employee has no manager (CEO)?
**A**: The CEO should have an **empty managerId field**:
```csv
123,Joe,Doe,60000,  # CEO - no manager
124,Martin,Chekov,45000,123  # Has manager ID 123
```

### Q: Can I have multiple CEOs?
**A**: **No**. The application expects exactly one CEO (one employee with no manager). Multiple CEOs will cause errors.

### Q: What happens if I have circular references in my data?
**A**: The application prevents circular references during hierarchy building. An employee cannot manage themselves.

### Q: Can I use different ID formats (numbers vs strings)?
**A**: **Yes**, but be consistent. You can use:
- Numbers: `123, 124, 125`
- Strings: `"EMP001", "EMP002", "EMP003"`
- Mixed: `"CEO", 123, "MGR001"`

## 💰 Salary Analysis

### Q: How does the application determine if a manager is underpaid or overpaid?
**A**: The application calculates a **salary ratio**:
```
Salary Ratio = Manager's Salary / Average Subordinate Salary
```
- **Underpaid**: Ratio < 1.2 (less than 20% more than subordinates)
- **Fair Pay**: Ratio between 1.2 and 1.5 (20-50% more than subordinates)
- **Overpaid**: Ratio > 1.5 (more than 50% more than subordinates)

### Q: What if a manager has no subordinates?
**A**: Managers without subordinates are **excluded from salary analysis** since there's no basis for comparison.

### Q: How is the average subordinate salary calculated?
**A**: It's the **arithmetic mean** of all direct subordinate salaries:
```
Average = (Subordinate1 + Subordinate2 + ... + SubordinateN) / N
```

### Q: What if a manager has only one subordinate?
**A**: The single subordinate's salary is used as the average. This may not provide meaningful analysis for very small teams.

### Q: Are the 20% and 50% thresholds configurable?
**A**: **Currently no**, but this could be made configurable in future versions. The thresholds are hardcoded in the Employee model.

## 📊 Reporting Line Analysis

### Q: What is a "reporting line"?
**A**: A reporting line is the chain of managers from an employee up to the CEO. For example:
```
CEO (Level 0) → Manager (Level 1) → Employee (Level 2)
```

### Q: Why is there a limit of 4 levels?
**A**: The 4-level limit is based on organizational best practices:
- **Communication efficiency**: Shorter chains improve communication
- **Decision-making speed**: Fewer levels mean faster decisions
- **Employee engagement**: Closer connection to leadership
- **Organizational agility**: Flatter structures are more responsive

### Q: Can I change the maximum reporting level?
**A**: **Currently no**, but this could be made configurable in future versions. The limit is hardcoded as 4 levels.

### Q: How are reporting levels calculated?
**A**: Levels are calculated recursively starting from the CEO:
- **Level 0**: CEO
- **Level 1**: Direct reports to CEO
- **Level 2**: Reports to Level 1 managers
- **Level 3**: Reports to Level 2 managers
- **Level 4**: Reports to Level 3 managers
- **Level 5+**: Too deep (violation)

### Q: What if I have employees at Level 5 or deeper?
**A**: The application will identify these employees as having "too long reporting lines" and report how many levels they exceed the limit.

## 🔧 Usage and Output

### Q: How do I run the analysis?
**A**: Use this command:
```bash
java -jar target/exercise-0.0.1-SNAPSHOT.jar your-employees.csv
```

### Q: Can I save the output to a file?
**A**: **Yes**, using output redirection:
```bash
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv > results.txt
```

### Q: What does the output include?
**A**: The output includes:
1. **Organizational Summary**: CEO, total employees, salary budget
2. **Manager Salary Analysis**: Underpaid and overpaid managers with amounts
3. **Reporting Line Analysis**: Employees with too long reporting lines

### Q: Can I analyze multiple files at once?
**A**: **Currently no**, but you can use shell scripting:
```bash
for file in *.csv; do
    echo "Analyzing $file..."
    java -jar target/exercise-0.0.1-SNAPSHOT.jar "$file"
done
```

### Q: How do I get help if I don't provide arguments?
**A**: Run the application without arguments:
```bash
java -jar target/exercise-0.0.1-SNAPSHOT.jar
```

## 🐛 Troubleshooting

### Q: The application says "No CEO found" - what does this mean?
**A**: This means no employee in your CSV has an empty `managerId` field. Ensure exactly one employee (the CEO) has no manager.

### Q: I get "Manager not found" errors - what's wrong?
**A**: This means some employees reference manager IDs that don't exist. Check that:
- All `managerId` values reference existing employee IDs
- There are no typos in the IDs
- The manager is defined before their subordinates

### Q: The application crashes with "OutOfMemoryError" - what should I do?
**A**: Increase the Java heap size:
```bash
java -Xmx2g -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
```

### Q: My CSV file has special characters - will this work?
**A**: The application should handle UTF-8 characters, but for best results:
- Use ASCII characters when possible
- Avoid special symbols in names
- Ensure proper UTF-8 encoding

### Q: Can I use Excel files instead of CSV?
**A**: **Currently no**. The application only supports CSV format. You can export Excel files to CSV format.

## 🔮 Future Features

### Q: Will there be a web interface?
**A**: This could be added in future versions. The current architecture supports adding REST API endpoints.

### Q: Can I export results to Excel or PDF?
**A**: **Currently no**, but this is a planned feature for future releases.

### Q: Will there be support for different salary thresholds?
**A**: **Yes**, this is planned for future versions to make the application more flexible.

### Q: Can I analyze historical data or trends?
**A**: **Currently no**, but this could be added for tracking organizational changes over time.

### Q: Will there be industry-specific analysis rules?
**A**: **Potentially**. Different industries may have different compensation models and organizational structures.

## 🧪 Testing and Validation

### Q: How do I test if the application is working correctly?
**A**: Use the provided sample files:
```bash
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
java -jar target/exercise-0.0.1-SNAPSHOT.jar example-complex.csv
```

### Q: How do I run the test suite?
**A**: Use Maven:
```bash
mvn test
```

### Q: What test coverage is provided?
**A**: The test suite covers:
- Employee model validation
- Organizational structure building
- Salary analysis calculations
- Reporting level calculations
- CSV file parsing
- Error handling scenarios

## 📚 Documentation and Support

### Q: Where can I find more detailed documentation?
**A**: Check the documentation in the `docs/` folder:
- [Quick Start Guide](quick-start.md)
- [User Guide](user-guide.md)
- [Architecture Overview](architecture.md)
- [Business Logic](business-logic.md)
- [Troubleshooting Guide](troubleshooting.md)

### Q: How do I report a bug?
**A**: Create an issue in the GitHub repository with:
- Complete error message
- Steps to reproduce
- Sample data (if applicable)
- Environment details

### Q: Can I contribute to the project?
**A**: **Yes!** Contributions are welcome. See the [Contributing Guidelines](contributing.md) for details.

### Q: Is there a support email or contact?
**A**: Use GitHub issues for support. Create an issue with your question or problem.

## 💡 Tips and Best Practices

### Q: What's the best way to prepare my employee data?
**A**: 
1. **Use consistent ID formats**
2. **Ensure all manager references are valid**
3. **Use plain numbers for salaries**
4. **Include all employees in the hierarchy**
5. **Test with a small subset first**

### Q: How often should I run this analysis?
**A**: Consider running the analysis:
- **Quarterly**: For regular organizational health checks
- **After reorganizations**: To validate new structures
- **During compensation reviews**: To ensure fair pay
- **When adding new employees**: To maintain structure integrity

### Q: What should I do with the results?
**A**: 
1. **Review underpaid managers**: Consider salary adjustments
2. **Investigate overpaid managers**: Review compensation justification
3. **Address long reporting lines**: Consider restructuring
4. **Track changes over time**: Monitor organizational health

### Q: Are there any limitations I should be aware of?
**A**: 
- **Single currency**: No support for multiple currencies
- **Static thresholds**: Fixed 20%/50% salary ratios
- **No historical tracking**: Only current state analysis
- **CSV only**: No database or Excel support
- **Command-line only**: No GUI interface

---

*For more detailed information, check the [User Guide](user-guide.md) or [Troubleshooting Guide](troubleshooting.md).*
