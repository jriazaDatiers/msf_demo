import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;


public class BaseLaunch extends ChromeVars{

    private WebDriver driver;
    private Wait<WebDriver> wait;

    @Before
    public WebDriver setUp() {
        pathVars();

        this.driver = new ChromeDriver( options());
        this.wait = new FluentWait<>(this.driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        return this.driver;
    }

    @After
    public void tearDown() {
        this.driver.quit();
    }



}
