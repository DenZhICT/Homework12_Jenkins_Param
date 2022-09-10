package guru.qa.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.Component.Checker;
import guru.qa.pages.Component.Setter;
import guru.qa.restaker.TestData;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegFormPage {
    private Setter setInPage = new Setter();
    private Checker matchIt = new Checker();
    private SelenideElement
            firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            emailInput = $("#userEmail"),
            genderInput = $("#genterWrapper"),
            phoneInput = $("#userNumber"),
            addressInput = $("#currentAddress"),
            stateInput = $("#react-select-3-input"),
            cityInput = $("#react-select-4-input"),
            dateOfBirthIput = $("#dateOfBirthInput"),
            subjInput = $("#subjectsInput"),
            hobbiesInput = $("#hobbiesWrapper"),
            pictUpload = $("#uploadPicture"),
            submitButton = $("#submit");


    public RegFormPage openPage() {
        open("/automation-practice-form");
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");
        return this;
    }

    public RegFormPage fillPageForm(TestData data) {
        setInPage
                .justSet(firstNameInput, data.firstName)
                .justSet(lastNameInput, data.lastName)
                .justSet(emailInput, data.email);
        this.setGender(data.gender);
        setInPage.justSet(phoneInput, data.phone);
        this
                .setDateOfBirth(data.birth)
                .setSubj(data.subj)
                .setHobbies(data.hobbies)
                .setPhoto(data.pict);
        setInPage
                .justSet(addressInput, data.address)
                .setAndEnt(stateInput, data.stateAndCity[0])
                .setAndEnt(cityInput, data.stateAndCity[1]);
        clickSubmit();
        return this;
    }

    public RegFormPage checkPageForm(TestData data) {
        LocalDate time = LocalDate.parse(data.birth, DateTimeFormatter.ofPattern("MM.dd.yyyy"));
        String newTime = time.format(DateTimeFormatter.ofPattern("dd MMMM,yyyy", new Locale("en")));

        StringBuilder strVersOfSubj = new StringBuilder(data.subj[0]);
        for (int i = 1; i < data.subj.length; i++) {
            strVersOfSubj.append(", ").append(data.subj[i]);
        }

        StringBuilder strVersOfHobbies = new StringBuilder(data.hobbies[0]);
        for (int i = 1; i < data.hobbies.length; i++) {
            strVersOfHobbies.append(", ").append(data.hobbies[i]);
        }
        matchIt
                .checkRow("Student Name", data.firstName + " " + data.lastName)
                .checkRow("Student Email", data.email)
                .checkRow("Gender", data.gender)
                .checkRow("Mobile", data.phone)
                .checkRow("Date of Birth", newTime)
                .checkRow("Subjects", strVersOfSubj.toString())
                .checkRow("Hobbies", strVersOfHobbies.toString())
                .checkRow("Picture", data.pict)
                .checkRow("Address", data.address)
                .checkRow("State and City", data.stateAndCity[0] + " " + data.stateAndCity[1]);
        return this;
    }

    private RegFormPage clickSubmit() {
        submitButton.scrollTo().click();
        return this;
    }

    private RegFormPage setPhoto(String pict) {
        pictUpload.uploadFromClasspath(pict);
        return this;
    }

    private RegFormPage setHobbies(String[] hobbies) {
        for (String hobby : hobbies) {
            hobbiesInput.find(byText(hobby)).click();
        }
        return this;
    }

    private RegFormPage setSubj(String[] subj) {
        for (String s : subj) {
            subjInput.setValue(s).pressEnter();
        }
        return this;
    }

    private RegFormPage setDateOfBirth(String datebirth) {
        dateOfBirthIput.sendKeys(Keys.CONTROL + "a");
        dateOfBirthIput.sendKeys(datebirth + Keys.ENTER);
        return this;
    }

    private RegFormPage setGender(String gender) {
        genderInput.find(byText(gender)).click();
        return this;
    }

}
