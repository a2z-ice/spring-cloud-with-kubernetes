package queue.pro.cloud.qapi.learn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ReactiveTest {
    @Test
    public void reactiveFluxMonoTest(){
        List<String> values = Arrays.asList("one", "two", "three", "four");
        Flux<String> ids = Flux.fromStream(values.stream());


        Flux<String> combinations = ids.flatMap(id -> {
            Mono<String> nameTask = ifHrName(id);
            Mono<String> statTask = ifHrStat(id);
            return nameTask.zipWith(statTask, (name, stat) -> "Name " + name + " has stats " + stat);
        });
        Mono<List<String>> result = combinations.collectList();
        List<String> list = result.block();


        assertThat(list).containsExactly("Name Mr one has stats stat one",
                "Name Mr two has stats stat two",
                "Name Mr three has stats stat three",
                "Name Mr four has stats stat four");

    }

    Mono<String> ifHrName(String id){
        return  Mono.just("Mr " + id);
    }
    Mono<String> ifHrStat(String id){
        return Mono.just("stat " + id);
    }
}
