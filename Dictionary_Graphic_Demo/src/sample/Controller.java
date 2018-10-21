package sample;

import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    TextField inPut;
    @FXML
    ListView listView;
    @FXML
    WebView webView;
    @FXML
    Button translate;
    @FXML
    Button search;
    @FXML
    Button Insert, Delete, Edit;

    public void setKeyBoard() {
        // set sự kiện cho phím Enter
        inPut.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ENTER) {
                String text = inPut.getText();
                text = text.toLowerCase();
                if ("".equals(text)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("THÔNG BÁO");
                    alert.setHeaderText("                       TỪ CHƯA ĐƯỢC NHẬP!");
                    alert.setContentText("*WARNING: FBI");
                    alert.show();
                }else {
                    try {
                        if (timTu(text).equals("")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("THÔNG BÁO");
                            alert.setHeaderText("                TỪ VỪA NHẬP KHÔNG HỢP LỆ!");
                            alert.setContentText("*ERROR: 404");
                            alert.show();
                        }
                        else webView.getEngine().loadContent(timTu(text));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    //hàm hiện từ lên listview và gợi ý từ tìm kiếm
    public void searchWord() throws SQLException {
        List DS = new ArrayList();
        setArray(DS);            // mảng chưa các từ tiếng Anh

        ObservableList<String> listWord = FXCollections.observableArrayList(DS);
        // nạp vào ObservableList DS chứa các từ tiếng anh   (ObservableList cho phép theo dõi những thay đổi khi chúng diễn ra)

        FilteredList<String> filteredData = new FilteredList<>(listWord, s -> true);
        //FilteredList dùng để lọc, các thay đổi trong  ObservableList được truyền ngay đến FilteredList

        listView.setItems(filteredData);
        // hiện lên listview

        inPut.textProperty().addListener((observable, oldValue, newValue) -> {    // những thay đổi trong input
            filteredData.setPredicate(s -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String tolower = newValue.toLowerCase();
                if (s.toLowerCase().startsWith(tolower)) {
                    return true;
                }
                return false;
            });
            listView.setItems(filteredData);
        });
    }

    public void setKeyPressed() throws SQLException{
        //TODO : bắt Mouse Event khi click vào listView
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    //Use ListView's getSelected Item
                    //currentItemSelected = listView.getSelectionModel().getSelectedItem();//use this to do whatever you want to. Open Link etc.
                    String text = (String) listView.getSelectionModel().getSelectedItem();
                    try {
                        inPut.setText(text);
                        webView.getEngine().loadContent(timTu(text));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });}

    public void Submit(ActionEvent event) throws SQLException {
        //TODO xử lí 2 nút search và translate
        //_cmd.dic_mana.insertFromFile();
        String text;
        if (event.getSource() == translate) {
            //TODO : Xử lý translate
            text = inPut.getText();
            text = text.toLowerCase();      // chuyển về chữ thường

            if ("".equals(text)) {           // nếu chưa nhập vào
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("THÔNG BÁO");
                alert.setHeaderText("                       TỪ CHƯA ĐƯỢC NHẬP!");
                alert.setContentText("*WARNING: FBI");
                alert.show();
            } else if (timTu(text).equals("")) {    // nếu nhập sai
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("THÔNG BÁO");
                alert.setHeaderText("                TỪ VỪA NHẬP KHÔNG HỢP LỆ!");
                alert.setContentText("*ERROR: 404");
                alert.show();
            } else webView.getEngine().loadContent(timTu(text));
        }
        if(event.getSource() == search){
            searchWord();
        }

    }

    public void openWindowAddWord(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddWordsample.fxml"));
        Parent sampleParent = loader.load();
        Scene scene = new Scene(sampleParent);
        stage.setScene(scene);
    }

    public void openWindowDeleteWord(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DeleteWordsample.fxml"));
        Parent sampleParent = loader.load();
        Scene scene = new Scene(sampleParent);
        stage.setScene(scene);
    }

    public void openWindowEditWord(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditWordsample.fxml"));
        Parent sampleParent = loader.load();
        Scene scene = new Scene(sampleParent);
        stage.setScene(scene);
    }

    /*
    Các hàm bổ sung cho phần bên trên
    */


    public void initialize(URL location, ResourceBundle resources) {
        setKeyBoard();
    }

    public void setArray(List DS) throws SQLException {
        Statement statement = MySQLConnUtils.getJDBCConnection().createStatement();
        String Sql = "SELECT * FROM tbl_edict";
        ResultSet rs = statement.executeQuery(Sql);
        while(rs.next()){
            DS.add(rs.getString("word"));
        }
    }

    public String timTu(String a) throws SQLException {
        String c="";
        Statement statement = MySQLConnUtils.getJDBCConnection().createStatement();
        String Sql = "SELECT * FROM tbl_edict WHERE word = '"+a+"'";
        ResultSet rs = statement.executeQuery(Sql);
        if(rs.next())
            return (rs.getString("detail"));
        else return "";
    }

    private void buttonClicked(){
        String message = "";
        ObservableList<String> movies;
        movies = listView.getSelectionModel().getSelectedItems();

        for(String m:movies) {
            message += m + "\n";
        }
        try {
            timTu(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void  handle(ActionEvent event )  {
        VoiceManager voiceManager = VoiceManager.getInstance();

        com.sun.speech.freetts.Voice syntheticVoice = voiceManager.getVoice("kevin16");
        syntheticVoice.allocate();
        String text = inPut.getText();
        syntheticVoice.speak(text);
        syntheticVoice.deallocate();
    }
}
