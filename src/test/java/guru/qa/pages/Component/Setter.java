package guru.qa.pages.Component;

import com.codeborne.selenide.SelenideElement;

public class Setter {
    public Setter setAndEnt(SelenideElement key, String value) {
        key.setValue(value).pressEnter();
        return this;
    }

    public Setter justSet(SelenideElement key, String value) {
        key.setValue(value);
        return this;
    }
}
