package com.catchex.repositorycreator.categoryresolving;

import com.catchex.models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import static com.catchex.logging.Log.initLogging;
import static org.junit.jupiter.api.Assertions.assertEquals;


class OperationCategoryResolverTest
{
    private final String categoryName = "CAR";
    private final String categoryKeyword = "fuel";


    @BeforeAll
    public static void setUpBeforeAll()
    {
        initLogging();
    }


    private static Stream<Arguments> provideDescriptionsTypesAndExpectedResults()
    {
        return Stream.of(
            Arguments.of( "", null, Category.OTHER_CATEGORY ),
            Arguments.of( null, OperationType.COMMISSION, Category.OTHER_CATEGORY ),
            Arguments.of( "", OperationType.COMMISSION, Category.OTHER_CATEGORY ),
            Arguments.of( "", OperationType.CASH_WITHDRAWAL, Category.CASH_WITHDRAWAL ),
            Arguments.of( "NOT_MATCHING", OperationType.COMMISSION, Category.OTHER_CATEGORY ) );
    }


    private void setupConfiguration()
    {
        SortedSet<Keyword> keywords = new TreeSet<>();
        keywords.add( new Keyword( categoryKeyword ) );

        Category carCategory = new Category( categoryName, keywords );

        SortedSet<Category> categories = new TreeSet<>();
        categories.add( carCategory );

        CategoriesConfiguration categoriesConfiguration = new CategoriesConfiguration( categories );

        Configuration.getInstance().setCategoriesConfiguration( categoriesConfiguration );
    }


    @ParameterizedTest
    @MethodSource( "provideDescriptionsTypesAndExpectedResults" )
    void should_Return_Other_Category_When_Null_As_Type_Given(
        String description, OperationType type, Category expectedCategory )
    {
        //WHEN
        Category resolvedCategory = OperationCategoryResolver.resolve( description, type );

        //THEN
        assertEquals( expectedCategory, resolvedCategory );
    }


    @Test
    void should_Return_Proper_Category_When_Can_Match_Given_Desc_To_Some_Keyword()
    {
        //GIVEN
        setupConfiguration();

        //WHEN
        Category resolvedCategory = OperationCategoryResolver.resolve( categoryKeyword );

        //THEN
        assertEquals( categoryName, resolvedCategory.getCategoryName() );
    }
}
