package reader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.CategoriesConfiguration;

import java.util.Optional;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;


public class JsonParser
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private static Gson gson = new Gson();


    public static Optional<CategoriesConfiguration> parseJsonToCategoryConfiguration(
        String categoryConfigurationJson )
    {
        try
        {
            return Optional.of( gson.fromJson( categoryConfigurationJson,
                CategoriesConfiguration.class ) );
        }
        catch( JsonSyntaxException e )
        {
            LOGGER.warning(
                "Error while parsing JSON categories configuration: " + e.getCause().toString() );
        }
        return Optional.empty();
    }

}
