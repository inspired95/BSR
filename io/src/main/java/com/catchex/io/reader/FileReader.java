package com.catchex.io.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.catchex.util.Constants.CATEGORIES_CONFIGURATION_FILE_NAME;
import static com.catchex.util.Constants.CONFIGURATION_PATH;


public class FileReader
{
    public static String readCategoriesConfigJson() throws IOException
    {
        return new String( Files
            .readAllBytes( Paths.get( CONFIGURATION_PATH, CATEGORIES_CONFIGURATION_FILE_NAME ) ) );

    }
}
