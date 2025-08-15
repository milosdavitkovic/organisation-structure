# Development Setup Guide

This guide helps developers set up the development environment for the Organizational Structure Analysis project.

## 🛠️ Prerequisites

### Required Software
- **Java 21** or later
- **Maven 3.6+**
- **Git** for version control
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Optional Tools
- **Docker** for containerized development
- **Postman** for API testing (future)
- **JUnit 5** for testing (included in project)

## 🚀 Development Environment Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd organisation-structure
```

### 2. Verify Java Installation
```bash
java -version
# Should show Java 21 or later

mvn -version
# Should show Maven 3.6 or later
```

### 3. Build the Project
```bash
mvn clean compile
```

### 4. Run Tests
```bash
mvn test
```

### 5. Build JAR
```bash
mvn clean package
```

## 🏗️ Project Structure

```
organisation-structure/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── organisation/structure/exercise/
│   │   │       ├── ExerciseApplication.java          # Main application
│   │   │       ├── model/
│   │   │       │   └── Employee.java                 # Core business model
│   │   │       └── service/
│   │   │           ├── CsvReaderService.java         # CSV parsing
│   │   │           ├── OrganizationalAnalyzerService.java  # Analysis logic
│   │   │           └── OrganizationalAnalysisService.java  # Orchestration
│   │   └── resources/
│   │       └── application.properties                # Configuration
│   └── test/
│       └── java/
│           └── organisation/structure/exercise/
│               ├── ExerciseApplicationTests.java     # Application tests
│               └── OrganizationalAnalysisTest.java   # Comprehensive tests
├── docs/                                              # Documentation
├── employees.csv                                      # Sample data
├── example-complex.csv                                # Complex sample data
├── pom.xml                                           # Maven configuration
└── README.md                                         # Project overview
```

## 🔧 IDE Setup

### IntelliJ IDEA
1. **Open Project**: File → Open → Select project directory
2. **Import Maven Project**: Auto-detected, click "Import"
3. **Configure SDK**: File → Project Structure → Project SDK → Java 21
4. **Run Configuration**: Create run configuration for `ExerciseApplication`

### Eclipse
1. **Import Project**: File → Import → Maven → Existing Maven Projects
2. **Select Directory**: Choose project root directory
3. **Configure JRE**: Project Properties → Java Build Path → Libraries → JRE System Library
4. **Run Configuration**: Run → Run Configurations → Java Application

### VS Code
1. **Open Folder**: File → Open Folder → Select project directory
2. **Install Extensions**:
   - Extension Pack for Java
   - Maven for Java
   - Spring Boot Extension Pack
3. **Configure Java**: Set Java 21 as default

## 🧪 Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=OrganizationalAnalysisTest

# Run specific test method
mvn test -Dtest=OrganizationalAnalysisTest#testEmployeeCreation

# Run with coverage
mvn test jacoco:report
```

### Test Structure
- **Unit Tests**: Test individual components in isolation
- **Integration Tests**: Test component interactions
- **End-to-End Tests**: Test complete workflows

### Writing Tests
```java
@Test
void testNewFeature() {
    // Arrange
    Employee employee = new Employee("123", "John", "Doe", 50000, null);
    
    // Act
    boolean result = employee.isCEO();
    
    // Assert
    assertTrue(result);
}
```

## 🔄 Development Workflow

### 1. Feature Development
```bash
# Create feature branch
git checkout -b feature/new-analysis-type

# Make changes
# ... edit files ...

# Run tests
mvn test

# Commit changes
git add .
git commit -m "Add new analysis type"

# Push branch
git push origin feature/new-analysis-type
```

### 2. Code Quality
```bash
# Check code style
mvn checkstyle:check

# Run static analysis
mvn spotbugs:check

# Format code
mvn spring-javaformat:apply
```

### 3. Documentation
- Update relevant documentation in `docs/`
- Add Javadoc comments for new methods
- Update README.md if needed

## 🐛 Debugging

### Debug Mode
```bash
# Run with debug output
mvn spring-boot:run -Dspring-boot.run.arguments="employees.csv" -Ddebug=true
```

### IDE Debugging
1. **Set Breakpoints**: Click in gutter next to line numbers
2. **Debug Configuration**: Create debug run configuration
3. **Step Through**: Use debug controls to step through code

### Logging
```java
// Add logging to your classes
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger logger = LoggerFactory.getLogger(YourClass.class);

logger.debug("Processing employee: {}", employee.getId());
logger.info("Analysis completed successfully");
logger.error("Error processing file: {}", e.getMessage());
```

## 📦 Building and Packaging

### Development Build
```bash
mvn clean compile
```

### Test Build
```bash
mvn clean test
```

### Production Build
```bash
mvn clean package
```

### Docker Build (Future)
```bash
docker build -t org-analysis .
docker run org-analysis employees.csv
```

## 🔍 Code Analysis Tools

### Maven Plugins
```xml
<!-- Checkstyle for code style -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
</plugin>

<!-- SpotBugs for static analysis -->
<plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
</plugin>

<!-- JaCoCo for code coverage -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
</plugin>
```

### IDE Integration
- **IntelliJ IDEA**: Built-in code analysis
- **Eclipse**: SonarLint plugin
- **VS Code**: SonarLint extension

## 🚀 Performance Testing

### Memory Usage
```bash
# Monitor memory usage
java -Xmx1g -jar target/exercise-0.0.1-SNAPSHOT.jar large-employees.csv
```

### Performance Profiling
```bash
# Run with profiling
java -agentpath:/path/to/profiler -jar target/exercise-0.0.1-SNAPSHOT.jar employees.csv
```

### Load Testing (Future)
```bash
# Test with large datasets
for i in {1..10}; do
    java -jar target/exercise-0.0.1-SNAPSHOT.jar large-employees.csv
done
```

## 🔧 Configuration

### Application Properties
```properties
# src/main/resources/application.properties
# Logging configuration
logging.level.organisation.structure.exercise=DEBUG
logging.pattern.console=[{}]{HH:mm:ss} %-5level %logger{36} - %msg. 

# Analysis thresholds (future)
analysis.salary.underpaid.threshold=1.2
analysis.salary.overpaid.threshold=1.5
analysis.reporting.max-levels=4
```

### Environment Variables
```bash
# Set environment variables
export JAVA_OPTS="-Xmx2g -Xms1g"
export ANALYSIS_DEBUG=true
```

## 📚 Development Resources

### Documentation
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add Javadoc comments for public methods
- Keep methods small and focused

### Best Practices
- Write tests for new features
- Update documentation when changing APIs
- Use meaningful commit messages
- Review code before merging

## 🐳 Docker Development (Future)

### Dockerfile
```dockerfile
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/exercise-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose
```yaml
version: '3.8'
services:
  app:
    build: .
    volumes:
      - ./data:/app/data
    command: ["data/employees.csv"]
```

## 🔄 Continuous Integration

### GitHub Actions (Future)
```yaml
name: CI/CD Pipeline
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '21'
      - run: mvn clean test
      - run: mvn package
```

## 📝 Contributing

### Before Contributing
1. **Fork the repository**
2. **Create a feature branch**
3. **Make your changes**
4. **Add tests**
5. **Update documentation**
6. **Submit a pull request**

### Code Review Checklist
- [ ] Code follows style guidelines
- [ ] Tests pass
- [ ] Documentation is updated
- [ ] No breaking changes (or documented)
- [ ] Performance impact considered

---

*For more information, see the [Contributing Guidelines](contributing.md) and [Architecture Documentation](architecture.md).*
