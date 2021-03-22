package dialogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class EventHandler<V>
{
    private static final Logger logger = LoggerFactory.getLogger( EventHandler.class );

    private final String eventName;

    private final DialogController controller;


    protected EventHandler( String eventName, DialogController controller )
    {
        this.eventName = eventName;
        this.controller = controller;
    }


    public void handle()
    {
        logAction();
    }


    void logAction()
    {
        logger.info( "Handling action [{}]", eventName );
    }


    protected void actionCancelled()
    {
        logger.info( "Cancelling action [{}]", eventName );
    }


    protected V getDialogController()
    {
        return (V)controller;
    }

}
