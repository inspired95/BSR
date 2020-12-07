package com.catchex.util;

import com.catchex.models.Operation;

import java.util.Comparator;


public class OperationsTableComparatorFactory
{
    public static Comparator<Operation> get( String operationsSortingBy )
    {
        switch( operationsSortingBy )
        {
            case Constants.AMOUNT:
                return new Operation.OperationAmountComparator();
            case Constants.TYPE:
                return new Operation.OperationTypeComparator();
            case Constants.CATEGORY:
                return new Operation.OperationCategoryComparator();
            default:
                return new Operation.OperationDateComparator();
        }
    }
}
