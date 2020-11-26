package client;

import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class Constants
{
    public static final DateTimeFormatter intervalTreeItemFormatter =
        DateTimeFormatter.ofPattern( "MMMM yyyy", Locale.US );
}
