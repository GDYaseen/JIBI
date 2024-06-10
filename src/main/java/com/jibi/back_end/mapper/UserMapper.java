package com.jibi.back_end.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.jibi.back_end.dto.UserDto;
import com.jibi.back_end.models.User;

public class UserMapper {
    public static UserDto mapUserToUserDTO(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
    public static List<UserDto> UserListToUserDtoList(List<User> list){
        return list.stream().map(u ->
                mapUserToUserDTO(u)
        ).collect(Collectors.toList());
    }
}
