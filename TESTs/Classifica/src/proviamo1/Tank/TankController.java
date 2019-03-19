/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proviamo1.Tank;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import proviamo1.Player;

/**
 *
 * @author Aurelio
 */
public class TankController implements Initializable {
    @FXML
    TableView<Player> myTable;    
    @FXML
    public TableColumn<Player, String> id;
    @FXML
    public TableColumn<Player, String> partite;
    @FXML
    public TableColumn<Player, String> vittorie;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        partite.setCellValueFactory(new PropertyValueFactory<>("partite"));
        vittorie.setCellValueFactory(new PropertyValueFactory<>("vittorie"));
        myTable.setItems(getPlayer());



    }    

    public ObservableList<Player> getPlayer()
    {
        ObservableList<Player> data = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn =
                                DriverManager.getConnection(
                                        "jdbc:mysql://localhost/gamevinb?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root",""
                                );
            Statement stmt;

            ResultSet rs;

            // creo la tabella
            stmt = conn.createStatement();


            // recupero i dati
            rs = stmt.executeQuery("SELECT * from classificatank order by vittorie DESC");
            
            while(rs.next())
            {
                data.add(new Player(rs.getString("id"), rs.getString("partite"), rs.getString("vittorie")));
                System.out.println(rs.getString("partite"));
            }

           myTable.setItems(data);

            stmt.close(); // rilascio le risorse
            conn.close(); // termino la connessione
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        } 
        
        return data;
    
}
     @FXML
    private void handleBtnTraffic(Event event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/proviamo1/TrafficRacer/TrafficRacerXML.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        
        stage.show();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

} 
    @FXML
    private void handleBtnBomber(Event event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/proviamo1/BomberMan/BomberManFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        
        stage.show();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

} 
    
}