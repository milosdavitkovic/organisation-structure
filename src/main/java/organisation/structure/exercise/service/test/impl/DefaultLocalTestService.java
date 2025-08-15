package organisation.structure.exercise.service.test.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import organisation.structure.exercise.core.model.AnalysisResult;
import organisation.structure.exercise.service.analysis.OrganizationalAnalyzerService;
import organisation.structure.exercise.service.test.LocalTestService;
import organisation.structure.exercise.service.logging.OrganizationalAnalysisLogging;

@Slf4j
@Service
public class DefaultLocalTestService implements LocalTestService {

    @Autowired
    private OrganizationalAnalyzerService organizationalAnalyzerService;

    @Autowired
    private OrganizationalAnalysisLogging analysisLogging;

    @Override
    public AnalysisResult analyzeOrganization(String csvFilePath) {
        log.info("Organizational Analysis System Started");

        try {
            final AnalysisResult result = organizationalAnalyzerService.analyzeOrganizationFromCsv(csvFilePath);

            // Display the results using the view
            analysisLogging.displayAnalysisResults(result);

            return result;

        } catch (Exception e) {
            AnalysisResult result = AnalysisResult.failure("Error analyzing organization: " + e.getMessage());
            analysisLogging.displayError(result.getErrorMessage());
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
        analysisLogging.displayInfo(instructions);
    }
}
