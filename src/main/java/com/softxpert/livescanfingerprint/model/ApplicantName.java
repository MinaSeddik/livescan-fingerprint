package com.softxpert.livescanfingerprint.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.softxpert.livescanfingerprint.model.ApplicationConstraint.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantName {
    @NotNull(message = "{applicant.name.firstName.notNull}")
//    @NotBlank(message = "{applicant.name.firstName.notBlank}")
    @Size(min = APPLICANT_FIRST_NAME_MIN, max = APPLICANT_FIRST_NAME_MAX, message = "{applicant.name.firstName.size}")
    @Pattern(regexp = APPLICANT_NAME_REGEX, message = "{applicant.name.firstName.pattern}")
    private String firstName;

    @NotNull(message = "{applicant.name.lastName.notNull}")
//    @NotBlank(message = "{applicant.name.lastName.notBlank}")
    @Size(min = APPLICANT_LAST_NAME_MIN, max = APPLICANT_LAST_NAME_MAX, message = "{applicant.name.lastName.size}")
    @Pattern(regexp = APPLICANT_NAME_REGEX, message = "{applicant.name.lastName.pattern}")
    private String lastName;

    @Size(min = APPLICANT_MIDDLE_NAME_MIN, max = APPLICANT_MIDDLE_NAME_MAX, message = "{applicant.name.middleName.size}")
    @Pattern(regexp = APPLICANT_NAME_REGEX, message = "{applicant.name.middleName.pattern}")
    private String middleName;


    @Override
    public String toString() {
        return String.format("%s, %s %s", lastName, firstName, middleName == null ? "" : middleName);
    }
}
