package organisation.structure.exercise.service.test;

import organisation.structure.exercise.core.model.AnalysisResult;

public interface LocalTestService {


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
