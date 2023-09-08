package com.sky.instagram.model;

import com.sky.instagram.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name="user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))
    })
    private UserDto user;
    @NotNull
    private String image;
    private String caption;
    private LocalDateTime timestamp;
}
