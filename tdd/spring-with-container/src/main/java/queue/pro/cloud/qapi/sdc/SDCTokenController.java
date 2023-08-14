package queue.pro.cloud.qapi.sdc;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import queue.pro.cloud.qapi.sdc.service.SDCTokenService;

@RestController
@RequestMapping("v1")
public record SDCTokenController(SDCTokenService sdcTokenService) {

    @GetMapping("sdc-token")
    public ResponseEntity<?> getSDCToken() {
        return ResponseEntity.ok(sdcTokenService.getToken());
    }
}
