package organisation.structure.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import organisation.structure.exercise.model.AnalysisResult;
import organisation.structure.exercise.service.IOrganizationalAnalysisService;
import organisation.structure.exercise.view.IOrganizationalAnalysisView;

/**
 * Controller implementation for organizational analysis.
 * Handles user requests and coordinates the analysis process following the MVC pattern.
 */
@Controller
public class OrganizationalAnalysisController implements IOrganizationalAnalysisController {
    
    @Autowired
    private IOrganizationalAnalysisService analysisService;
    
    @Autowired
    private IOrganizationalAnalysisView view;
    
    @Override
    public void startAnalysis() {
        // This method will be called when the application starts
        // It can be used to initialize the analysis process
        view.displayInfo("Organizational Analysis System Started");
    }
    
    @Override
    public AnalysisResult analyzeOrganization(String csvFilePath) {
        try {
            // Delegate the analysis to the service layer and get the actual results
            AnalysisResult result = analysisService.analyzeOrganizationFromCsv(csvFilePath);
            
            // Display the results using the view
            view.displayAnalysisResults(result);
            
            return result;
            
        } catch (Exception e) {
            AnalysisResult result = AnalysisResult.failure("Error analyzing organization: " + e.getMessage());
            view.displayError(result.getErrorMessage());
            return result;
        }
    }
    
    @Override
    public void getUsageInstructions() {
        String instructions = """
            Usage: java -jar exercise.jar <path-to-csv-file>
            Example: java -jar exercise.jar employees.csv
            
            CSV file should have the format:
            Id,firstName,lastName,salary,managerId
            123,Joe,Doe,60000,
            124,Martin,Chekov,45000,123
            125,Bob,Ronstad,47000,123
            300,Alice,Hasacat,50000,124
            305,Brett,Hardleaf,34000,300
            """;
        
        // Display the instructions using the view
        view.displayInfo(instructions);
    }
}
