package com.cinema.api;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exchangeRate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private double mid;

}