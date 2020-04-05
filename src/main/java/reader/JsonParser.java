package reader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.CategoriesConfiguration;

import java.util.Optional;

import static app.Log.LOGGER;


public class JsonParser
{
    private static Gson gson = new Gson();


    public static Optional<CategoriesConfiguration> parseJsonToCategoryConfiguration(
        String categoryConfigurationJson )
    {
        try
        {
            return Optional
                .of( gson.fromJson( categoryConfigurationJson, CategoriesConfiguration.class ) );
        }
        catch( JsonSyntaxException | NullPointerException e )
        {
            LOGGER.warning( "Error while parsing JSON categories configuration" );
        }
        return Optional.empty();
    }

}
