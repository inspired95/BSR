package com.catchex.util;

import com.catchex.models.Operation;

import java.util.Comparator;

public class OperationsTableComparatorFactory
{
    public static Comparator<Operation> get( String operationsSortingBy ){
        if( operationsSortingBy.equals( Constants.DATE ) ) return new Operation.OperationDateComparator();
        else if( operationsSortingBy.equals( Constants.AMOUNT ) ) return new Operation.OperationAmountComparator();
        else if( operationsSortingBy.equals( Constants.TYPE ) ) return new Operation.OperationTypeComparator();
        else if( operationsSortingBy.equals( Constants.CATEGORY ) ) return new Operation.OperationCategoryComparator();
        return new Operation.OperationDateComparator();
    }
}
