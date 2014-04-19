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
    
    public void createNewConnection(Module from_module, Module to_module,int from_port, int to_port){
        
        ModuleConnection new_con = new ModuleConnection(from_module,to_module,from_port, to_port) ;
        this.module_connection_list.add(new_con) ;
    }
    
    public void createNewConnection(MainInputMarker from_module, Module to_module, int to_port){
        
        ModuleConnection new_con = new ModuleConnection(from_module,to_module, to_port) ;
        this.module_connection_list.add(new_con) ;
    }
    
    public void createNewConnection(Module from_module, MainOutputMarker to_module, int from_port){
        
        ModuleConnection new_con = new ModuleConnection(from_module,to_module,from_port) ;
        this.module_connection_list.add(new_con) ;
    }
    
    public void addConnection(ModuleConnection module_connection){
        this.module_connection_list.add(module_connection) ;
    }
    
    
    
    
    
    
    private boolean isModuleConnectedToModule(Module module){
        
        for(int i = 0; i < this.module_connection_list.size(); i++){
          
            if(this.module_connection_list.get(i).getFrom() == module || this.module_connection_list.get(i).getType() == 2){
                return true ;
            }
          
        }
        
        return false ;
    }
    
    public ArrayList<Module> getBranch(ArrayList<Module> branch, Module from_module, int port){
        
        Module connected_module = this.getConnectedModule(from_module, port) ;
        
        if(connected_module == null){
            return branch ;
            
        }
        else{
            
            branch.add(connected_module) ;
            getBranch(branch,connected_module,1) ;
            return branch ;
        }
        
    }
    
    public int calculateModulesInBranch(Module from_module , int port){
        
        Module connected_module = this.getConnectedModule(from_module, port) ;
        
        if(connected_module == null){
            return 0 ;
        }
        else{
            
            //Module mod = getConnectedModule(from_module, port) ;
            return 1 + calculateModulesInBranch(connected_module, 1) ;
        }
        
    }
    
    public Module getConnectedModule(Module module_from, int port){
        
       // try{
            
            for(int i = 0; i < this.module_connection_list.size(); i++){
               
                if(this.module_connection_list.get(i).getType() == 2){
                    
                    if(this.module_connection_list.get(i).getFrom().equals(module_from) && this.module_connection_list.get(i).getFromPort() == port ){
                        //System.out.println("Returning "+ this.module_connection_list.get(i).getTo().getModuleID() + " connected to " + module_from.getModuleID() + "at port " + port) ;
                        return this.module_connection_list.get(i).getTo() ;
                     }
                    
                }
            }
            
       /* }
        catch(NullPointerException e){
            System.out.println("null");
        }
        */
        
        
        return null;
    }
    
    public ArrayList<ModuleConnection> getMainConnection(){
        
        
        ArrayList<ModuleConnection> output_connections = new ArrayList<ModuleConnection>();
        for(int i = 0; i < this.module_connection_list.size(); i++){
            
            if(this.module_connection_list.get(i).getType() == 1){
                output_connections.add( this.module_connection_list.get(i)) ;
            }
        }
        
        return output_connections ;
        
    }
    
    public ModuleConnection getMainConnectionAtPort(int to_port){
        
        for(int i = 0; i < this.module_connection_list.size(); i++){
            
            if(this.module_connection_list.get(i).getType() == 1 && this.module_connection_list.get(i).getToPort() == to_port){
               return this.module_connection_list.get(i) ;
            }
        }
        
        return null ;
    }
    
    public ArrayList<ModuleConnection> getModuleConnectionList(){
        return this.module_connection_list ;
    }
    
    public boolean isModuleConnectedFrom(Module module, String fromModuleID){
        
        boolean output = false ;
        
         for(int i = 0; i < this.module_connection_list.size(); i++){
               
                if(this.module_connection_list.get(i).getType() == 2){
                    
                    if(this.module_connection_list.get(i).getTo() == module && this.module_connection_list.get(i).getFrom().getModuleID().indexOf(fromModuleID) != -1){
                        return true ;
                    }
                }
         }
        
        
        return output ;
    }
    
    public Module getConnectedFromModule(Module module){
        
        for(int i = 0; i < this.module_connection_list.size(); i++){
               
                if(this.module_connection_list.get(i).getType() == 2){
                    
                    if(this.module_connection_list.get(i).getTo() == module ){
                        return this.module_connection_list.get(i).getFrom() ;
                     }
                    
                }
            }
            
       return null;
        
    }
    
    public int getTotalModulesConnectedWithMain(){
        
        int output = 0 ;
        
        for(int i = 0; i < this.module_connection_list.size(); i++){
               
                if(this.module_connection_list.get(i).getType() == 1){
                    
                   output += 1;
                    
                }
            }
        
        
        return output ;
    }
    
    public ArrayList<Module> getModulesConnectedWithMain(){
        
        ArrayList<Module> output = new ArrayList<Module>() ;
        
        for(int i = 0; i < this.module_connection_list.size(); i++){
               
                if(this.module_connection_list.get(i).getType() == 1){
                    
                   output.add(this.module_connection_list.get(i).getTo()) ;
                    
                }
            }
        
        return output ;
    }

    
    public int getInputPort(Module module_from,Module module_to){
        
        for(int i = 0; i < this.module_connection_list.size(); i++){
            
            if(this.module_connection_list.get(i).getType() == 2){
                
                if(this.module_connection_list.get(i).getFrom() == module_from && this.module_connection_list.get(i).getTo() == module_to){
                    return this.module_connection_list.get(i).getToPort() ;
                }
            }
        }
        
        return -1;
    }
   
}
