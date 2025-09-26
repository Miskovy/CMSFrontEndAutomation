package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.Hooks;
import java.time.Duration;

public class P02_Dashboard {
    // Setup
    private WebDriver driver;
    private WebDriverWait wait;

    public P02_Dashboard(WebDriver driver) {
        this.wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));// 10-second wait
    }

    // Side Bar and Menu
    public WebElement homeButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div/div/div[2]/ul/li[1]"));}
    public WebElement usersButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div/div/div[2]/ul/li[2]"));}
    public WebElement plansButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div/div/div[2]/ul/li[3]"));}
    public WebElement categoriesButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div/div/div[2]/ul/li[4]"));}
    public WebElement templatesButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div/div/div[2]/ul/li[5]"));}
    public WebElement websitesButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div/div/div[2]/ul/li[6]"));}
    public WebElement subscriptionsButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div/div/div[2]/ul/li[7]"));}
    public WebElement paymentsButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div/div/div[2]/ul/li[8]"));}
    public WebElement settingsDropdown(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/div/div/div/div[2]/ul/li[9]"));}
    public WebElement logoutButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/header/div[2]"));}
    public WebElement sidebarButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/header/div[1]/div[1]"));}
    // Users Management
    public WebElement userTitle(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/div/div/div[1]/h2"));}
    public WebElement addUser(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/div/div/div[1]/a"));}
    public WebElement searchUsersBox(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/div/div/div[2]/div[1]/div[2]/div[2]/div/div/input"));}
    public WebElement searchUsersByDropdown(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/div/div/div[2]/div[1]/div[2]/div[2]/div/button[1]"));}
    public WebElement filterUsersButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/div/div/div[2]/div[1]/div[2]/div[2]/div/button[2]"));}
    private WebElement getUserActionButton(String userEmail, String action) {
        String svgIconClass = action.equalsIgnoreCase("edit") ? "lucide-square-pen" : "lucide-trash";

        // This XPath finds the row with the user's email, then finds the button
        // within that row that contains the correct SVG icon.
        String dynamicXPath = String.format(
                "//tr[td[contains(text(), '%s')]]//button[.//svg[contains(@class, '%s')]]",
                userEmail,
                svgIconClass
        );

        By locator = By.xpath(dynamicXPath);

        // Wait until the button is present and clickable before returning it
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void clickEditButtonForUser(String userEmail) {
        System.out.println("Attempting to click EDIT for user: " + userEmail);
        WebElement editButton = getUserActionButton(userEmail, "edit");
        editButton.click();
        System.out.println("Successfully clicked the edit button.");
    }
    public void clickDeleteButtonForUser(String userEmail) {
        System.out.println("Attempting to click DELETE for user: " + userEmail);
        WebElement deleteButton = getUserActionButton(userEmail, "delete");
        deleteButton.click();
        System.out.println("Successfully clicked the delete button.");
    }
}
