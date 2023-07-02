package queue.pro.cloud.qapi.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import queue.pro.cloud.qapi.token.repo.TokenRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {
    @Mock
    TokenRepo tokenRepo;
    @InjectMocks
    private TokenSvc tokenSvc;

    @Test
    public void shouldNotBeNull(){
        assertNotNull(tokenRepo);
        assertNotNull(tokenSvc);
    }

    @Test
    @DisplayName("Test when repository return empty")
    public void testEmptyToken_shouldThrowNoSuchElementException(){
        //Given
        String id = "id";
        //When
        when(tokenRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());
        //Then
        Mono<TokenEntity> token = tokenSvc.getTokenById(id);
        //Verify
        StepVerifier.create(token)
                .expectError(NoSuchElementException.class)
                .verify();
    }

    @Test
    @DisplayName("When repository return existing token should match given id")
    public void testExistingToken_shouldMatchWithGivenId(){
        String id = "id";
        when(tokenRepo.findById(Mockito.anyString())).thenReturn(Optional.of(TokenEntity.builder().id(id).build()));
        Mono<TokenEntity> token = tokenSvc.getTokenById(id);

        StepVerifier.create(token)
                .assertNext(tokenEntity -> {
                    assertThat(tokenEntity.getId()).isEqualTo(id);
                }).verifyComplete();
    }

    @Test
    @DisplayName("Test getTokenAll - Verify Each Element ID and total size is 3")
    public void testGetTokenAll_VerifyEachElementId() {
        //given
        String expectedId = "exampleTokenId";
        List<TokenEntity> tokenEntities = Arrays.asList(
                TokenEntity.builder().id(expectedId).build(),
                TokenEntity.builder().id(expectedId).build(),
                TokenEntity.builder().id(expectedId).build()

        );
        //When
        when(tokenRepo.findAll()).thenReturn(tokenEntities);
        //Then
        Flux<TokenEntity> result = tokenSvc.getTokenAllOrderByIdDesc();
        //Verify
        StepVerifier.create(result)
                .expectNextMatches(tokenEntity -> tokenEntity.getId().equals(expectedId))
                .expectNextMatches(tokenEntity -> tokenEntity.getId().equals(expectedId))
                .expectNextMatches(tokenEntity -> tokenEntity.getId().equals(expectedId))
                .verifyComplete();
        assertThat(tokenEntities).hasSize(3);
    }

    @Test
    @DisplayName("Test getTokenAll - Empty List")
    public void testGetTokenAll_EmptyList() {
        //Given
        List<TokenEntity> emptyTokenEntities = Collections.emptyList();
        //When
        when(tokenRepo.findAll()).thenReturn(emptyTokenEntities);
        //Then
        Flux<TokenEntity> result = tokenSvc.getTokenAllOrderByIdDesc();
        //Verify
        StepVerifier.create(result)
                .expectComplete()
                .verify();

        assertThat(emptyTokenEntities).isEmpty();
    }
}
