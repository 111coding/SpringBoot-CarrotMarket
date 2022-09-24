package com.example.carrotmarket.modules.user.domain.dto;

import com.example.carrotmarket.modules.file.domain.dto.FileDto;
import com.example.carrotmarket.modules.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long idx;
    private String username;
    private String nickname;
    private FileDto profileImage;

    public UserDto(User user){
        this.idx = user.getIdx();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        if(user.getProfileImage() != null){
            this.profileImage = new FileDto(user.getProfileImage());
        }
    }

}