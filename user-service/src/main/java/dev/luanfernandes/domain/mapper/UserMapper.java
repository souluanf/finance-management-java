package dev.luanfernandes.domain.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import dev.luanfernandes.domain.entity.User;
import dev.luanfernandes.domain.request.UserCreateRequest;
import dev.luanfernandes.domain.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    User map(UserCreateRequest userCreateRequest);

    UserResponse map(User user);
}
