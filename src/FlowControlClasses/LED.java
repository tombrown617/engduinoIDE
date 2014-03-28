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
public class LED extends Module {
    
    private int LED_number = 0 ;
    
    private String color ;
    
    private int brightness_level = 10 ;
    
    
    
    
    public LED(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch) {
        super(new Image("graphics/draggables/green_light.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ) ;
        
    }
    
     public LED(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch, String color) {
        super(new Image("graphics/draggables/green_light.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ) ;
        this.color = color ;
        
    }
     
     public LED(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,int number, String color) {
        super(new Image("graphics/draggables/green_light.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ) ;
        this.color = color ;
        this.LED_number = number ;
        
    }
    
    @Override
    public String getModuleCode(){
        
        if(this.LED_number == 0){
            return "EngduinoLEDs.setAll(" + this.color + ", 20)" ;
        }
        else{
            return "EngduinoLEDs.setLED("+ this.LED_number +"," + this.color + ", 20)" ;
        }
        
    }
    
    
}
