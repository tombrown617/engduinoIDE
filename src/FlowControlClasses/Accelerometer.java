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
public class Accelerometer extends Module {
 
   private String direction ;
    
    public Accelerometer(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch, String direction,String type) {
        super(new Image("graphics/draggables/accel.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch,type ) ;
        this.direction = direction ;
    }
    
    public String getModuleCode(){
        
        if(this.direction.equals("X")){
            return "accelerations[0]" ;
        }
        else if (this.direction.equals("Y")){
            return "accelerations[1]" ;
        }
        else if(this.direction.equals("Z")){
            return "accelerations[2]" ;
        }
        else{
            return "" ;
        }
        
    }
    
    public String getMainDataArrayCode(){
        
        return "    float accelerations[3]; \n" +
        "   EngduinoAccelerometer.xyz(accelerations); " ;
        
    }
    
    public void setDirection(String direction){
        this.direction = direction ;
    }
    
    
}
