package queue.pro.cloud.qapi.learn;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import queue.pro.cloud.qapi.service.ServiceEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/learn")
public class LearnServiceController {
    final LearnServiceSvc learnServiceSvc;
    @PostMapping(value = "/service")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ServiceEntity> addService(@RequestBody @Valid ServiceEntity service){
        return learnServiceSvc.addService(service).log();

    }
    @GetMapping("/service")
    Flux<ServiceEntity> getAllService(){
        return learnServiceSvc.getAllSvc();
    }

    @GetMapping("/service/{id}")
    Mono<Optional<ServiceEntity>> serviceById(@PathVariable String id){
        return learnServiceSvc.getSvcById(id);
    }
    @PutMapping("/service/{id}")
    Mono<ServiceEntity> updateService(@RequestBody ServiceEntity updatedService, @PathVariable String id){
        return learnServiceSvc.updateService(updatedService, id);
    }

}
