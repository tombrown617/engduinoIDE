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
public class Constant extends Module {
    
    private int value  = 10 ;
    
    public Constant(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch) {
        super(new Image("graphics/draggables/variable.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ) ;
        
    }
    
    public String getModuleCode(){
        return Integer.toString(this.value );
    }
    
    
    public void setValue(int value){
        this.value = value ;
    }
}
