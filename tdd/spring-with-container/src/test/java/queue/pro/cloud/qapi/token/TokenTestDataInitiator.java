package queue.pro.cloud.qapi.token;

import queue.pro.cloud.qapi.token.repo.TokenRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenTestDataInitiator {

    public static void populateIdenticalTokensWithGivenIds(TokenRepo tokenRepo, String...tokens) {
        List<TokenEntity> tokenEntities = Arrays.stream(tokens).map(tokenId ->
                TokenEntity.builder().id(tokenId).categoryWiseTokenSeqNo(0).svcSeqNoInAToken(0)
                        .state(0).tokenPrefix("A").tokenNoInt(1).tokenNoStr("001").tokenIssueDate(LocalDateTime.now())
                        .tkisId("5fb3948c-8930-45e9-909f-3be6bacd83bb").scId("5faddb28-d8c3-4e76-8962-27eaa7d5a8fd")
                        .langId(1).tokenPriority(8)
                        .serviceId("39b77775-4da2-49ee-9618-3cb84e84a384").svcPriority(100)
                        .noOfTry(0).nextTryTime(null).hasAnyFollowupSvc(false).isThisAFollowupSvc(false)
                        .tkisPageNavigation(null).custIdentificationType(null).custIdentificationNumber(null)
                        .customerMobNumber(null).remarks(null).metaData(null).created(LocalDateTime.now())
                        .createdBy("tdd").modified(LocalDateTime.now()).modifiedBy("tdd").build()
        ).toList();
        tokenRepo.saveAllAndFlush(tokenEntities);
    }

    public static void populate2TokenWithCurrentDate(TokenRepo tokenRepo) {
        List<TokenEntity> tokens = new ArrayList<>();
        tokens.add(
                TokenEntity.builder().categoryWiseTokenSeqNo(0).svcSeqNoInAToken(0)
                .state(0).tokenPrefix("A").tokenNoInt(1).tokenNoStr("001").tokenIssueDate(LocalDateTime.now())
                .tkisId("5fb3948c-8930-45e9-909f-3be6bacd83bb").scId("5faddb28-d8c3-4e76-8962-27eaa7d5a8fd")
                .langId(1).tokenPriority(8)
                .serviceId("39b77775-4da2-49ee-9618-3cb84e84a384").svcPriority(100)
                .noOfTry(0).nextTryTime(null).hasAnyFollowupSvc(false).isThisAFollowupSvc(false)
                .tkisPageNavigation(null).custIdentificationType(null).custIdentificationNumber(null)
                .customerMobNumber(null).remarks(null).metaData(null).created(LocalDateTime.now())
                .createdBy("tdd").modified(LocalDateTime.now()).modifiedBy("tdd").build()
        );
        tokens.add(
                TokenEntity.builder().categoryWiseTokenSeqNo(0).svcSeqNoInAToken(0)
                .state(0).tokenPrefix("B").tokenNoInt(1).tokenNoStr("002").tokenIssueDate(LocalDateTime.now())
                .tkisId("5fb3948c-8930-45e9-909f-3be6bacd83bb").scId("5faddb28-d8c3-4e76-8962-27eaa7d5a8fd").langId(1)
                .tokenPriority(8)
                .serviceId("4e392b34-a027-4bef-b906-02631f55be77").svcPriority(100)
                .noOfTry(0).nextTryTime(null)
                .hasAnyFollowupSvc(false).isThisAFollowupSvc(false)
                .tkisPageNavigation(null).custIdentificationType(null).custIdentificationNumber(null)
                .customerMobNumber(null).remarks(null).metaData(null).created(LocalDateTime.now())
                .createdBy("tdd").modified(LocalDateTime.now()).modifiedBy("tdd").build()
        );

        saveAndFlushListOfData(tokens, tokenRepo);

    }

    private static void saveAndFlushListOfData(List<TokenEntity> tokens, TokenRepo tokenRepo){
        tokenRepo.saveAllAndFlush(tokens);
    }
}
