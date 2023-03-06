package com.softxpert.livescanfingerprint.model;

public class ApplicationConstraint {


    public static final int APPLICANT_FIRST_NAME_MIN = 1;
    public static final int APPLICANT_FIRST_NAME_MAX = 50;
    public static final String APPLICANT_NAME_REGEX = "^[A-Za-z -]+$";
    public static final int APPLICANT_LAST_NAME_MIN = 1;
    public static final int APPLICANT_LAST_NAME_MAX = 50;
    public static final int APPLICANT_MIDDLE_NAME_MIN = 1;
    public static final int APPLICANT_MIDDLE_NAME_MAX = 50;
    public static final int ORI_MAX_SIZE = 10;
    public static final int OCA_MAX_SIZE = 10;
    public static final int REASON_FINGERPRINTED_MAX_SIZE = 30;
    public static final String DATE_PATTERN = "MM/dd/yyyy";
    public static final int APPLICANT_ADDRESS_ADDRESS1_MIN = 1;
    public static final int APPLICANT_ADDRESS_ADDRESS1_MAX = 50;
    public static final int APPLICANT_ADDRESS_CITY_MAX = 20;
    public static final String APPLICANT_ADDRESS_STATE_REGEX = "^[A-Z]{2}$";
    public static final String APPLICANT_ADDRESS_ZIPCODE_REGEX = "^[0-9]{5}$";
    public static final String APPLICANT_SSN_REGEX = "^[0-9]{9}$";
    public static final String APPLICANT_CITIZENSHIP_REGEX = "^[A-Z]{2}$";
    public static final String APPLICANT_PLACE_OF_BIRTH_REGEX = "^[A-Z]{2}$";
    public static final String APPLICANT_SEX_REGEX = "^[MFU]$";
    public static final String APPLICANT_RACE_REGEX = "^[WBAIU]$";
    public static final String APPLICANT_HAIR_COLOR_CODE_REGEX = "^(BAL|BLK|BLN|BLU|BRO|GRY|GRN|ONG|PNK|PLE|RED|SDY|WHI)$";
    public static final String APPLICANT_EYES_COLOR_CODE_REGEX = "^(BLK|BLU|BRO|GRY|GRN|HAZ|MAR|MUL|PNK)$";
    public static final int APPLICANT_HEIGHT_MIN = 400;
    public static final int APPLICANT_HEIGHT_MAX = 711;
    public static final int APPLICANT_WIGHT_MIN = 50;
    public static final int APPLICANT_WIGHT_MAX = 499;

    public static final int APPLICANT_FINGERPRINT_POSITION_MIN = 1;
    public static final int APPLICANT_FINGERPRINT_POSITION_MAX = 15;

    public static final String BASE64_ENCODED_IMAGE_REGEX = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$";
}