package com.catchex.bankstmt.categories;

import com.catchex.models.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OperationCategoryResolverTest
{
    @Test
    public void should_Return_Other_Category_When_Null_As_Desc_Given()
    {
        //GIVEN
        OperationCategoryResolver operationCategoryResolver =
            new OperationCategoryResolverImpl( null );

        //WHEN
        Category resolvedCategory = operationCategoryResolver.resolve( null );

        //THEN
        assertEquals( Category.OTHER_CATEGORY, resolvedCategory );
    }


    @Test
    public void should_Return_Other_Category_When_Empty_String_As_Desc_Given()
    {
        //GIVEN
        OperationCategoryResolver operationCategoryResolver =
            new OperationCategoryResolverImpl( null );

        //WHEN
        Category resolvedCategory = operationCategoryResolver.resolve( "" );

        //THEN
        assertEquals( Category.OTHER_CATEGORY, resolvedCategory );
    }


    @Test
    public void should_Return_Other_Category_When_Cannot_Match_Given_Desc_To_Any_Keyword()
    {
        //GIVEN
        OperationCategoryResolver operationCategoryResolver =
            new OperationCategoryResolverImpl( createCategoriesMock() );

        //WHEN
        Category resolvedCategory = operationCategoryResolver.resolve( "NOT_MATCHING_KEYWORD" );

        //THEN
        assertEquals( Category.OTHER_CATEGORY, resolvedCategory );
    }


    @Test
    public void should_Return_Category_When_Can_Match_Given_Desc_To_Some_Keyword()
    {
        //GIVEN
        OperationCategoryResolver operationCategoryResolver =
            new OperationCategoryResolverImpl( createCategoriesMock() );

        //WHEN
        Category resolvedCategory = operationCategoryResolver.resolve( "qwert" );

        //THEN
        assertEquals( createCategoryMock().getCategoryName(), resolvedCategory.getCategoryName() );
    }


    private Category[] createCategoriesMock()
    {
        return new Category[] { createCategoryMock() };
    }


    private Category createCategoryMock()
    {
        return new Category( "MOCK_CATEGORY", new String[] { "qwert", "asdfg", "zxcvb" } );
    }
}
