package com.softxpert.livescanfingerprint.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import validation.ValidApplicationData;
import validation.ValidDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static com.softxpert.livescanfingerprint.model.ApplicationConstraint.*;


@ValidApplicationData(message = "{application.data.validApplicationData}")
@Data
@NoArgsConstructor
public class Application {

    // Reference:
//    https://github.com/MinaSeddik/dev-shortcut/blob/master/Spring/spring-mvc.md#spring_validation


    @Valid
    @NotNull(message = "{applicant.name.notNull}")
    private ApplicantName applicantName;

    @Size(max = ORI_MAX_SIZE, message = "{applicant.ori.size}")
    private String ori;

    @ValidDate(message = "{applicant.dateOfBirth.valid}")
    private String dateOfBirth;

    @Valid
    @NotNull(message = "{applicant.address.notNull}")
    private Address residenceOfPersonFingerPrinted;

    @ValidDate(message = "{applicant.completionDate.valid}")
    private String completionDate;

    @Size(max = REASON_FINGERPRINTED_MAX_SIZE, message = "{applicant.reasonFingerPrinted.size}")
    private String reasonFingerPrinted;

    @NotNull(message = "{applicant.citizenship.notNull}")
    @Pattern(regexp = APPLICANT_CITIZENSHIP_REGEX, message = "{applicant.citizenship.pattern}")
    private String citizenship;

    @Size(max = OCA_MAX_SIZE, message = "{applicant.oca.size}")
    private String oca;

    @Pattern(regexp = APPLICANT_SSN_REGEX, message = "{applicant.ssn.pattern}")
    private String ssn;

    @NotNull(message = "{applicant.sex.notNull}")
    @Pattern(regexp = APPLICANT_SEX_REGEX, message = "{applicant.sex.pattern}")
    private String sex;

    @NotNull(message = "{applicant.race.notNull}")
    @Pattern(regexp = APPLICANT_RACE_REGEX, message = "{applicant.race.pattern}")
    private String race;

    @NotNull(message = "{applicant.height.notNull}")
    @Range(min = APPLICANT_HEIGHT_MIN, max = APPLICANT_HEIGHT_MAX, message = "{applicant.height.range}")
    private Integer height;
    @NotNull(message = "{applicant.wight.notNull}")
    @Range(min = APPLICANT_WIGHT_MIN, max = APPLICANT_WIGHT_MAX, message = "{applicant.wight.range}")
    private Integer wight;

    @NotNull(message = "{applicant.eyes.notNull}")
    @Pattern(regexp = APPLICANT_EYES_COLOR_CODE_REGEX, message = "{applicant.eyes.pattern}")
    private String eyes;

    @NotNull(message = "{applicant.hair.notNull}")
    @Pattern(regexp = APPLICANT_HAIR_COLOR_CODE_REGEX, message = "{applicant.hair.pattern}")
    private String hair;

    @NotNull(message = "{applicant.placeOfBirth.notNull}")
    @Pattern(regexp = APPLICANT_PLACE_OF_BIRTH_REGEX, message = "{applicant.placeOfBirth.pattern}")
    private String placeOfBirth;

    @NotNull(message = "{applicant.fingerprints.notNull}")
    private List<@Valid FingerprintImage> fingerprints;

    @NotNull(message = "{applicant.signatureImage.notNull}")
    @Pattern(regexp = BASE64_ENCODED_IMAGE_REGEX, message = "{applicant.signatureImage.pattern}")
    private String signatureImage;


}
