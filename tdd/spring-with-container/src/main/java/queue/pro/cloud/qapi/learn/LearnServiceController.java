package queue.pro.cloud.qapi.learn;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queue.pro.cloud.qapi.service.ServiceEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
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
    @PutMapping("/service-null/{id}")
    Mono<ResponseEntity<?>> updateServiceOrNull(@RequestBody ServiceEntity updatedService, @PathVariable String id){

        return  learnServiceSvc.performUpdateOrNull(updatedService,id)
                .map(monoServiceEntity -> ResponseEntity.ok().body(monoServiceEntity))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .map(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return response; // If it's a success response, keep it as is.
                    } else {
                        final Map<String,String> map = new HashMap<>();
                        map.put("serviceId", id);
                        map.put("error", "not found");
                        // If it's not a success response, create a custom response with a string body.
                        String customErrorMessage = "Custom error message: Service not found or update failed.";
                        return ResponseEntity.status(response.getStatusCode())
                                .headers(response.getHeaders())
                                .body(map);
                    }
                })
               .log()
        ;
    }

    private Mono<ResponseEntity<?>> customErrorResponse() {
        String customErrorMessage = "Custom error message: Service not found or update failed.";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(customErrorMessage));
    }

}
