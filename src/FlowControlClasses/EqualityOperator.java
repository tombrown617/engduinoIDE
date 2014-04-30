/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FlowControlClasses;

import SketchClasses.Sketch;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class EqualityOperator extends Module {
    
    private String first_value = "" ;
    
    private String second_value = "" ;
    
    private String operator ;
    
     public EqualityOperator(Image img, String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,String operator,String type) {
        super(img, mod_id,x_coordinate,y_coordinate,sketch,main_sketch,type ) ;
        this.operator = operator ;
        
    }
     
    public String getModuleCode(){
        
        
        return "  " + this.operator + "  " ; 
                
    }
    
    public void setFirstValue(String first_value){
        this.first_value = first_value ;
    }
    
    public void setSecondValue(String second_value){
        this.second_value = second_value ;
    }
     
     
    
    
}
