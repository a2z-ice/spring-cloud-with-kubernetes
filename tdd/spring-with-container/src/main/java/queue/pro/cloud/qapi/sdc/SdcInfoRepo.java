package queue.pro.cloud.qapi.sdc;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SdcInfoRepo extends JpaRepository<SdcInfoEntity, String> {

    Optional<SdcInfoEntity> findByServingUserLoginId(String loginUserId);
}
