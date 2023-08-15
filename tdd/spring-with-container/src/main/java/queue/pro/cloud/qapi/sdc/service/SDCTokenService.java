package queue.pro.cloud.qapi.sdc.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import queue.pro.cloud.qapi.bean.LoginUserInfoBean;
import queue.pro.cloud.qapi.common.UserContext;
import queue.pro.cloud.qapi.sdc.SdcServiceRepo;
import queue.pro.cloud.qapi.sdc.SdcWithServiceInfo;
import queue.pro.cloud.qapi.sdc.bean.SDCTokenBean;
import queue.pro.cloud.qapi.token.detail.TokenDetailEntity;
import queue.pro.cloud.qapi.token.detail.TokenDetailRepo;
import queue.pro.cloud.qapi.token.repo.TokenFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public record SDCTokenService (SdcServiceRepo sdcServiceRepo, TokenDetailRepo tokenDetailRepo, ModelMapper modelMapper){

    private static final int FIRST_ONE_ROW = 1;

    public Flux<SDCTokenBean> getToken() {
         return UserContext.loginUserInfoBean()
                .map(this::getSdcToken).flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic());
    }

    List<SDCTokenBean> getSdcToken(LoginUserInfoBean loginUserInfoBean){
        List<SdcWithServiceInfo> serviceList = sdcServiceRepo.findServiceListByLoginUserId(loginUserInfoBean.loginId());
        if(serviceList.isEmpty()) return Collections.emptyList();
        SdcWithServiceInfo sdcSvcInfo = serviceList.get(0);
        TokenFilter filter = TokenFilter.builder()
                .corporateId(sdcSvcInfo.getCorporateId())
                .scId(sdcSvcInfo.getScId())
                .sdcId(sdcSvcInfo.getSdcId())
                .states(Collections.emptyList())
                .serviceIds(serviceList.stream().map(SdcWithServiceInfo::getServiceId).collect(Collectors.toList()))
                .build();
        List<TokenDetailEntity> tokenDetails = tokenDetailRepo.getSdcServiceToken(filter, Sort.unsorted(), FIRST_ONE_ROW);
        return tokenDetails.stream().map(value -> modelMapper.map(value, SDCTokenBean.class)).collect(Collectors.toList());
    }


}
