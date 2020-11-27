package client;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.catchex.util.Constants.PKO;


public class Constants
{
    public static final DateTimeFormatter intervalTreeItemFormatter =
        DateTimeFormatter.ofPattern( "MMMM yyyy", Locale.US );

    public static final String[] supportedBanks = { PKO };
}
