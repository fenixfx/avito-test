package org.fenix_fx.fenix_fx;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;


public class AvitoPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public AvitoPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
    }

    @FindBy(id = "search")
    private WebElement searchField;

    @FindBy(xpath = "//div[contains(@class, \"locationWrapper\")]")
    private WebElement regionBtn;

    @FindBy(xpath = "//input[@data-marker=\"popup-location/region/input\"]")
    private WebElement regionField;

    @FindBy(xpath = "//button[@data-marker=\"popup-location/save-button\"]")
    private WebElement regionSaveBtn;

    @FindBy(xpath = "//div[contains(@class,\"sort-select\")]")
    private WebElement sortSelect;

    @FindBy(xpath = "//span[contains(text(), \"только с фото\")]")
    private WebElement withImagesOnly;

    @FindAll(
            @FindBy(xpath = "//div[@data-marker=\"catalog-serp\"]/div[@data-marker=\"item\"][position() <= 10]"))
    private List<WebElement> itemElements;

    @FindBy(xpath = "//ul[@data-marker=\"rubricator/list\"]")
    private WebElement categoryList;

    public void changeRegion(String region){
        regionBtn.click();
        regionField.sendKeys(region);
        WebElement suggestion = driver.findElement(By.xpath("//li[@data-marker=\"suggest(0)\"]"));
        wait.until(textToBePresentInElement(suggestion, region));
        driver.findElement(By.xpath("//li[@data-marker=\"suggest(0)\"]")).click();
        regionSaveBtn.click();
    }

    public void execSearch(String search){
        searchField.sendKeys(search);
        searchField.sendKeys(Keys.ENTER);
    }

    public void sortByDate(){
        sortSelect.click();
        sortSelect.findElement(By.xpath("//option[contains(text(), \"По дате\")]")).click();
    }

    public void changeWithImagesOnly(){
        withImagesOnly.click();
    }

    public List<WebElement> getItems(){
        return itemElements;
    }

    public void changeCategory(String category){
        categoryList.findElement(By.xpath(String.format("//a[text()=\"%s\"]", category))).click();
    }

}