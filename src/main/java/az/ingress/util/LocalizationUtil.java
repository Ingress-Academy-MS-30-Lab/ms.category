package az.ingress.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ResourceBundle;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

public enum LocalizationUtil {
    LOCALIZATION_UTIL;

    public String getMessageByKey(String bundle, String key) {
        var resourceBundle = ResourceBundle.getBundle(bundle, getLocale());
        return resourceBundle.getString(key);
    }

    public String getLanguage() {
        var locale = LocaleContextHolder.getLocale();
        return locale.getLanguage().toLowerCase();
    }
}