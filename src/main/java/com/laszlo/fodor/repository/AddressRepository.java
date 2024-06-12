package com.laszlo.fodor.repository;

import com.laszlo.fodor.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    List<AddressEntity> findAllByUserId(Long userId);

    AddressEntity findByIdAndUserId(Long addressId, Long userId);

    void deleteByIdAndUserId(Long addressId, Long userId);

    boolean existsByIdAndUserId(Long addressId, Long userId);
}
