package client.control.event;

import client.control.RepositoryCreatorDialogController;
import com.catchex.models.Configuration;
import javafx.scene.control.Alert;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class CategoriesConfigurationChangeListener
    implements PropertyChangeListener
{
    private RepositoryCreatorDialogController controller;


    public CategoriesConfigurationChangeListener( RepositoryCreatorDialogController controller )
    {
        this.controller = controller;
        Configuration.getInstance().addCategoriesConfigurationChangeListener( this );
    }


    @Override
    public void propertyChange( PropertyChangeEvent propertyChangeEvent )
    {
        controller.showAlert( Alert.AlertType.ERROR, "Config", "Config change", "Listener" );
        controller.handleConfigurationChange();
    }


    public void stopListen()
    {
        Configuration.getInstance().removeCategoriesConfigurationChangeListener( this );
    }
}
