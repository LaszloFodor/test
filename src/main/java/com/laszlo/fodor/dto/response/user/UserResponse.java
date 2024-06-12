package com.laszlo.fodor.dto.response.user;

import com.laszlo.fodor.dto.response.address.AddressResponse;
import com.laszlo.fodor.dto.response.phone.PhoneNumberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String dateOfBirth;
    private String placeOfBirth;
    private String nameOfMother;
    private String socialSecurityIdentification;
    private List<PhoneNumberResponse> phoneNumber;
    private List<AddressResponse> addresses;

}
