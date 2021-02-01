package dialogs;

import javafx.application.Application;
import javafx.stage.Stage;

import static com.catchex.logging.Log.LOGGER;


public abstract class DialogController
    extends Application
{
    private final String DIALOG_NAME;

    protected DialogView view;

    protected Stage stage;


    public DialogController( String dialogName )
    {
        this.DIALOG_NAME = dialogName;
    }


    @Override
    public void start( Stage stage ) throws Exception
    {
        logOnDialogStart();
        this.stage = stage;
        view.initView( stage );
    }


    public String getDIALOG_NAME()
    {
        return DIALOG_NAME;
    }


    public abstract void onClose();


    public void closeDialog()
    {
        stage.close();
    }


    protected void logOnDialogStart()
    {
        LOGGER.info( DIALOG_NAME + " dialog opening" );
    }


    protected void logOnDialogClose()
    {
        LOGGER.info( DIALOG_NAME + " dialog closing" );
    }
}
