import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeVars {

    public ChromeVars() {
    }

    public void pathVars() {
        String chromeDriverFile = "src/main/resources/chromedriver.exe";
        String chromeProperty = "webdriver.chrome.driver";
        System.setProperty(chromeProperty, chromeDriverFile);
    }

    public ChromeOptions options() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1260,800", "--disable-gpu", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");
        return options;
    }

}
