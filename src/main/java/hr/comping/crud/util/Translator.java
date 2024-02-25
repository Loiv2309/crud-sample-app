package hr.comping.crud.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Class used to translate i18n message codes defined in the resource bundle
 */
public class Translator {

    private Translator() {
        throw new IllegalStateException("Utility class");
    }
    private static final String BUNDLE_BASE_NAME ="messages";

    /**
     * Used to translate a message using a message code
     * @param messageCode The message code
     * @return The translated message
     */
    public static String translate(String messageCode) {
        return ResourceBundle.getBundle(BUNDLE_BASE_NAME,LocaleContextHolder.getLocale()).getString(messageCode);
    }

    /**
     * Used to translate a message using a message code
     * Parameters can be provided to fill variable data in message bundle entires
     * @param messageCode The message code
     * @param parameters The parameters used to fill variable data
     * @return The translated message
     */
    public static String translate(String messageCode, Object... parameters) {
        String pattern = ResourceBundle.getBundle(BUNDLE_BASE_NAME,LocaleContextHolder.getLocale()).getString(messageCode);
        return MessageFormat.format(pattern, parameters);
    }
}