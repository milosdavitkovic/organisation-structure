package organisation.structure.exercise.facade.test.local.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import organisation.structure.exercise.core.configuration.annotation.Facade;
import organisation.structure.exercise.core.model.AnalysisResult;
import organisation.structure.exercise.facade.test.local.LocalTestFacade;
import organisation.structure.exercise.service.logging.OrganizationalAnalysisLogging;
import organisation.structure.exercise.service.test.LocalTestService;

@Slf4j
@Facade
public class DefaultLocalTestFacade implements LocalTestFacade {

    @Autowired
    private OrganizationalAnalysisLogging analysisLogging;

    @Autowired
    private LocalTestService localTestService;


    @Override
    public void localTestAnalysis() {
        localTestService.getUsageInstructions();

        final String csvFilePath = "src/main/resources/employees.csv";
        final AnalysisResult result = localTestService.analyzeOrganization(csvFilePath);

        if (!result.isSuccess()) {
            analysisLogging.displayError("Analysis failed: " + result.getErrorMessage());
        } else {
            analysisLogging.displaySuccess("Analysis completed successfully");
        }

    }

    @Override
    public void localExtendedTestAnalysis() {
        localTestService.getUsageInstructions();

        final String csvFilePath = "src/main/resources/large-employees.csv";
        final AnalysisResult result = localTestService.analyzeOrganization(csvFilePath);

        if (!result.isSuccess()) {
            analysisLogging.displayError("Analysis failed: " + result.getErrorMessage());
        } else {
            analysisLogging.displaySuccess("Analysis completed successfully");
        }
    }
}
