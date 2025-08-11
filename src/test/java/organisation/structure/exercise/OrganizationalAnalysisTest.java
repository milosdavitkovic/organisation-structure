package organisation.structure.exercise;

import organisation.structure.exercise.model.Employee;
import organisation.structure.exercise.service.CsvReaderService;
import organisation.structure.exercise.service.OrganizationalAnalyzerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrganizationalAnalysisTest {

    @Autowired
    private CsvReaderService csvReaderService;

    @Autowired
    private OrganizationalAnalyzerService analyzerService;

    private List<Employee> testEmployees;

    @BeforeEach
    void setUp() throws IOException {
        // Create test data programmatically
        testEmployees = List.of(
            new Employee("123", "Joe", "Doe", 60000, null), // CEO
            new Employee("124", "Martin", "Chekov", 45000, "123"),
            new Employee("125", "Bob", "Ronstad", 47000, "123"),
            new Employee("300", "Alice", "Hasacat", 50000, "124"),
            new Employee("305", "Brett", "Hardleaf", 34000, "300"),
            new Employee("306", "Sarah", "Johnson", 38000, "300"),
            new Employee("307", "Michael", "Brown", 42000, "125"),
            new Employee("308", "Emily", "Davis", 39000, "125"),
            new Employee("309", "David", "Wilson", 41000, "123"), // Direct report to CEO
            new Employee("310", "Lisa", "Anderson", 36000, "309"),
            new Employee("311", "James", "Taylor", 44000, "309"),
            new Employee("312", "Emma", "Thomas", 37000, "311"),
            new Employee("313", "Christopher", "Lee", 43000, "311"),
            new Employee("314", "Amanda", "White", 35000, "312"),
            new Employee("315", "Daniel", "Harris", 40000, "312"),
            new Employee("316", "John", "Deep", 32000, "314") // Level 5 - too deep
        );
    }

    @Test
    void testEmployeeCreation() {
        Employee employee = new Employee("123", "Joe", "Doe", 60000, null);
        assertEquals("123", employee.getId());
        assertEquals("Joe", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals(60000, employee.getSalary());
        assertTrue(employee.isCEO());
    }

    @Test
    void testOrganizationalStructure() {
        analyzerService.analyzeOrganizationalStructure(testEmployees);
        
        // Find CEO
        Employee ceo = testEmployees.stream()
                .filter(Employee::isCEO)
                .findFirst()
                .orElse(null);
        
        assertNotNull(ceo);
        assertEquals("Joe Doe", ceo.getFullName());
        assertEquals(0, ceo.getReportingLevel());
        
        // Check that CEO has subordinates
        assertTrue(ceo.hasSubordinates());
        assertEquals(3, ceo.getDirectSubordinates().size()); // Martin, Bob, and David (all direct reports)
    }

    @Test
    void testReportingLevels() {
        analyzerService.analyzeOrganizationalStructure(testEmployees);
        
        // Find employees at different levels
        Employee ceo = testEmployees.stream()
                .filter(Employee::isCEO)
                .findFirst()
                .orElse(null);
        
        Employee level1 = testEmployees.stream()
                .filter(e -> e.getId().equals("124"))
                .findFirst()
                .orElse(null);
        
        Employee level2 = testEmployees.stream()
                .filter(e -> e.getId().equals("300"))
                .findFirst()
                .orElse(null);
        
        Employee level3 = testEmployees.stream()
                .filter(e -> e.getId().equals("305"))
                .findFirst()
                .orElse(null);
        
        Employee level4 = testEmployees.stream()
                .filter(e -> e.getId().equals("314"))
                .findFirst()
                .orElse(null);
        
        assertNotNull(ceo);
        assertNotNull(level1);
        assertNotNull(level2);
        assertNotNull(level3);
        assertNotNull(level4);
        
        assertEquals(0, ceo.getReportingLevel());
        assertEquals(1, level1.getReportingLevel());
        assertEquals(2, level2.getReportingLevel());
        assertEquals(3, level3.getReportingLevel());
        assertEquals(4, level4.getReportingLevel());
        
        // Test long reporting line detection
        assertFalse(ceo.hasTooLongReportingLine());
        assertFalse(level1.hasTooLongReportingLine());
        assertFalse(level2.hasTooLongReportingLine());
        assertFalse(level3.hasTooLongReportingLine());
        assertFalse(level4.hasTooLongReportingLine()); // Level 4 is acceptable
        
        Employee level5 = testEmployees.stream()
                .filter(e -> e.getId().equals("316"))
                .findFirst()
                .orElse(null);
        
        assertNotNull(level5);
        assertEquals(5, level5.getReportingLevel());
        assertTrue(level5.hasTooLongReportingLine());
        assertEquals(1, level5.getExcessReportingLevels());
    }

    @Test
    void testManagerSalaryAnalysis() {
        analyzerService.analyzeOrganizationalStructure(testEmployees);
        
        // Find a manager with subordinates
        Employee manager = testEmployees.stream()
                .filter(e -> e.getId().equals("124")) // Martin Chekov
                .findFirst()
                .orElse(null);
        
        assertNotNull(manager);
        assertTrue(manager.hasSubordinates());
        
        // Calculate average subordinate salary
        double avgSubordinateSalary = manager.getAverageSubordinateSalary();
        assertTrue(avgSubordinateSalary > 0);
        
        // Test salary ratio calculations
        double ratio = manager.getSalaryRatioToAverage();
        assertTrue(ratio > 0);
    }

    @Test
    void testCsvReaderService() throws IOException {
        // Test with the sample CSV file
        List<Employee> employees = csvReaderService.readEmployeesFromCsv("employees.csv");
        
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        
        // Check that CEO is present
        Employee ceo = employees.stream()
                .filter(Employee::isCEO)
                .findFirst()
                .orElse(null);
        
        assertNotNull(ceo);
        assertEquals("Joe Doe", ceo.getFullName());
    }
}
