package com.market.domain;

import com.market.constant.Role;
import com.market.dto.UserFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class User extends Timestamped {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    List<Item> items = new ArrayList<Item>();

    public static User createUser(UserFormDto userFormDto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(userFormDto.getUsername());
        user.setEmail(userFormDto.getEmail());
        user.setAddress(userFormDto.getAddress());
        String password = passwordEncoder.encode(userFormDto.getPassword());
        user.setPassword(password);
        user.setRole(Role.ADMIN);
        return user;
    }
}
