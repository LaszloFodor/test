package com.laszlo.fodor.dto.response.phone;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PhoneNumberListResponse {

    List<PhoneNumberResponse> phoneNumbers;
}
