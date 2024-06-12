package com.laszlo.fodor.dto.response.phone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PhoneNumberResponse {

    private Long id;

    private String phoneNumber;
}
