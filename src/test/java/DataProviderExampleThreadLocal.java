import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DataProviderExampleThreadLocal {

    public ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();
    public ThreadLocal<DesiredCapabilities> dc = new ThreadLocal<DesiredCapabilities>();
    WebDriver driver;
    DesiredCapabilities capabilities;

    public WebDriver getDriver() {
        return dr.get();
    }

    public void setWebDriver(WebDriver driver) {
        dr.set(driver);
    }

    public DesiredCapabilities getDesiredCapabilities() {
        return dc.get();
    }

    public void setDesiredCapabilities(DesiredCapabilities capabilities) {
        dc.set(capabilities);
    }

    @BeforeTest
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @Test(dataProvider = "getData")
    public void BrowserDriverTest(String userName, String browser, String version) throws MalformedURLException, InterruptedException {
        capabilities = loadCapabilities(browser, version);
        setDesiredCapabilities(capabilities);
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getDesiredCapabilities());
        setWebDriver(driver);


        getDriver().navigate().to("http://gmail.com");
        manageBrowser();
        getDriver().findElement(By.xpath("//input[@id='identifierId']")).sendKeys(userName);
        getDriver().quit();

    }

    public void manageBrowser() {
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
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
        // TODO:  not working the quit giving null, working only above method
        //getDriver().quit();
    }

    @DataProvider(parallel = true)
    public Object[][] getData() {

        Object data[][] = new Object[4][3];
        data[0][0] = "xyz@gmail.com";
        data[0][1] = "firefox";
        data[0][2] = "101";

        data[1][0] = "abc@gmail.com";
        data[1][1] = "chrome";
        data[1][2] = "102";

        data[2][0] = "xyz@gmail.com";
        data[2][1] = "firefox";
        data[2][2] = "101";

        data[3][0] = "abc@gmail.com";
        data[3][1] = "chrome";
        data[3][2] = "102";

        return data;


    }
}