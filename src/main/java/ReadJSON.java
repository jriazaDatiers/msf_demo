import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class ReadJSON {

    public HashMap<String, JSONObject> loadProductsJson()
    {
        HashMap<String, JSONObject> jsonMap = new HashMap<>();
        JSONParser jsonParser = new JSONParser();
        String filename = "src/main/resources/products2.json";

        try (FileReader reader = new FileReader( filename))
        {
            Object obj = jsonParser.parse(reader);

            JSONArray productsList = (JSONArray) obj;

            productsList.forEach( emp-> {JSONObject productObject = (JSONObject) emp;
            String productId = (String) productObject.get("productId");
                jsonMap.put((productId), (JSONObject) emp);
            });


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonMap;
    }


}
