package com.catchex.repositorycreator.categoryresolving;

import com.catchex.models.Category;
import com.catchex.models.Keyword;
import org.junit.jupiter.api.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OperationCategoryResolverTest
{
    @Test
    public void should_Return_Other_Category_When_Null_As_Desc_Given()
    {
        //GIVEN
        OperationCategoryResolver operationCategoryResolver = new OperationCategoryResolverImpl();

        //WHEN
        Category resolvedCategory = operationCategoryResolver.resolve( null );

        //THEN
        assertEquals( Category.OTHER_CATEGORY, resolvedCategory );
    }


    @Test
    public void should_Return_Other_Category_When_Empty_String_As_Desc_Given()
    {
        //GIVEN
        OperationCategoryResolver operationCategoryResolver = new OperationCategoryResolverImpl();

        //WHEN
        Category resolvedCategory = operationCategoryResolver.resolve( "" );

        //THEN
        assertEquals( Category.OTHER_CATEGORY, resolvedCategory );
    }


    @Test
    public void should_Return_Other_Category_When_Cannot_Match_Given_Desc_To_Any_Keyword()
    {
        //GIVEN
        OperationCategoryResolver operationCategoryResolver = new OperationCategoryResolverImpl();

        //WHEN
        Category resolvedCategory = operationCategoryResolver.resolve( "NOT_MATCHING_KEYWORD" );

        //THEN
        assertEquals( Category.OTHER_CATEGORY, resolvedCategory );
    }


    @Test
    public void should_Return_Category_When_Can_Match_Given_Desc_To_Some_Keyword()
    {
        //GIVEN
        OperationCategoryResolver operationCategoryResolver = new OperationCategoryResolverImpl();

        //WHEN
        Category resolvedCategory = operationCategoryResolver.resolve( "qwert" );

        //THEN
        assertEquals( createCategoryMock().getCategoryName(), resolvedCategory.getCategoryName() );
    }


    private SortedSet<Category> createCategoriesMock()
    {
        SortedSet<Category> categories = new TreeSet<>();
        categories.add( createCategoryMock() );
        return categories;
    }


    private Category createCategoryMock()
    {
        SortedSet<Keyword> keywords = new TreeSet<>();
        keywords.add( new Keyword( "qwert" ) );
        keywords.add( new Keyword( "asdfg" ) );
        keywords.add( new Keyword( "zxcvb" ) );
        return new Category( "MOCK_CATEGORY", keywords );
    }
}
