package com.catchex.repositorycreator.client.model;

import com.catchex.models.CurrentOperation;
import com.catchex.models.Operation;
import com.catchex.models.Repository;
import com.catchex.repositorycreator.client.control.CurrentOperationsUtil;
import com.catchex.repositorycreator.client.model.repository.CurrentRepositoryHolder;

import java.beans.PropertyChangeListener;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.catchex.logging.Log.LOGGER;


public class CurrentRepositoryUtil
{
    private CurrentRepositoryHolder currentRepositoryHolder = CurrentRepositoryHolder.getInstance();


    public void addCurrentRepositoryListener(
        Optional<PropertyChangeListener> propertyChangeListener )
    {
        propertyChangeListener.ifPresentOrElse(
            listener -> currentRepositoryHolder.addListener( listener ),
            () -> LOGGER.warning( "Cannot add null as listener" ) );
    }


    public void addCurrentOperations( Optional<Set<CurrentOperation>> currentOperations )
    {
        currentOperations.ifPresentOrElse(
            this::addCurrentOperations,
            () -> LOGGER.warning( "Cannot add null set of operations" ) );
    }


    public void applyRepository( Optional<Repository> repository )
    {
        clearCurrentRepository();
        appendRepositoryToCurrentRepository( repository );
    }


    public void appendRepositoryToCurrentRepository( Optional<Repository> repository )
    {
        repository.ifPresentOrElse( repo -> {
            Set<CurrentOperation> currentOperations =
                new CurrentOperationsUtil().mapToCurrentOperations( repo.getOperations() );
            addCurrentOperations( currentOperations );
        }, () -> LOGGER.warning( "Cannot load null repository" ) );
    }


    public Repository getRepositoryFromCurrentRepository()
    {
        Set<Operation> operations = currentRepositoryHolder.get().getOperations().stream()
            .map( CurrentOperation::getOperation ).collect( Collectors.toSet() );
        return new Repository( operations );
    }


    public void recalculateCategories()
    {
        CurrentRepositoryHolder.getInstance().get().getOperations()
            .forEach( currOp -> new CurrentOperationsUtil().recalculateCategory( currOp ) );
    }


    public boolean addCurrentOperation( CurrentOperation operation )
    {
        boolean result = currentRepositoryHolder.get().getOperations().add( operation );
        if( !result )
        {
            LOGGER.info(
                "Operation cannot be added. Operation: " + operation.toString() + " already " +
                    "exists in the repository" );
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
        LOGGER.info(
            addedCount.get() + "/" + operations.size() + " operations have been added to " +
                "repository" );
        if( addedCount.get() > 0 )
        {
            currentRepositoryHolder.notifyCurrentRepositorySizeChanged();
        }

    }


    public void clearCurrentRepository()
    {
        currentRepositoryHolder.get().getOperations().clear();
        currentRepositoryHolder.notifyCurrentRepositorySizeChanged();
    }

}
