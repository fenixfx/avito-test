package org.fenix_fx.fenix_fx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class AvitoTest {
    public static WebDriver driver;
    public static AvitoPage avitoPage;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        avitoPage = new AvitoPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.get("https://www.avito.ru/rossiya");
    }

    public void saveToFile(String input) {
        Path path = Paths.get("./result.txt");
        try {
            Files.write(path, input.getBytes());
            System.out.print(String.format("Файл с результатом доступен по пути %s", path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void avitoTest(){
        avitoPage.changeWithImagesOnly();
        avitoPage.changeRegion("Москва");
        avitoPage.execSearch("iPhone XS 256gb");
        avitoPage.changeCategory("Телефоны");
        avitoPage.sortByDate();

        StringBuilder res = new StringBuilder();
        for (WebElement item: avitoPage.getItems()) {
            res.append(item.findElement(By.xpath(".//a")).getAttribute("href")).append("\n");
        }
        saveToFile(res.toString());
    }

    @AfterClass
    public static void quit(){
        driver.quit();
    }
}

