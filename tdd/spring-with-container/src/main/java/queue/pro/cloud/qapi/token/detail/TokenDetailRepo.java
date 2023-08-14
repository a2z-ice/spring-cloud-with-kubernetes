package queue.pro.cloud.qapi.token.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import queue.pro.cloud.qapi.token.detail.TokenDetailEntity;

import java.util.List;

public interface TokenDetailRepo extends JpaRepository<TokenDetailEntity, String> , TokenDetailCustomRepo{
    List<TokenDetailEntity> findByServiceIdIn(List<String> serviceIds);

}
