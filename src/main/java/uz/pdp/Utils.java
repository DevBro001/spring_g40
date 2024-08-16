package uz.pdp;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component
public class Utils {

    final MessageSource messageSource;

    public Utils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code){
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(code, null,code, locale);
        return message;
    }
    public String getMessage(String code, Object[] args){
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(code, args,code, locale);
        return message;
    }
    public String getMessageWithDefault(String code,String defaultMessage){
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(code, null,defaultMessage, locale);
        return message;
    }
}
