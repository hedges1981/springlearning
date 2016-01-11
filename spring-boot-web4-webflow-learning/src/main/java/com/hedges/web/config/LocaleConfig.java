package com.hedges.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Created by rowland-hall on 11/01/16
 */
@Configuration
public class LocaleConfig extends WebMvcConfigurerAdapter
{
    //NOTE: to demo the locale changing, go to localhost:8080/springtagsdemo and change the language.
    //NOTE: the below for configuring locale related stuff:
    //NOTE: with spring boot we get the ResourceBundleMessageSource that reads the messages and the messages.properties file by default,
    // but we still need the below beans to allow for locale switching.
    @Bean
    public LocaleResolver localeResolver()
    {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale( Locale.UK);
        return slr;
    }

    //NOTE: with the below, any urls with e.g. ?language=es will cause it to switch language.
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("language");
        return lci;
    }

    //NOTE: finally we need this overridden method to add the interceptor in.
    // NOTE that using this overridden method is the same as using <mvc:interceptors> in xml.
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
