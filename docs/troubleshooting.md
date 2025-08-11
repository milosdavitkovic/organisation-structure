# Troubleshooting Guide

This guide helps you resolve common issues when using the Organizational Structure Analysis application.

## 🚨 Common Issues

### 1. Application Won't Start

#### Issue: "No such file or directory"
```
Error: java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
Exception: java.io.FileNotFoundException: employees.csv (No such file or directory)
```

**Solutions:**
1. **Check file path**: Ensure the CSV file exists in the specified location
2. **Use absolute path**: Try using the full path to the file
   ```bash
   java -jar target/exercise-0.0.1-SNAPSHOT.jar /full/path/to/employees.csv
   ```
3. **Check current directory**: Verify you're in the correct directory
   ```bash
   pwd
   ls -la employees.csv
   ```

#### Issue: "Permission denied"
```
Error: Permission denied when trying to access employees.csv
```

**Solutions:**
1. **Check file permissions**:
   ```bash
   ls -la employees.csv
   chmod 644 employees.csv
   ```
2. **Run with appropriate permissions**: Ensure you have read access to the file

#### Issue: JAR file not found
```
Error: target/exercise-0.0.1-SNAPSHOT.jar: No such file or directory
```

**Solutions:**
1. **Build the application first**:
   ```bash
   mvn clean package
   ```
2. **Verify the JAR was created**:
   ```bash
   ls -la target/exercise-0.0.1-SNAPSHOT.jar
   ```
3. **Check for build errors**: Review the Maven build output for compilation errors

### 2. CSV Parsing Issues

#### Issue: "Invalid line format"
```
Error: Invalid line format: 123,Joe,Doe,60000
```

**Solutions:**
1. **Check CSV structure**: Ensure the file has the correct format
   ```csv
   Id,firstName,lastName,salary,managerId
   123,Joe,Doe,60000,
   124,Martin,Chekov,45000,123
   ```
2. **Verify field count**: Each line should have exactly 5 fields (including empty managerId)
3. **Check for extra commas**: Remove any trailing commas
4. **Validate header**: Ensure the first line matches exactly: `Id,firstName,lastName,salary,managerId`

#### Issue: "Error parsing salary in line"
```
Error: Error parsing salary in line: 123,Joe,Doe,$60000,
```

**Solutions:**
1. **Remove currency symbols**: Use plain numbers without $, €, £, etc.
   ```csv
   # ❌ Wrong
   123,Joe,Doe,$60000,
   
   # ✅ Correct
   123,Joe,Doe,60000,
   ```
2. **Remove commas in numbers**: Use plain numbers without thousands separators
   ```csv
   # ❌ Wrong
   123,Joe,Doe,60,000,
   
   # ✅ Correct
   123,Joe,Doe,60000,
   ```
3. **Check for decimal points**: Use periods, not commas for decimals
   ```csv
   # ❌ Wrong
   123,Joe,Doe,60000,50,
   
   # ✅ Correct
   123,Joe,Doe,60000.50,
   ```

#### Issue: "Manager not found for employee"
```
Error: Manager not found for employee: Martin Chekov
```

**Solutions:**
1. **Check manager ID**: Verify the managerId references an existing employee ID
   ```csv
   # ❌ Wrong - manager ID 999 doesn't exist
   123,Joe,Doe,60000,
   124,Martin,Chekov,45000,999
   
   # ✅ Correct
   123,Joe,Doe,60000,
   124,Martin,Chekov,45000,123
   ```
2. **Check for typos**: Ensure manager IDs match exactly (case-sensitive)
3. **Verify employee order**: Manager should be defined before their subordinates

### 3. Organizational Structure Issues

#### Issue: "No CEO found in the organization!"
```
Error: No CEO found in the organization!
```

**Solutions:**
1. **Ensure CEO has empty managerId**: The CEO should have no manager
   ```csv
   # ❌ Wrong - CEO has a manager
   123,Joe,Doe,60000,456
   
   # ✅ Correct - CEO has no manager
   123,Joe,Doe,60000,
   ```
2. **Check for hidden characters**: Ensure the managerId field is truly empty
3. **Verify only one CEO**: Ensure exactly one employee has no manager

#### Issue: "Multiple CEOs found"
```
Error: Multiple employees found without managers
```

**Solutions:**
1. **Identify all employees without managers**: Check your CSV for multiple empty managerId fields
2. **Designate single CEO**: Ensure only one employee has no manager
3. **Fix organizational structure**: All other employees should have managers

### 4. Build and Compilation Issues

#### Issue: Maven build fails
```
Error: [ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin
```

**Solutions:**
1. **Check Java version**: Ensure you have Java 21 installed
   ```bash
   java -version
   ```
2. **Set JAVA_HOME**: Ensure JAVA_HOME points to Java 21
   ```bash
   echo $JAVA_HOME
   export JAVA_HOME=/path/to/java21
   ```
3. **Clean and rebuild**:
   ```bash
   mvn clean
   mvn package
   ```

#### Issue: Dependencies not found
```
Error: Could not resolve dependencies
```

**Solutions:**
1. **Update Maven dependencies**:
   ```bash
   mvn dependency:resolve
   ```
2. **Check internet connection**: Ensure you can access Maven repositories
3. **Clear Maven cache**:
   ```bash
   mvn dependency:purge-local-repository
   ```

### 5. Runtime Issues

#### Issue: Out of memory
```
Error: java.lang.OutOfMemoryError: Java heap space
```

**Solutions:**
1. **Increase heap size**:
   ```bash
   java -Xmx2g -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
   ```
2. **Reduce data size**: Process smaller CSV files
3. **Check for memory leaks**: Ensure CSV file isn't corrupted

#### Issue: Application hangs
```
Application starts but doesn't complete analysis
```

**Solutions:**
1. **Check for circular references**: Ensure no employee manages themselves
2. **Verify data integrity**: Check for corrupted or invalid data
3. **Monitor system resources**: Check CPU and memory usage
4. **Add timeout**: Consider adding timeout parameters for large datasets

### 6. Output Issues

#### Issue: No analysis results
```
Application runs but shows no findings
```

**Solutions:**
1. **Check data quality**: Ensure you have managers with subordinates
2. **Verify salary data**: Ensure salaries are reasonable and varied
3. **Check organizational depth**: Ensure you have employees at different levels
4. **Review business rules**: Understand what constitutes underpaid/overpaid managers

#### Issue: Unexpected results
```
Results don't match expectations
```

**Solutions:**
1. **Review business logic**: Check the [Business Logic](business-logic.md) documentation
2. **Verify calculations**: Double-check salary ratios and reporting levels
3. **Test with known data**: Use the provided sample files to verify functionality

## 🔧 Diagnostic Commands

### Check System Requirements
```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check available memory
java -XX:+PrintFlagsFinal -version | grep MaxHeapSize
```

### Validate CSV File
```bash
# Check file format
head -5 employees.csv

# Count lines
wc -l employees.csv

# Check for syntax errors
cat employees.csv | awk -F',' 'NF!=5 {print "Line " NR ": " $0}'
```

### Test Application
```bash
# Test with sample data
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv

# Test with verbose output
java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv 2>&1 | tee output.log

# Test with different memory settings
java -Xmx1g -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
```

## 📋 Debugging Checklist

### Before Running Analysis
- [ ] Java 21+ installed and configured
- [ ] Maven 3.6+ installed
- [ ] Application built successfully
- [ ] CSV file exists and is readable
- [ ] CSV format is correct
- [ ] No syntax errors in CSV

### During Analysis
- [ ] Application starts without errors
- [ ] CSV file is parsed successfully
- [ ] Organizational hierarchy is built
- [ ] Analysis completes without hanging
- [ ] Results are displayed

### After Analysis
- [ ] Results make sense
- [ ] No unexpected errors
- [ ] All employees are accounted for
- [ ] Salary calculations are correct
- [ ] Reporting levels are accurate

## 🆘 Getting Help

### Self-Service Resources
1. **Check this troubleshooting guide** for common issues
2. **Review the [User Guide](user-guide.md)** for usage instructions
3. **Consult the [FAQ](faq.md)** for frequently asked questions
4. **Read the [Business Logic](business-logic.md)** documentation for understanding results

### When to Create an Issue
Create a GitHub issue if you encounter:
- **Bugs**: Application crashes or produces incorrect results
- **Missing features**: Functionality that should exist but doesn't
- **Documentation gaps**: Missing or unclear documentation
- **Performance issues**: Application is too slow for reasonable datasets

### Information to Include
When reporting issues, include:
1. **Error message**: Complete error text
2. **Steps to reproduce**: Exact commands and data used
3. **Environment**: OS, Java version, Maven version
4. **Sample data**: Minimal CSV file that reproduces the issue
5. **Expected vs actual behavior**: What you expected vs what happened

### Example Issue Report
```
**Issue**: Application crashes when parsing CSV with special characters

**Steps to reproduce**:
1. Create CSV file with employee name "José"
2. Run: java -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
3. Application crashes with encoding error

**Environment**:
- OS: macOS 12.0
- Java: 21.0.1
- Maven: 3.8.4

**Sample data**:
```csv
Id,firstName,lastName,salary,managerId
123,José,García,60000,
```

**Expected**: Application should handle UTF-8 characters
**Actual**: Application crashes with encoding error
```

---

*For additional help, check the [User Guide](user-guide.md) or create an issue in the repository.*
