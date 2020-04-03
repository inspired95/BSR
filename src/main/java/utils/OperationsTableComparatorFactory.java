package utils;

import model.Operation;

import java.util.Comparator;

import static utils.Constants.*;


public class OperationsTableComparatorFactory
{
    public static Comparator<Operation> get( String operationsSortingBy ){
        if( operationsSortingBy.equals( DATE ) ) return new Operation.OperationDateComparator();
        else if( operationsSortingBy.equals( AMOUNT ) ) return new Operation.OperationAmountComparator();
        else if( operationsSortingBy.equals( TYPE ) ) return new Operation.OperationTypeComparator();
        else if( operationsSortingBy.equals( CATEGORY ) ) return new Operation.OperationCategoryComparator();
        return new Operation.OperationDateComparator();
    }
}
