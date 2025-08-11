package organisation.structure.exercise.controller;

import organisation.structure.exercise.model.AnalysisResult;

/**
 * Interface for the organizational analysis controller.
 * Provides methods to handle user interactions and coordinate the analysis process.
 */
public interface IOrganizationalAnalysisController {
    
    /**
     * Initiates the organizational analysis process.
     * This method handles the main workflow of the application.
     */
    void startAnalysis();
    
    /**
     * Analyzes an organization from a CSV file and returns the results.
     * 
     * @param csvFilePath The path to the CSV file containing employee data
     * @return AnalysisResult containing the complete analysis results
     */
    AnalysisResult analyzeOrganization(String csvFilePath);
    
    /**
     * Displays usage instructions for the application.
     */
    void getUsageInstructions();
}
