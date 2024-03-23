/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mirielle
 */
public class FXMLMenuInicialController implements Initializable {

    @FXML
    private Button buttonInfo;
   @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void FXMLhandleSobre() throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/javafx/view/FXMLSobre.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
}
