/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CodeView;

import FlowControlClasses.Module;
import ModuleClasses.ModuleConnection;
import SketchClasses.Sketch;
import java.util.ArrayList;

/**
 *
 * @author shehrozebhatti
 */
public class CodeViewController {
    
    private ArrayList<String> header_files ;
    
    private String code ;
    
    private final String main_function_body = "" ;
    
    private ArrayList<TreeNode> tree_nodes = new ArrayList<TreeNode>() ;
    
    private Sketch sketch ;
    
    
    
    public CodeViewController(Sketch sketch){
        this.sketch = sketch ;
        this.header_files = new ArrayList<String>() ;
        
    }
    
    public void addHeader(String header){
        
        
        if(!this.header_files.contains(header) && !(header.equals(""))){
            this.header_files.add(header) ;
        }
    }
    
    public String getCode(boolean full){
        
        if(full == false){
            return this.getHeaders() + "\n" + this.getSetupCode() + "\n" + this.getMainBodyCode() ;
        }
        else{
            return this.getHeaders() + "\n" + this.getSetupCode() + "\n" + this.getMainBodyFullCode() ;
        }
        
    }
    
    public String getHeaders(){
        
        String output = "" ;
        for(int i = 0; i < header_files.size();i++){
            output += header_files.get(i) ;
            output += "\n" ;
        }
        
        return output ;
        
    }
    
    
    private String getSetupCode(){
        
        String output = "void setup(){\n" ;
        
        try{
           
            for(int i = 0; i < header_files.size();i++){
                
                output += "     " + header_files.get(i).substring(10, header_files.get(i).length() - 2) ;
                output += "begin();\n" ;
            }    
            
        }
        catch(StringIndexOutOfBoundsException e){
            System.out.println("Error !") ;
        }
        
        
        output += "}" ;
        return output ;
        
    }
    
    private String getMainBodyCode(){
        return "void loop(){\n}" ;
    }
    
     private String getMainBodyFullCode(){
        return "void loop(){\n" + this.getChildNodeCode() + "}" ;
    }
    
    
    public String getChildNodeCode(){
        
        String output = "" ;
        
        this.makeTree(); 
        
        for(int i = 0 ; i < this.tree_nodes.size(); i++){
             output += "    " + tree_nodes.get(i).getNodeModule().getModuleCode() ;
             output += "    ;\n" ;
        }
        
        System.out.println("the dhild code is " + output);
        return output ;
        
    }
    
    private void makeTree(){
        
        sketch.getModuleConnectionController().getModuleConnectionList() ;
        Module current_module = null  ;
        
        for(int i = 0; i < sketch.getModuleConnectionController().getModuleConnectionList().size(); i++){
            
            ModuleConnection module_connection = sketch.getModuleConnectionController().getModuleConnectionList().get(i) ;
            
            if(module_connection.getType() == 1){
                this.tree_nodes.add(new TreeNode(module_connection.getTo())) ;
                current_module = module_connection.getTo() ;
            }
            else if(module_connection.getType() == 2){
                
                if(module_connection.getFrom().equals(current_module)){
                    this.tree_nodes.add(new TreeNode(module_connection.getTo())) ;
                    current_module = module_connection.getTo() ;
                }
                
            }
            else if(module_connection.getType() == 3){
                
                
                
            }
            
        }
        
    }
    
    
    
}
