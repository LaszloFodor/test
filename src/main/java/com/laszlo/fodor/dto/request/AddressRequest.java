package com.laszlo.fodor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressRequest {

    private Long id;
    @NotBlank(message = "Postcode must not be null")
    private String postcode;
    @NotBlank(message = "Country must not be null")
    private String country;
    @NotBlank(message = "City must not be null")
    private String city;
    @NotBlank(message = "Address must not be null")
    private String address;
}
