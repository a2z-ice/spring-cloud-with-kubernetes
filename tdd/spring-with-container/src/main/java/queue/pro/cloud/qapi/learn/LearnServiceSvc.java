package queue.pro.cloud.qapi.learn;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import queue.pro.cloud.qapi.service.ServiceEntity;
import queue.pro.cloud.qapi.service.ServiceRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class LearnServiceSvc {
    final ServiceRepo serviceRepo;
    public Flux<ServiceEntity> getAllSvc() {
        return Flux.fromStream(() -> this.serviceRepo.findAll().stream())
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<ServiceEntity> addService(ServiceEntity service) {
        System.out.println("value of serviceRepo: *************************" + serviceRepo);
        return Mono.fromSupplier(() -> serviceRepo.save(service))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Optional<ServiceEntity>> getSvcById(String id) {
        return Mono.fromSupplier(() -> serviceRepo.findById(id))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<ServiceEntity> updateService(final ServiceEntity updatedService, final String id) {
        return Mono.fromSupplier(()-> performUpdate(updatedService,id))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private ServiceEntity performUpdate(ServiceEntity updatedService, String id) {
            final ServiceEntity dbService = serviceRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
            dbService.setName(updatedService.getName());
            return serviceRepo.saveAndFlush(dbService);

    }

    public Mono<ServiceEntity> performUpdateOrNull(final ServiceEntity updateService, final String id){
        return Mono.fromSupplier(() -> performUpdateIfNotNull(updateService,id))
                .subscribeOn(Schedulers.boundedElastic()).log();
    }
    private ServiceEntity performUpdateIfNotNull(ServiceEntity updatedService, String id) {
            final ServiceEntity dbService = serviceRepo.findById(id).orElse(null);
            if(dbService == null) return null;
            dbService.setName(updatedService.getName());
            return serviceRepo.saveAndFlush(dbService);

    }
}
