package com.catchex.logging;

import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;


public class Log
{

    private Log()
    {
    }


    public static Logger LOGGER;


    public static void initLogging()
    {
        LOGGER = getLogger( GLOBAL_LOGGER_NAME );
    }

}
