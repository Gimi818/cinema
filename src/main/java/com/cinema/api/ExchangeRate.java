package com.cinema.api;

import com.cinema.common.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exchangeRate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeRate extends AbstractEntity {
    private String currency;
    private String code;
    private double mid;

}
