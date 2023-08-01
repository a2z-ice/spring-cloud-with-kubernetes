package queue.pro.cloud.qapi.learn.sdc;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.web.reactive.server.WebTestClient;
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@WebFluxTest(controllers = SdcLearnController.class)
@AutoConfigureWebTestClient
public class SdcLearnControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    SdcSvc sdcSvc;

    @Test
    void getSdcList_shouldGet200WithEmptyArray(){
        //When
        when(sdcSvc.getSdcInfoList(any())).thenReturn(Flux.empty());

        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri("/learn/sdc")
                .exchange().expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$")
                .isArray()
                .jsonPath("$").isEmpty();
    }

    @Test
    void getSdcList_shouldGet200With2SdcInfoAsList(){
        //Given
        List<SdcInfoEntity> sdcinfoList = new ArrayList<>();
        sdcinfoList.add(SdcInfoEntity.builder().accountHolderRatio(5).build());
        sdcinfoList.add(SdcInfoEntity.builder().accountHolderRatio(3).build());

        //When //when static method must be from org.mockito.Mockito class
        when(sdcSvc.getSdcInfoList(any())).thenReturn(Flux.fromStream(sdcinfoList.stream()));
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri("/learn/sdc")
                .exchange()
        //Then
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$").isArray()
                .jsonPath("$.size()").isEqualTo(2)
                .jsonPath("[0].accountHolderRatio").isEqualTo(5)
                .jsonPath("[1].accountHolderRatio").isEqualTo(3);
    }

    @Test
    void getSdcInfoPage_shouldGet200WithEmptyArray(){
        //When
        when(sdcSvc.getSdcInfoPage(any())).thenReturn(Mono.just(Page.empty()));
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri("/learn/sdc-page")
                .exchange().expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.pageable").isEqualTo("INSTANCE")
                .jsonPath("$.content").exists()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content", Matchers.empty())
                .hasJsonPath()
                .jsonPath("$.last").isEqualTo(Boolean.TRUE)
                .jsonPath("$.totalPages").isEqualTo(1)
                .jsonPath("$.totalElements").isEqualTo(0)
                .jsonPath("$.size").isEqualTo(0)
                .jsonPath("$.number").isEqualTo(0)
                .jsonPath("$.sort").hasJsonPath()
                .jsonPath("$.sort.empty").isEqualTo(Boolean.TRUE)
                .jsonPath("$.sort.unsorted").isEqualTo(Boolean.TRUE)
                .jsonPath("$.sort.sorted").isEqualTo(Boolean.FALSE)
                .jsonPath("$.first").isEqualTo(Boolean.TRUE)
                .jsonPath("$.numberOfElements").isEqualTo(0)
                .jsonPath("$.empty").isEqualTo(Boolean.TRUE);
    }

}
