package com.catchex.repositorycreator.client.model;

import com.catchex.models.CurrentOperation;
import com.catchex.models.Operation;
import com.catchex.models.Repository;
import com.catchex.repositorycreator.client.control.CurrentOperationsUtil;
import com.catchex.repositorycreator.client.model.repository.CurrentRepositoryHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class CurrentRepositoryUtil
{
    private static final Logger logger = LoggerFactory.getLogger( CurrentRepositoryUtil.class );

    private final CurrentRepositoryHolder currentRepositoryHolder =
        CurrentRepositoryHolder.getInstance();


    public void addCurrentRepositoryListener(
        Optional<PropertyChangeListener> propertyChangeListener )
    {
        propertyChangeListener.ifPresentOrElse(
            currentRepositoryHolder::addListener,
            () -> logger.warn( "Cannot add null as listener" ) );
    }

    public void applyRepository( Repository repository )
    {
        clearCurrentRepository();
        appendRepositoryToCurrentRepository( repository );
    }


    public void appendRepositoryToCurrentRepository( Repository repository )
    {

        Set<CurrentOperation> currentOperations =
            new CurrentOperationsUtil().mapToCurrentOperations( repository.getOperations() );
        synchronized( currentRepositoryHolder )
        {
            addCurrentOperations( currentOperations );
        }
    }


    public Repository getRepositoryFromCurrentRepository()
    {
        Set<Operation> operations = currentRepositoryHolder.get().getOperations().stream()
            .map( CurrentOperation::getOperation ).collect( Collectors.toSet() );
        return new Repository( operations );
    }


    public void recalculateCategories()
    {
        synchronized( currentRepositoryHolder )
        {
            currentRepositoryHolder.get().getOperations()
                .forEach( currOp -> new CurrentOperationsUtil().recalculateCategory( currOp ) );
        }
    }


    public boolean addCurrentOperation( CurrentOperation operation )
    {
        boolean result;
        synchronized( currentRepositoryHolder )
        {
            result = currentRepositoryHolder.get().getOperations().add( operation );
        }
        if( !result )
        {
            logger.info(
                "Operation cannot be added. Operation: {} already exists in the repository",
                operation );
        }
        return result;
    }


    public void addCurrentOperations( Set<CurrentOperation> operations )
    {

        AtomicInteger addedCount = new AtomicInteger();
        operations.forEach( currentOperation -> {
            boolean result = addCurrentOperation( currentOperation );
            if( result )
                addedCount.getAndIncrement();
        } );
        logger.info( "{}/{} operations have been added to repository", addedCount.get(),
            operations.size() );
        if( addedCount.get() > 0 )
        {
            currentRepositoryHolder.notifyCurrentRepositorySizeChanged();
        }

    }


    public void clearCurrentRepository()
    {
        synchronized( currentRepositoryHolder )
        {
            currentRepositoryHolder.get().getOperations().clear();
        }
        currentRepositoryHolder.notifyCurrentRepositorySizeChanged();
    }


    public Set<CurrentOperation> getCurrentOperations()
    {
        Set<CurrentOperation> operations = new HashSet<>();
        synchronized( currentRepositoryHolder )
        {
            operations.addAll( currentRepositoryHolder.get().getOperations() );
        }
        return operations;
    }

}
