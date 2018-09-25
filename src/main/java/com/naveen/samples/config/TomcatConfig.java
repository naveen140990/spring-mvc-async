package com.naveen.samples.config;

/**
 * @author naveen on 19/8/18
 */

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
//redirection logic would not work in case of CORS as browser will terminate request


    @Value("${httpPort}")
    int httpPort;

    @Value("${http.port.maxThreads}")
    int maxThreads;
/*
    @Value("${server.port}")
    int httpsPort;
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }

        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {

        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(httpPort);
        connector.setSecure(false);
        connector.setRedirectPort(httpsPort);
        return connector;
    }*/

    @Bean
    public ConfigurableServletWebServerFactory tomcatCustomizer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(httpPort);
        ProtocolHandler handler = connector.getProtocolHandler();
        if (handler instanceof AbstractProtocol) {
            AbstractProtocol protocol = (AbstractProtocol) handler;
            protocol.setMaxThreads(maxThreads);
        }

        factory.addAdditionalTomcatConnectors(connector);
        return factory;
    }
}
