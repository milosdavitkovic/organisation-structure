package organisation.structure.exercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import organisation.structure.exercise.model.AnalysisResult;
import organisation.structure.exercise.model.Employee;
import organisation.structure.exercise.service.IOrganizationalAnalysisService;
import organisation.structure.exercise.service.analysis.IOrganizationalAnalyzerService;
import organisation.structure.exercise.service.csv.ICsvReaderService;
import organisation.structure.exercise.util.CsvValidationUtil;

/**
 * Default implementation of the organizational analysis service.
 * Orchestrates the entire analysis process from CSV reading to result generation.
 */
@Slf4j
@Service
public class DefaultOrganizationalAnalysisService implements IOrganizationalAnalysisService {
    
    @Autowired
    private ICsvReaderService csvReaderService;
    
    @Autowired
    private IOrganizationalAnalyzerService analyzerService;
    
    @Override
    public AnalysisResult analyzeOrganizationFromCsv(String csvFilePath) {
        log.info("Starting organizational analysis from CSV file: {}", csvFilePath);
        
        try {
            // Validate input file
            if (!validateInputFile(csvFilePath)) {
                log.error("Input file validation failed: {}", csvFilePath);
                return AnalysisResult.failure("Invalid input file: " + csvFilePath);
            }
            
            // Read employees from CSV
            List<Employee> employees = csvReaderService.readEmployeesFromCsv(csvFilePath);
            log.info("Successfully loaded {} employees from CSV", employees.size());
            
            // Perform comprehensive analysis
            AnalysisResult result = analyzerService.analyzeOrganizationalStructure(employees);
            
            if (result.isSuccess()) {
                log.info("Organizational analysis completed successfully");
            } else {
                log.error("Organizational analysis failed: {}", result.getErrorMessage());
            }
            
            return result;
            
        } catch (Exception e) {
            log.error("Error during organizational analysis: {}", e.getMessage(), e);
            return AnalysisResult.failure("Error during analysis: " + e.getMessage());
        }
    }
    
    @Override
    public boolean validateInputFile(String csvFilePath) {
        log.debug("Validating input file: {}", csvFilePath);
        
        // Use the CSV validation utility
        if (!CsvValidationUtil.isValidCsvFile(csvFilePath)) {
            log.warn("File validation failed: {}", csvFilePath);
            return false;
        }
        
        // Use the CSV reader service validation
        if (!csvReaderService.validateCsvFile(csvFilePath)) {
            log.warn("CSV validation failed: {}", csvFilePath);
            return false;
        }
        
        log.debug("Input file validation successful: {}", csvFilePath);
        return true;
    }
    
    @Override
    public long estimateMemoryRequirements(String csvFilePath) {
        log.debug("Estimating memory requirements for: {}", csvFilePath);
        
        try {
            // Use the CSV validation utility for memory estimation
            long estimatedMemory = CsvValidationUtil.estimateMemoryRequirements(csvFilePath);
            log.debug("Estimated memory requirements: {} bytes", estimatedMemory);
            return estimatedMemory;
        } catch (Exception e) {
            log.warn("Error estimating memory requirements: {}", e.getMessage());
            return 0;
        }
    }
}
