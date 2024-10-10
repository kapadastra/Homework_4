package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserFactory {

    // Получение драйвера браузера с указанием режима работы
    public static WebDriver getBrowser(String browser, String mode) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                applyMode(firefoxOptions, mode);
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                applyMode(edgeOptions, mode);
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                applyMode(chromeOptions, mode);
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        return driver;
    }

    // Метод для применения режимов работы браузера (headless, fullscreen, kiosk и т.д.)
    private static void applyMode(ChromeOptions options, String mode) {
        switch (mode.toLowerCase()) {
            case "headless":
                options.addArguments("--headless", "--disable-gpu");
                break;
            case "kiosk":
                options.addArguments("--kiosk");
                break;
            case "fullscreen":
                options.addArguments("--start-fullscreen");
                break;
            default:
                options.addArguments("--start-maximized");
                break;
        }
    }

    private static void applyMode(FirefoxOptions options, String mode) {
        switch (mode.toLowerCase()) {
            case "headless":
                options.addArguments("--headless");
                break;
            case "fullscreen":
                options.addArguments("--start-fullscreen");
                break;
            default:
                options.addArguments("--start-maximized");
                break;
        }
    }

    private static void applyMode(EdgeOptions options, String mode) {
        switch (mode.toLowerCase()) {
            case "headless":
                options.addArguments("--headless", "--disable-gpu");
                break;
            case "kiosk":
                options.addArguments("--kiosk");
                break;
            case "fullscreen":
                options.addArguments("--start-fullscreen");
                break;
            default:
                options.addArguments("--start-maximized");
                break;
        }
    }
}

