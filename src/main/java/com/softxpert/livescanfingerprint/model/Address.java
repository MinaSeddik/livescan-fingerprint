package com.softxpert.livescanfingerprint.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.softxpert.livescanfingerprint.model.ApplicationConstraint.*;

@Data
@NoArgsConstructor
public class Address {

    @NotNull(message = "{applicant.address.address1.notNull}")
    @NotBlank(message = "{applicant.address.address1.notBlank}")
    @Size(min = APPLICANT_ADDRESS_ADDRESS1_MIN, max = APPLICANT_ADDRESS_ADDRESS1_MAX, message = "{applicant.address.address1.size}")
    private String address1;
    @Size(max = APPLICANT_ADDRESS_CITY_MAX, message = "{applicant.address.city.size}")
    private String city;
    @NotNull(message = "{applicant.address.state.notNull}")
    @NotBlank(message = "{applicant.address.state.notBlank}")
    @Pattern(regexp = APPLICANT_ADDRESS_STATE_REGEX, message = "{applicant.address.state.pattern}")
    private String state;
    @Pattern(regexp = APPLICANT_ADDRESS_ZIPCODE_REGEX, message = "{applicant.address.zipCode.pattern}")
    private String zipCode;


    @Override
    public String toString() {
        return String.format("%s\n%s, %s %s", address1, city == null ? "" : city, state, zipCode == null ? "" : zipCode);
    }
}
