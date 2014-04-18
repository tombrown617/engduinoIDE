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
    
    private String LED_number = "0" ;
    
    private String color ;
    
    private int brightness_level = 10 ;
    
    private boolean one_led = true ;
    
    
    public LED(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,String type) {
        super(new Image("graphics/draggables/green_light.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch,type ) ;
        
    }
    
     public LED(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch, String color,String type) {
        super(new Image("graphics/draggables/green_light.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ,type) ;
        this.color = color ;
        this.one_led = false ;
    }
     
     public LED(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,int number, String color,String type) {
        super(new Image("graphics/draggables/green_light.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch,type ) ;
        this.color = color ;
        this.LED_number = Integer.toString(number) ;
        
    }
    
    @Override
    public String getModuleCode(){
        
        if(this.one_led == false){
            return "    EngduinoLEDs.setAll(" + this.color + ", 20);" ;
        }
        else{
            return "    EngduinoLEDs.setLED("+ this.LED_number +"," + this.color + ", 20);" ;
        }
        
    }
    
    @Override
    public String getContent(){
        return this.LED_number ;
    }
    
    @Override
    public String getLEDColor(){
        return this.color ;
    }
    
    public void setLEDNumber(String number){
        this.LED_number = number ;
    }
    
    public void setLEDColor(String color){
        this.color = color ;
        
    }
    
    public void setBrightness(int brightness){
        this.brightness_level = brightness ;
    }
    
    public boolean isSingleLEDActivated(){
        return this.one_led ;
    }
    
}
