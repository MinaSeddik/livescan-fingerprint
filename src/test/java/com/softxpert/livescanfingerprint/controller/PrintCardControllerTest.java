package com.softxpert.livescanfingerprint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softxpert.livescanfingerprint.builder.ApplicationBuilder;
import com.softxpert.livescanfingerprint.exception.FingerprintErrorCode;
import com.softxpert.livescanfingerprint.exception.GlobalExceptionHandler;
import com.softxpert.livescanfingerprint.model.ApplicantName;
import com.softxpert.livescanfingerprint.model.Application;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.softxpert.livescanfingerprint.model.ApplicationConstraint.APPLICANT_FIRST_NAME_MAX;
import static com.softxpert.livescanfingerprint.model.ApplicationConstraint.APPLICANT_FIRST_NAME_MIN;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest({PrintCardController.class, GlobalExceptionHandler.class})
@DisplayName("Given PrintCard Controller to create FD-258 form")
class PrintCardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Disabled("Just 4 JsonPath training ...")
    @DisplayName("When No application provided, then it must fail with 400 HTTP error code")
    public void when_no_application_provided_request_should_be_invalid_ignored() throws Exception {

        // When
        mvc.perform(post("/v1/create-fd-258")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())


                // Then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.error.code").value(FingerprintErrorCode.VALIDATION_ERROR_CODE.ordinal()))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='application')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='application')].errorMessage").value("Invalid Application Data, Please provide a valid application data"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName')].errorMessage").value("Applicant Name is required"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='fingerprints')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='fingerprints')].errorMessage").value("Applicant Fingerprints are required"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='signatureImage')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='signatureImage')].errorMessage").value("Applicant signature is required"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='dateOfBirth')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='dateOfBirth')].errorMessage").value("Date of Birth is required with format MM/dd/yyyy"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='completionDate')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='completionDate')].errorMessage").value("Completion Date is required with format MM/dd/yyyy"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='citizenship')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='citizenship')].errorMessage").value("Citizenship is a required field"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='wight')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='wight')].errorMessage").value("Wight is a required field"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='height')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='height')].errorMessage").value("Height is a required field"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='residenceOfPersonFingerPrinted')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='residenceOfPersonFingerPrinted')].errorMessage").value("Applicant Address is required"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='sex')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='sex')].errorMessage").value("Sex is a required field"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='race')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='race')].errorMessage").value("Race is a required field"))


                .andExpect(jsonPath("$.validation[?(@.fieldName=='hair')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='hair')].errorMessage").value("Hair Color is a required field"))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='eyes')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='eyes')].errorMessage").value("Eyes Color is a required field"));

    }

    @Test
    @DisplayName("When No application provided, then it must fail with 400 HTTP error code")
    public void when_no_application_provided_request_should_be_invalid() throws Exception {

        // When
        mvc.perform(post("/v1/create-fd-258")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())


                // Then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.error.code").value(FingerprintErrorCode.APPLICATION_ERROR_CODE.ordinal()))
                .andExpect(jsonPath("$.error.message").value("Missing request body."));

    }

    @Test
    @DisplayName("When No applicant Name provided, then it must fail with 400 HTTP error code")
    public void when_applicant_name_not_provided_request_should_be_invalid() throws Exception {

        // Given
        ApplicantName applicantName = ApplicantName.builder()
                .lastName("LastName")
                .middleName("MiddleName")
                .build();
        Application application = ApplicationBuilder.newApplicationAndSetField("applicantName", applicantName);
        String applicationAsString = objectMapper.writeValueAsString(application);


        // When
        mvc.perform(post("/v1/create-fd-258")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(applicationAsString))
                .andDo(print())


                // Then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.error.code").value(FingerprintErrorCode.VALIDATION_ERROR_CODE.ordinal()))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')].errorMessage").value("First Name is a required field"));

    }

    @Test
    @DisplayName("When applicant FirstName is Not provided, then it must fail with 400 HTTP error code")
    public void when_applicant_firstName_is_blank_request_should_be_invalid() throws Exception {

        // Given
        ApplicantName applicantName = ApplicantName.builder()
                .firstName("")
                .lastName("LastName")
                .middleName("MiddleName")
                .build();
        Application application = ApplicationBuilder.newApplicationAndSetField("applicantName", applicantName);
        String applicationAsString = objectMapper.writeValueAsString(application);


        // When
        String expectedError1 = "Invalid First Name: '', The First Name must be between " + APPLICANT_FIRST_NAME_MIN + " and " + APPLICANT_FIRST_NAME_MAX + " characters long";
        String expectedError2 = "Invalid First Name: '', The First Name allows alpha characters, spaces and hyphens";
        mvc.perform(post("/v1/create-fd-258")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(applicationAsString))
                .andDo(print())


                // Then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.error.code").value(FingerprintErrorCode.VALIDATION_ERROR_CODE.ordinal()))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')]").isArray())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')]", hasSize(2)))
                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')].errorMessage", hasItem(expectedError1)))
                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')].errorMessage", hasItem(expectedError2)));

    }

    @Test
    @DisplayName("When applicant FirstName greater than " + APPLICANT_FIRST_NAME_MAX + " chars, then it must fail with 400 HTTP error code")
    public void when_applicant_firstName_is_greater_than_max_char_request_should_be_invalid() throws Exception {

        // Given
        String longFirstName = RandomStringUtils.random(51, true, false);
        ApplicantName applicantName = ApplicantName.builder()
                .firstName(longFirstName)
                .lastName("LastName")
                .middleName("MiddleName")
                .build();
        Application application = ApplicationBuilder.newApplicationAndSetField("applicantName", applicantName);
        String applicationAsString = objectMapper.writeValueAsString(application);


        // When
        mvc.perform(post("/v1/create-fd-258")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(applicationAsString))
                .andDo(print())


                // Then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.error.code").value(FingerprintErrorCode.VALIDATION_ERROR_CODE.ordinal()))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')].errorMessage").value("Invalid First Name: '" + longFirstName + "', The First Name must be between " + APPLICANT_FIRST_NAME_MIN + " and " + APPLICANT_FIRST_NAME_MAX + " characters long"));
    }


    @Test
    @DisplayName("When applicant FirstName contains un-allowed special char, then it must fail with 400 HTTP error code")
    public void when_applicant_firstName_contains_unAllowed_special_char_request_should_be_invalid() throws Exception {

        // Given
        String invalidFirstName = new StringBuilder(RandomStringUtils.random(5, true, false))
                .append("@")  // Un-allowed char in the first Name
                .append(RandomStringUtils.random(5, true, false))
                .toString();


        ApplicantName applicantName = ApplicantName.builder()
                .firstName(invalidFirstName)
                .lastName("LastName")
                .middleName("MiddleName")
                .build();
        Application application = ApplicationBuilder.newApplicationAndSetField("applicantName", applicantName);
        String applicationAsString = objectMapper.writeValueAsString(application);


        // When
        mvc.perform(post("/v1/create-fd-258")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(applicationAsString))
                .andDo(print())


                // Then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.error.code").value(FingerprintErrorCode.VALIDATION_ERROR_CODE.ordinal()))

                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')]").exists())
                .andExpect(jsonPath("$.validation[?(@.fieldName=='applicantName.firstName')].errorMessage").value("Invalid First Name: '" + invalidFirstName + "', The First Name allows alpha characters, spaces and hyphens"));
    }


}