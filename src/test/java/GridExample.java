import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class GridExample {

    WebDriver driver;

    @BeforeTest
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @Test
    @Parameters("browser")

    public void FirefoxDriverTest(String browser) throws MalformedURLException, InterruptedException {
        Capabilities caps;

        if (browser.equalsIgnoreCase("chrome")) {
            System.out.println("using chrome browser");
            caps = new ChromeOptions();
            driver = new RemoteWebDriver(new URL("http://localhost:4444"),caps );
        }
        if (browser.equalsIgnoreCase("firefox")) {
            System.out.println("using firefox browser");
            caps = new FirefoxOptions();

            driver = new RemoteWebDriver(new URL("http://localhost:4444"),caps );
        }

        driver.navigate().to("http://gmail.com");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys("username");
    }


    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }

}