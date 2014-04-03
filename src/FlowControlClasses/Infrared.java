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
public class Infrared extends Module {
    
    private boolean receive = true ;
    
    private int receive_buffer_size ;
    
    private int send_buffer_size = 12 ;
    
    public Infrared(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch) {
        super(new Image("graphics/draggables/infrared_drag.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch ) ;
        
    }
    
    public String getModuleCode(){
        
        if(this.receive == true){
            
            return  "    uint8_t buf_" + this.getModuleID() + "[IRBUFSZ]; \n" + "EngduinoIR.recv(buf_"  +this.getModuleID()+", 500); " ;
        }
        else{
            
            return "    char buf_"+ this.getModuleID()+"[" +this.send_buffer_size + "]; " + "    EngduinoIR.send(buf_"+this.getModuleID() +"  , " + this.send_buffer_size + "  )"; 
        }
    }
    
   
    
}
