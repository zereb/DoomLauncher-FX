/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doomlauncher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class Controller {
    
    @FXML
    Button btnLaunch;
        
    
    public void launch(){
      
        String s="/home/lul/programms/DooM/gzdoom/gzdoom";
        ProcessBuilderD processBuilderD = new ProcessBuilderD(s);
        
    }
    
}
