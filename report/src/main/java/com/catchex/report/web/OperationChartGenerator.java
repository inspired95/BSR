package com.catchex.report.web;

import com.catchex.models.Category;
import com.catchex.report.statictics.OperationsStatistics;
import j2html.tags.ContainerTag;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

import static com.catchex.configuration.Configuration.getCategoriesConfiguration;
import static j2html.TagCreator.*;


public class OperationChartGenerator
{
    private OperationsStatistics operationsStatistics;


    public OperationChartGenerator(
        OperationsStatistics operationsStatistics )
    {
        this.operationsStatistics = operationsStatistics;
    }


    public ContainerTag generateCategoriesExpensesPerMonthChartScript( String chartId )
    {
        StringBuilder chart = new StringBuilder( "new Chart(\n" );
        chart.append( "document.getElementById(\"" + chartId + "\")," ).append( "{\n" );
        chart.append( "type: 'line'" ).append( ",\n" );
        chart.append( "data: {\n" );
        chart.append( generateLabels() ).append( ",\n" );
        chart.append( generateDatasets() ).append( "},\n" );
        chart.append( "options: {\n" );
        chart.append( "title: {" ).append( "display: true,\n" )
            .append( "text: 'Categories expenses per month'\n" ).append( "}\n" );
        chart.append( "}\n" );
        chart.append( "}\n" );
        chart.append( ");" );

        return script( rawHtml( chart.toString() ) );
    }


    public ContainerTag generateExpensesSummaryChartScript( String chartId )
    {
        StringBuilder chart = new StringBuilder( "new Chart(\n" );
        chart.append( "document.getElementById(\"" + chartId + "\")," ).append( "{\n" );
        chart.append( "type: 'pie'" ).append( ",\n" );
        chart.append( "data: {\n" );
        chart.append( "label: \"Expenses\"," );
        chart.append( generateExpensesSummaryLabels() ).append( ",\n" );
        chart.append( generateExpensesSummaryDatasets() ).append( "},\n" );
        chart.append( "options: {\n" );
        chart.append( "title: {" ).append( "display: true,\n" )
            .append( "text: 'Expenses summary'\n" ).append( "}\n" );
        chart.append( "}\n" );
        chart.append( "}\n" );
        chart.append( ");" );

        return script( rawHtml( chart.toString() ) );
    }


    public ContainerTag generateCanvas( String chartId, String cssClass )
    {
        return div( attrs( "." + cssClass ), canvas( attrs( "#" + chartId ) ) );
    }


    private String generateLabels()
    {
        StringBuilder labels = new StringBuilder( "\"labels\":[\n" );
        Set<String> monthsInts = operationsStatistics.getExpensesCategoriesPerMonth().keySet();
        for( String monthInt : monthsInts )
        {
            labels.append( "'" + monthInt + "'," );
        }
        labels.deleteCharAt( labels.length() - 1 );
        labels.append( "]\n" );
        return labels.toString();
    }


    private String generateExpensesSummaryLabels()
    {
        StringBuilder labels = new StringBuilder( "\"labels\":[\n" );
        for( Category category : computeCategories() )
        {
            labels.append( "'" + category.getCategoryName() + "'," );
        }
        labels.deleteCharAt( labels.length() - 1 );
        labels.append( "]\n" );
        return labels.toString();
    }


    private String getMonth( int month )
    {
        return Month.of( month ).getDisplayName( TextStyle.FULL_STANDALONE, Locale.ENGLISH );
    }


    private String generateDatasets()
    {
        StringBuilder datasets = new StringBuilder( "datasets: [\n" );

        for( Category category : computeCategories() )
        {
            datasets.append( generateCategoryDataset( category,
                operationsStatistics.getExpensesCategoriesPerMonth().values() ) );
        }
        datasets.deleteCharAt( datasets.length() - 1 );
        datasets.append( "]\n" );
        return datasets.toString();
    }


    private String generateCategoryDataset(
        Category category, Collection<Map<String, Double>> monthExpenses )
    {
        String color = randomColor();
        StringBuilder categoryDataset = new StringBuilder( "{label:\n" );
        categoryDataset.append( "'" ).append( category.getCategoryName() ).append( "'\n" )
            .append( "," ).append( "borderColor:'" + color + "',\n" )
            .append( "backgroundColor: '" + color + "',\n" ).append( "fill: false,\n" )
            .append( "data:[\n" );
        for( Map<String, Double> monthExpense : monthExpenses )
        {
            Double amount = monthExpense.get( category.getCategoryName() );
            categoryDataset.append( amount == null ? 0 : Math.abs( amount ) ).append( "," + "\n" );
        }
        categoryDataset.deleteCharAt( categoryDataset.length() - 1 );
        categoryDataset.append( "]},\n" );
        return categoryDataset.toString();
    }


    private String generateExpensesSummaryDatasets()
    {
        StringBuilder datasets = new StringBuilder( "datasets: [{\n" );
        datasets.append( "backgroundColor: [" );
        for( Category computeCategory : computeCategories() )
        {
            datasets.append( "'" + randomColor() ).append( "'," );
        }
        datasets.deleteCharAt( datasets.length() - 1 );
        datasets.append( "]," );

        datasets.append( "data: [" );
        for( Category category : computeCategories() )
        {
            Double amount = operationsStatistics.getExpenses().get( category.getCategoryName() );
            datasets.append( amount == null ? 0 : Math.abs( amount ) ).append( "," );
        }
        datasets.deleteCharAt( datasets.length() - 1 );
        datasets.append( "]" );
        datasets.append( "}]\n" );
        return datasets.toString();
    }


    private String randomColor()
    {
        Random rand = new Random();
        StringBuilder color = new StringBuilder( "rgba(" );
        color.append( rand.nextInt( 255 ) + 1 ).append( "," );
        color.append( rand.nextInt( 255 ) + 1 ).append( "," );
        color.append( rand.nextInt( 255 ) + 1 ).append( "," );
        color.append( 1 );
        color.append( ")" );
        return color.toString();
    }


    private List<Category> computeCategories()
    {
        List<Category> categories =
            new ArrayList( Arrays.asList( getCategoriesConfiguration().getCategories() ) );
        categories.add( Category.OTHER_CATEGORY );
        categories.add( Category.CASH_WITHDRAWAL );
        return categories;
    }
}
