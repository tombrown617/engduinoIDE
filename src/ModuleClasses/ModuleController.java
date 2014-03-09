/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import java.util.HashMap;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shehrozebhatti
 */
public class ModuleController {
    
    private HashMap<String,Condition> module_list = new HashMap<String,Condition>() ;
    
    private final HashMap<Integer,String> module_types = new HashMap<Integer,String>() ;
    
    public ModuleController(){
        
        this.fillModuleTypesList();
        
    }
    
    public Condition createModule(String type, int id_num, double x_coordinate, double y_coordinate, AnchorPane sketch){
       
        Condition new_module ;
        
       if(type.indexOf("IF..ELSE..") != -1){
           new_module = new Condition(id_num, x_coordinate, y_coordinate,sketch) ;
           module_list.put("Condition", new_module) ;
           
           //new_module = new ModuleCanvas(x_coordinate, y_coordinate) ;
           //module_list.put("Condition", new_module) ;
           
       }else{
           new_module = new Condition(id_num, x_coordinate, y_coordinate,sketch) ;
           module_list.put("Condition", new_module) ;
           
           //new_module = new ModuleCanvas(x_coordinate, y_coordinate) ;
           //module_list.put("Condition", new_module) ;
            
       }
       
       
       return new_module ;
       
    }
    
    public void addModule(Module module){
        String type = "" ;
        //this.module_list.put(type, module) ;
    }
    
    public void addModule(Module module, String type){
        
        //this.module_list.put(type, module) ;
    }
    
    public void getModule(String name){
        //return this.module_list.get(name) ;
    }
    
     private void fillModuleTypesList(){
        
         this.module_types.put(1, "Condition") ;
         this.module_types.put(2, "ForLoop") ;
         this.module_types.put(3, "WhileLoop") ;
         this.module_types.put(4, "Function") ;
         
        
    }
    
}
