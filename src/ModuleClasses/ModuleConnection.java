/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import FlowControlClasses.Module;
import engduino_ide.Beziercurve;

/**
 *
 * @author shehrozebhatti
 */
public class ModuleConnection {
    
    private Module from_module  ;
    
    private Module to_module  ;
    
    private MainInputMarker main_input ;
    
    private MainOutputMarker main_output;
    
    private int type ;
    
    
    public ModuleConnection(MainInputMarker main_input, Module module){
        
        this.type = 1;
        this.main_input = main_input ;
        this.to_module = module ;
        
        System.out.println("Conection created from main to " + module.getModuleID() );
       
    }
    
    public ModuleConnection(Module module , MainOutputMarker main_output){
        
        this.from_module = module ;
        this.main_output = main_output;
        this.type = 3 ;
        
        System.out.println("Conection created from " + module.getModuleID() + " to main output" );
         
    }
    
    public ModuleConnection(Module from_module, Module to_module){
        
        this.type = 2 ;
        this.from_module = from_module ;
        this.to_module = to_module ;
        
         System.out.println("Conection created from " + from_module.getModuleID() + " to " + to_module.getModuleID() );
        
    }
    
    public Module getTo(){
        return this.to_module ;
    }
    
    public Module getFrom(){
        return this.from_module ;
    }
    
    public int getType(){
        return this.type ;
    }
    
}
