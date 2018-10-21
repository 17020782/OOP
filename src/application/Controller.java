package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class Controller implements Initializable {


    @FXML private TextField textFieldInput;
    @FXML private Label labelOutput;
    @FXML private WebView webView;
    @FXML private WebEngine webEngine;
    @FXML private ListView<String> listView;
    
    
    @FXML
    public  void Insert(ActionEvent event)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("Insert.fxml")));
            Parent rootinsert = (Parent) fxmlLoader.load();
            Stage stageinsert = new Stage();
            stageinsert.initStyle(StageStyle.UTILITY);

            stageinsert.hide();
            stageinsert.setTitle("insert new Word");
            stageinsert.setScene(new Scene(rootinsert));
            stageinsert.show();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}  