/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FlowControlClasses;

import SketchClasses.Sketch;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class CustomSketchModule extends Module {
    
    public Sketch sketch ;
    
    public CustomSketchModule(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,String type){
        super(new Image("graphics/draggables/custom_module.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ,type) ;
        
    }
    
    public Sketch getSketch(){
        return this.sketch ;
    }
    
    public void setSketch(Sketch sketch){
        this.sketch = sketch ;
    }
    
}
