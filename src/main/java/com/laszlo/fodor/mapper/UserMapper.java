package com.laszlo.fodor.mapper;

import com.laszlo.fodor.dto.request.AddressRequest;
import com.laszlo.fodor.dto.request.UserRequest;
import com.laszlo.fodor.dto.response.address.AddressResponse;
import com.laszlo.fodor.dto.response.phone.PhoneNumberResponse;
import com.laszlo.fodor.dto.response.user.UserResponse;
import com.laszlo.fodor.entity.AddressEntity;
import com.laszlo.fodor.entity.PhoneNumberEntity;
import com.laszlo.fodor.entity.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", uses = {AddressMapper.class, PhoneNumberMapper.class}, builder = @Builder(disableBuilder = true))
public interface UserMapper {

    @Mapping(target = "phoneNumbers", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    UserEntity mapUserRequestToUserEntity(UserRequest request);

    @Mapping(target = "phoneNumber", source = "phoneNumbers", qualifiedByName = "mapPhoneNumber")
    @Mapping(target = "addresses", source = "addresses", qualifiedByName = "mapAddressToResponse")
    UserResponse mapUserEntityToUserResponse(UserEntity entity);

    List<UserResponse> mapUserEntitiestoUserResponseList(List<UserEntity> users);

    @Mapping(source = "addresses", target = "addresses", qualifiedByName = "mapAddressRequestToEntity")
    void updateUserEntityFromRequest(UserRequest request, @MappingTarget UserEntity userEntity);

    @AfterMapping
    default void mapPhoneNumbers(UserRequest request, @MappingTarget UserEntity entity) {
        List<PhoneNumberEntity> phoneNumberEntities = request.getPhoneNumber().stream().map(phone -> PhoneNumberEntity.builder()
                .id(phone.getId() == null ? null : phone.getId())// bitzos?
                .phoneNumber(phone.getPhoneNumber())
                .user(entity)
                .build()
        ).toList();
        entity.setPhoneNumbers(phoneNumberEntities);
    }
    @AfterMapping
    default void mapAddresses(UserRequest request, @MappingTarget UserEntity entity) {
        List<AddressEntity> addressEntities = request.getAddresses().stream().map(address -> AddressEntity.builder()
                .id(address.getId() == null ? null : address.getId()) // bitzos?
                .address(address.getAddress())
                .city(address.getCity())
                .country(address.getCountry())
                .postcode(address.getPostcode())
                .user(entity)
                .build()
        ).toList();
        entity.setAddresses(addressEntities);
    }

    @Named("mapPhoneNumber")
    default List<PhoneNumberResponse> mapPhoneNumber(List<PhoneNumberEntity> phoneNumberEntities) {
        return phoneNumberEntities.stream()
                .map(Mappers.getMapper(PhoneNumberMapper.class)::mapEntityToResponse)
                .collect(Collectors.toList());
    }
    @Named("mapAddressToResponse")
    default List<AddressResponse> mapAddressToResponse(List<AddressEntity> addressEntities) {
        return addressEntities.stream()
                .map(Mappers.getMapper(AddressMapper.class)::mapEntityToResponse)
                .collect(Collectors.toList());
    }
    @Named("mapAddressRequestToEntity")
    default List<AddressEntity> mapAddressRequestToEntity(List<AddressRequest> requests) {
        return requests.stream()
                .map(Mappers.getMapper(AddressMapper.class)::mapRequestToEntity)
                .collect(Collectors.toList());
    }
}
