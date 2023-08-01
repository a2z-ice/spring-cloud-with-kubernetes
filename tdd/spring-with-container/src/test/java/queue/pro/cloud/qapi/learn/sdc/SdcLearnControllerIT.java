package queue.pro.cloud.qapi.learn.sdc;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("integration-test")
public class SdcLearnControllerIT extends PostgresSupportedBaseTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testLearnSdcPageWithEmptyDataWithoutAnyPageInfo() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri("/learn/sdc-page")
                .exchange().expectStatus().is2xxSuccessful()
//                .expectBody(String.class)
//                .consumeWith(result -> {
//                    System.out.println(result.getResponseBody());
//                })
                .expectBody()
                .jsonPath("$.content", Matchers.empty())
                .hasJsonPath()
                .jsonPath("$.content.size()").isEqualTo(0)
                .jsonPath("$.size").isEqualTo(10)
                .jsonPath("$.totalPages").isEqualTo(0)
                .jsonPath("$.totalPages").isEqualTo(0)
        ;
    }

    @Test
    public void testLearnSdcPageWithPageableInformation() {

        String requestUri = UriComponentsBuilder.fromPath("/learn/sdc-page")
                .queryParam("page", "0")
                .queryParam("size", "20").toUriString();

        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(requestUri)
                .exchange().expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.pageable.pageSize").isEqualTo(20)
                .jsonPath("$.pageable.offset").isEqualTo(0)
                .jsonPath("$.pageable.pageNumber").isEqualTo(0)
        ;
    }

}
