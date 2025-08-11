package organisation.structure.exercise.service.csv.impl;

import organisation.structure.exercise.service.ICsvReaderService;
import organisation.structure.exercise.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DefaultCsvReaderServiceTest {

    @InjectMocks
    private DefaultCsvReaderService csvReaderService;

    @Test
    void testReadEmployeesFromCsv_ValidFile() throws IOException {
        // Given
        String filePath = "src/test/resources/test-data/employees.csv";

        // When
        List<Employee> employees = csvReaderService.readEmployeesFromCsv(filePath);

        // Then
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        
        // Check CEO
        Employee ceo = employees.stream()
                .filter(Employee::isCEO)
                .findFirst()
                .orElse(null);
        assertNotNull(ceo);
        assertEquals("Joe", ceo.getFirstName());
        assertEquals("Doe", ceo.getLastName());
        assertEquals(60000, ceo.getSalary());
    }

    @Test
    void testReadEmployeesFromCsv_LargeFile() throws IOException {
        // Given
        String filePath = "src/test/resources/test-data/large-employees.csv";

        // When
        List<Employee> employees = csvReaderService.readEmployeesFromCsv(filePath);

        // Then
        assertNotNull(employees);
        assertTrue(employees.size() > 100); // Should have many employees
        
        // Check CEO
        Employee ceo = employees.stream()
                .filter(Employee::isCEO)
                .findFirst()
                .orElse(null);
        assertNotNull(ceo);
        assertEquals("John", ceo.getFirstName());
        assertEquals("CEO", ceo.getLastName());
    }

    @Test
    void testValidateCsvFile_ValidFile() {
        // Given
        String filePath = "src/test/resources/test-data/employees.csv";

        // When
        boolean isValid = csvReaderService.validateCsvFile(filePath);

        // Then
        assertTrue(isValid);
    }

    @Test
    void testValidateCsvFile_InvalidFile() {
        // Given
        String filePath = "non-existent-file.csv";

        // When
        boolean isValid = csvReaderService.validateCsvFile(filePath);

        // Then
        assertFalse(isValid);
    }

    @Test
    void testValidateCsvFile_NonCsvFile() {
        // Given
        String filePath = "src/test/resources/test-data/not-a-csv.txt";

        // When
        boolean isValid = csvReaderService.validateCsvFile(filePath);

        // Then
        assertFalse(isValid);
    }

    @Test
    void testGetCsvLineCount_ValidFile() throws IOException {
        // Given
        String filePath = "src/test/resources/test-data/employees.csv";

        // When
        long lineCount = csvReaderService.getCsvLineCount(filePath);

        // Then
        assertTrue(lineCount > 0);
    }

    @Test
    void testGetCsvLineCount_LargeFile() throws IOException {
        // Given
        String filePath = "src/test/resources/test-data/large-employees.csv";

        // When
        long lineCount = csvReaderService.getCsvLineCount(filePath);

        // Then
        assertTrue(lineCount > 100);
    }

    @Test
    void testReadEmployeesFromCsv_InvalidFormat() {
        // Given
        String filePath = "src/test/resources/test-data/invalid-format.csv";

        // When & Then
        assertThrows(IOException.class, () -> {
            csvReaderService.readEmployeesFromCsv(filePath);
        });
    }

    @Test
    void testReadEmployeesFromCsv_EmptyFile() throws IOException {
        // Given
        String filePath = "src/test/resources/test-data/empty.csv";

        // When
        List<Employee> employees = csvReaderService.readEmployeesFromCsv(filePath);

        // Then
        assertNotNull(employees);
        assertTrue(employees.isEmpty());
    }
}
