package com.laszlo.fodor.controller;

import com.laszlo.fodor.dto.request.AddressRequest;
import com.laszlo.fodor.dto.response.address.AddressResponse;
import com.laszlo.fodor.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddressForUser(@PathVariable Long userId, @Valid @RequestBody AddressRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(addressService.addAddressToUser(userId, request));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateUser(@PathVariable Long userId, @PathVariable Long addressId, @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addressService.updateAddress(userId, addressId, request));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddress(@PathVariable Long userId, @PathVariable Long addressId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAddressById(userId, addressId));
    }

    @GetMapping
    public ResponseEntity<?> getAllAddressesForUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAllAddresses(userId));
    }
}
