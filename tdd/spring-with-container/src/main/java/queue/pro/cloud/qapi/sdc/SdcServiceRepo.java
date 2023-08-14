package queue.pro.cloud.qapi.sdc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SdcServiceRepo extends JpaRepository<SDCServiceEntity, String> {
   @Query("""
           select sdc.id as sdcId, sdc.scId as scId,
           sdc.corporateId as corporateId,
           sdcs.serviceId as serviceId
           from SDCServiceEntity sdcs
           left join SdcInfoEntity sdc on sdcs.sdcId = sdc.id
           where sdc.servingUserLoginId=:loginUserId
           """
            )
    List<SdcWithServiceInfo> findServiceListByLoginUserId(String loginUserId);
}
