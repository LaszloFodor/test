package com.laszlo.fodor.service;


import com.laszlo.fodor.dto.request.AddressRequest;
import com.laszlo.fodor.dto.response.address.AddressListResponse;
import com.laszlo.fodor.dto.response.address.AddressResponse;
import com.laszlo.fodor.entity.AddressEntity;
import com.laszlo.fodor.entity.UserEntity;
import com.laszlo.fodor.mapper.AddressMapper;
import com.laszlo.fodor.repository.AddressRepository;
import com.laszlo.fodor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Transactional
    public AddressResponse updateAddress(Long userId, Long addressId, AddressRequest request) {
        AddressEntity address;
        if (addressRepository.existsByIdAndUserId(addressId, userId)) {
            address = addressRepository.findByIdAndUserId(addressId, userId);
            addressMapper.updateEntityFromRequest(request, address);
        } else {
            address = addressMapper.mapRequestToEntity(request);
        }
        AddressEntity savedEntity = addressRepository.save(address);
        return addressMapper.mapEntityToResponse(savedEntity);
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        addressRepository.deleteByIdAndUserId(addressId, userId);
    }


    @Transactional(readOnly = true)
    public AddressResponse getAddressById(Long userId, Long addressId) throws Exception {
        AddressEntity addressEntity = addressRepository.findByIdAndUserId(addressId, userId);
        return addressMapper.mapEntityToResponse(addressEntity);
    }

    @Transactional(readOnly = true)
    public AddressListResponse getAllAddresses(Long userId) {
        List<AddressEntity> addressEntities = addressRepository.findAllByUserId(userId);
        return AddressListResponse.builder()
                .addresses(addressMapper.mapEntitiestoResponseList(addressEntities))
                .build();
    }

    @Transactional
    public AddressResponse addAddressToUser(Long userId, AddressRequest request) throws Exception {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(Exception::new);
        AddressEntity addressEntity = addressMapper.mapRequestToEntity(request);
        addressEntity.setUser(userEntity);
        AddressEntity savedAddress = addressRepository.save(addressEntity);
        return addressMapper.mapEntityToResponse(savedAddress);
    }
}
