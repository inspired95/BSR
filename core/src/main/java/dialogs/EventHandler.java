package dialogs;

import static com.catchex.logging.Log.LOGGER;


public abstract class EventHandler<T>
{
    private final String eventName;


    public EventHandler( String eventName )
    {
        this.eventName = eventName;
    }


    public void handle( T event )
    {
        actionHandle();
    }


    ;


    void actionHandle()
    {
        LOGGER.info( "Action " + eventName + " handling" );
    }


    protected void actionCancelled()
    {
        LOGGER.info( "Action " + eventName + " has been cancelled" );
    }

}
