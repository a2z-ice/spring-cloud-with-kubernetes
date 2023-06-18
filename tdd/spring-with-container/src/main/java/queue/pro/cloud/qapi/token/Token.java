package queue.pro.cloud.qapi.token;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "queue.pro.cloud.qapi.common.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "category_wise_token_seq_no")
    private Integer categoryWiseTokenSeqNo;


    @Version
    @Column(name = "VERSION")
    private Integer version;
}
