package com.laszlo.fodor.repository;

import com.laszlo.fodor.entity.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, Long> {

    List<PhoneNumberEntity> findAllByUserId(Long userId);

    PhoneNumberEntity findByIdAndUserId(Long phoneNumberId, Long userId);

    void deleteByIdAndUserId(Long phoneNumberId, Long userId);

    boolean existsByIdAndUserId(Long phoneNumberId, Long userId);
}
