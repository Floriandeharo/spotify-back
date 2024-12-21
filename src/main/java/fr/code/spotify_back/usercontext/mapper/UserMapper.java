package fr.code.spotify_back.usercontext.mapper;

import org.mapstruct.Mapper;

import fr.code.spotify_back.usercontext.ReadUserDTO;
import fr.code.spotify_back.usercontext.domain.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	ReadUserDTO readUserDTOToUser(User entity);
}
