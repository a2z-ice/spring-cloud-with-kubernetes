package queue.pro.cloud.qapi.learn.sdc;


import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;
import queue.pro.cloud.qapi.sdc.SdcInfoRepo;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers(disabledWithoutDocker = true)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SdcLearnRepoTest extends PostgresSupportedBaseTest {
    @Autowired
    SdcInfoRepo sdcInfoRepo;
    @Test
    void notNull() {
        assertNotNull(sdcInfoRepo);
    }
    @Test
    public void getSdcInfoPage_expectEmptyPage(){
        //Given
        //When
        Page<SdcInfoEntity> sdcInfoPage = sdcInfoRepo.findAll(PageRequest.of(0, 10));
        //Then
        assertNotNull(sdcInfoPage);
        assertTrue(sdcInfoPage.getContent().isEmpty(),"The content is empty");
        assertEquals(0,sdcInfoPage.getTotalPages(), "The total pages should be 0");
    }
    @Test
    public void getSdcInfoPage_sortByNameShouldGetSortedResult(){
        //Given
        sdcInfoRepo.saveAll(add2SdcInfoEntity());
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        //When
        Page<SdcInfoEntity> unsortedSdcInfo = sdcInfoRepo.findAll(PageRequest.of(0,2));
        assertNotNull(unsortedSdcInfo,"Must return value so the object must not null");
        List<SdcInfoEntity> unsortedList = unsortedSdcInfo.getContent();

        //Then
        assertEquals("value", unsortedList.get(0).getName(),"The first result should be 'value' since 'value' inserted first");
        assertEquals("abc", unsortedList.get(1).getName(),"The last result should be 'abc' since 'abc' inserted last");

        Page<SdcInfoEntity> sortedSdcInfo = sdcInfoRepo.findAll(PageRequest.of(0, 2, sort));
        List<SdcInfoEntity> sortedList = sortedSdcInfo.getContent();

        assertEquals("abc", sortedList.get(0).getName(),"The first result should be 'abc' since it sorted by name with asc");
        assertEquals("value", sortedList.get(1).getName(),"The last result should be 'value' since it sorted by name with asc");

        //Clear
        sdcInfoRepo.deleteAll();
    }

    @Test
    public void getSdcInfoPage_pageSizeShould1ForDataInDb2(){
        //Given
        sdcInfoRepo.saveAll(add2SdcInfoEntity());
        //When
        Page<SdcInfoEntity> singleSdcInfoPage = sdcInfoRepo.findAll(PageRequest.of(0, 1));
        //Then
        assertEquals(1, singleSdcInfoPage.getSize());
        assertEquals("value", singleSdcInfoPage.getContent().get(0).getName());

        //Clear
        sdcInfoRepo.deleteAll();
    }

    private List<SdcInfoEntity> add2SdcInfoEntity(){
        SdcInfoEntity sdcInfoEntity = new SdcInfoEntity();
        sdcInfoEntity.setServingUserLoginId("test");
        sdcInfoEntity.setLedId("value");
        sdcInfoEntity.setLedId("value");
        sdcInfoEntity.setName("value");
        sdcInfoEntity.setState(1);
        sdcInfoEntity.setEndTime(LocalDateTime.now());
        sdcInfoEntity.setStartTime(LocalDateTime.now());
        sdcInfoEntity.setCreated(LocalDateTime.now());
        sdcInfoEntity.setCreatedBy("test");
        sdcInfoEntity.setModified(LocalDateTime.now());
        sdcInfoEntity.setModifiedBy("test");

        SdcInfoEntity sdcInfoEntity1 = new SdcInfoEntity();
        sdcInfoEntity1.setServingUserLoginId("test");
        sdcInfoEntity1.setLedId("abc");
        sdcInfoEntity1.setLedId("abc");
        sdcInfoEntity1.setName("abc");
        sdcInfoEntity1.setState(1);
        sdcInfoEntity1.setEndTime(LocalDateTime.now());
        sdcInfoEntity1.setStartTime(LocalDateTime.now());
        sdcInfoEntity1.setCreated(LocalDateTime.now());
        sdcInfoEntity1.setCreatedBy("test");
        sdcInfoEntity1.setModified(LocalDateTime.now());
        sdcInfoEntity1.setModifiedBy("test");
        return List.of(sdcInfoEntity,sdcInfoEntity1);

    }

}
