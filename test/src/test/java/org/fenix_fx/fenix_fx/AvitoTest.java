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
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
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

        List<WebElement> items = avitoPage.getItems();

        Comparator<WebElement> comp = (el1, el2) -> {
            Integer price1 = Integer.parseInt(el1.findElement(By.xpath(".//meta[@itemprop=\"price\"]")).getAttribute("content"));
            Integer price2 = Integer.parseInt(el2.findElement(By.xpath(".//meta[@itemprop=\"price\"]")).getAttribute("content"));
            return price1.compareTo(price2);
        };
        TreeSet<WebElement> itemSet = new TreeSet<WebElement>(comp);
        itemSet.addAll(items);

        StringBuilder res = new StringBuilder();
        itemSet.forEach(item -> res.append(item.findElement(By.xpath(".//a")).getAttribute("href")).append("\n"));
        saveToFile(res.toString());
    }

    @AfterClass
    public static void quit(){
        driver.quit();
    }
}

