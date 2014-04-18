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
public class Wait extends Module {

    private int waiting_time = 500 ;
    
    public Wait(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,String type) {
        super(new Image("graphics/draggables/waiting.png"), mod_id, x_coordinate, y_coordinate, sketch, main_sketch,type);
    }
    
    public void setWaitingTime(int time){
        this.waiting_time = time ;
    }
    
    public int getWaitingTime(){
        return this.waiting_time ;
    }
    
    @Override
    public String getModuleCode(){
        
        String output = "   delay(" + this.getWaitingTime() + ") ;" ;
        return output ;
    }
    
    @Override
    public String getContent(){
        return Integer.toString(this.waiting_time) ;
    }
    
    
}
