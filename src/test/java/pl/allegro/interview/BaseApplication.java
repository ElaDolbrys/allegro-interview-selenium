package pl.allegro.interview;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class BaseApplication {

    private static final long DEFAULT_TIMEOUT = 20L;

    private String baseAppUrl;

    private WebDriver driver;
    private Actions actions;

    protected BaseApplication(String baseAppUrl) {
        this.baseAppUrl = baseAppUrl;

        this.driver = new FirefoxDriver();
        this.actions = new Actions(driver);

    }

    public static BaseApplication forUrl(String baseAppUrl) {
        return new BaseApplication(baseAppUrl)
                ;
    }

    public BaseApplication open() {
        driver.get(baseAppUrl);
        return this;
    }


    public void close() {
        driver.close();
    }


    public BaseApplication enterUrl(String url) {
        driver.get((baseAppUrl + url).replace("//", "/"));
        return this;
    }

    public BaseApplication clickOn(String xPathElementLocation) {
        driver.findElement(By.xpath(xPathElementLocation)).click();
        return this;
    }

    public BaseApplication insertText(String xPathElementLocation, String inputText) {
        driver.findElement(By.xpath(xPathElementLocation)).sendKeys(inputText);
        return this;
    }
    public BaseApplication pressEnter(String xPathElementFocus) {
        driver.findElement(By.xpath(xPathElementFocus)).sendKeys(Keys.ENTER);
        return this;
    }

    public String getTextFromElement(String xPathElementLocation) {
        String textFromElement = driver.findElement(By.xpath(xPathElementLocation)).getText();
        return textFromElement;
    }

    public BaseApplication think() {
        return think(DEFAULT_TIMEOUT);
    }

    public BaseApplication think(long time) {
        long now = Calendar.getInstance().getTimeInMillis();
        final long expectedGoTimeMillis = now + time * 1000L;

        WebDriverWait wait = new WebDriverWait(driver, time + DEFAULT_TIMEOUT);
        wait.until(webDriver -> Calendar.getInstance().getTimeInMillis() >= expectedGoTimeMillis);

        return this;
    }


    public BaseApplication ensureXPathElementExists(String elementPath) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementPath)));
        return this;
    }

    public BaseApplication ensureCurrentUrlLike(String urlRegex) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.urlMatches(urlRegex));
        return this;
    }



    public BaseApplication enterRandomTableElement(String dataTableXpath) {
        List<WebElement> tableRows = driver.findElements(By.xpath(dataTableXpath + "/tr"));
        Integer rowsNumber = tableRows.size();
        Integer randomRow = new Random().nextInt(rowsNumber);
        tableRows.get(randomRow)
                .findElement(By.xpath(".//td[1]"))
                .click();
        WebElement myDynamicElement = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Otwórz' and not(@class='ui-state-disabled')]")));
        clickOn("//a[text()='Otwórz' and not(@class='ui-state-disabled')]");
        return this;
    }
}
