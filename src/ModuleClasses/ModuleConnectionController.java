/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import FlowControlClasses.Module;
import engduino_ide.Beziercurve;
import java.util.ArrayList;

/**
 *
 * @author shehrozebhatti
 */
public class ModuleConnectionController {
    
    private ArrayList<ModuleConnection> module_connection_list = new ArrayList<ModuleConnection>() ;
    
    public ModuleConnectionController(){
        
    }
    
    public void createNewConnection(Module from_module, Module to_module){
        
        ModuleConnection new_con = new ModuleConnection(from_module,to_module) ;
        this.module_connection_list.add(new_con) ;
    }
    
    public void createNewConnection(MainInputMarker from_module, Module to_module){
        
        ModuleConnection new_con = new ModuleConnection(from_module,to_module) ;
        this.module_connection_list.add(new_con) ;
    }
    
    public void createNewConnection(Module from_module, MainOutputMarker to_module){
        
        ModuleConnection new_con = new ModuleConnection(from_module,to_module) ;
        this.module_connection_list.add(new_con) ;
    }
    
    public void addConnection(ModuleConnection module_connection){
        this.module_connection_list.add(module_connection) ;
    }
    
    
    public ArrayList<ModuleConnection> getModuleConnectionList(){
        return this.module_connection_list ;
    }
}
