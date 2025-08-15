package organisation.structure.exercise.service.logging.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import organisation.structure.exercise.core.model.AnalysisResult;
import organisation.structure.exercise.core.model.OrganizationalSummary;
import organisation.structure.exercise.core.util.LoggingUtil;
import organisation.structure.exercise.service.logging.OrganizationalAnalysisLogging;

/**
 * Console-based view implementation for organizational analysis.
 * Displays analysis results to the console output.
 */
@Slf4j
@Service
public class DefaultOrganizationalAnalysisLogging implements OrganizationalAnalysisLogging {

    @Override
    public void displayAnalysisResults(AnalysisResult result) {
        if (result.isSuccess()) {
            log.debug("[Organization Analyzes] === ANALYSIS COMPLETED SUCCESSFULLY ===");

            if (result.getOrganizationalSummary() != null) {
                displayOrganizationalSummary(result.getOrganizationalSummary());
            }

            if (result.getUnderpaidManagers() != null && !result.getUnderpaidManagers().isEmpty()) {
                displayUnderpaidManagers(result.getUnderpaidManagers());
            }

            if (result.getOverpaidManagers() != null && !result.getOverpaidManagers().isEmpty()) {
                displayOverpaidManagers(result.getOverpaidManagers());
            }

            if (result.getEmployeesWithLongReportingLines() != null && !result.getEmployeesWithLongReportingLines().isEmpty()) {
                displayLongReportingLines(result.getEmployeesWithLongReportingLines());
            }

        } else {
            displayError(result.getErrorMessage());
        }
    }

    @Override
    public void displayOrganizationalSummary(OrganizationalSummary summary) {
        log.info("------------------------------------------------------");
        log.info("[Organization Analyzes] === ORGANIZATIONAL SUMMARY ===");
        log.info("[Organization Analyzes] CEO: [{}] (ID: [{}]). ",
                summary.getCeo().getFullName(), summary.getCeo().getId());
        log.info("[Organization Analyzes] Total Employees: [{}]", summary.getTotalEmployees());
        log.info("[Organization Analyzes] Managers: [{}]", summary.getManagers());
        log.info("[Organization Analyzes] Individual Contributors: [{}]", summary.getIndividualContributors());
        log.info("[Organization Analyzes] Total Salary Budget: {}", summary.getTotalSalaryBudget());
        log.info("[Organization Analyzes] Average Salary: {}", summary.getAverageSalary());
    }

    @Override
    public void displayError(String errorMessage) {
        log.error("\n [Organization Analyzes] ERROR: " + errorMessage);
    }

    @Override
    public void displaySuccess(String message) {
        log.info("[Organization Analyzes] SUCCESS: " + message);
    }

    @Override
    public void displayInfo(String message) {
        log.info("[Organization Analyzes] INFO: {}", message);
    }


    private void displayUnderpaidManagers(java.util.List<organisation.structure.exercise.core.model.Employee> managers) {
        log.info("--------------------------------------------------------");
        log.info("[Organization Analyzes]  === MANAGER SALARY ANALYSIS ===");
        log.info("[Organization Analyzes] ⚠ UNDERPAID MANAGERS:");
        for (organisation.structure.exercise.core.model.Employee manager : managers) {
            double underpayment = manager.getUnderpaymentAmount();
            log.info("[Organization Analyzes] [{}] (ID: [{}]): Underpaid by {}",
                    manager.getFullName(), manager.getId(), LoggingUtil.logDollarValue(underpayment));
        }
    }

    private void displayOverpaidManagers(java.util.List<organisation.structure.exercise.core.model.Employee> managers) {
        log.info("[Organization Analyzes] ⚠ OVERPAID MANAGERS:");
        for (organisation.structure.exercise.core.model.Employee manager : managers) {
            double overpayment = manager.getOverpaymentAmount();
            log.info("[Organization Analyzes] [{}] (ID: [{}]): Overpaid by {}",
                    manager.getFullName(), manager.getId(), LoggingUtil.logDollarValue(overpayment));
        }
    }

    private void displayLongReportingLines(java.util.List<organisation.structure.exercise.core.model.Employee> employees) {
        log.info("[Organization Analyzes]  === REPORTING LINE ANALYSIS ===");
        log.info("[Organization Analyzes] ⚠ EMPLOYEES WITH TOO LONG REPORTING LINES:");
        for (organisation.structure.exercise.core.model.Employee employee : employees) {
            int excessLevels = employee.getExcessReportingLevels();
            log.info("[Organization Analyzes] [{}] (ID: [{}]): [{}] levels too deep (Level [{}]). ",
                    employee.getFullName(), employee.getId(),
                    excessLevels, employee.getReportingLevel());
        }
    }
}
