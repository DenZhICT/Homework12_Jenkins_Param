package guru.qa.restaker;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.List;


public class TestData {
    public String firstName, lastName, email, gender, phone, birth, pict, address;
    public String[] subj, hobbies, stateAndCity;

    public TestData() throws Exception {
        this.firstName = randomFirstName();
        this.lastName = randomLastName();
        this.email = randomEmail();
        this.gender = randomGender();
        this.phone = randomPhoneNumber();
        this.birth = randomDateOfBirth();
        this.subj = randomSubjects();
        this.hobbies = randomHobbies();
        this.pict = notRandomPicture();
        this.address = randomAddress();
        this.stateAndCity = randomStateAndCity();
    }

    private String[] randomStateAndCity() {
        String[] states = {"NCR", "Uttar Pradesh", "Haryana", "Rajasthan"};
        String curState = randomElement(states);
        String[] citys = new String[0];
        switch (curState) {
            case ("NCR"):
                citys = new String[]{"Delhi", "Gurgaon", "Noida"};
                break;
            case ("Uttar Pradesh"):
                citys = new String[]{"Agra", "Lucknow", "Merrut"};
                break;
            case ("Haryana"):
                citys = new String[]{"Karnal", "Panipat"};
                break;
            case ("Rajasthan"):
                citys = new String[]{"Jaipur", "Jaiselmer"};
                break;
        }
        String curCity = randomElement(citys);
        return new String[]{curState, curCity};
    }

    private String randomAddress() {
        return new Faker().address().fullAddress();
    }

    private String notRandomPicture() {
        return "photo.png";
    }

    private String[] randomHobbies() throws Exception {
        Path pathHobbies = Path.of("src/test/resources/hobbies.text");
        List<String> list = Files.readAllLines(pathHobbies);
        return randomArrayMaker(list);
    }

    private String[] randomSubjects() throws Exception {
        Path pathSubjects = Path.of("src/test/resources/subjects.text");
        List<String> list = Files.readAllLines(pathSubjects);
        return randomArrayMaker(list);
    }

    private String randomDateOfBirth() {
        SimpleDateFormat needForm = new SimpleDateFormat("MM.dd.yyyy");
        return needForm.format(new Faker().date().birthday());
    }

    private String randomPhoneNumber() {
        return new Faker().phoneNumber().subscriberNumber(10);
    }

    private String randomGender() {
        String[] gender = {"Male", "Female", "Other"};
        return randomElement(gender);
    }

    private String randomEmail() {
        return new Faker().internet().emailAddress();
    }

    private String randomLastName() {
        return new Faker().name().lastName();
    }

    private String randomFirstName() {
        return new Faker().name().firstName();
    }

    private String[] randomArrayMaker(List<String> list) {
        RandomService faker = new Faker().random();
        int randomLen = faker.nextInt(1, list.size());
        String[] array = new String[randomLen];
        int randomIndex;
        for (int i = 0; i < randomLen; i++) {
            randomIndex = faker.nextInt(list.size());
            array[i] = list.get(randomIndex);
            list.remove(randomIndex);
        }
        return array;
    }

    private String randomElement(String[] list) {
        int randomIndex = new Faker().random().nextInt(list.length);
        return list[randomIndex];
    }
}
