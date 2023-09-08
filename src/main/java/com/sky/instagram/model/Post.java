package com.sky.instagram.model;

import com.sky.instagram.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String caption;
    private String image;
    private String location;
    private LocalDateTime createdAt;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name="user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))
    })
    private UserDto user;
    @OneToMany
    private List<Comment> comments = new ArrayList<>();
    @Embedded
    @ElementCollection
    @JoinTable(name="likedByUsers", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserDto> likedByUsers = new HashSet<>();
}
