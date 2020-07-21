package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import ru.netology.web.DataGenerator;
import ru.netology.web.RegistrationByCustomerInfo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.DataGenerator.Registration.generateByInfo;

public class CardDeliveryTest {

    RegistrationByCustomerInfo registrationInfo = generateByInfo("ru");

    @Test
    void shouldEnterValidData() {
        open("http://localhost:7777");
        $("[data-test-id='city'] input").setValue("Калуга");
        $("[data-test-id='date'] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id='date'] input").setValue(DataGenerator.forwardDate(3));
        $("[data-test-id='name'] input").setValue(registrationInfo.getFullName());
        $("[data-test-id='phone'] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(withText("Успешно")).waitUntil(Condition.visible, 15000);
        $("[data-test-id='date'] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id='date'] input").setValue(DataGenerator.forwardDate(5));
        $(byText("Запланировать")).click();
        $(withText("Необходимо подтверждение")).waitUntil(Condition.visible, 15000);
        $(byText("Перепланировать")).click();
        $(withText("Успешно")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldEnterNotValidCity() {
        open("http://localhost:7777");
        $("[data-test-id='city'] input").setValue("Караганда");
        $("[data-test-id='date'] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id='date'] input").setValue(DataGenerator.forwardDate(3));
        $("[data-test-id='name'] input").setValue(registrationInfo.getFullName());
        $("[data-test-id='phone'] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(".input_invalid[data-test-id='city']").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldEnterEmptyCity() {
        open("http://localhost:7777");
        $("[data-test-id='date'] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id='date'] input").setValue(DataGenerator.forwardDate(3));
        $("[data-test-id='name'] input").setValue(registrationInfo.getFullName());
        $("[data-test-id='phone'] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(".input_invalid[data-test-id='city']").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEnterNotValidName() {
        open("http://localhost:7777");
        $("[data-test-id='city'] input").setValue("Калуга");
        $("[data-test-id='date'] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id='date'] input").setValue(DataGenerator.forwardDate(3));
        $("[data-test-id='name'] input").setValue("Petrov");
        $("[data-test-id='phone'] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(".input_invalid[data-test-id='name']").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldEnterNotValidPhone() {
        open("http://localhost:7777");
        $("[data-test-id='city'] input").setValue("Калуга");
        $("[data-test-id='date'] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id='date'] input").setValue(DataGenerator.forwardDate(3));
        $("[data-test-id='name'] input").setValue(registrationInfo.getFullName());
        $("[data-test-id='phone'] input").setValue("+790845678");
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(".input_invalid[data-test-id='phone']").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotClickAgreement() {
        open("http://localhost:7777");
        $("[data-test-id='city'] input").setValue("Калуга");
        $("[data-test-id='date'] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id='date'] input").setValue(DataGenerator.forwardDate(3));
        $("[data-test-id='name'] input").setValue(registrationInfo.getFullName());
        $("[data-test-id='phone'] input").setValue(registrationInfo.getPhoneNumber());
        $(byText("Запланировать")).click();
        $(".input_invalid[data-test-id='agreement']").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
