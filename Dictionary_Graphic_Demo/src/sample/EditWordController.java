package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditWordController {
    @FXML
    TextField inputWord, inputExplain;

    public boolean CheckWordInDataBase(String word) throws SQLException {
        String c="";
        Statement statement = MySQLConnUtils.getJDBCConnection().createStatement();
        String Sql = "SELECT * FROM tbl_edict WHERE word = '"+word+"'";
        ResultSet rs = statement.executeQuery(Sql);
        if(rs.next())
            return true;
        else return false;
    }

    public void SetEditButton(ActionEvent event) throws SQLException {
        Statement statement = MySQLConnUtils.getJDBCConnection().createStatement();
        String word = inputWord.getText();
        String detail = inputExplain.getText();

        if(CheckWordInDataBase(word)){
            String sql = "UPDATE tbl_edict set detail = '"+detail+"' WHERE word = '"+word+"'";
            Alert alert = new Alert(Alert.AlertType.NONE, "BẠN CÓ SỬA TỪ!", ButtonType.YES, ButtonType.NO);
            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                statement.executeUpdate(sql);
                Alert delete = new Alert(Alert.AlertType.WARNING);
                delete.setTitle("THÔNG BÁO");
                delete.setHeaderText("               ĐÃ SỬA!");
                delete.setContentText("*WARNING: FBI");
                delete.show();
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
