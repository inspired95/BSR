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

    private static final String CHART_ID = "expenses-chart";


    public OperationChartGenerator(
        OperationsStatistics operationsStatistics )
    {
        this.operationsStatistics = operationsStatistics;
        String s = generateLabels();
        String datasets = generateDatasets();
    }


    public ContainerTag generateChartScript()
    {
        StringBuilder chart = new StringBuilder( "new Chart(\n" );
        chart.append( "document.getElementById(\"" + CHART_ID + "\")," ).append( "{\n" );
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

        return script( rawHtml( chart.toString()));
    }


    public ContainerTag generateCanvas()
    {
        return div( canvas( attrs( "#" + CHART_ID ) ) );
    }


    private String generateLabels()
    {
        StringBuilder labels = new StringBuilder( "\"labels\":[\n" );
        Set<Integer> monthsInts = operationsStatistics.getExpensesCategoriesPerMonth().keySet();
        for( Integer monthInt : monthsInts )
        {
            labels.append( "'" + getMonth( monthInt ) + "'," );
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

        for( Category category : getCategoriesConfiguration().getCategories() )
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
}
