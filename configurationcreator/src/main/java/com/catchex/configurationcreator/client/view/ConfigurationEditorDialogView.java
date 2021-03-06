package com.catchex.configurationcreator.client.view;

import com.catchex.configurationcreator.client.control.ConfigurationEditorDialogController;
import com.catchex.configurationcreator.client.control.event.DeleteKeywordEventHandler;
import com.catchex.configurationcreator.client.control.event.RenameCategoryEventHandler;
import com.catchex.configurationcreator.client.control.event.RenameKeywordEventHandler;
import com.catchex.models.Category;
import com.catchex.models.Keyword;
import dialogs.DialogView;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.catchex.util.Constants.APP_CONFIGURATION_EDITOR_TITLE;


public class ConfigurationEditorDialogView
    extends DialogView
{
    private static final Logger logger =
        LoggerFactory.getLogger( ConfigurationEditorDialogView.class );

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 400;
    private final ConfigurationEditorDialogController controller;

    private VBox categoriesContainer;
    private FlowPane keywordsContainer = new FlowPane();

    private ActiveCategoryButton activeCategory = null;

    private MenuItem importConfigurationMenuItem;
    private MenuItem exportConfigurationMenuItem;
    private MenuItem loadDefaultConfigurationMenuItem;
    private MenuItem setCurrentConfigurationAsDefaultMenuItem;
    private MenuItem exitMenuItem;
    private MenuItem addNewCategoryMenuItem;
    private MenuItem removeCategoryMenuItem;
    private MenuItem addNewKeywordMenuItem;


    public ConfigurationEditorDialogView( ConfigurationEditorDialogController controller )
    {
        this.controller = controller;
    }


    @Override
    public void initView( Stage stage )
    {
        super.initView( stage );
        VBox windowContainer = new VBox();
        windowContainer.getChildren().add( getMenuBar() );
        windowContainer.getChildren().add( createMainContainer() );

        Scene scene = new Scene( windowContainer, WINDOW_WIDTH, WINDOW_HEIGHT );
        stage.setScene( scene );
        stage.setTitle( APP_CONFIGURATION_EDITOR_TITLE );
    }


    public MenuItem getImportConfigurationMenuItem()
    {
        return importConfigurationMenuItem;
    }


    public MenuItem getExportConfigurationMenuItem()
    {
        return exportConfigurationMenuItem;
    }


    public MenuItem getLoadDefaultConfigurationMenuItem()
    {
        return loadDefaultConfigurationMenuItem;
    }


    public MenuItem getSetCurrentConfigurationAsDefaultMenuItem()
    {
        return setCurrentConfigurationAsDefaultMenuItem;
    }


    public MenuItem getExitMenuItem()
    {
        return exitMenuItem;
    }


    public MenuItem getAddNewCategoryMenuItem()
    {
        return addNewCategoryMenuItem;
    }


    public MenuItem getRemoveCategoryMenuItem()
    {
        return removeCategoryMenuItem;
    }


    public MenuItem getAddNewKeywordMenuItem()
    {
        return addNewKeywordMenuItem;
    }


    public void refreshView()
    {
        if( activeCategory != null &&
            !controller.getCurrentCategoriesConfiguration().getCategories()
                .contains( activeCategory.getCategory() ) )
        {
            activeCategory = null;
        }

        clearCategoriesContainer();
        clearKeywordsContainer();
        for( Category category : controller.getCurrentCategoriesConfiguration().getCategories() )
        {
            putNewCategoryButtonIntoContainer( category );
        }
        updateActiveCategoryButtonReference();
        updateViewByActiveCategory();
    }


    public void updateViewByActiveCategory()
    {
        if( activeCategory != null )
        {
            setKeywords( activeCategory.getCategory() );
            activeCategory.getButton().setStyle( "-fx-background-color: red;" );
        }
    }


    public void updateActiveCategoryButtonReference()
    {
        if( activeCategory != null )
        {
            activeCategory.updateButtonReference(
                findCategoryButton( activeCategory.getCategory().getCategoryName() ) );
        }
    }


    public void setKeywords( Category category )
    {
        clearKeywordsContainer();
        for( Keyword keyword : category.getKeywords() )
        {
            ContextMenu contextMenu = new ContextMenu();
            Button keywordButton =
                new Button( keyword.getValue(), new ImageView( getDeleteIcon() ) );
            MenuItem deleteKeywordMenuItem = new MenuItem( "Delete keyword" );
            deleteKeywordMenuItem.setOnAction( event -> {
                logger.debug( "Delete keyword [{}] menu item handler initialization", keyword );
                new DeleteKeywordEventHandler( controller, category, keyword ).handle();
            } );
            MenuItem renameKeywordMenuItem = new MenuItem( "Rename keyword" );
            renameKeywordMenuItem.setOnAction( event -> {
                logger.debug( "Rename keyword [{}] menu item handler initialization", keyword );
                new RenameKeywordEventHandler( controller, keyword ).handle();
            } );
            contextMenu.getItems().addAll( deleteKeywordMenuItem, renameKeywordMenuItem );
            keywordButton.setOnContextMenuRequested( event -> contextMenu
                .show( keywordButton, event.getScreenX(), event.getScreenY() ) );
            keywordsContainer.getChildren().add( keywordButton );
        }
    }


    public void clearKeywordsContainer()
    {
        keywordsContainer.getChildren().clear();
    }


    public void clearCategoriesContainer()
    {
        categoriesContainer.getChildren().clear();
    }


    private SplitPane createMainContainer()
    {
        SplitPane splitPane =
            new SplitPane( createCategoriesContainer(), createKeywordsContainer() );
        splitPane.setDividerPosition( 0, 0.2125 );
        return splitPane;
    }


    private ScrollPane createKeywordsContainer()
    {
        keywordsContainer = new FlowPane();
        keywordsContainer.setVgap( 10 );
        keywordsContainer.setHgap( 10 );
        keywordsContainer.setAlignment( Pos.CENTER );
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth( true );
        scrollPane.setFitToHeight( true );
        scrollPane.setContent( keywordsContainer );

        return scrollPane;
    }


    private MenuBar getMenuBar()
    {
        createMenuItems();

        return generateMenuBar();
    }


    private MenuBar generateMenuBar()
    {
        MenuBar menuBar = new MenuBar();
        createConfigurationMenu( menuBar );
        createActionsMenu( menuBar );

        return menuBar;
    }


    private void createMenuItems()
    {
        createImportConfigurationMenuItem();
        createExportConfigurationMenuItem();
        createLoadDefaultConfigurationMenuItem();
        createSetCurrentConfigurationAsDefaultMenuItem();
        createExitMenuItem();
        createAddNewCategoryMenuItem();
        createRemoveNewCategoryMenuItem();
        createAddNewKeywordMenuItem();
    }


    private void createActionsMenu( MenuBar menuBar )
    {
        Menu actionsMenu = new Menu( "Actions" );
        menuBar.getMenus().add( actionsMenu );
        actionsMenu.getItems().add( addNewCategoryMenuItem );
        actionsMenu.getItems().add( removeCategoryMenuItem );
        actionsMenu.getItems().add( addNewKeywordMenuItem );
    }


    private void createConfigurationMenu( MenuBar menuBar )
    {
        Menu configurationMenu = new Menu( "Configuration" );
        menuBar.getMenus().add( configurationMenu );
        configurationMenu.getItems().add( importConfigurationMenuItem );
        configurationMenu.getItems().add( exportConfigurationMenuItem );
        configurationMenu.getItems().add( loadDefaultConfigurationMenuItem );
        configurationMenu.getItems().add( setCurrentConfigurationAsDefaultMenuItem );
        configurationMenu.getItems().add( exitMenuItem );
    }


    private void createAddNewCategoryMenuItem()
    {
        addNewCategoryMenuItem = new MenuItem( "Add new category" );
        addNewCategoryMenuItem
            .setAccelerator( new KeyCodeCombination( KeyCode.C, KeyCombination.ALT_DOWN ) );
    }


    private void createRemoveNewCategoryMenuItem()
    {
        removeCategoryMenuItem = new MenuItem( "Remove category" );
    }


    private void createAddNewKeywordMenuItem()
    {
        addNewKeywordMenuItem = new MenuItem( "Add new keyword" );
        addNewKeywordMenuItem
            .setAccelerator( new KeyCodeCombination( KeyCode.K, KeyCombination.ALT_DOWN ) );
    }


    private void createExitMenuItem()
    {
        exitMenuItem = new MenuItem( "Exit" );
    }


    private void createSetCurrentConfigurationAsDefaultMenuItem()
    {
        setCurrentConfigurationAsDefaultMenuItem =
            new MenuItem( "Set current configuration as default" );
        setCurrentConfigurationAsDefaultMenuItem
            .setAccelerator( new KeyCodeCombination( KeyCode.S, KeyCombination.CONTROL_DOWN ) );
    }


    private void createLoadDefaultConfigurationMenuItem()
    {
        loadDefaultConfigurationMenuItem = new MenuItem( "Load default configuration" );
    }


    private void createExportConfigurationMenuItem()
    {
        exportConfigurationMenuItem = new MenuItem( "Export configuration" );
    }


    private void createImportConfigurationMenuItem()
    {
        importConfigurationMenuItem = new MenuItem( "Import configuration" );
    }


    private ScrollPane createCategoriesContainer()
    {
        categoriesContainer = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent( categoriesContainer );

        return scrollPane;
    }


    private void putNewCategoryButtonIntoContainer( Category category )
    {
        Button categoryButton = new Button( category.getCategoryName() );
        categoryButton.setMaxHeight( WINDOW_WIDTH / 5 );
        categoryButton.setPrefWidth( WINDOW_WIDTH / 5 );
        categoryButton.setMinWidth( WINDOW_WIDTH / 5 );
        categoryButton.setOnAction( actionEvent -> {
            if( activeCategory != null )
            {
                activeCategory.getButton().setStyle( null );
            }
            activeCategory = new ActiveCategoryButton( categoryButton, category );
            updateViewByActiveCategory();
        } );

        ContextMenu contextMenu = new ContextMenu();
        MenuItem changeNameMenuItem = new MenuItem( "Change name" );
        changeNameMenuItem.setOnAction( event -> {
            logger.debug( "Rename category [{}] menu item handler initialization", category );
            new RenameCategoryEventHandler( controller, category ).handle();
        } );
        contextMenu.getItems().addAll( changeNameMenuItem );
        categoryButton.setOnContextMenuRequested(
            event -> contextMenu.show( categoryButton, event.getScreenX(), event.getScreenY() ) );

        categoriesContainer.getChildren().add( categoryButton );
    }


    private Button findCategoryButton( String name )
    {
        ObservableList<Node> children = categoriesContainer.getChildren();
        for( Node child : children )
        {
            if( child instanceof Button && ((Button)child).getText().equals( name ) )
            {
                return (Button)child;
            }
        }
        return null;
    }


    private Image getDeleteIcon()
    {
        return new Image(
            ConfigurationEditorDialogView.class.getResourceAsStream( "/hashIcon.png" ) );
    }


    private static class ActiveCategoryButton
    {
        private final Category category;
        private Button button;


        public ActiveCategoryButton( Button button, Category category )
        {
            this.button = button;
            this.category = category;
        }


        public Button getButton()
        {
            return button;
        }


        public Category getCategory()
        {
            return category;
        }


        public void updateButtonReference( Button button )
        {
            this.button = button;
        }
    }
}

