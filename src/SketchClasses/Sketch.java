/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SketchClasses;

import CodeView.CodeViewController;
import ModuleClasses.ModuleConnectionController;
import ModuleClasses.ModuleController;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shehrozebhatti
 */
public class Sketch {
    
    private String name ;
    
    private ModuleController module_controller ;
    
    private ModuleConnectionController module_connection_controller ;
    
    private CodeViewController codeViewController ;
    
    private AnchorPane sketch_pane ;
    
    public Sketch(String name){
        
        
        this.name = name ;
        ModuleController new_module_controller = new ModuleController() ;
        this.module_controller = new_module_controller ;
        this.module_controller.setSketch(this);
       
        this.module_connection_controller = new ModuleConnectionController() ;
        this.codeViewController = new CodeViewController(this) ;
    }
    
   
    
    public String getName(){
        return this.name ;
    }
    
    public void setName(String new_name){
        this.name = new_name ;
    }
    
    public ModuleController getModuleController(){
        return this.module_controller ;
    }
    
    public CodeViewController getCodeViewController(){
        return this.codeViewController ;
    }
    
    public ModuleConnectionController getModuleConnectionController(){
        
        return this.module_connection_controller ;
    }
    
    
    public void setSketchanchorPane(AnchorPane sketch_anchor_pane){
        this.sketch_pane = sketch_anchor_pane ;
    }
    
    public AnchorPane getSketchAnchorPane(){
        return this.sketch_pane ;
    }
}
