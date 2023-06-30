package queue.pro.cloud.qapi.token;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;
import queue.pro.cloud.qapi.initializer.WiremockInitializer;
import queue.pro.cloud.qapi.token.repo.TokenRepo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration-test")
@ContextConfiguration(initializers = WiremockInitializer.class)
public class TokenControllerIT extends PostgresSupportedBaseTest {

    @Autowired
    TokenRepo tokenRepo;
    @Test
    void testIntegrationTest() {
        assertNotNull(tokenRepo);
    }
}
