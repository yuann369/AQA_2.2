import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class SelenideTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void test() {
        String planningDate = generateDate(4);

        open("http://localhost:9999/");

        $x("//input[@placeholder='Город']").setValue("Самара");
        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("span[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("span[data-test-id='date'] input").setValue(planningDate);
        $x("//input[@name='name']").setValue("Петров Александр");
        $x("//input[@name='phone']").setValue("+79278764312");
        $(By.className("checkbox__box")).click();
        $x("//*[text()='Забронировать']").click();

        $(By.className("notification__title")).shouldBe(visible, Duration.ofSeconds(25));
        $(By.cssSelector(".notification__content")).shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate)).shouldBe(visible);
    }
}
