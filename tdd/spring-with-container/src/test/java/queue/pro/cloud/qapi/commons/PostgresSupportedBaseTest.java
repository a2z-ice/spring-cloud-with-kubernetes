package queue.pro.cloud.qapi.commons;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.testcontainers.containers.PostgreSQLContainer;
import queue.pro.cloud.qapi.initializer.RSAKeyGenerator;
import queue.pro.cloud.qapi.stubs.OAuth2Stubs;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class PostgresSupportedBaseTest {

    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.3")
            .withDatabaseName("test")
            .withUsername("duke")
            .withPassword("s3cret")
            .withReuse(true);
    @DynamicPropertySource
    static void datasourceProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }

    static {
        container.start();
    }


}
