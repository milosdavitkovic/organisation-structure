package organisation.structure.exercise.facade.test.local.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import organisation.structure.exercise.core.configuration.annotation.Facade;
import organisation.structure.exercise.core.model.AnalysisResult;
import organisation.structure.exercise.core.util.LoggingUtil;
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
        LoggingUtil.logTestStartInfo();

        final String csvFilePath = "src/test/resources/test-data/employees.csv";
        final AnalysisResult result = localTestService.analyzeOrganization(csvFilePath);

        if (!result.isSuccess()) {
            analysisLogging.displayError("[Organization Analyzes] Local Analysis failed: " + result.getErrorMessage());
        } else {
            analysisLogging.displaySuccess("[Organization Analyzes] Local analysis of organization completed successfully.");
        }

    }

    @Override
    public void localExtendedTestAnalysis() {
        LoggingUtil.logTestStartInfo();

        final String csvFilePath = "src/test/resources/test-data/large-employees.csv";
        final AnalysisResult result = localTestService.analyzeOrganization(csvFilePath);

        if (!result.isSuccess()) {
            analysisLogging.displayError("[Organization Analyzes] Local extended analysis failed: " + result.getErrorMessage());
        } else {
            analysisLogging.displaySuccess("[Organization Analyzes] Local extended analysis of organization completed successfully.");
        }
    }
}
