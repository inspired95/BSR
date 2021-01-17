package com.catchex.io.reader;

import com.catchex.util.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileReader
{
    public static String readCategoriesConfigJson() throws IOException
    {
        return new String( Files.readAllBytes( Paths
            .get( Constants.CONFIGURATION_PATH, Constants.CATEGORIES_CONFIGURATION_FILE_NAME ) ) );

    }
}
