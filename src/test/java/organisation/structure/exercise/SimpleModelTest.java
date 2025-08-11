package organisation.structure.exercise;

import organisation.structure.exercise.model.Employee;
import organisation.structure.exercise.model.AnalysisResult;
import organisation.structure.exercise.model.OrganizationalSummary;
import organisation.structure.exercise.util.EmployeeValidationUtil;
import organisation.structure.exercise.util.CsvValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleModelTest {

    private List<Employee> testEmployees;

    @BeforeEach
    void setUp() {
        // Create test data programmatically
        testEmployees = List.of(
            new Employee("123", "Joe", "Doe", 60000, null), // CEO
            new Employee("124", "Martin", "Chekov", 45000, "123"),
            new Employee("125", "Bob", "Ronstad", 47000, "123"),
            new Employee("300", "Alice", "Hasacat", 50000, "124"),
            new Employee("305", "Brett", "Hardleaf", 34000, "300")
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
        assertEquals("Joe Doe", employee.getFullName());
    }

    @Test
    void testEmployeeWithManager() {
        Employee employee = new Employee("124", "Martin", "Chekov", 45000, "123");
        assertEquals("124", employee.getId());
        assertEquals("Martin", employee.getFirstName());
        assertEquals("Chekov", employee.getLastName());
        assertEquals(45000, employee.getSalary());
        assertEquals("123", employee.getManagerId());
        assertFalse(employee.isCEO());
        assertEquals("Martin Chekov", employee.getFullName());
    }

    @Test
    void testEmployeeSalaryCalculations() {
        Employee manager = new Employee("124", "Martin", "Chekov", 45000, "123");
        Employee subordinate1 = new Employee("300", "Alice", "Hasacat", 50000, "124");
        Employee subordinate2 = new Employee("305", "Brett", "Hardleaf", 34000, "124");
        
        // Add subordinates to manager
        manager.getDirectSubordinates().add(subordinate1);
        manager.getDirectSubordinates().add(subordinate2);
        
        // Test average subordinate salary
        double avgSalary = manager.getAverageSubordinateSalary();
        assertEquals(42000.0, avgSalary, 0.01); // (50000 + 34000) / 2
        
        // Test salary ratio
        double ratio = manager.getSalaryRatioToAverage();
        assertEquals(1.071, ratio, 0.01); // 45000 / 42000
        
        // Test underpaid/overpaid detection
        assertTrue(manager.isUnderpaid()); // ratio 1.071 < 1.2 threshold (underpaid)
        assertFalse(manager.isOverpaid()); // ratio 1.071 < 1.5 threshold
    }

    @Test
    void testEmployeeReportingLevels() {
        Employee employee = new Employee("305", "Brett", "Hardleaf", 34000, "300");
        
        // Test initial reporting level
        assertEquals(0, employee.getReportingLevel());
        assertFalse(employee.hasTooLongReportingLine());
        assertEquals(0, employee.getExcessReportingLevels());
        
        // Set reporting level to test long reporting line detection
        employee.setReportingLevel(5);
        assertTrue(employee.hasTooLongReportingLine());
        assertEquals(1, employee.getExcessReportingLevels());
        
        employee.setReportingLevel(4);
        assertFalse(employee.hasTooLongReportingLine());
        assertEquals(0, employee.getExcessReportingLevels());
    }

    @Test
    void testAnalysisResultCreation() {
        // Test successful result
        AnalysisResult successResult = AnalysisResult.success(null, null, null, null);
        assertTrue(successResult.isSuccess());
        assertNull(successResult.getErrorMessage());
        
        // Test failure result
        AnalysisResult failureResult = AnalysisResult.failure("Test error");
        assertFalse(failureResult.isSuccess());
        assertEquals("Test error", failureResult.getErrorMessage());
    }

    @Test
    void testOrganizationalSummaryCreation() {
        Employee ceo = new Employee("123", "Joe", "Doe", 60000, null);
        
        OrganizationalSummary summary = new OrganizationalSummary(
            ceo, 5, 2, 100000.0, 20000.0, 3
        );
        
        assertEquals(ceo, summary.getCeo());
        assertEquals(5, summary.getTotalEmployees());
        assertEquals(2, summary.getManagers());
        assertEquals(3, summary.getIndividualContributors()); // 5 - 2
        assertEquals(100000.0, summary.getTotalSalaryBudget(), 0.01);
        assertEquals(20000.0, summary.getAverageSalary(), 0.01);
        assertEquals(3, summary.getMaxReportingLevel());
    }

    @Test
    void testEmployeeValidationUtil() {
        // Test valid employees
        assertTrue(EmployeeValidationUtil.validateEmployees(testEmployees));
        
        // Test invalid employee (null)
        List<Employee> invalidEmployees = new ArrayList<>(testEmployees);
        invalidEmployees.add(null);
        assertFalse(EmployeeValidationUtil.validateEmployees(invalidEmployees));
        
        // Test duplicate IDs
        List<Employee> duplicateEmployees = new ArrayList<>(testEmployees);
        duplicateEmployees.add(new Employee("123", "Duplicate", "ID", 50000, null));
        assertFalse(EmployeeValidationUtil.validateEmployees(duplicateEmployees));
    }

    @Test
    void testEmployeeValidationUtilIndividualValidation() {
        Employee validEmployee = new Employee("123", "Joe", "Doe", 60000, null);
        assertTrue(EmployeeValidationUtil.isValidEmployee(validEmployee));
        
        // Test invalid employee (null)
        assertFalse(EmployeeValidationUtil.isValidEmployee(null));
        
        // Test invalid employee (empty ID)
        Employee invalidEmployee = new Employee("", "Joe", "Doe", 60000, null);
        assertFalse(EmployeeValidationUtil.isValidEmployee(invalidEmployee));
        
        // Test invalid employee (empty first name)
        invalidEmployee = new Employee("123", "", "Doe", 60000, null);
        assertFalse(EmployeeValidationUtil.isValidEmployee(invalidEmployee));
        
        // Test invalid employee (empty last name)
        invalidEmployee = new Employee("123", "Joe", "", 60000, null);
        assertFalse(EmployeeValidationUtil.isValidEmployee(invalidEmployee));
        
        // Test invalid employee (negative salary)
        invalidEmployee = new Employee("123", "Joe", "Doe", -1000, null);
        assertFalse(EmployeeValidationUtil.isValidEmployee(invalidEmployee));
    }

    @Test
    void testCsvValidationUtil() {
        // Test valid CSV file path
        assertTrue(CsvValidationUtil.isValidCsvFile("src/test/resources/test-data/employees.csv"));
        
        // Test invalid file path
        assertFalse(CsvValidationUtil.isValidCsvFile("non-existent-file.csv"));
        
        // Test non-CSV file
        assertFalse(CsvValidationUtil.isValidCsvFile("src/test/resources/test-data/not-a-csv.txt"));
    }

    @Test
    void testCsvValidationUtilLineValidation() {
        // Test valid CSV line
        assertTrue(CsvValidationUtil.isValidCsvLine("123,Joe,Doe,60000,"));
        assertTrue(CsvValidationUtil.isValidCsvLine("124,Martin,Chekov,45000,123"));
        
        // Test invalid CSV lines
        assertFalse(CsvValidationUtil.isValidCsvLine("")); // Empty line
        assertFalse(CsvValidationUtil.isValidCsvLine("123,Joe")); // Too few columns
        assertFalse(CsvValidationUtil.isValidCsvLine("123,Joe,Doe,invalid,123")); // Invalid salary
        assertFalse(CsvValidationUtil.isValidCsvLine("123,,Doe,60000,")); // Empty first name
        assertFalse(CsvValidationUtil.isValidCsvLine("123,Joe,,60000,")); // Empty last name
        assertFalse(CsvValidationUtil.isValidCsvLine(",Joe,Doe,60000,")); // Empty ID
    }
}
