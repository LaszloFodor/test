package com.laszlo.fodor.service;


import com.laszlo.fodor.dto.request.PhoneNumberRequest;
import com.laszlo.fodor.dto.response.phone.PhoneNumberListResponse;
import com.laszlo.fodor.dto.response.phone.PhoneNumberResponse;
import com.laszlo.fodor.entity.PhoneNumberEntity;
import com.laszlo.fodor.entity.UserEntity;
import com.laszlo.fodor.mapper.PhoneNumberMapper;
import com.laszlo.fodor.repository.PhoneNumberRepository;
import com.laszlo.fodor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final PhoneNumberMapper phoneNumberMapper;
    private final UserRepository userRepository;

    @Transactional
    public PhoneNumberResponse updatePhoneNumber(Long userId, Long phoneNumberId, PhoneNumberRequest request) {
        PhoneNumberEntity phoneNumberEntity;
        if (phoneNumberRepository.existsByIdAndUserId(phoneNumberId, userId)) {
            phoneNumberEntity = phoneNumberRepository.findByIdAndUserId(phoneNumberId, userId);
            phoneNumberMapper.updateEntityFromRequest(request, phoneNumberEntity);
        } else {
            phoneNumberEntity = phoneNumberMapper.mapRequestToEntity(request);
        }
        PhoneNumberEntity savedEntity = phoneNumberRepository.save(phoneNumberEntity);
        return phoneNumberMapper.mapEntityToResponse(savedEntity);
    }

    @Transactional
    public void deletePhoneNumber(Long userId, Long PhoneNumberId) {
        phoneNumberRepository.deleteByIdAndUserId(PhoneNumberId, userId);
    }


    @Transactional(readOnly = true)
    public PhoneNumberResponse getPhoneNumberById(Long userId, Long PhoneNumberId) throws Exception {
        PhoneNumberEntity PhoneNumberEntity = phoneNumberRepository.findByIdAndUserId(PhoneNumberId, userId);
        return phoneNumberMapper.mapEntityToResponse(PhoneNumberEntity);
    }

    @Transactional(readOnly = true)
    public PhoneNumberListResponse getAllPhoneNumberes(Long userId) {
        List<PhoneNumberEntity> phoneNumberEntities = phoneNumberRepository.findAllByUserId(userId);
        return PhoneNumberListResponse.builder()
                .phoneNumbers(phoneNumberMapper.mapEntitiestoResponseList(phoneNumberEntities))
                .build();
    }

    @Transactional
    public PhoneNumberResponse addPhoneNumberToUser(Long userId, PhoneNumberRequest request) throws Exception {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(Exception::new);
        PhoneNumberEntity PhoneNumberEntity = phoneNumberMapper.mapRequestToEntity(request);
        PhoneNumberEntity.setUser(userEntity);
        PhoneNumberEntity savedPhoneNumber = phoneNumberRepository.save(PhoneNumberEntity);
        return phoneNumberMapper.mapEntityToResponse(savedPhoneNumber);
    }

}
