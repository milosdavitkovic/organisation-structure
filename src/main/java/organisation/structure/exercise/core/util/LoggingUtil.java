package organisation.structure.exercise.core.util;

import lombok.extern.slf4j.Slf4j;
import organisation.structure.exercise.core.configuration.annotation.UtilClass;

@Slf4j
@UtilClass
public class LoggingUtil {

    public static void logTestStartInfo() {
        log.info("[Organization Analyzes] ------------------------------------------------------------------");
        log.info("[Organization Analyzes] [Organization Analyzes] Analysis of Organization has been started.");
        log.info("[Organization Analyzes] ------------------------------------------------------------------");
    }

    public static String logDoubleValue(final Double value) {
        return String.format(" %.2f ", value);
    }

    public static String logDollarValue(final Double value) {
        return "$" + logDoubleValue(value);
    }
}
