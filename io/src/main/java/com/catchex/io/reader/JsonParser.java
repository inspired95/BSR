package com.catchex.io.reader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.catchex.models.CategoriesConfiguration;

import java.util.Optional;

import static com.catchex.util.Log.*;


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
            LOGGER.warning( "Error while parsing JSON com.catchex.bankstmt.categories configuration" );
        }
        return Optional.empty();
    }

}
