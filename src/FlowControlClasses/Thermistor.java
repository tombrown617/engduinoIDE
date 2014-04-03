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
public class Thermistor extends Module {
    
    private String valueIn ;
    
    public Thermistor(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch, String valueIn) {
        super(new Image("graphics/draggables/thermistor.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ) ;
        
        this.valueIn = valueIn ;
    }
    
    public String getModuleCode(){
        
        if(this.valueIn.equals("CELSIUS")){
            return "    EngduinoThermistor.temperature(CELSIUS)" ;
        }
        else if(this.valueIn.equals("(KELVIN")){
            return "EngduinoThermistor.temperature(KELVIN) " ;
        }
        else if(this.valueIn.equals("FAHRENHEIT")){
            return "    EngduinoThermistor.temperature(FAHRENHEIT) " ;
        }
        else{
            return "" ;
        }
        
    }
    
}
