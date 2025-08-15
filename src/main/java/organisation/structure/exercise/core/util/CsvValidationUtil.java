package organisation.structure.exercise.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class for CSV file validation operations.
 * Provides methods to validate CSV file format, structure, and content.
 */
@Slf4j
public class CsvValidationUtil {
    
    private static final String CSV_EXTENSION = ".csv";
    private static final String EXPECTED_HEADER = "Id,firstName,lastName,salary,managerId";
    private static final int MIN_COLUMNS = 4;
    private static final int MAX_COLUMNS = 5;
    
    /**
     * Validates if a file is a valid CSV file.
     * 
     * @param filePath The path to the file to validate
     * @return true if the file is a valid CSV file, false otherwise
     */
    public static boolean isValidCsvFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            log.warn("File path is null or empty");
            return false;
        }
        
        File file = new File(filePath);
        
        // Check if file exists
        if (!file.exists()) {
            log.warn("File does not exist: {}", filePath);
            return false;
        }
        
        // Check if file is readable
        if (!file.canRead()) {
            log.warn("File is not readable: {}", filePath);
            return false;
        }
        
        // Check if file has CSV extension
        if (!filePath.toLowerCase().endsWith(CSV_EXTENSION)) {
            log.warn("File does not have CSV extension: {}", filePath);
            return false;
        }
        
        // Check if file is not empty
        if (file.length() == 0) {
            log.warn("File is empty: {}", filePath);
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates the CSV file header.
     * 
     * @param filePath The path to the CSV file
     * @return true if the header is valid, false otherwise
     */
    public static boolean validateCsvHeader(String filePath) {
        try {
            String firstLine = Files.lines(Path.of(filePath))
                    .findFirst()
                    .orElse("");
            
            if (firstLine.trim().equals(EXPECTED_HEADER)) {
                return true;
            } else {
                log.warn("Invalid CSV header. Expected: {}, Found: {}", EXPECTED_HEADER, firstLine);
                return false;
            }
        } catch (IOException e) {
            log.error("Error reading CSV header: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates CSV file content structure.
     * 
     * @param filePath The path to the CSV file
     * @return true if the content structure is valid, false otherwise
     */
    public static boolean validateCsvContent(String filePath) {
        try {
            return Files.lines(Path.of(filePath))
                    .skip(1) // Skip header
                    .filter(line -> !line.trim().isEmpty())
                    .allMatch(CsvValidationUtil::isValidCsvLine);
        } catch (IOException e) {
            log.error("Error validating CSV content: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates a single CSV line.
     * 
     * @param line The CSV line to validate
     * @return true if the line is valid, false otherwise
     */
    public static boolean isValidCsvLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return false;
        }
        
        String[] parts = line.split(",");
        
        // Check column count
        if (parts.length < MIN_COLUMNS || parts.length > MAX_COLUMNS) {
            log.debug("Invalid column count: {} (expected {}-{})", parts.length, MIN_COLUMNS, MAX_COLUMNS);
            return false;
        }
        
        // Validate ID (should not be empty)
        if (parts[0].trim().isEmpty()) {
            log.debug("Employee ID is empty");
            return false;
        }
        
        // Validate first name (should not be empty)
        if (parts[1].trim().isEmpty()) {
            log.debug("First name is empty for employee ID: {}", parts[0]);
            return false;
        }
        
        // Validate last name (should not be empty)
        if (parts[2].trim().isEmpty()) {
            log.debug("Last name is empty for employee ID: {}", parts[0]);
            return false;
        }
        
        // Validate salary (should be a positive number)
        try {
            double salary = Double.parseDouble(parts[3].trim());
            if (salary <= 0) {
                log.debug("Invalid salary: {} for employee ID: {}", salary, parts[0]);
                return false;
            }
        } catch (NumberFormatException e) {
            log.debug("Invalid salary format for employee ID: {}", parts[0]);
            return false;
        }
        
        return true;
    }
    
    /**
     * Estimates the number of employees in the CSV file.
     * 
     * @param filePath The path to the CSV file
     * @return Estimated number of employees (excluding header)
     */
    public static long estimateEmployeeCount(String filePath) {
        try {
            return Files.lines(Path.of(filePath))
                    .skip(1) // Skip header
                    .filter(line -> !line.trim().isEmpty())
                    .count();
        } catch (IOException e) {
            log.error("Error estimating employee count: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Estimates memory requirements for processing the CSV file.
     * 
     * @param filePath The path to the CSV file
     * @return Estimated memory requirements in bytes
     */
    public static long estimateMemoryRequirements(String filePath) {
        try {
            long lineCount = estimateEmployeeCount(filePath);
            // Rough estimate: 1KB per employee for Employee object + overhead
            return lineCount * 1024;
        } catch (Exception e) {
            log.error("Error estimating memory requirements: {}", e.getMessage());
            return 0;
        }
    }
}
