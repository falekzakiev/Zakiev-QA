import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class First_Test {
    private WebDriver driver;
    private String baseUrl;


    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver");
        driver = new ChromeDriver();
        baseUrl = "https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().fullscreen();
    }

    @Test
    public void testPaymenConfirmited() {
        driver.get(baseUrl);

        WebElement cardNumber = driver.findElement(By.id("input-card-number"));
        WebElement cardHolder = driver.findElement(By.id("input-card-holder"));
        WebElement cardCvc = driver.findElement(By.id("input-card-cvc"));
        WebElement cardMonth = driver.findElement(By.id("card-expires-month"));
        WebElement cardYear = driver.findElement(By.id("card-expires-year"));

        cardNumber.click();
        cardNumber.clear();
        cardNumber.sendKeys("4000 0000 0000 0036");
        cardHolder.click();
        cardHolder.clear();
        cardHolder.sendKeys("DFF FFF");
        cardMonth.click();
        new Select(cardMonth).selectByVisibleText("04");
        cardYear.click();
        new Select(cardYear).selectByVisibleText("2023");
        cardCvc.click();
        cardCvc.clear();
        cardCvc.sendKeys("111");
        driver.findElement(By.id("action-submit")).click();
        /*driver.get("https://sandbox.cardpay.com/sandbox-emulator/acspage/cap?RID=136&VAA=A");
        driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?bank_id=sandbox");*/

        assertEquals(driver.findElement(By.xpath("//div[@id='payment-item-status']/div[2]")).getText(), "Confirmed");


    }

    @Test
    public void testCardNumberValideter() {
        driver.get(baseUrl);

        WebElement cardNumber = driver.findElement(By.id("input-card-number"));
        WebElement cardHolder = driver.findElement(By.id("input-card-holder"));

        cardNumber.clear();
        cardNumber.sendKeys("1111 1111 1111 1111");
        cardHolder.click();
        assertEquals(driver.findElement(By.xpath("//*[@id=\"card-number-field\"]/div/label")).getText(), "Card number is not valid");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void TestMoveMouse() throws InterruptedException {
        driver.get(baseUrl);
        WebElement cvcHint = driver.findElement(By.id("cvc-hint"));

        Actions actions = new Actions(driver);
        actions.moveToElement(cvcHint).click().build().perform();
        driver.wait(3000);
    }
}