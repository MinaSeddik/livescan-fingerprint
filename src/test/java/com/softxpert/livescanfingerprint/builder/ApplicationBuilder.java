package com.softxpert.livescanfingerprint.builder;

import com.softxpert.livescanfingerprint.model.Address;
import com.softxpert.livescanfingerprint.model.ApplicantName;
import com.softxpert.livescanfingerprint.model.Application;
import com.softxpert.livescanfingerprint.model.FingerprintImage;
import org.instancio.Instancio;
import org.instancio.InstancioApi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.softxpert.livescanfingerprint.model.ApplicationConstraint.*;
import static org.instancio.Select.field;

public class ApplicationBuilder {

    private static final String TEST_WSQ_FILE_NAME = "test.wsq";

    public static Application newApplication() {

        return createNewApplication()
                .create();
    }

    public static Application newApplicationAndSetField(String fieldName, ApplicantName applicantName) {

        return createNewApplication()
                .set(field(fieldName), applicantName)
                .create();
    }

    public static Application newApplicationIgnoreField(String fieldName) {

        return createNewApplication()
                .ignore(field(fieldName))
                .create();
    }

    public static Application newApplicationIgnoreField(Class clazz, String fieldName) {

        return createNewApplication()
                .ignore(field(clazz, fieldName))
                .create();
    }
    private static InstancioApi<Application> createNewApplication() {
        ApplicantName generatedApplicantName = Instancio.of(ApplicantName.class)
                .generate(field(ApplicantName::getFirstName), gen -> gen.text().pattern("#C#C#C#C#C#C"))
                .generate(field(ApplicantName::getLastName), gen -> gen.text().pattern("#C#C#C#C#C#C"))
                .generate(field(ApplicantName::getMiddleName), gen -> gen.text().pattern("#C#C#C#C#C#C"))
                .create();

        Address generatedApplicantAddress = Instancio.of(Address.class)
                .generate(field(Address::getAddress1), gen -> gen.text().pattern("#a#a#a#a#a#a"))
                .generate(field(Address::getCity), gen -> gen.text().pattern("#C#c#c#c"))
                .generate(field(Address::getState), gen -> gen.text().pattern("#C#C"))
                .generate(field(Address::getZipCode), gen -> gen.text().pattern("#d#d#d#d#d"))
                .create();


        AtomicInteger fingerPosition = new AtomicInteger(1);
        List<FingerprintImage> generatedFingerprints = Instancio.ofList(FingerprintImage.class)
                .size(14)
                .set(field(FingerprintImage::getPosition), fingerPosition.getAndIncrement())
                .set(field(FingerprintImage::getWsqImage), buildWsqImageAsBase64())
                .create();

        return Instancio.of(Application.class)
                .set(field(Application::getApplicantName), generatedApplicantName)
                .generate(field(Application::getOri), gen -> gen.text().pattern("#C#C#C#C#C#C#C#C#C#C"))
                .supply(field(Application::getDateOfBirth),
                        random -> LocalDate.now().minusYears(18 + random.intRange(0, 60)).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))
                .supply(field(Application::getCompletionDate),
                        random -> LocalDate.now().minusDays(random.intRange(0, 5)).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))
                .set(field(Application::getResidenceOfPersonFingerPrinted), generatedApplicantAddress)
                .generate(field(Application::getReasonFingerPrinted), gen -> gen.text().pattern("#a#a#a#a#a#a#a#a#a#a#a#a"))
                .generate(field(Application::getCitizenship), gen -> gen.text().pattern("#C#C"))
                .generate(field(Application::getPlaceOfBirth), gen -> gen.text().pattern("#C#C"))
                .generate(field(Application::getOca), gen -> gen.text().pattern("#C#C#C#C#C#C"))
                .generate(field(Application::getSsn), gen -> gen.text().pattern("#d#d#d#d#d#d#d#d#d"))
                .supply(field(Application::getSex), random -> String.valueOf("MFU".charAt(random.intRange(0, 2))))
                .supply(field(Application::getRace), random -> String.valueOf("WBAIU".charAt(random.intRange(0, 4))))
                .supply(field(Application::getWight), random -> random.intRange(APPLICANT_WIGHT_MIN, APPLICANT_WIGHT_MAX))
                .supply(field(Application::getHeight), random -> random.intRange(APPLICANT_HEIGHT_MIN, APPLICANT_HEIGHT_MAX))
                .supply(field(Application::getHair), random -> "BAL,BLK,BLN,BLU,BRO,GRY,GRN,ONG,PNK,PLE,RED,SDY,WHI".split(",")[random.intRange(0, 12)])
                .supply(field(Application::getEyes), random -> "BLK,BLU,BRO,GRY,GRN,HAZ,MAR,MUL,PNK".split(",")[random.intRange(0, 8)])


                .set(field(Application::getSignatureImage), buildWsqImageAsBase64())
                .set(field(Application::getFingerprints), generatedFingerprints);
    }

    public static String buildWsqImageAsBase64() {


        try {
            ClassLoader classLoader = ApplicationBuilder.class.getClassLoader();
            File file = new File(classLoader.getResource(TEST_WSQ_FILE_NAME).getFile());

            return Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
