import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class SelenideTest {
    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        open("http://localhost:9999");
    }

    @Test
    void test() {
        $x("//input[@placeholder='Город']").setValue("Самара");
        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("span[data-test-id='date'] input").sendKeys(Keys.DELETE);

        LocalDate date = LocalDate.now().plusDays(4);
        String localDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("span[data-test-id='date'] input").setValue(localDate);


        $x("//input[@name='name']").setValue("Петров Александр");
        $x("//input[@name='phone']").setValue("+79278764312");
        $(By.className("checkbox__box")).click();
        $x("//*[text()='Забронировать']").click();

        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(20));
        $(byText("Встреча успешно забронирована на " + localDate));
    }
}
