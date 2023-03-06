package com.softxpert.livescanfingerprint.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.softxpert.livescanfingerprint.model.ApplicationConstraint.*;

@Data
public class FingerprintImage {

    @NotNull(message = "{applicant.fingerprints.position.notNull}")
    @Range(min = APPLICANT_FINGERPRINT_POSITION_MIN, max = APPLICANT_FINGERPRINT_POSITION_MAX, message = "{applicant.fingerprints.position.range}")
    private int position;

    @NotNull(message = "{applicant.fingerprints.wsqImage.notNull}")
    @Pattern(regexp = BASE64_ENCODED_IMAGE_REGEX, message = "{applicant.fingerprints.wsqImage.pattern}")
    private String wsqImage;
}
