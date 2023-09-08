package com.sky.instagram.model;

import com.sky.instagram.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name="user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))
    })
    private UserDto user;
    private String content;
    @Embedded
    @ElementCollection
    private Set<UserDto> LikedByUsers = new HashSet<>();
    private LocalDateTime createdAt;

}
