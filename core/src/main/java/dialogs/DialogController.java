package dialogs;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class DialogController
    extends Application
{
    private static final Logger logger = LoggerFactory.getLogger( DialogController.class );

    private final String dialogName;

    protected DialogView view;


    public DialogController( String dialogName )
    {
        logger.info( "Dialog {} initialization", dialogName );
        this.dialogName = dialogName;
    }


    public DialogView getDialogView()
    {
        return view;
    }


    @Override
    public void start( Stage stage ) throws Exception
    {
        logOnDialogStart();
        view.initView( stage );
        initSpecificHandlers();
        stage.setOnHiding( actionEvent -> onClose() );
        view.getStage().show();
    }


    public abstract void initSpecificHandlers();


    public String getDialogName()
    {
        return dialogName;
    }


    public abstract void onClose();


    public void closeDialog()
    {
        onClose();
    }


    protected void logOnDialogStart()
    {
        logger.info( "{} dialog starting", dialogName );
    }


    protected void logOnDialogClose()
    {
        logger.info( "{} dialog closing", dialogName );
    }
}
