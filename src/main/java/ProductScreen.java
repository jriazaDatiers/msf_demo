import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductScreen {

    private JSONObject productJson;
    private WebDriver driver;
    private final static String COMMA_DELIMITER = ";";
    private HashMap<String, String> xpathsMap = new HashMap<>();
    private List<String> productDetailsList = new ArrayList();
    private List<String> checkerList = new ArrayList();
    private List<String> productIdList = new ArrayList();


    public void checkProduct(){

        ReadJSON jsonMap = new ReadJSON();
        HashMap<String, JSONObject> productJsonMap = jsonMap.loadProductsJson();
        BaseLaunch launcher;

        loadXpathsToMap();
        loadCheckerList();
        loadProductList();

        for (String productId : productJsonMap.keySet()) {
            this.productJson = productJsonMap.get(productId);
            launcher = new BaseLaunch();
            this.driver = launcher.setUp();
            this.driver.get(composeUrl(productId));

            for (Object o : this.productJson.keySet()) {

                String key = (String) o;

                if (productDetailsList.contains(key.replaceAll(" ", "").toLowerCase())) {
                    checkProductDetails(key);
                }
                if (checkerList.contains(key.replaceAll(" ", "").toLowerCase())) {
                    checkBoxes(key);
                }
                if (key.equals("description")) {
                    checkDescription();
                }

            }
            launcher.tearDown();

        }
    }

    public ProductScreen() {

    }

    public void checkDescription(){
        String descriptionXpath = xpathsMap.get("description");
        WebElement element = this.driver.findElement(By.xpath(descriptionXpath));
        String element_content = element.findElement(By.xpath(descriptionXpath)).getText();
        String description = (String) this.productJson.get("description");

        assertTrue(element_content.contains( description));
    }

    public void checkProductDetails(String productDetail){
        String description = (String) this.productJson.get(productDetail);
        WebElement element = this.driver.findElement(By.xpath("//*[contains(., '"+description+"')]"));
        String element_content = element.getText();
        assertTrue(element_content.contains(description));

    }

    public void checkBoxes(String checkBok){

        WebElement element = this.driver.findElement(By.name(checkBok));
        String description = (String) this.productJson.get(checkBok);
        boolean selected = element.isSelected();
        if(description.equals("checked")){
            assertTrue(selected);
        }else{
            assertFalse(selected);
        }
    }

    private void loadXpathsToMap ( )  {
        String[] rawNextlineSplitted;
        String key;
        String value;

        Scanner scanner = openFile("src/main/resources/xpaths.txt");

        while (scanner.hasNextLine()) {
            rawNextlineSplitted = scanner.nextLine().split(COMMA_DELIMITER);
            key = rawNextlineSplitted[0];
            value = rawNextlineSplitted[1];
            this.xpathsMap.put(key, value);

        }
        closeFile(scanner);
    }

    private void loadProductList(){
        String line;

        Scanner scanner = openFile("src/main/resources/productDetails.txt");

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            this.productDetailsList.add(line);

        }
        closeFile(scanner);
    }

    private void loadCheckerList(){
        String line;

        Scanner scanner = openFile("src/main/resources/checkerList.txt");

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            this.checkerList.add(line);

        }
        closeFile(scanner);
    }

    private Scanner openFile(String path){
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(path));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scanner;

    }

    private void closeFile(Scanner scanner){
        try {
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String composeUrl(String product){
        String base= "https://unicat.msf.org/cat/product/";

        return base + product;
    }

}
