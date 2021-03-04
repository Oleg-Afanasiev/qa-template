package com.academy.core.test;

import com.academy.core.util.PropertyProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private static final Logger LOG =  LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected String baseUrl;

    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) throws Exception {
        baseUrl = PropertyProvider.get("base.url");

        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", PropertyProvider.get("driver.chrome"));
                driver = new ChromeDriver();
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", PropertyProvider.get("driver.firefox"));
                driver = new FirefoxDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void logTestStart(Method method, Object[] params) {
        LOG.info("Start test {} with parameters {}",
                method.getName(), Arrays.toString(params));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method method) {
        LOG.info("Finish test {}", method.getName());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}
