package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Hooks {
    public static WebDriver driver;
    private static WebDriverWait wait;

    @Before
    public void OpenBrowser() {
        // 1- Bridge
        System.setProperty("webdriver.chrome.driver","C:\\Program Files\\chromedriver.exe");
        // 2- create object from Chrome browser
        driver = new ChromeDriver();
        //3- Configurations
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
// 4- navigate to url
        driver.get("http://localhost:5173/");
    }

    @After
    public void quitDriver() throws InterruptedException {
        Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
        }
    }
}