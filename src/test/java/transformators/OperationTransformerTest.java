package transformators;

import categories.OperationCategoryResolver;
import model.Category;
import model.Operation;
import model.RawOperation;
import operationtype.OperationType;
import operationtype.OperationTypeResolver;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.util.List.of;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class OperationTransformerTest
{
    private OperationTransformer operationTransformer = new OperationTransformer( createOperationTypeResolverMock(),
        createOperationCategoryResolverMock() );

    @Test
    public void should_Return_Empty_List_When_Null_As_List_Given(){
        //WHEN
        List<Operation> operations =
            operationTransformer.transform( null );

        //THEN
        assertEquals( operations, of() );
    }

    @Test
    public void should_Return_List_Contains_Transformed_Raw_Operation_Into_Operation(){
        //GIVEN
        List<RawOperation> rawOperations = of( createRawOperationMock( "ID_MOCK" ) );

        //WHEN
        List<Operation> operations =
            operationTransformer.transform( rawOperations );

        //THEN
        assertEquals( 1, operations.size() );
        Operation actualOperation = operations.get( 0 );
        assertEquals( actualOperation.getID(),"ID_MOCK");
        assertEquals( actualOperation.getAmount(),Double.NaN);
        assertEquals( actualOperation.getDate(), LocalDate.MAX );
        assertEquals( actualOperation.getType(), OperationType.NOT_RESOLVED );
        assertEquals( actualOperation.getCategory().getCategoryName(), "CATEGORY_MOCK"  );
    }




    private OperationCategoryResolver createOperationCategoryResolverMock(){
        OperationCategoryResolver operationCategoryResolverMock =
            mock( OperationCategoryResolver.class );
        expect( operationCategoryResolverMock.resolve( anyString() ) ).andReturn( createCategoryMock() );
        replay( operationCategoryResolverMock );
        return operationCategoryResolverMock;
    }

    private OperationTypeResolver createOperationTypeResolverMock(){
        OperationTypeResolver operationTypeResolverMock = mock( OperationTypeResolver.class );
        expect( operationTypeResolverMock.resolve( anyString() ) ).andReturn( OperationType.NOT_RESOLVED );
        replay( operationTypeResolverMock );
        return operationTypeResolverMock;
    }

    private Category createCategoryMock(){
        Category category = mock( Category.class );
        expect( category.getCategoryName() ).andReturn( "CATEGORY_MOCK" ).anyTimes();
        expect( category.getKeywords() ).andReturn( new String[]{} );
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
    }
}
