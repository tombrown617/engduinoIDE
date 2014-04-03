/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FlowControlClasses;

import SketchClasses.Sketch;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shehrozebhatti
 */
public class EngButton extends Module {
    
    private boolean wait = true ;
    
    public EngButton(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch, boolean wait) {
        super(new Image("graphics/draggables/button.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ) ;
        this.wait = wait ;
    }
    
    public String getModuleCode(){
        
        if(wait == true){
            return "    EngduinoButton.waitUntilPressed(); " ;
        }
        else{
            return "    EngduinoButton.isPressed();" ;
        }
        
    }
    
}
