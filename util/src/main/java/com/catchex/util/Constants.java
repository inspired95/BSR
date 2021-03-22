package com.catchex.util;

import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;


public class Constants
{
    public static final String PDF_EXTENSION_LABEL = "Bank statements";

    public static final String BSR = "BSR";
    public static final String APP_TITLE = "Bank Statement Reader";
    public static final String APP_CONFIGURATION_EDITOR_TITLE =
        APP_TITLE + " - Configuration " + "Editor";
    public static final String SELECT_BANK_TXT = "Select bank";
    public static final String SELECT_BANK_STATEMENT_TXT = "Select bank statement";
    public static final String PDF_EXTENSION = "*.pdf";
    public static final String[] BANK_STATEMENT_EXTENSIONS = { PDF_EXTENSION };
    public static final List<String> BANK_STMT_ALLOWED_EXTENSIONS = asList( ".pdf" );


    private Constants()
    {
    }


    public static final String TRANSFER_PL = "PRZELEW";
    public static final String TRANSFER_TYPE_INCOME_SHORT_PL = "PRZYCH";
    public static final String TRANSFER_TYPE_INCOME_PL = "WPŁATA";
    public static final String TRANSFER_TYPE_OUTGOING_PL = "WYCHODZĄCY";
    public static final String STANDING_ORDER = "REALIZACJA ZLECENIA STAŁEGO";
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
    public static final String REGEX_AMOUNT2 = "-?[0-9 ]+\\,{1}\\d{2}";

    public static final char DOT = '.';

    public static final String CONFIGURATION_PATH =
        Paths.get( System.getProperty( "user.home" ), BSR ).toString();

    public static final String BSR_REPOSITORY_EXTENSION_FILE_CHOOSER = "*.bsrrepository";

    public static final String BSR_CONFIGURATION_EXTENSION = ".bsrconfig";

    public static final String BSR_CONFIGURATION_EXTENSION_FILE_CHOOSER =
        "*" + BSR_CONFIGURATION_EXTENSION;

    public static final String DEFAULT_CONFIGURATION_FILE_NAME =
        "Configuration" + BSR_CONFIGURATION_EXTENSION;

    public static final String REPORT = "Report";

    public static final String HTML_EXTENSION = ".html";

    public static final String CATEGORIES_CONFIGURATION_FILE_NAME =
        "defaultCategoryConfiguration.json";

    public static final String CSS_STYLE_FILE_NAME = "style.css";

    public static final String DATE = "Date";

    public static final String DESCRIPTION = "Description";

    public static final String FILE_NAME = "File name";

    public static final String AMOUNT = "Amount";

    public static final String ID = "ID";

    public static final String INTERVAL = "Interval";

    public static final String BANK = "Bank";

    public static final String TYPE = "Type";

    public static final String CATEGORY = "Category";

    public static final String SOURCE = "Source";

    public static final String SOURCES = "Sources";

    public static final String OPERATIONS_LIST_SORTING_BY = "Sort by:";

    public static final String GENERATE_REPORT = "Generate report";

    public static final String OPERATIONS = "Operations";

    public static final String NOT_APPLICABLE = "N/A";

    public static final String PROFIT = "Profit";

    public static final String LOSS = "Loss";

    public static final DateTimeFormatter intervalTreeItemFormatter =
        DateTimeFormatter.ofPattern( "MMMM yyyy", Locale.US );

    public static final String[] supportedBanks = { PKO };
}
