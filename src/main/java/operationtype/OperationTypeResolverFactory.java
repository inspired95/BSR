package operationtype;

import exceptions.OperationTypeResolverNotFoundException;

import static utils.Constants.PKO;


public class OperationTypeResolverFactory
{
    public OperationTypeResolver match( String chosenBank )
    {
        if( PKO.equals( chosenBank ) )
        {
            return new PKOOperationTypeResolver();
        }
        throw new OperationTypeResolverNotFoundException();
    }
}
