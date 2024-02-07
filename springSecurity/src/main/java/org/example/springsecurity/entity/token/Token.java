package org.example.springsecurity.entity.token;

import jakarta.persistence.*;
import lombok.*;
import org.example.springsecurity.Const.enums.TokenType;
import org.example.springsecurity.entity.user.User;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn( name = "user_id")
    private User user;
}
