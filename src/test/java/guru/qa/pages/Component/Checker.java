package guru.qa.pages.Component;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class Checker {
    public Checker checkRow(String key, String value) {
        $(".modal-body").$(byText(key))
                .parent().shouldHave(text(value));
        return this;
    }
}
