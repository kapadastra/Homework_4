import factory.BrowserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OtusWebDriverTest {

    private static final Logger logger = LogManager.getLogger(OtusWebDriverTest.class);
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        // Инициализация драйвера
        driver = BrowserFactory.getBrowser(browser, "headless");
        driver.get("https://otus.home.kartushin.su/training.html");
        logger.info("Перешли на страницу OTUS");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        logger.info("Браузер закрыт");
    }

    @Test
    public void testHeadlessModeInput() {
        logger.info("Тест на ввод текста в headless режиме");
        WebElement inputField = driver.findElement(By.id("textInput"));
        inputField.sendKeys("ОТУС");

        String enteredText = inputField.getAttribute("value");
        assertEquals("ОТУС", enteredText, "Текст в поле не соответствует ОТУС");
        logger.info("Проверка текста прошла успешно");
    }

    @Test
    public void testKioskModeModalWindow() {
        logger.info("Тест на открытие модального окна в режиме киоска");
        WebElement modalButton = driver.findElement(By.id("openModalBtn"));
        modalButton.click();

        WebElement modalWindow = driver.findElement(By.id("myModal"));
        assertTrue(modalWindow.isDisplayed(), "Модальное окно не открылось");
        logger.info("Модальное окно успешно открылось");
    }

    @Test
    public void testFullscreenModeFormSubmission() {
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
