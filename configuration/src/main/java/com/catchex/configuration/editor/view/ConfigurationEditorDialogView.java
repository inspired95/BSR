package com.catchex.configuration.editor.view;

import com.catchex.configuration.editor.control.ConfigurationEditorDialogController;
import com.catchex.models.Category;
import com.catchex.models.Keyword;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

import static com.catchex.util.Constants.*;


public class ConfigurationEditorDialogView
{
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 400;
    private ConfigurationEditorDialogController controller;

    private Stage stage;

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


    public void initView( Stage stage )
    {
        this.stage = stage;
        VBox windowContainer = new VBox();
        windowContainer.getChildren().add( generateMenuBar() );
        windowContainer.getChildren().add( createMainContainer() );

        Scene scene = new Scene( windowContainer, WINDOW_WIDTH, WINDOW_HEIGHT );
        stage.setScene( scene );
        stage.setTitle( APP_CONFIGURATION_EDITOR_TITLE );
    }


    public void refreshView()
    {
        if( activeCategory != null && !controller.getCategoriesConfiguration().getCategories()
            .contains( activeCategory.getCategory() ) )
        {
            activeCategory = null;
        }

        clearCategoriesContainer();
        clearKeywordsContainer();
        for( Category category : controller.getCategoriesConfiguration().getCategories() )
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
            MenuItem item1 = new MenuItem( "Delete keyword" );
            MenuItem item2 = new MenuItem( "Rename keyword" );
            item1.setOnAction( event -> controller.removeKeyword( category, keyword ) );
            item2.setOnAction( event -> controller.renameKeyword( keyword ) );
            contextMenu.getItems().addAll( item1, item2 );
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


    public Optional<File> showOpenFileChooser( String title )
    {
        FileChooser fileChooser = getFileChooser( title );
        return Optional.ofNullable( fileChooser.showOpenDialog( stage ) );
    }


    public Optional<File> showSaveFileChooser( String title )
    {
        FileChooser fileChooser = getFileChooser( title );
        return Optional.ofNullable( fileChooser.showSaveDialog( stage ) );
    }


    private FileChooser getFileChooser( String title )
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory( new File( CONFIGURATION_PATH ) );
        fileChooser.setTitle( title );
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter( "BSR " + "configuration",
                '*' + BSR_CONFIGURATION_EXTENSION ) );
        return fileChooser;
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


    private MenuBar generateMenuBar()
    {
        createImportConfigurationMenuItem();
        createExportConfigurationMenuItem();
        createLoadDefaultConfigurationMenuItem();
        createSetCurrentConfigurationAsDefaultMenuItem();
        createExitMenuItem();
        createAddNewCategoryMenuItem();
        createRemoveNewCategoryMenuItem();
        createAddNewKeywordMenuItem();

        MenuBar menuBar = new MenuBar();
        createConfigurationMenu( menuBar );
        createActionsMenu( menuBar );

        return menuBar;
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
        addNewCategoryMenuItem.setOnAction( actionEvent -> controller.addNewCategory() );
        addNewCategoryMenuItem
            .setAccelerator( new KeyCodeCombination( KeyCode.C, KeyCombination.ALT_DOWN ) );
    }


    private void createRemoveNewCategoryMenuItem()
    {
        removeCategoryMenuItem = new MenuItem( "Remove category" );
        removeCategoryMenuItem.setOnAction( actionEvent -> controller.removeCategory() );
    }


    private void createAddNewKeywordMenuItem()
    {
        addNewKeywordMenuItem = new MenuItem( "Add new keyword" );
        addNewKeywordMenuItem.setOnAction( actionEvent -> controller.addNewKeyword() );
        addNewKeywordMenuItem
            .setAccelerator( new KeyCodeCombination( KeyCode.K, KeyCombination.ALT_DOWN ) );
    }


    private void createExitMenuItem()
    {
        exitMenuItem = new MenuItem( "Exit" );
        exitMenuItem.setOnAction( actionEvent -> controller.exitApplication() );
    }


    private void createSetCurrentConfigurationAsDefaultMenuItem()
    {
        setCurrentConfigurationAsDefaultMenuItem =
            new MenuItem( "Set current configuration as default" );
        setCurrentConfigurationAsDefaultMenuItem
            .setOnAction( actionEvent -> controller.setConfigurationAsDefault() );
        setCurrentConfigurationAsDefaultMenuItem
            .setAccelerator( new KeyCodeCombination( KeyCode.S, KeyCombination.CONTROL_DOWN ) );
    }


    private void createLoadDefaultConfigurationMenuItem()
    {
        loadDefaultConfigurationMenuItem = new MenuItem( "Load default configuration" );
        loadDefaultConfigurationMenuItem
            .setOnAction( actionEvent -> controller.loadDefaultConfiguration() );
    }


    private void createExportConfigurationMenuItem()
    {
        exportConfigurationMenuItem = new MenuItem( "Export configuration" );
        exportConfigurationMenuItem.setOnAction( actionEvent -> controller.exportConfiguration() );
    }


    private void createImportConfigurationMenuItem()
    {
        importConfigurationMenuItem = new MenuItem( "Import configuration" );
        importConfigurationMenuItem.setOnAction( actionEvent -> controller.importConfiguration() );
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
        MenuItem item1 = new MenuItem( "Change name" );
        item1.setOnAction( event -> controller.renameCategory( category ) );
        contextMenu.getItems().addAll( item1 );
        categoryButton.setOnContextMenuRequested(
            event -> contextMenu.show( categoryButton, event.getScreenX(), event.getScreenY() ) );

        categoriesContainer.getChildren().add( categoryButton );
    }


    private Button findCategoryButton( String name )
    {
        ObservableList<Node> children = categoriesContainer.getChildren();
        for( Node child : children )
        {
            if( child instanceof Button )
            {
                if( ((Button)child).getText().equals( name ) )
                {
                    return (Button)child;
                }
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
        private Button button;
        private Category category;


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

