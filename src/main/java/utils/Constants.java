package utils;

import java.util.List;

import static java.util.Arrays.asList;


public class Constants
{
    public static String APP_TITLE = "BSR - Bank Statement Reader";
    public static String SELECT_BANK_TXT = "Select bank";
    public static String SELECT_BANK_STATEMENT_TXT = "Select bank statement";

    public static List BANK_STMT_ALLOWED_EXTENSIONS = asList( ".pdf" );

    public static String TRANSFER_PL = "PRZELEW";
    public static String TRANSFER_TYPE_INCOME_SHORT_PL = "PRZYCH";
    public static String TRANSFER_TYPE_OUTGOING_PL = "WYCHODZĄCY";
    public static String DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL = "ZAKUP PRZY UŻYCIU KARTY";
    public static String MOBILE_CODE_PAYMENT_RESOLVER_TXT_PL = "KOD MOBILNY";
    public static String CASH_WITHDRAWAL = "WYPŁATA W BANKOMACIE";
    public static String COMMISSION = "PROWIZJA";

    public static String PKO = "PKO";
    public static String PKO_BANK_STMT_BALANCE_SUMMARY_TXT = "Saldo końcowe";
    public static String PKO_BANK_STMT_PAGE_BALANCE_SUMMARY_TXT = "Saldo do przeniesienia";
    public static String PKO_DATE_FORMAT = "dd.MM.yyyy";

    public static String REGEX_AMOUNT = "-?[0-9 ]+,\\d{2}";

    public static char DOT = '.';
}
