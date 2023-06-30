package queue.pro.cloud.qapi.initializer;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import queue.pro.cloud.qapi.stubs.OAuth2Stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1000)
public class WiremockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("About to start WireMockServer");
        WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
        wireMockServer.start();
        log.info("WireMockServer successfully started");

        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();
        rsaKeyGenerator.initializeKeys();
        OAuth2Stubs oAuth2Stubs = new OAuth2Stubs(wireMockServer,rsaKeyGenerator);
        oAuth2Stubs.stubForConfiguration();
        oAuth2Stubs.stubForJWKS();

        applicationContext.getBeanFactory().registerSingleton("wiremockServer",wireMockServer);
        applicationContext.getBeanFactory().registerSingleton("oAuth2Stubs",oAuth2Stubs);
        applicationContext.getBeanFactory().registerSingleton("rsaKeyGenerator",rsaKeyGenerator);

        applicationContext.addApplicationListener(applicationEvent ->{
            if(applicationEvent instanceof ContextClosedEvent){
                log.info("Stopping the WireMockServer");
                wireMockServer.stop();
            }
        });

        TestPropertyValues.of(
                "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:"
                        + wireMockServer.port()
                        + "/auth/realms/spring")
                .applyTo(applicationContext);
    }
}
