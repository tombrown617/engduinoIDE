/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import java.util.HashMap;

/**
 *
 * @author shehrozebhatti
 */
public class ModuleController {
    
    private HashMap<String,Module> module_list = new HashMap<String,Module>() ;
    
    private final HashMap<Integer,String> module_types = new HashMap<Integer,String>() ;
    
    public ModuleController(){
        
        this.fillModuleTypesList();
        
    }
    
    public void createModule(String type){
       
       if(type.indexOf("IF..ELSE..") != -1){
           Module new_module = new Condition() ;
           module_list.put("Condition", new_module) ;
       }     
       
    }
    
    public void addModule(Module module){
        String type = "" ;
        this.module_list.put(type, module) ;
    }
    
    public void addModule(Module module, String type){
        
        this.module_list.put(type, module) ;
    }
    
    public Module getModule(String name){
        return this.module_list.get(name) ;
    }
    
     private void fillModuleTypesList(){
        
         this.module_types.put(1, "Condition") ;
         this.module_types.put(2, "ForLoop") ;
         this.module_types.put(3, "WhileLoop") ;
         this.module_types.put(4, "Function") ;
         
        
    }
    
}
