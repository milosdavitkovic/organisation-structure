package organisation.structure.exercise.view;

import organisation.structure.exercise.model.AnalysisResult;
import organisation.structure.exercise.model.OrganizationalSummary;

/**
 * Interface for the organizational analysis view.
 * Provides methods to display analysis results and organizational information to users.
 */
public interface IOrganizationalAnalysisView {
    
    /**
     * Displays the organizational summary.
     * 
     * @param summary The organizational summary to display
     */
    void displayOrganizationalSummary(OrganizationalSummary summary);
    
    /**
     * Displays the complete analysis results.
     * 
     * @param result The analysis result to display
     */
    void displayAnalysisResults(AnalysisResult result);
    
    /**
     * Displays an error message.
     * 
     * @param message The error message to display
     */
    void displayError(String message);
    
    /**
     * Displays a success message.
     * 
     * @param message The success message to display
     */
    void displaySuccess(String message);
    
    /**
     * Displays an informational message.
     * 
     * @param message The informational message to display
     */
    void displayInfo(String message);
}
