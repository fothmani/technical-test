package ch.itecor.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CustomDriver {

    public static WebDriver initialiseDriverOfType(String driverRequired) {
        WebDriver driver = null;
        String projectPath = System.getProperty("user.dir");
        switch(driverRequired) {
            case "Chrome":
                // Code for Chrome driver
                // Place the chromedriver in ch.itecor.driver folder
            	System.setProperty("webdriver.chrome.driver", projectPath + "//src//main//java//ch//itecor//driver//chromedriver.exe");
            	driver = new ChromeDriver();
                break;
            case "Firefox":
                // Code for Firefox driver
            	System.setProperty("webdriver.gecko.driver", projectPath + "//src//main//java//ch//itecor//driver//geckodriver.exe");
            	driver = new FirefoxDriver();
                break;
            default:
                return null;
        }
        // Code for opening the driver in full screen mode
        driver.manage().window().maximize();
        return driver;
    }

}
