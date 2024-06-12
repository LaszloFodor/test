package com.laszlo.fodor.service;

import com.laszlo.fodor.dto.request.UserRequest;
import com.laszlo.fodor.dto.response.user.UserListResponse;
import com.laszlo.fodor.dto.response.user.UserResponse;
import com.laszlo.fodor.entity.UserEntity;
import com.laszlo.fodor.mapper.UserMapper;
import com.laszlo.fodor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        UserEntity userEntity = userMapper.mapUserRequestToUserEntity(request);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.mapUserEntityToUserResponse(savedUserEntity);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserRequest request) {
        UserEntity userEntity;
        if (userRepository.existsById(userId)) {
            userEntity = userRepository.findById(userId).get();
            userMapper.updateUserEntityFromRequest(request, userEntity);
        } else {
            userEntity = userMapper.mapUserRequestToUserEntity(request);
        }
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.mapUserEntityToUserResponse(savedUserEntity);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) throws Exception {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(Exception::new);
        return userMapper.mapUserEntityToUserResponse(userEntity);
    }

    @Transactional(readOnly = true)
    public UserListResponse getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return UserListResponse.builder()
                .users(userMapper.mapUserEntitiestoUserResponseList(userEntities))
                .build();
    }

    @Transactional
    public UserResponse depersonalizeUser(Long userId) throws Exception {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(Exception::new);
        depersonalizeUser(userEntity);
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.mapUserEntityToUserResponse(savedUser);
    }

    private void depersonalizeUser(UserEntity userEntity) {
        userEntity.setName(hideField(userEntity.getName()));
        userEntity.setDateOfBirth(hideField(userEntity.getDateOfBirth()));
        userEntity.setNameOfMother(hideField(userEntity.getNameOfMother()));
        userEntity.setSocialSecurityIdentification(hideField(userEntity.getSocialSecurityIdentification()));
    }

    private String hideField(String field) {
        return field.chars()
                .mapToObj(i -> i < 3 ? (char) i : '*')
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
