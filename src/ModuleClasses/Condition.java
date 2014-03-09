/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shehrozebhatti
 */
public class Condition {
   
    private Moduleanchor module_anchor ;
    
    private Outputmarker output_marker ;
    
    private final Image img = new Image("graphics/condition.png") ;
    
    public Condition(int int_id, double x_coordinate, double y_coordinate, AnchorPane sketch){
        System.out.println("condition object created");
        
        
        module_anchor = new Moduleanchor(img,int_id,x_coordinate,y_coordinate,sketch ) ;
        //this.output_marker = module
                
    }
    
    public Moduleanchor getAnchor(){
        return this.module_anchor ;
    }
    
            
    
}
