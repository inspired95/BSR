package com.catchex.reportcreator.web;

import com.catchex.models.CurrentOperation;
import com.catchex.util.Constants;

import java.util.Comparator;


public class OperationsTableComparatorFactory
{
    private OperationsTableComparatorFactory()
    {

    }


    public static Comparator<CurrentOperation> get( String operationsSortingBy )
    {
        switch( operationsSortingBy )
        {
            case Constants.AMOUNT:
                return new CurrentOperation.CurrentOperationAmountComparator();
            case Constants.TYPE:
                return new CurrentOperation.CurrentOperationTypeComparator();
            case Constants.CATEGORY:
                return new CurrentOperation.CurrentOperationCategoryComparator();
            default:
                return new CurrentOperation.CurrentOperationDateComparator();
        }
    }
}
