package hr.comping.crud.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class I18nConfig {

    private static final String CROATIAN_COUNTRY_TAG = "hr";
    private static final String ENGLISH_COUNTRY_TAG = "en";

    // This is the list of locales that are supported.
    // These are used to compare and match against requested locales.
    private static final List<Locale> SUPPORTED_LANGUAGES = List.of(Locale.of(CROATIAN_COUNTRY_TAG), Locale.of(ENGLISH_COUNTRY_TAG));

    // Default locale used if the requested locale is not supported.
    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;


    /**
     * Bean to get requested locale by Accept-Language header
     * If the requested locale isn't supported, the DEFAULT_LOCALE is used.
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setSupportedLocales(SUPPORTED_LANGUAGES);
        localeResolver.setDefaultLocale(DEFAULT_LOCALE);
        return localeResolver;
    }

    /**
     * Bean to handle i18n message codes for @Valid annotations
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    /**
     * General config for message bundle
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resource =
                new ResourceBundleMessageSource();
        resource.setBasename("messages");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }
}
