package client.controller;

import com.catchex.bankstmt.pdfconverters.PKOBankStmtConverter;
import com.catchex.io.reader.PDFReader;
import com.catchex.models.RawOperation;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class PdfEditorController implements Initializable {

    @FXML private TableView<RawOperation> rawOperationTable;
    @FXML private TableColumn<RawOperation, LocalDate> dateColumn;
    @FXML private TableColumn<RawOperation, String> idColumn;
    @FXML private TableColumn<RawOperation, String> typeColumn;
    @FXML private TableColumn<RawOperation, Double> amountColumn;
    @FXML private TableColumn<RawOperation, String> descriptionColumn;
    @FXML private TableColumn<RawOperation, String> fileNameColumn;

    @FXML private SplitPane mainPane;
    @FXML private Button loadButton;



    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        rawOperationTable.setEditable(true);

        dateColumn.setCellValueFactory(new PropertyValueFactory<RawOperation, LocalDate>("date"));

        idColumn.setCellValueFactory(new PropertyValueFactory<RawOperation, String>("ID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<RawOperation, String>("type"));

        amountColumn.setCellValueFactory(new PropertyValueFactory<RawOperation, Double>("amount"));

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<RawOperation, String>("desc"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        fileNameColumn.setCellValueFactory(new PropertyValueFactory<RawOperation, String>("fileName"));
    }



    @FXML
    private void loadBankStatement(){
        Stage window = (Stage) mainPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        List<File> bankStatements = fileChooser.showOpenMultipleDialog(window);

        if (bankStatements != null){
            for (File bankStatement : bankStatements) {
                Optional<String> read = PDFReader.read(bankStatement.getAbsolutePath());
                List<RawOperation> convert = Collections.emptyList();
                if (read.isPresent()){
                    convert = new PKOBankStmtConverter().convert(bankStatement.getName(), read.get());
                }
                rawOperationTable.setItems(FXCollections.observableArrayList(convert));
            }
        }
    }
}
