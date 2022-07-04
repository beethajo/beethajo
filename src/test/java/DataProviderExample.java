
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DataProviderExample {

    WebDriver driver;

    @BeforeTest
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @Test(dataProvider = "getData")
    public void BrowserDriverTest(String userName, String browser, String version) throws MalformedURLException, InterruptedException {
        DesiredCapabilities capabilities = loadCapabilities(browser, version);
        driver = new RemoteWebDriver(new URL("http://localhost:5556/wd/hub"), capabilities);
        driver.navigate().to("http://gmail.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys(userName);
        Thread.sleep(1000);
        driver.quit();
       }

    private DesiredCapabilities loadCapabilities(String browser, String version) {
        System.out.println("Parameterized value is : " + browser);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setVersion(version);
        capabilities.setPlatform(Platform.ANY);
        return capabilities;
    }


    @AfterTest
    public void closeDriver() {
       //driver.close();
        //driver.quit();
    }

    @DataProvider(parallel = true)
    public Object[][] getData() {

        Object data[][] = new Object[2][3];
        data[0][0] = "xyz@gmail.com";
        data[0][1] = "firefox";
        data[0][2] = "101";

        data[1][0] = "abc@gmail.com";
        data[1][1] = "chrome";
        data[1][2] = "102";
        return data;


    }
}