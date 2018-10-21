package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeleteWordController {
    @FXML
    TextField inputdelete;
    @FXML
    ListView listView;
    @FXML
    Button Search;

    public void Submit(ActionEvent event) throws SQLException{
        searchWord();
    }

    public boolean CheckWordInDataBase(String word) throws SQLException {
        String c="";
        Statement statement = MySQLConnUtils.getJDBCConnection().createStatement();
        String Sql = "SELECT * FROM tbl_edict WHERE word = '"+word+"'";
        ResultSet rs = statement.executeQuery(Sql);
        if(rs.next())
            return true;
        else return false;
    }

    public void setArray(List DS) throws SQLException {
        Statement statement = MySQLConnUtils.getJDBCConnection().createStatement();
        String Sql = "SELECT * FROM tbl_edict";
        ResultSet rs = statement.executeQuery(Sql);
        while(rs.next()){
            DS.add(rs.getString("word"));
        }
    }

    public void searchWord() throws SQLException {
        List DS = new ArrayList();
        setArray(DS);            // mảng chưa các từ tiếng Anh

        ObservableList<String> listWord = FXCollections.observableArrayList(DS);
        // nạp vào ObservableList DS chứa các từ tiếng anh   (ObservableList cho phép theo dõi những thay đổi khi chúng diễn ra)

        FilteredList<String> filteredData = new FilteredList<>(listWord, s -> true);
        //FilteredList dùng để lọc, các thay đổi trong  ObservableList được truyền ngay đến FilteredList

        listView.setItems(filteredData);
        // hiện lên listview

        inputdelete.textProperty().addListener((observable, oldValue, newValue) -> {    // những thay đổi trong input
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

    public void SetDeleteButton(ActionEvent event) throws SQLException {
        Statement statement = MySQLConnUtils.getJDBCConnection().createStatement();

        String word = inputdelete.getText();

        if(CheckWordInDataBase(word)) {

            try {
                String sql = "DELETE  FROM tbl_edict WHERE word = '"+ word+"'";
                Alert alert = new Alert(Alert.AlertType.NONE, "BẠN CÓ CHẮC MUỐN XÓA TỪ!", ButtonType.YES, ButtonType.NO);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                    statement.executeUpdate(sql);
                    Alert delete = new Alert(Alert.AlertType.WARNING);
                    delete.setTitle("THÔNG BÁO");
                    delete.setHeaderText("               ĐÃ XÓA!");
                    delete.setContentText("*WARNING: FBI");
                    delete.show();
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("               TỪ KHÔNG CÓ SẴN!");
            alert.setContentText("*WARNING: FBI");
            alert.show();
        }
    }

    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent sampleParent = loader.load();
        Scene scene = new Scene(sampleParent);
        stage.setScene(scene);
    }
}
