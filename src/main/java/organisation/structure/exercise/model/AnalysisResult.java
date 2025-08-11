package organisation.structure.exercise.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Represents the result of an organizational analysis.
 * Contains all analysis findings and summary information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResult {
    
    private boolean success;
    private String errorMessage;
    private OrganizationalSummary organizationalSummary;
    private List<Employee> underpaidManagers;
    private List<Employee> overpaidManagers;
    private List<Employee> employeesWithLongReportingLines;
    
    /**
     * Creates a successful analysis result.
     */
    public static AnalysisResult success(OrganizationalSummary summary, 
                                       List<Employee> underpaidManagers,
                                       List<Employee> overpaidManagers,
                                       List<Employee> employeesWithLongReportingLines) {
        return new AnalysisResult(true, null, summary, underpaidManagers, 
                                overpaidManagers, employeesWithLongReportingLines);
    }
    
    /**
     * Creates a failed analysis result with error message.
     */
    public static AnalysisResult failure(String errorMessage) {
        return new AnalysisResult(false, errorMessage, null, null, null, null);
    }
    
    /**
     * Checks if the analysis was successful.
     */
    public boolean isSuccess() {
        return success;
    }
    
    /**
     * Gets the error message if the analysis failed.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * Gets the organizational summary.
     */
    public OrganizationalSummary getOrganizationalSummary() {
        return organizationalSummary;
    }
    
    /**
     * Gets the list of underpaid managers.
     */
    public List<Employee> getUnderpaidManagers() {
        return underpaidManagers;
    }
    
    /**
     * Gets the list of overpaid managers.
     */
    public List<Employee> getOverpaidManagers() {
        return overpaidManagers;
    }
    
    /**
     * Gets the list of employees with too long reporting lines.
     */
    public List<Employee> getEmployeesWithLongReportingLines() {
        return employeesWithLongReportingLines;
    }
}
