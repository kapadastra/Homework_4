import factory.BrowserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class OtusWebDriverTest {

    private static final Logger logger = LogManager.getLogger(OtusWebDriverTest.class);
    private WebDriver driver;
    private String mode;



    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        String baseUrl = System.getProperty("baseUrl", "https://otus.home.kartushin.su/training.html");
        String mode = System.getProperty("mode", "headless");
        driver = BrowserFactory.getBrowser(browser, mode);
        driver.get(baseUrl);
        logger.info("Перешли на страницу: " + baseUrl);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Браузер закрыт");
    }

    @Test
    public void testHeadlessModeInput() {
        mode = "headless";

        logger.info("Тест на ввод текста в headless режиме");
        WebElement inputField = driver.findElement(By.id("textInput"));
        inputField.sendKeys("ОТУС");

        String enteredText = inputField.getAttribute("value");
        assertEquals("ОТУС", enteredText, "Текст в поле не соответствует ОТУС");
        logger.info("Проверка текста прошла успешно");
    }

    @Test
    public void testKioskModeModalWindow() {
        mode = "kiosk";

        logger.info("Тест на проверку модального окна в режиме киоска");
        WebElement modalWindow = driver.findElement(By.id("myModal"));


        assertFalse(modalWindow.isDisplayed(), "Модальное окно должно быть закрыто до клика");

        WebElement modalButton = driver.findElement(By.id("openModalBtn"));
        modalButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeToBe(modalWindow, "style", "display: block;"));

        assertTrue(modalWindow.isDisplayed(), "Модальное окно должно быть открыто после клика");
        logger.info("Модальное окно успешно открылось");

    }

    @Test
    public void testFullscreenModeFormSubmission() {
        mode = "fullscreen";

        logger.info("Тест на отправку формы в полноэкранном режиме");
        WebElement nameField = driver.findElement(By.id("name"));
        WebElement emailField = driver.findElement(By.id("email"));

        nameField.sendKeys("Александр");
        emailField.sendKeys("test@otus.ru");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement dynamicMessage = driver.findElement(By.id("messageBox"));
        assertTrue(dynamicMessage.getText().contains("Форма отправлена с именем: Александр и email: test@otus.ru"),
                "Сообщение о подтверждении не появилось");
        logger.info("Сообщение о подтверждении успешно проверено");
    }
}
