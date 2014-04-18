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
public class Condition extends Module {
   
    private final Image img = new Image("graphics/condition.png") ;
    
    private String boolean_expression = "" ;
    
    public Condition(String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,String type){
        
        super(new Image("graphics/condition.png"), mod_id,x_coordinate,y_coordinate,sketch,main_sketch,type ) ;
        
        
    }
    
    public String getModuleCode(){
        return "if(" + this.boolean_expression  ;
    }
    
    public void setExpression(String boolean_expression){
        this.boolean_expression = boolean_expression ;
    }
            
    
}
