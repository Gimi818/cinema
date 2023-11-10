package com.cinema.user;

import com.cinema.user.userEnum.AccountType;
import com.cinema.user.userEnum.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    private String confirmationToken;

}
