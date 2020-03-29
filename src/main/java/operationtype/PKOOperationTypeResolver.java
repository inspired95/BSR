package operationtype;

import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static utils.Constants.*;


public class PKOOperationTypeResolver
    implements OperationTypeResolver
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );


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
        else if( typeOperationDesc.contains( DEBIT_CARD_PAYMENT_RESOLVER_TXT_PL ) )
            return OperationType.DEBIT_CARD_PAYMENT;
        else if( typeOperationDesc.contains( MOBILE_CODE_PAYMENT_RESOLVER_TXT_PL ) )
            return OperationType.MOBILE_CODE_PAYMENT;
        else if( typeOperationDesc.contains( CASH_WITHDRAWAL ) )
            return OperationType.CASH_WITHDRAWAL;
        else if( typeOperationDesc.contains( COMMISSION ) )
            return OperationType.COMMISSION;
        else if( typeOperationDesc.contains( REFUND ) ){
            return OperationType.REFUND;
        }
        System.out.println(
            typeOperationDesc
        );
        LOGGER.warning( "Operation type can not be resolved" );
        return OperationType.NOT_RESOLVED;
    }
}
