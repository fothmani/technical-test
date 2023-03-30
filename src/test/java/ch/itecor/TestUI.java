package ch.itecor;

import ch.itecor.driver.CustomDriver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestUI
{
    @Test
    public void technicalTestUI() {
        String baseUrl = "https://www.itecor-app.ch/";
        String expectedUsername = "User1";

        // Exercise 0: driver management
        WebDriver driver = CustomDriver.initialiseDriverOfType("Chrome");
        

        // Exercise 1: Login
        // Access the base url
        driver.get(baseUrl);
        // Log in by filling the username and password and click on the Submit button
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("User1");
        WebElement pwd = driver.findElement(By.id("password"));
        pwd.sendKeys("Password1234");
        WebElement submitBtn = driver.findElement(By.id("connexion_btn"));
        submitBtn.click();
        
        // Verify the username on the greetings message
        String actualUsername = driver.findElement(By.id("displayed_name")).getText(); 
        assertThat(actualUsername).isEqualTo(expectedUsername);

        // Exercise 2
        // Wait for spinner to disappear
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".loader-section.disappear.hidden")));

        // Select the Geneva office
        WebElement genevaOffice = driver.findElement(By.id("img_geneva_office"));
        genevaOffice.click();

        // Exercise 3
        // Get number of free seats from the orange number
        // Constraint: implement the method countTotalNumberOfFreeSeats at the bottom of this class
          int numOfFreeSeats = countTotalNumberOfFreeSeats(driver);
        // Verify that the total number of free seats is equal to 10
          assertThat(numOfFreeSeats).isEqualTo(10);
        
     

        // Exercise 4
        // Get the number of free seats in the HR Office
        int numberOfFreeSeatsInHROffice = countTotalNumberOfFreeSeatsInHROffice(driver);
        // Verify that the number of free seats in the HR office is equal to 4
        assertThat(numberOfFreeSeatsInHROffice).isEqualTo(4);

        // Exercise 5
        // Click on the third free seat in HR Office and then click on the 6th free seat among the remaining ones
        WebElement thirdFreeSeat = driver.findElement(By.cssSelector(".office-hr .free:nth-child(6)"));
        thirdFreeSeat.click();
        WebElement sixthFreeSeat = driver.findElement(By.cssSelector(".office-consultants .row:nth-child(3) > .free:first-child"));
        sixthFreeSeat.click();

        // Exercise 6
        // Verify that the total number of free seats was decreased by 2
        int currentNumOfFreeSeats = countTotalNumberOfFreeSeats(driver);
        assertThat(currentNumOfFreeSeats).isEqualTo(numOfFreeSeats-2);

        // Exercise 7
        // Show that the seats in the conference room are lightblue -> rgb(173, 216, 230)
        List<WebElement> conferenceRoomSeatsColor = driver.findElements(By.cssSelector(".conference .unbookable-seat"));   
        for(WebElement conferenceRoomSeatColor : conferenceRoomSeatsColor) {
        	assertThat(conferenceRoomSeatColor.getCssValue("background-color")).isEqualTo("rgba(173, 216, 230, 1)");
        	}

        // Exercise 8
        // Click on the HR chair and verify the following properties
        driver.findElement(By.cssSelector(".office-hr .unbookable-seat")).click();
        // 1) A popup bubble appears (is visible)
        // 2) The chair title is "HR chair"
        // 3) The popup message says "This seat cannot be booked"
        // 1)
        WebElement popup = driver.findElement(By.id("popup_hr"));
        assertThat(popup.getAttribute("style")).isNotEqualTo("display: none;");
        // 2)
        String actualHRChairTitle = driver.findElement(By.cssSelector(".office-hr .bubble-title")).getText();
        assertThat(actualHRChairTitle).isEqualTo("HR chair");
       
        // 3)
        String actualHRChairMessage = driver.findElement(By.cssSelector(".office-hr .bubble-description")).getText();
        assertThat(actualHRChairMessage).isEqualTo("This seat cannot be booked");

        // Exercise 9
        driver.navigate().back();
        driver.findElement(By.id("logout-symbol")).click();
        driver.findElement(By.id("logout")).click();
        String loginPageTitle = driver.findElement(By.tagName("h1")).getText();
        assertThat(loginPageTitle).isEqualTo("Itecor test platform");
        

        // Close the driver
         driver.close();
    }
    
    public int countTotalNumberOfFreeSeats(WebDriver driver) {
    	String numberOfFreeSeats = driver.findElement(By.className("free-seats-summary")).getText();
		return Integer.parseInt(numberOfFreeSeats);
    }
    
    public int countTotalNumberOfFreeSeatsInHROffice(WebDriver driver) {
    	List<WebElement> totalGreenSeats = driver.findElements(By.cssSelector(".office-hr .consultant-seat.free"));
    	return totalGreenSeats.size();
    }

}
