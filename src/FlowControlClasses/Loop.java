/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FlowControlClasses;

import SketchClasses.Sketch;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class Loop extends Module {

    private int start = 0 ;
    
    private String end = "16" ;
    
    private boolean increment = true ;
    
    private String symbol = "i"  ;
    
    public Loop(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,String type) {
        super(new Image("graphics/draggables/loop.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch,type ) ;
        
    }
    
    @Override
    public String getModuleCode(){
        if(increment == false){
            return "for(int "+ this.symbol +" = " + this.end + "; " +this.symbol + " > " + this.start + "; "+this.symbol+"-- ){" ;
        }
        else{
            return "for(int " + this.symbol +" = " + this.start + "; "+this.symbol+" < " + this.end + "; "+ this.symbol + "++ ){" ;
        }
    }
    
    public String getStart(){
        return Integer.toString(this.start) ;
    }
    
     public String getEnd(){
        return this.end ;
    }
     
     @Override
     public String getContent(){
         if(this.increment == true){
             return "Increment" ;
         }
         else{
             return "Decrement" ;
        }
     }
     
     public void setIncrement(boolean value){
         
         if(value == true){
             this.increment = true ;
         }
         else{
             this.increment = false ;
         }
         
         
     }
    
     public void setLowerBound(int lower_bound){
         this.start = lower_bound ;
     }
     
     public void setUpperBound(String upper_bound){
         this.end = upper_bound ;
     }
     
     public String getLoopSymbol(){
         return this.symbol ;
     }
     
     public void setLoopSymbol(String symbol){
         this.symbol = symbol ;
     }
}
