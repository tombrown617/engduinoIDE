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
public class Magnetometer extends Module {
    
    private String direction  ;
    
    public Magnetometer(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,String direction) {
        super(new Image("graphics/draggables/magno.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ) ;
        this.direction = direction = direction ;
        
    }
    
    public void setDirection(String direction){
        this.direction = direction ;
    }
    
    public String getModuleCode(){
        
        if(this.direction.equals("X")){
            return "    magneticField[0]" ;
        }
        else if(this.direction.equals("Y")){
            return "    magneticField[1]" ;

        }
        else if(this.direction.equals("Z")){
            return "    magneticField[2]" ; 

        }
        else{
            return "" ;
        }
    }
    
    public String getMainDataArrayCode(){
        return "    float magneticField[3];\n   EngduinoMagnetometer.xyz(magneticField); " ;
    }
    
}
