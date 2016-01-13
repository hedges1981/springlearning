package com.sage.lspcs.sls.intouchproxy.web.config;


import com.sage.lspcs.sls.intouchproxy.web.wsclients.WeatherClient;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Created by rowland-hall on 13/01/16
 */
@Configuration
@EnableWs
@ComponentScan( "com.sage.lspcs.sls.hedges.web.wsendpoints" )
public class WebServicesConfig extends WsConfigurerAdapter
{

    //****************Stuff to do with the countries WS we provide *****************************
    @Bean
    public ServletRegistrationBean messageDispatcherServlet( ApplicationContext applicationContext )
    {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext( applicationContext );
        servlet.setTransformWsdlLocations( true );
        return new ServletRegistrationBean( servlet, "/ws/*" );
    }

    @Bean( name = "countries" )
    public DefaultWsdl11Definition defaultWsdl11Definition( XsdSchema countriesSchema )
    {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName( "CountriesPort" );
        wsdl11Definition.setLocationUri( "/ws" );
        wsdl11Definition.setTargetNamespace( "http://spring.io/guides/gs-producing-web-service" );
        wsdl11Definition.setSchema( countriesSchema );
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema()
    {
        return new SimpleXsdSchema( new ClassPathResource( "countries.xsd" ) );
    }

    //*****************Stuff to do with the weather webservice we consume
    @Bean
    public Jaxb2Marshaller weatherServiceMarshaller()
    {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath( "hello.wsdl" );//NOTE: looks like we tell the marshaller a package to 'scan' for its jax-b objects.
        return marshaller;
    }

    @Bean
    public WeatherClient weatherClient()
    {
        WeatherClient client = new WeatherClient();
        client.setDefaultUri( "http://ws.cdyne.com/WeatherWS" );
        client.setMarshaller( weatherServiceMarshaller() );
        client.setUnmarshaller( weatherServiceMarshaller() );

        return client;
    }
}
