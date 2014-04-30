/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import FlowControlClasses.Module;
import engduino_ide.Beziercurve;


public class ModuleConnection {
    
    private Module from_module  ;
    
    private Module to_module  ;
    
    private MainInputMarker main_input ;
    
    private MainOutputMarker main_output;
    
    private int type ;
    
    private int from_port ;
    
    private int to_port ;
    
    private Beziercurve wire ;
    
    
    public ModuleConnection(MainInputMarker main_input, Module module ,int to_port,Beziercurve wire){
        
        this.type = 1;
        this.main_input = main_input ;
        this.to_module = module ;
        this.to_port = to_port ;
        this.wire = wire ;
        
    }
    
    public ModuleConnection(Module module , MainOutputMarker main_output, int from_port,Beziercurve wire){
        
        this.from_module = module ;
        this.main_output = main_output;
        this.type = 3 ;
        this.from_port = from_port ;
        this.wire  = wire ;
        
    }
    
    public ModuleConnection(Module from_module, Module to_module,int from_port, int to_port,Beziercurve wire){
        
        this.type = 2 ;
        this.from_module = from_module ;
        this.to_module = to_module ;
        
        this.from_port = from_port ;
        this.to_port = to_port ;
        this.wire  = wire ;
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
    
    public int getFromPort(){
        return this.from_port ;
    }
    
    public int getToPort(){
        return this.to_port ;
    }
    
    public Beziercurve getConnectionWire(){
        return this.wire ;
    }
}
