package organisation.structure.exercise.service.analysis;

import organisation.structure.exercise.core.model.Employee;
import organisation.structure.exercise.core.model.AnalysisResult;
import organisation.structure.exercise.core.model.OrganizationalSummary;

import java.util.List;

/**
 * Interface for organizational structure analysis operations.
 * Provides methods to analyze organizational hierarchies, salary distributions, and reporting lines.
 */
public interface OrganizationalAnalyzerService {
    
    /**
     * Performs comprehensive analysis of the organizational structure.
     * This includes building the hierarchy, calculating reporting levels, and analyzing manager salaries.
     * 
     * @param employees List of employees to analyze
     * @return AnalysisResult containing all analysis findings
     */
    AnalysisResult analyzeOrganizationalStructure(List<Employee> employees);
    
    /**
     * Builds the organizational hierarchy by establishing reporting relationships.
     * 
     * @param employees List of employees to build hierarchy for
     * @return List of employees with established reporting relationships
     */
    List<Employee> buildOrganizationalHierarchy(List<Employee> employees);
    
    /**
     * Calculates reporting levels for all employees in the organization.
     * 
     * @param employees List of employees with established hierarchy
     */
    void calculateReportingLevels(List<Employee> employees);
    
    /**
     * Analyzes manager salaries to identify underpaid and overpaid managers.
     * 
     * @param employees List of employees to analyze
     * @return AnalysisResult containing salary analysis findings
     */
    AnalysisResult analyzeManagerSalaries(List<Employee> employees);
    
    /**
     * Identifies employees with too long reporting lines.
     * 
     * @param employees List of employees to analyze
     * @return List of employees with reporting lines longer than 4 levels
     */
    List<Employee> findEmployeesWithLongReportingLines(List<Employee> employees);
    
    /**
     * Generates an organizational summary with key metrics.
     * 
     * @param employees List of employees to summarize
     * @return OrganizationalSummary containing key organizational metrics
     */
    OrganizationalSummary generateOrganizationalSummary(List<Employee> employees);

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
