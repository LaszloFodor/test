package com.laszlo.fodor.mapper;

import com.laszlo.fodor.dto.request.AddressRequest;
import com.laszlo.fodor.dto.response.address.AddressResponse;
import com.laszlo.fodor.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressEntity mapRequestToEntity(AddressRequest request);
    AddressResponse mapEntityToResponse(AddressEntity request);

    void updateEntityFromRequest(AddressRequest request, @MappingTarget AddressEntity address);

    List<AddressResponse> mapEntitiestoResponseList(List<AddressEntity> addressEntities);
}
