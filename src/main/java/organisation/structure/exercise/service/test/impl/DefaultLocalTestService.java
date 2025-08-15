package organisation.structure.exercise.service.test.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import organisation.structure.exercise.core.model.AnalysisResult;
import organisation.structure.exercise.service.analysis.OrganizationalAnalyzerService;
import organisation.structure.exercise.service.logging.OrganizationalAnalysisLogging;
import organisation.structure.exercise.service.test.LocalTestService;

@Slf4j
@Service
public class DefaultLocalTestService implements LocalTestService {

    @Autowired
    private OrganizationalAnalyzerService organizationalAnalyzerService;

    @Autowired
    private OrganizationalAnalysisLogging analysisLogging;

    @Override
    public AnalysisResult analyzeOrganization(String csvFilePath) {
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
}
