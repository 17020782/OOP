package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddWordController {
    @FXML
    TextField inputword;
    @FXML
    TextField inputexplain;
    @FXML
    Button Add;
    @FXML
    Button Cancel;


    public boolean CheckWordInDataBase(String word) throws SQLException {
        String c="";
        Statement statement = MySQLConnUtils.getJDBCConnection().createStatement();
        String Sql = "SELECT * FROM tbl_edict WHERE word = '"+word+"'";
        ResultSet rs = statement.executeQuery(Sql);
        if(rs.next())
            return true;
        else return false;
    }

    public void SetInsertButton(ActionEvent event) throws SQLException {
        Statement statement = MySQLConnUtils.getJDBCConnection().createStatement();

        String word = inputword.getText();
        String detail = inputexplain.getText();
        word.toLowerCase();
        detail.toLowerCase();
        System.out.println(word);
        if(!CheckWordInDataBase(word)) {
            try {
                String sql = "INSERT INTO tbl_edict(word, detail) VALUES ('"+word+"', '"+detail+"')";
                Alert alert = new Alert(Alert.AlertType.NONE, "BẠN CÓ CHẮC THÊM TỪ!", ButtonType.YES, ButtonType.NO);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                    statement.executeUpdate(sql);

                    Alert confirm  = new Alert(Alert.AlertType.INFORMATION);
                    confirm.setTitle("THÔNG BÁO");
                    confirm.setHeaderText("                     ĐÃ THÊM");
                    confirm.setContentText("*WARNING: FBI");
                    confirm.show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("                       TỪ BẠN NHẬP ĐÃ TỒN TẠI!");
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
