package com.laszlo.fodor.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Validated
public class UserRequest {

    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(
            message = "Email is not in the right format",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"
    )
    private String email;

    @NotBlank(message = "Date of birth must not be blank")
    private String dateOfBirth;

    @NotBlank(message = "Place of birth must not be blank")
    private String placeOfBirth;

    @NotBlank(message = "Name of mother must not be blank")
    private String nameOfMother;

    @NotBlank(message = "Social security id must not be blank")
    @Length(max = 9, min = 9, message = "Social security id must be the length of 9")
    private String socialSecurityIdentification;

    @Valid
    private List<PhoneNumberRequest> phoneNumber;

    @Valid
    private List<AddressRequest> addresses;
}
