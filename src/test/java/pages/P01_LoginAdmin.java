package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepDefinitions.Hooks;

public class P01_LoginAdmin {
    public WebElement emailTxt(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/form/div[1]/input"));}
    public WebElement passwordTxt(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/form/div[2]/input"));}
    public WebElement loginButton(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/form/div[3]/button"));}
    public WebElement loginTitle(){return Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/h2"));}
}
