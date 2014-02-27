/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SketchClasses;

import ModuleClasses.ModuleController;

/**
 *
 * @author shehrozebhatti
 */
public class Sketch {
    
    private String name ;
    
    private ModuleController module_controller ;
    
    public Sketch(String name){
        this.name = name ;
        ModuleController new_module_controller = new ModuleController() ;
        this.module_controller = new_module_controller ;
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
}
