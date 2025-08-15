package organisation.structure.exercise.core.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import organisation.structure.exercise.core.configuration.annotation.UtilClass;

import java.text.NumberFormat;
import java.util.Locale;

@Slf4j
@UtilClass
public class LoggingUtil {

    public static void logTestStartInfo() {
        log.info("[Organization Analyzes] ------------------------------------------------------------------");
        log.info("[Organization Analyzes] [Organization Analyzes] Analysis of Organization has been started.");
        log.info("[Organization Analyzes] ------------------------------------------------------------------");
    }

    public static String logDoubleValue(@NonNull final Double value) {
        return String.format(" %.2f ", value);
    }

    public static String logDollarValue(@NonNull final Double value) {
        return "$" + logDoubleValue(value);
    }

    public static String logSwissFrankValue(@NonNull final Double value) {
        final Locale swissLocale = new Locale("de", "CH");
        final NumberFormat chfFormat = NumberFormat.getCurrencyInstance(swissLocale);

        return chfFormat.format(value);
    }
}
