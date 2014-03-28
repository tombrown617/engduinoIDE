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
public class InfinityLoop extends Module {

    public InfinityLoop(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch) {
        super(new Image("graphics/draggables/infinity.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ) ;
        
    }
    

    
    
}
