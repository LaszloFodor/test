package com.laszlo.fodor.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PhoneNumberRequest {

    private Long id;

    @NotBlank(message = "Phone number must not be null")
    @Pattern(regexp = "^\\+36(20|30|70)[0-9]{7,}$", message = "Invalid phone number")
    private String phoneNumber;
}
