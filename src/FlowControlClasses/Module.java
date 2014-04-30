/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FlowControlClasses;

import ModuleClasses.Moduleanchor;
import ModuleClasses.Outputmarker;
import SketchClasses.Sketch;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public abstract class Module {
    
    
    private String module_type ;
    
    private Moduleanchor module_anchor ;
    
    private Sketch sketch ;
    
    private String header = "" ;
    
    private String module_id ;
    
    
    public Module(Image img,String mod_id, double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,String type){
        
        this.module_anchor = new Moduleanchor(img,mod_id,x_coordinate,y_coordinate,sketch,main_sketch,this ) ;
        this.sketch = main_sketch ;
        this.module_id = mod_id ;
        this.module_type = type ;
    }
    
    public Moduleanchor getAnchor(){
        return this.module_anchor ;
    }
    
    public String getHeader(){
        
       
        return this.header ;
    }
    
    public void setHeader(String header){
        this.header = header ;
    }
    
    public String getModuleID(){
        return this.module_id ;
    }
    
    public void setModuleID(String module_id){
        this.module_id = module_id ;
    }
    
    
    public String getModuleCode(){
        return null ;
    }
    
     public String getMainDataArrayCode(){
        return "" ;
    }
     
     public String getModuleType(){
         
        return this.module_type ;
         
     }
     
     public String getStart(){
         return null ;
     }
     
     public String getEnd(){
         return null ;
     }
     
     public String getContent(){
         return null ;
     }
    
     public String getLEDColor(){
         return null ;
     }
     
     public void setContent(String value){
         
     }
   
}
