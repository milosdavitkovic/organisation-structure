package organisation.structure.exercise.service;

import organisation.structure.exercise.model.AnalysisResult;

/**
 * Interface for the main organizational analysis orchestration service.
 * Provides methods to coordinate the entire analysis process from CSV reading to result output.
 */
public interface IOrganizationalAnalysisService {
    
    /**
     * Analyzes an organization from a CSV file containing employee data.
     * This method orchestrates the entire analysis process including data loading,
     * organizational structure building, and result generation.
     * 
     * @param csvFilePath The path to the CSV file containing employee data
     * @return AnalysisResult containing the complete analysis results
     */
    AnalysisResult analyzeOrganizationFromCsv(String csvFilePath);
    
    /**
     * Validates the input CSV file before processing.
     * 
     * @param csvFilePath The path to the CSV file to validate
     * @return true if the file is valid, false otherwise
     */
    boolean validateInputFile(String csvFilePath);
    
    /**
     * Estimates memory requirements for processing the given file.
     * 
     * @param csvFilePath The path to the CSV file
     * @return Estimated memory requirements in bytes
     */
    long estimateMemoryRequirements(String csvFilePath);
}
