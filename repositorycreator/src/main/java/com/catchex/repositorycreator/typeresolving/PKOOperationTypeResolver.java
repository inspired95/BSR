package com.catchex.repositorycreator.typeresolving;

import com.catchex.models.OperationType;

import static com.catchex.logging.Log.LOGGER;
import static com.catchex.util.Constants.*;


public class PKOOperationTypeResolver
    implements OperationTypeResolver
{
    @Override
    public OperationType resolve( String typeOperationDesc )
    {
        if( typeOperationDesc.startsWith( TRANSFER_PL ) )
        {
            if( typeOperationDesc.contains( TRANSFER_TYPE_INCOME_SHORT_PL ) )
                return OperationType.INCOME_TRANSFER;
            else if( typeOperationDesc.contains( TRANSFER_TYPE_OUTGOING_PL ) )
                return OperationType.OUTGOING_TRANSFER;
        }
        else if( typeOperationDesc.contains( TRANSFER_TYPE_INCOME_PL ) )
            return OperationType.INCOME_TRANSFER;
        else if( typeOperationDesc.contains( STANDING_ORDER ) )
            return OperationType.STANDING_ORDER;
        else if( typeOperationDesc.contains( DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL ) )
            return OperationType.DEBIT_CARD_PAYMENT;
        else if( typeOperationDesc.contains( MOBILE_CODE_PAYMENT_RESOLVER_TXT_PL ) )
            return OperationType.MOBILE_CODE_PAYMENT;
        else if( typeOperationDesc.contains( CASH_WITHDRAWAL ) )
            return OperationType.CASH_WITHDRAWAL;
        else if( typeOperationDesc.contains( COMMISSION ) )
            return OperationType.COMMISSION;
        else if( typeOperationDesc.contains( REFUND ) )
        {
            return OperationType.REFUND;
        }
        LOGGER.warning( "Operation type can not be resolved: " + typeOperationDesc );
        return OperationType.NOT_RESOLVED;
    }
}
