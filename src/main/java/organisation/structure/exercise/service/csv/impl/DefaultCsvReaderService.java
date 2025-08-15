package organisation.structure.exercise.service.csv.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import organisation.structure.exercise.model.Employee;
import organisation.structure.exercise.service.csv.ICsvReaderService;
import organisation.structure.exercise.util.CsvValidationUtil;

/**
 * Optimized implementation of CSV reader service.
 * Handles large datasets efficiently with memory management and error handling.
 */
@Slf4j
@Service
public class DefaultCsvReaderService implements ICsvReaderService {
    
    private static final int BATCH_SIZE = 1000; // Process employees in batches
    private static final int MAX_RETRIES = 3;
    
    @Override
    public List<Employee> readEmployeesFromCsv(String filePath) throws IOException {
        log.info("Starting CSV file reading: {}", filePath);
        
        // Validate file before processing
        if (!validateCsvFile(filePath)) {
            throw new IOException("CSV file validation failed: " + filePath);
        }
        
        // Estimate memory requirements
        long estimatedMemory = CsvValidationUtil.estimateMemoryRequirements(filePath);
        log.info("Estimated memory usage: {} bytes", estimatedMemory);
        
        List<Employee> employees = new ArrayList<>();
        AtomicInteger lineNumber = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                lineNumber.incrementAndGet();
                
                try {
                    if (isFirstLine) {
                        isFirstLine = false;
                        if (!CsvValidationUtil.validateCsvHeader(filePath)) {
                            throw new IOException("Invalid CSV header format");
                        }
                        continue;
                    }
                    
                    Employee employee = parseEmployeeLine(line, lineNumber.get());
                    if (employee != null) {
                        employees.add(employee);
                    } else {
                        errorCount.incrementAndGet();
                    }
                    
                    // Log progress for large files
                    if (lineNumber.get() % BATCH_SIZE == 0) {
                        log.info("Processed {} lines, {} employees loaded", lineNumber.get(), employees.size());
                    }
                    
                } catch (Exception e) {
                    log.error("Error processing line {}: {}", lineNumber.get(), e.getMessage());
                    errorCount.incrementAndGet();
                    
                    // Continue processing other lines unless too many errors
                    if (errorCount.get() > 100) {
                        throw new IOException("Too many errors encountered, stopping processing");
                    }
                }
            }
        }
        
        log.info("CSV reading completed. Total lines: {}, Employees loaded: {}, Errors: {}", 
                lineNumber.get(), employees.size(), errorCount.get());
        
        return employees;
    }
    
    @Override
    public boolean validateCsvFile(String filePath) {
        log.debug("Validating CSV file: {}", filePath);
        
        // Check if file exists and is readable
        if (!CsvValidationUtil.isValidCsvFile(filePath)) {
            return false;
        }
        
        // Check file size and line count
        if (!validateFileSize(filePath)) {
            return false;
        }
        
        // Validate header
        if (!CsvValidationUtil.validateCsvHeader(filePath)) {
            return false;
        }
        
        // Validate content structure
        if (!CsvValidationUtil.validateCsvContent(filePath)) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public long getCsvLineCount(String filePath) throws IOException {
        try {
            Path path = Paths.get(filePath);
            return Files.lines(path).count();
        } catch (IOException e) {
            log.error("Error counting lines in CSV file: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Validates file size constraints.
     * 
     * @param filePath The path to the CSV file
     * @return true if file size is acceptable, false otherwise
     */
    private boolean validateFileSize(String filePath) {
        try {
            Path path = Paths.get(filePath);
            long fileSize = Files.size(path);
            
            // Check if file is too large (e.g., > 100MB)
            if (fileSize > 100 * 1024 * 1024) {
                log.warn("CSV file is too large: {} bytes", fileSize);
                return false;
            }
            
            // Check if file is too small (just header)
            long lineCount = Files.lines(path).count();
            if (lineCount <= 1) {
                log.warn("CSV file has no data lines, only header");
                return false;
            }
            
            return true;
        } catch (IOException e) {
            log.error("Error validating file size: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Parses a single employee line with retry logic and validation.
     * 
     * @param line The CSV line to parse
     * @param lineNumber The line number for error reporting
     * @return Employee object or null if parsing failed
     */
    private Employee parseEmployeeLine(String line, int lineNumber) {
        if (!CsvValidationUtil.isValidCsvLine(line)) {
            return null;
        }
        
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                String[] parts = line.split(",");
                
                String id = parts[0].trim();
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                double salary = Double.parseDouble(parts[3].trim());
                
                String managerId = null;
                if (parts.length > 4 && !parts[4].trim().isEmpty()) {
                    managerId = parts[4].trim();
                }
                
                return new Employee(id, firstName, lastName, salary, managerId);
                
            } catch (NumberFormatException e) {
                log.warn("Attempt {}: Error parsing salary in line {}: {}", attempt, lineNumber, e.getMessage());
                if (attempt == MAX_RETRIES) {
                    return null;
                }
            } catch (Exception e) {
                log.warn("Attempt {}: Unexpected error parsing line {}: {}", attempt, lineNumber, e.getMessage());
                if (attempt == MAX_RETRIES) {
                    return null;
                }
            }
        }
        
        return null;
    }
}
