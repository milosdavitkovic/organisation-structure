package organisation.structure.exercise.service.csv;

import organisation.structure.exercise.model.Employee;

import java.io.IOException;
import java.util.List;

/**
 * Interface for CSV file reading operations.
 * Provides methods to read and parse employee data from CSV files.
 */
public interface ICsvReaderService {
    
    /**
     * Reads employee data from a CSV file and returns a list of Employee objects.
     * 
     * @param filePath The path to the CSV file to read
     * @return List of Employee objects parsed from the CSV file
     * @throws IOException If there's an error reading the file
     */
    List<Employee> readEmployeesFromCsv(String filePath) throws IOException;
    
    /**
     * Validates CSV file format and structure.
     * 
     * @param filePath The path to the CSV file to validate
     * @return true if the file is valid, false otherwise
     */
    boolean validateCsvFile(String filePath);
    
    /**
     * Gets the number of lines in the CSV file for memory estimation.
     * 
     * @param filePath The path to the CSV file
     * @return Number of lines in the file
     * @throws IOException If there's an error reading the file
     */
    long getCsvLineCount(String filePath) throws IOException;
}
