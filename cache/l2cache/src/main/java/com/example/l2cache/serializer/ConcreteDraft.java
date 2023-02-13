package com.example.l2cache.serializer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wildfly.common.annotation.NotNull;

@Data
@NoArgsConstructor
public class ConcreteDraft implements DraftData {
    @NotBlank(message = "Not empty")
    private String cityName;
    private String cityPINCode;
}
