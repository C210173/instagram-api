package com.sky.instagram.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String name;
    private String userImage;
}
