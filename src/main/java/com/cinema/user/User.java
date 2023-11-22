package com.cinema.user;

import com.cinema.user.userEnum.AccountType;
import com.cinema.user.userEnum.UserRole;
import com.cinema.common.entity.AbstractUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Setter
@Builder
public class User extends AbstractUUIDEntity {
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
