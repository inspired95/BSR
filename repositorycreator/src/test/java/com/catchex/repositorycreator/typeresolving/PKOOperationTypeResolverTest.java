package com.catchex.repositorycreator.typeresolving;

import com.catchex.models.OperationType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PKOOperationTypeResolverTest
{

    @ParameterizedTest
    @MethodSource( "correctOperationIncomeTransferDescriptions" )
    void testCorrectOperationIncomeTransferDescription( String operationIncomeDescription )
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
    void testCorrectOperationOutgoingTransferDescription( String operationIncomeDescription )
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
    void testCorrectOperationDebitCardPaymentDescription( String operationIncomeDescription )
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
    void testCorrectOperationMobileCodePaymentDescription(
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
    void testNotCorrectOperationDescription( String operationIncomeDescription )
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
