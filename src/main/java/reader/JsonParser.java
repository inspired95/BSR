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
        Optional<String> categoryConfigurationJson )
    {
        if( categoryConfigurationJson.isPresent() )
        {
            try
            {
                return Optional.of( gson
                    .fromJson( categoryConfigurationJson.get(), CategoriesConfiguration.class ));
            }
            catch( JsonSyntaxException e )
            {
                LOGGER.warning(
                    "Error while reading categories configuration: " + e.getCause().toString() );
            }
        }
        return Optional.empty();
    }

}
