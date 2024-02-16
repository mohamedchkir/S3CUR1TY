package org.example.springsecurity.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Data
public class UserDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private List<String> permissions;
}
