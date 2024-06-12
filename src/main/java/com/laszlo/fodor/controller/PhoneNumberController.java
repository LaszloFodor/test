package com.laszlo.fodor.controller;


import com.laszlo.fodor.dto.request.PhoneNumberRequest;
import com.laszlo.fodor.dto.response.phone.PhoneNumberListResponse;
import com.laszlo.fodor.dto.response.phone.PhoneNumberResponse;
import com.laszlo.fodor.service.PhoneNumberService;
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
@RequestMapping("/users/{userId}/phone-number")
public class PhoneNumberController {
    
    private final PhoneNumberService phoneNumberService;

    @PostMapping
    public ResponseEntity<PhoneNumberResponse> createPhoneNumberForUser(@PathVariable Long userId, @Valid @RequestBody PhoneNumberRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(phoneNumberService.addPhoneNumberToUser(userId, request));
    }

    @PutMapping("/{phoneNumberId}")
    public ResponseEntity<PhoneNumberResponse> updateUser(@PathVariable Long userId, @PathVariable Long phoneNumberId, @Valid @RequestBody PhoneNumberRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(phoneNumberService.updatePhoneNumber(userId, phoneNumberId, request));
    }

    @DeleteMapping("/{phoneNumberId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, @PathVariable Long phoneNumberId) {
        phoneNumberService.deletePhoneNumber(userId, phoneNumberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/{phoneNumberId}")
    public ResponseEntity<PhoneNumberResponse> getPhoneNumber(@PathVariable Long userId, @PathVariable Long phoneNumberId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(phoneNumberService.getPhoneNumberById(userId, phoneNumberId));
    }

    @GetMapping
    public ResponseEntity<PhoneNumberListResponse> getAllPhoneNumbersForUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(phoneNumberService.getAllPhoneNumberes(userId));
    }
}
