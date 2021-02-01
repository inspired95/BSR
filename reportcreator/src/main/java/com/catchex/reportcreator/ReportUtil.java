package com.catchex.reportcreator;

import com.catchex.models.CurrentOperation;
import com.catchex.models.OperationType;


public class ReportUtil
{
    public boolean isIncome( CurrentOperation currentOperation )
    {
        return !isNotResolved( currentOperation ) &&
            currentOperation.getOperation().getRawOperation().getAmount() > 0;
    }


    public boolean isExpense( CurrentOperation currentOperation )
    {
        return !isIncome( currentOperation ) && !isNotResolved( currentOperation );
    }


    public boolean isNotResolved( CurrentOperation currentOperation )
    {
        return currentOperation.getOperation().getType().equals( OperationType.NOT_RESOLVED );
    }
}
