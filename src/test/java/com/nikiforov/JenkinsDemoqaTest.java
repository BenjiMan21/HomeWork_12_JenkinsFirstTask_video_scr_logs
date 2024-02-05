package com.nikiforov;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@DisplayName("Первая работа с Jenkins")
@Tag("first_task")
public class JenkinsDemoqaTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        //Configuration.holdBrowserOpen = true;
        Configuration.timeout = 5000; // default 4000
    }

    @Test
    @Owner("Nikiforov")
    @DisplayName("Заполнение и проверка формы на сайте demoqa")
    void fillRegistrationForm() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем раздел automation-practice-form", ()-> {
            open("/automation-practice-form");
            executeJavaScript("$('#fixedban').remove()");
            executeJavaScript("$('footer').remove()");
        });

        step("Заполняем форму", ()-> {
            $("#firstName").setValue("Alex");
            $("#lastName").setValue("Nikiforov");
            $("#userEmail").setValue("unleash21@mail.ru");
            $("#genterWrapper").$(byText("Male")).click();
            $("#userNumber").setValue("8999777665");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").click();
            $("[value='10']").click();
            $(".react-datepicker__year-select").click();
            $("[value='1989']").click();
            $(".react-datepicker__month").$(byText("20")).click();
            $("#subjectsInput").setValue("Eng");
            $("#subjectsWrapper").$(byText("English")).click();
            $("#subjectsInput").setValue("Ma");
            $("#subjectsWrapper").$(byText("Maths")).click();
            $("#hobbiesWrapper").$(byText("Sports")).click();
            $("#hobbiesWrapper").$(byText("Reading")).click();
            $("#uploadPicture").uploadFromClasspath("image.jpg");
            $("#currentAddress").setValue("Lorem ipsum dolor");
            $("#stateCity-wrapper").$(byText("Select State")).click();
            $("#stateCity-wrapper").$(byText("NCR")).click();
            $("#stateCity-wrapper").$(byText("Select City")).click();
            $("#stateCity-wrapper").$(byText("Delhi")).click();
            $("#submit").click();
        });

        step("Проверяем результат", ()-> {
            $(".modal-content").shouldHave(text("Thanks for submitting the form"));
            $(".modal-body").shouldHave(text("Alex Nikiforov"));
            $(".modal-body").shouldHave(text("unleash21@mail.ru"));
            $(".modal-body").shouldHave(text("Male"));
            $(".modal-body").shouldHave(text("8999777665"));
            $(".modal-body").shouldHave(text("20 November,1989"));
            $(".modal-body").shouldHave(text("English, Maths"));
            $(".modal-body").shouldHave(text("Sports, Reading"));
            $(".modal-body").shouldHave(text("image.jpg"));
            $(".modal-body").shouldHave(text("Lorem ipsum dolor"));
            $(".modal-body").shouldHave(text("NCR Delhi"));
        });
    }
}