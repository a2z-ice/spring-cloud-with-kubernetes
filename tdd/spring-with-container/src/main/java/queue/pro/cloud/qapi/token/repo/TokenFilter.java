package queue.pro.cloud.qapi.token.repo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenFilter  {
    private String scId;
    private String sdcId;
    private List<Integer> states;
}
