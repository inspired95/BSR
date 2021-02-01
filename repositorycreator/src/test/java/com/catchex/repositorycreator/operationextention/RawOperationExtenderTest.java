package com.catchex.repositorycreator.operationextention;

public class RawOperationExtenderTest
{
    /*private RawOperationExtender rawOperationExtender =
        new RawOperationExtender( createOperationTypeResolverMock(),
            createOperationCategoryResolverMock() );


    @Test
    public void should_Return_List_Contains_Transformed_Raw_Operation_Into_Operation()
    {
        //GIVEN
        List<RawOperation> rawOperations = of( createRawOperationMock( "ID_MOCK" ) );

        //WHEN
        Set<Operation> operations = rawOperationExtender.extend( rawOperations );

        //THEN
        assertEquals( 1, operations.size() );
        Iterator<Operation> iterator = operations.iterator();
        assertTrue( iterator.hasNext() );
        Operation actualOperation = iterator.next();
        assertEquals( actualOperation.getRawOperation().getID(), "ID_MOCK" );
        assertEquals( actualOperation.getRawOperation().getAmount(), Double.NaN );
        assertEquals( actualOperation.getRawOperation().getDate(), LocalDate.MAX );
        assertEquals( actualOperation.getType(), OperationType.NOT_RESOLVED );
        assertEquals( actualOperation.getCategory().getCategoryName(), "CATEGORY_MOCK" );
    }


    private OperationCategoryResolver createOperationCategoryResolverMock()
    {
        OperationCategoryResolver operationCategoryResolverMock =
            mock( OperationCategoryResolver.class );
        expect( operationCategoryResolverMock.resolve( anyString() ) )
            .andReturn( createCategoryMock() );
        replay( operationCategoryResolverMock );
        return operationCategoryResolverMock;
    }


    private OperationTypeResolver createOperationTypeResolverMock()
    {
        OperationTypeResolver operationTypeResolverMock = mock( OperationTypeResolver.class );
        expect( operationTypeResolverMock.resolve( anyString() ) )
            .andReturn( OperationType.NOT_RESOLVED );
        replay( operationTypeResolverMock );
        return operationTypeResolverMock;
    }


    private Category createCategoryMock()
    {
        Category category = mock( Category.class );
        expect( category.getCategoryName() ).andReturn( "CATEGORY_MOCK" ).anyTimes();
        expect( category.getKeywords() ).andReturn( new TreeSet<>() );
        replay( category );
        return category;
    }


    private RawOperation createRawOperationMock( String ID )
    {
        RawOperation rawOperation = mock( RawOperation.class );
        expect( rawOperation.getID() ).andReturn( ID );
        expect( rawOperation.getDesc() ).andReturn( "DESC_MOCK" );
        expect( rawOperation.getType() ).andReturn( "TYPE_MOCK" );
        expect( rawOperation.getDate() ).andReturn( LocalDate.MAX );
        expect( rawOperation.getAmount() ).andReturn( Double.NaN );
        replay( rawOperation );
        return rawOperation;
    }*/
}