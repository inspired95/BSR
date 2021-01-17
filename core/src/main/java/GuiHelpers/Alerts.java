package GuiHelpers;

import com.catchex.models.Category;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import static com.catchex.util.Constants.APP_CONFIGURATION_EDITOR_TITLE;
import static com.catchex.util.Constants.APP_TITLE;


public class Alerts
{
    public static void showAlert(
        Alert.AlertType type, String title, String header, String content )
    {
        Alert alert = new Alert( type );
        alert.setTitle( title );
        alert.setHeaderText( header );
        alert.setContentText( content );

        alert.showAndWait();
    }


    public static Optional<String> showChoiceFromListDialog(
        String dialogHeader, String dialogMessage, SortedSet<Category> categories )
    {
        List<String> choices = new ArrayList<>();
        for( Category category : categories )
        {
            choices.add( category.getCategoryName() );
        }

        return showChoiceFromListDialog( dialogHeader, dialogMessage, choices );
    }


    public static Optional<String> showChoiceFromListDialog(
        String dialogHeader, String dialogMessage, List<String> choices )
    {
        ChoiceDialog<String> dialog = new ChoiceDialog<>( choices.get( 0 ), choices );
        dialog.setTitle( APP_CONFIGURATION_EDITOR_TITLE );
        dialog.setHeaderText( dialogHeader );
        dialog.setContentText( dialogMessage );

        return dialog.showAndWait();
    }


    public static Optional<String> showAskForStringDialog(
        String dialogHeader, String dialogMessage, String defaultValue )
    {
        TextInputDialog dialog = new TextInputDialog( defaultValue );
        dialog.setTitle( APP_TITLE );
        dialog.setHeaderText( dialogHeader );
        dialog.setContentText( dialogMessage );

        return dialog.showAndWait();
    }


    public static List<File> showOpenMultipleDialog( Stage stage, String title )
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle( title );

        return fileChooser.showOpenMultipleDialog( stage );
    }


    public static void showWaringDialog( String dialogHeader, String dialogMessage )
    {
        Alert alert = new Alert( Alert.AlertType.WARNING );
        alert.setTitle( APP_CONFIGURATION_EDITOR_TITLE );
        alert.setHeaderText( dialogHeader );
        alert.setContentText( dialogMessage );

        alert.showAndWait();
    }


    public static Optional<ButtonType> showConfirmationDialog(
        String dialogHeader, String dialogMessage )
    {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
        alert.setTitle( APP_CONFIGURATION_EDITOR_TITLE );
        alert.setHeaderText( dialogHeader );
        alert.setContentText( dialogMessage );

        return alert.showAndWait();
    }
}
