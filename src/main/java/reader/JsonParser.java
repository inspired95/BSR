package reader;

import com.google.gson.Gson;
import model.CategoryConfiguration;


public class JsonParser
{
    private Gson gson;

    public JsonParser()
    {
        gson = new Gson();
    }


    public CategoryConfiguration parseJsonToCategoryConfiguration(
        String categoryConfigurationJson )
    {
        return gson.fromJson( categoryConfigurationJson, CategoryConfiguration.class );
    }

}
