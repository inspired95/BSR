package dialogs;

import javafx.stage.Stage;


public abstract class DialogView
{
    private Stage stage;


    public void initView( Stage stage )
    {
        this.stage = stage;
    }


    public abstract void refreshView();


    public Stage getStage()
    {
        return stage;
    }

}
