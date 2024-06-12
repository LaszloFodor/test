package com.laszlo.fodor.mapper;

import com.laszlo.fodor.dto.request.PhoneNumberRequest;
import com.laszlo.fodor.dto.response.phone.PhoneNumberResponse;
import com.laszlo.fodor.entity.PhoneNumberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {

    PhoneNumberEntity mapRequestToEntity(PhoneNumberRequest request);
    PhoneNumberResponse mapEntityToResponse(PhoneNumberEntity entity);

    void updateEntityFromRequest(PhoneNumberRequest request, @MappingTarget PhoneNumberEntity phoneNumberEntity);

    List<PhoneNumberResponse> mapEntitiestoResponseList(List<PhoneNumberEntity> phoneNumberEntities);
}
