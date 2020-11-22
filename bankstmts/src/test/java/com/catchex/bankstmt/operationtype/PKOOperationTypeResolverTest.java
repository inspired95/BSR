package com.catchex.bankstmt.operationtype;

import com.catchex.models.OperationType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PKOOperationTypeResolverTest
{

    @ParameterizedTest
    @MethodSource( "correctOperationIncomeTransferDescriptions" )
    public void testCorrectOperationIncomeTransferDescription( String operationIncomeDescription )
    {
        //Given
        PKOOperationTypeResolver operationTypeResolver = new PKOOperationTypeResolver();

        //When
        OperationType actualOperationType =
            operationTypeResolver.resolve( operationIncomeDescription );

        //Then
        assertEquals( OperationType.INCOME_TRANSFER, actualOperationType );
    }


    private static Stream<Arguments> correctOperationIncomeTransferDescriptions()
    {
        return Stream.of(
            Arguments.of( "PRZELEW PRZYCH. SYSTEMAT. WPŁYW" ),
            Arguments.of( "PRZELEW PRZYCHODZĄCY" ) );
    }


    @ParameterizedTest
    @MethodSource( "correctOperationOutgoingTransferDescriptions" )
    public void testCorrectOperationOutgoingTransferDescription( String operationIncomeDescription )
    {
        //Given
        PKOOperationTypeResolver operationTypeResolver = new PKOOperationTypeResolver();

        //When
        OperationType actualOperationType =
            operationTypeResolver.resolve( operationIncomeDescription );

        //Then
        assertEquals( OperationType.OUTGOING_TRANSFER, actualOperationType );
    }


    private static Stream<Arguments> correctOperationOutgoingTransferDescriptions()
    {
        return Stream.of( Arguments.of( "PRZELEW WYCHODZĄCY" ) );
    }


    @ParameterizedTest
    @MethodSource( "correctOperationDebitCardPaymentDescriptions" )
    public void testCorrectOperationDebitCardPaymentDescription( String operationIncomeDescription )
    {
        //Given
        PKOOperationTypeResolver operationTypeResolver = new PKOOperationTypeResolver();

        //When
        OperationType actualOperationType =
            operationTypeResolver.resolve( operationIncomeDescription );

        //Then
        assertEquals( OperationType.DEBIT_CARD_PAYMENT, actualOperationType );
    }


    private static Stream<Arguments> correctOperationDebitCardPaymentDescriptions()
    {
        return Stream.of( Arguments.of( "ZAKUP PRZY UŻYCIU KARTY" ) );
    }


    @ParameterizedTest
    @MethodSource( "correctOperationMobileCodePaymentDescriptions" )
    public void testCorrectOperationMobileCodePaymentDescription(
        String operationIncomeDescription )
    {
        //Given
        PKOOperationTypeResolver operationTypeResolver = new PKOOperationTypeResolver();

        //When
        OperationType actualOperationType =
            operationTypeResolver.resolve( operationIncomeDescription );

        //Then
        assertEquals( OperationType.MOBILE_CODE_PAYMENT, actualOperationType );
    }


    private static Stream<Arguments> correctOperationMobileCodePaymentDescriptions()
    {
        return Stream.of( Arguments.of( "PŁATNOŚĆ WEB - KOD MOBILNY" ) );
    }


    @ParameterizedTest
    @MethodSource( "notCorrectOperationDescriptions" )
    public void testNotCorrectOperationDescription( String operationIncomeDescription )
    {
        //Given
        PKOOperationTypeResolver operationTypeResolver = new PKOOperationTypeResolver();

        //When
        OperationType actualOperationType =
            operationTypeResolver.resolve( operationIncomeDescription );

        //Then
        assertEquals( OperationType.NOT_RESOLVED, actualOperationType );
    }


    private static Stream<Arguments> notCorrectOperationDescriptions()
    {
        return Stream.of( Arguments.of( "qwertyuiop" ), Arguments.of( "zxcvbnm" ) );
    }

}
