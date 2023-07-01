package queue.pro.cloud.qapi.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo  extends JpaRepository<ServiceEntity, String> {
}
