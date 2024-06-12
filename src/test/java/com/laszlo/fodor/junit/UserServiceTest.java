package com.laszlo.fodor.junit;

import com.laszlo.fodor.dto.request.AddressRequest;
import com.laszlo.fodor.dto.request.PhoneNumberRequest;
import com.laszlo.fodor.dto.request.UserRequest;
import com.laszlo.fodor.dto.response.address.AddressResponse;
import com.laszlo.fodor.dto.response.phone.PhoneNumberResponse;
import com.laszlo.fodor.dto.response.user.UserListResponse;
import com.laszlo.fodor.dto.response.user.UserResponse;
import com.laszlo.fodor.entity.AddressEntity;
import com.laszlo.fodor.entity.PhoneNumberEntity;
import com.laszlo.fodor.entity.UserEntity;
import com.laszlo.fodor.mapper.UserMapper;
import com.laszlo.fodor.repository.UserRepository;
import com.laszlo.fodor.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserRequest userRequest;
    private UserEntity userEntity;
    private UserEntity savedUserEntity;
    private UserResponse userResponse;
    private UserResponse depersonalizedResponse;
    private UserEntity depersonalizedEntity;
    private UserEntity updatedUserEntity;

    @BeforeEach
    public void setUp() {
        userRequest = UserRequest.builder()
                .email("test@test.com")
                .name("first last")
                .dateOfBirth("1990-01-01")
                .nameOfMother("mother name")
                .placeOfBirth("Budapest")
                .phoneNumber(List.of(
                        PhoneNumberRequest.builder()
                                .phoneNumber("+36301234567")
                                .build()
                ))
                .addresses(List.of(
                        AddressRequest.builder()
                                .address("Test utca 3")
                                .city("Budapest")
                                .country("Magyarország")
                                .postcode("1111")
                                .build()
                ))
                .socialSecurityIdentification("123456789")
                .build();

        userEntity = UserEntity.builder()
                .email("test@test.com")
                .name("first last")
                .dateOfBirth("1990-01-01")
                .nameOfMother("mother name")
                .placeOfBirth("Budapest")
                .phoneNumbers(List.of(
                        PhoneNumberEntity.builder()
                                .phoneNumber("+36301234567")
                                .build()
                ))
                .addresses(List.of(
                        AddressEntity.builder()
                                .address("Test utca 3")
                                .city("Budapest")
                                .country("Magyarország")
                                .postcode("1111")
                                .build()
                ))
                .socialSecurityIdentification("123456789")
                .build();

        updatedUserEntity = UserEntity.builder()
                .email("test@test.com")
                .name("new new")
                .dateOfBirth("1990-01-01")
                .nameOfMother("new mother name")
                .placeOfBirth("Budapest")
                .phoneNumbers(List.of(
                        PhoneNumberEntity.builder()
                                .phoneNumber("+36301234567")
                                .build()
                ))
                .addresses(List.of(
                        AddressEntity.builder()
                                .address("Test utca 3")
                                .city("Budapest")
                                .country("Magyarország")
                                .postcode("1111")
                                .build()
                ))
                .socialSecurityIdentification("000000000")
                .build();

        savedUserEntity = UserEntity.builder()
                .id(1L)
                .email("test@test.com")
                .name("first last")
                .dateOfBirth("1990-01-01")
                .nameOfMother("mother name")
                .placeOfBirth("Budapest")
                .phoneNumbers(List.of(
                        PhoneNumberEntity.builder()
                                .id(1L)
                                .phoneNumber("+36301234567")
                                .build()
                ))
                .addresses(List.of(
                        AddressEntity.builder()
                                .id(1L)
                                .address("Test utca 3")
                                .city("Budapest")
                                .country("Magyarország")
                                .postcode("1111")
                                .build()
                ))
                .socialSecurityIdentification("123456789")
                .build();

        depersonalizedEntity = UserEntity.builder()
                .id(1L)
                .email("test@test.com")
                .name("*********")
                .dateOfBirth("1990-01-01")
                .nameOfMother("*******")
                .placeOfBirth("Budapest")
                .phoneNumbers(List.of(
                        PhoneNumberEntity.builder()
                                .id(1L)
                                .phoneNumber("+36301234567")
                                .build()
                ))
                .addresses(List.of(
                        AddressEntity.builder()
                                .id(1L)
                                .address("Test utca 3")
                                .city("Budapest")
                                .country("Magyarország")
                                .postcode("1111")
                                .build()
                ))
                .socialSecurityIdentification("*********")
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .email("test@test.com")
                .name("first last")
                .dateOfBirth("1990-01-01")
                .nameOfMother("mother name")
                .placeOfBirth("Budapest")
                .phoneNumber(List.of(
                        PhoneNumberResponse.builder()
                                .id(1L)
                                .phoneNumber("+36301234567")
                                .build()
                ))
                .addresses(List.of(
                        AddressResponse.builder()
                                .id(1L)
                                .address("Test utca 3")
                                .city("Budapest")
                                .country("Magyarország")
                                .postcode("1111")
                                .build()
                ))
                .socialSecurityIdentification("123456789")
                .build();

        depersonalizedResponse = UserResponse.builder()
                .id(1L)
                .email("test@test.com")
                .name("******")
                .dateOfBirth("1990-01-01")
                .nameOfMother("********")
                .placeOfBirth("Budapest")
                .phoneNumber(List.of(
                        PhoneNumberResponse.builder()
                                .id(1L)
                                .phoneNumber("+36301234567")
                                .build()
                ))
                .addresses(List.of(
                        AddressResponse.builder()
                                .id(1L)
                                .address("Test utca 3")
                                .city("Budapest")
                                .country("Magyarország")
                                .postcode("1111")
                                .build()
                ))
                .socialSecurityIdentification("*********")
                .build();
    }

    @Test
    public void testGetAllUsers_shouldReturnAllUsers_whenRequestIsValid() {
        when(userRepository.findAll()).thenReturn(List.of(savedUserEntity));
        when(userMapper.mapUserEntitiestoUserResponseList(any())).thenReturn(List.of(userResponse));
        UserListResponse allUsers = userService.getAllUsers();
        assertEquals(1, allUsers.getUsers().size());
        UserResponse user = allUsers.getUsers().get(0);
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getName(), userResponse.getName());
        assertEquals(user.getSocialSecurityIdentification(), userResponse.getSocialSecurityIdentification());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(user.getPhoneNumber(), userResponse.getPhoneNumber());
        assertEquals(user.getNameOfMother(), userResponse.getNameOfMother());
        assertEquals(user.getDateOfBirth(), userResponse.getDateOfBirth());
        assertEquals(user.getPlaceOfBirth(), userResponse.getPlaceOfBirth());
        assertEquals(user.getAddresses().size(), userResponse.getAddresses().size());
        assertEquals(user.getPhoneNumber().size(), userResponse.getPhoneNumber().size());

        verify(userRepository).findAll();
        verify(userMapper).mapUserEntitiestoUserResponseList(any());
    }

    @Test
    public void testDepersonalizeUser_shouldReturnPersonalizedUser_whenRequestIsValid() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userEntity));
        when(userMapper.mapUserEntityToUserResponse(any())).thenReturn(depersonalizedResponse);
        when(userRepository.save(any())).thenReturn(depersonalizedEntity);

        UserResponse userResponse = userService.depersonalizeUser(1L);
        assertEquals(depersonalizedResponse.getId(), userResponse.getId());
        assertEquals(depersonalizedResponse.getName(), userResponse.getName());
        assertEquals(depersonalizedResponse.getSocialSecurityIdentification(), userResponse.getSocialSecurityIdentification());
        assertEquals(depersonalizedResponse.getEmail(), userResponse.getEmail());
        assertEquals(depersonalizedResponse.getPhoneNumber(), userResponse.getPhoneNumber());
        assertEquals(depersonalizedResponse.getNameOfMother(), userResponse.getNameOfMother());
        assertEquals(depersonalizedResponse.getDateOfBirth(), userResponse.getDateOfBirth());
        assertEquals(depersonalizedResponse.getPlaceOfBirth(), userResponse.getPlaceOfBirth());
        assertEquals(depersonalizedResponse.getAddresses().size(), userResponse.getAddresses().size());
        assertEquals(depersonalizedResponse.getPhoneNumber().size(), userResponse.getPhoneNumber().size());

        verify(userRepository).findById(1L);
        verify(userMapper).mapUserEntityToUserResponse(any());
        verify(userRepository).save(any());
    }
    @Test
    public void testGetUserById_shouldReturnUser_whenRequestIsValid() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userEntity));
        when(userMapper.mapUserEntityToUserResponse(any())).thenReturn(userResponse);

        UserResponse userResponse = userService.getUserById(1L);
        assertEquals(userResponse.getId(), userResponse.getId());
        assertEquals(userResponse.getName(), userResponse.getName());
        assertEquals(userResponse.getSocialSecurityIdentification(), userResponse.getSocialSecurityIdentification());
        assertEquals(userResponse.getEmail(), userResponse.getEmail());
        assertEquals(userResponse.getPhoneNumber(), userResponse.getPhoneNumber());
        assertEquals(userResponse.getNameOfMother(), userResponse.getNameOfMother());
        assertEquals(userResponse.getDateOfBirth(), userResponse.getDateOfBirth());
        assertEquals(userResponse.getPlaceOfBirth(), userResponse.getPlaceOfBirth());
        assertEquals(userResponse.getAddresses().size(), userResponse.getAddresses().size());
        assertEquals(userResponse.getPhoneNumber().size(), userResponse.getPhoneNumber().size());

        verify(userRepository).findById(1L);
        verify(userMapper).mapUserEntityToUserResponse(any());
    }

    @Test
    public void testDeleteUser_shouldDeleteUser_whenRequestIsValid() throws Exception {
        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    public void testCreateUser_shouldCreateUser_whenRequestIsValid() {
        when(userMapper.mapUserRequestToUserEntity(userRequest)).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(any());
        when(userMapper.mapUserEntityToUserResponse(savedUserEntity)).thenReturn(userResponse);


        UserResponse response = userService.createUser(userRequest);
        assertEquals(userResponse.getId(), response.getId());
        assertEquals(userResponse.getName(), response.getName());
        assertEquals(userResponse.getSocialSecurityIdentification(), response.getSocialSecurityIdentification());
        assertEquals(userResponse.getEmail(), response.getEmail());
        assertEquals(userResponse.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(userResponse.getNameOfMother(), response.getNameOfMother());
        assertEquals(userResponse.getDateOfBirth(), response.getDateOfBirth());
        assertEquals(userResponse.getPlaceOfBirth(), response.getPlaceOfBirth());
        assertEquals(userResponse.getAddresses().size(), response.getAddresses().size());
        assertEquals(userResponse.getPhoneNumber().size(), response.getPhoneNumber().size());

        verify(userMapper).mapUserRequestToUserEntity(userRequest);
        verify(userRepository).save(any());
        verify(userMapper).mapUserEntityToUserResponse(any());
    }


}
