package com.example.l2cache;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Cache;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@Table(name = "CITY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "city_name")
    private String cityName;
    @Column(name = "city_pincode")
    private String cityPINCode;
}
