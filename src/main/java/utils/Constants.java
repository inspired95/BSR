package utils;

import java.nio.file.Paths;
import java.util.List;

import static java.util.Arrays.asList;


public class Constants
{
    public static final String BSR = "BSR";
    public static final String APP_TITLE = "BSR - Bank Statement Reader";
    public static final String SELECT_BANK_TXT = "Select bank";
    public static final String SELECT_BANK_STATEMENT_TXT = "Select bank statement";

    public static final List BANK_STMT_ALLOWED_EXTENSIONS = asList( ".pdf" );

    public static final String TRANSFER_PL = "PRZELEW";
    public static final String TRANSFER_TYPE_INCOME_SHORT_PL = "PRZYCH";
    public static final String TRANSFER_TYPE_OUTGOING_PL = "WYCHODZĄCY";
    public static final String DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL = "ZAKUP PRZY UŻYCIU KARTY";
    public static final String MOBILE_CODE_PAYMENT_RESOLVER_TXT_PL = "KOD MOBILNY";
    public static final String CASH_WITHDRAWAL = "WYPŁATA W BANKOMACIE";
    public static final String COMMISSION = "PROWIZJA";
    public static final String REFUND = "ZWROT TOWARU";

    public static final String PKO = "PKO";
    public static final String PKO_BANK_STMT_BALANCE_SUMMARY_TXT = "Saldo końcowe";
    public static final String PKO_BANK_STMT_PAGE_BALANCE_SUMMARY_TXT = "Saldo do przeniesienia";
    public static final String PKO_DATE_FORMAT = "dd.MM.yyyy";

    public static final String REGEX_AMOUNT = "-?[0-9 ]+,\\d{2}";

    public static final char DOT = '.';

    public static final String CONFIGURATION_PATH =
        Paths.get( System.getProperty( "user.home" ), BSR ).toString();

    public static final String REPORT = "Report";

    public static final String HTML_EXTENSION = ".html";

    public static final String CATEGORIES_CONFIGURATION_FILE_NAME = "categoryConfiguration.json";
}
