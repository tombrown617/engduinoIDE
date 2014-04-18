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
import java.util.HashMap;
import java.util.Set;
import javafx.scene.control.Dialogs;

/**
 *
 * @author shehrozebhatti
 */
public class CodeViewController {
    
    private ArrayList<String> header_files ;
    
    private String code = "" ;
    
    private final String main_function_body = "" ;
    
    private ArrayList<TreeNode> tree_nodes = new ArrayList<TreeNode>() ;
    
    private ArrayList<String> array_code = new ArrayList<String>() ;
    
    private ArrayList<String> array_code_types = new ArrayList<String>() ;
    
    private TreeNode tree_root  ;
    
    private Sketch sketch ;
    
    public CodeViewController(Sketch sketch){
        this.sketch = sketch ;
        this.header_files = new ArrayList<String>() ;
        this.tree_root = new TreeNode() ;
        
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
    
    public String getArrayCode(){
        String output = "" ;
       
       for(int i = 0; i < this.array_code.size(); i++){
           output += "  " + this.array_code.get(i) + "\n";
       }
       
       output += "\n";
       return output ;
    }
    
    public void setOutputArrayCode(String type, Module module){
        
        if(!this.array_code_types.contains(type)){
            array_code.add(module.getMainDataArrayCode()) ;
            array_code_types.add(type) ;
        }
        
        
    }
    
    private String getSetupCode(){
        
        String output = "void setup(){\n" ;
        
        try{
           
            for(int i = 0; i < header_files.size();i++){
                
                if(header_files.get(i).indexOf("Wire") != -1){
                    output += "     EngduinoMagnetometer.begin();\n" ;
                }
                else{
                    output += "     " + header_files.get(i).substring(10, header_files.get(i).length() - 2) ;
                    output += "begin();\n" ;
                }
                
            }    
            
        }
        catch(StringIndexOutOfBoundsException e){
            System.out.println("Error !") ;
        }
        
        
        output += "}" ;
        return output ;
        
    }
    
    private String getMainBodyCode(){
        return "void loop(){\n" + 
                
                this.getArrayCode() + 
                " }" ;
    }
    
     private String getMainBodyFullCode(){
        return "void loop(){\n" +  this.getArrayCode() + this.getChildNodeCode() + "}" ;
    }
    
    
    public String getChildNodeCode(){
        
        String output = "" ;
        this.code = "" ;
        this.tree_root = new TreeNode() ;
        
        this.makeTree(this.tree_root, sketch.getModuleConnectionController().getModuleConnectionList()); 
        this.traverseTree(this.tree_root,true) ;
        
        output = this.code ;
        
        
        return output ;
        
    }
    
    private String traverseTree2(TreeNode node ,boolean line_break){
        
        if(!node.hasChildNode()){
            
            if(line_break == true){        
                return node.getNodeModule().getModuleCode() + "\n" ;
            }
            else{
                return node.getNodeModule().getModuleCode()  ;
            }
            
            
        }
        else{
            
            String end = "" ;
                
            if(node.getNodeModule() != null ){
                   
               if(node.getTotalChildren() == 1){
                   return null ;
               }
               else if(node.getTotalChildren() == 2){
                   
                   if(node.getNodeModule().getModuleID().indexOf("forl") != -1){
                        return node.getNodeModule().getModuleCode() + traverseTree2(node.getChild(0), true) + "\n}" +  traverseTree2(node.getChild(1),true);
                    }
                   
                   
               }
               
                    
                    
            }
            else{
                
                for(int i = 0; i < node.getTotalChildren();i++){
                   return traverseTree2(node.getChild(i),true) ;
                }
                
            }
            
            
        }
        
        return null ;
        
    }
    
    
    private void traverseTree(TreeNode node ,boolean line_break){
        
        if(!node.hasChildNode()){
            this.code +=  node.getNodeModule().getModuleCode() ;
            if(line_break == true){        
                this.code += "\n" ;
            }
            
            return ;
        }
        else{
            
            
                
            if(node.getNodeModule() != null ){
                   
                    if(node.getNodeModule().getModuleID().indexOf("forl") != -1){
                         
                        this.code += node.getNodeModule().getModuleCode() + "\n" ;
                        
                        for(int i = 0; i < node.getTotalChildren();i++){
                            traverseTree(node.getChild(i), true) ;
                        }
                        
                        this.code += "}\n "  ;
                        
                        return ;
                        
                    }
                    else if(node.getNodeModule().getModuleID().indexOf("cond") != -1){
                        
                        this.code += node.getNodeModule().getModuleCode() ;
                        traverseTree(node.getChild(0), false) ;
                        this.code += "){\n "  ;
                        
                        traverseTree(node.getChild(1),false) ;
                        this.code += "}\nelse{\n "  ;
                        
                        traverseTree(node.getChild(2),false) ;
                        this.code += " }\n" ; 
                        
                        return ;
                    }
                    else if(node.getNodeModule().getModuleID().indexOf("whil") != -1){
                        
                        this.code += node.getNodeModule().getModuleCode() ;
                        traverseTree(node.getChild(0), false) ;
                        this.code += "){\n "  ;
                        
                        for(int i = 1; i < node.getTotalChildren();i++){
                         
                            traverseTree(node.getChild(i),true) ;
                        }
                        
                        this.code += "\n}\n"  ;
                        
                        return ;
                        
                    }
                    else if(node.getNodeModule().getModuleID().indexOf("equa") != -1){
                
                        if(node.getTotalChildren() == 2){

                            traverseTree(node.getChild(0),false) ;
                            this.code += node.getNodeModule().getModuleCode() ;
                            traverseTree(node.getChild(1),false) ;
                            return ;
                        }
                    }
                    
                    this.code += node.getNodeModule().getModuleCode() + "\n" ;
            }
            
            
           for(int i = 0; i < node.getTotalChildren();i++){
                traverseTree(node.getChild(i),true) ;
           }
           //this.code += end ;
            
            
        }
        
    }
    
    
    
    private void makeTree(TreeNode node, ArrayList<ModuleConnection> branch){
        
        
        ArrayList<ModuleConnection> module_connection = sketch.getModuleConnectionController().getMainConnection() ;
        
        if(module_connection.size() == 1){
            addNodes(this.tree_root,new TreeNode(), module_connection.get(0).getTo()) ;
        }
        else if (module_connection.size() == 0){
            //String newName = Dialogs.showInputDialog(getStage(), "Please enter a Sketch name :", "Create a New Sketch", "New Sketch");
        }
        else if(module_connection.size() == 2){
            
            
            
            Module first_module = module_connection.get(0).getTo() ;
            Module second_module = module_connection.get(1).getTo() ;
            
            if(sketch.getModuleConnectionController().getConnectedModule(first_module, 1) == sketch.getModuleConnectionController().getConnectedModule(second_module, 1)){
                
                Module module_to = sketch.getModuleConnectionController().getConnectedModule(first_module, 1) ;
                TreeNode node_operator = new TreeNode(module_to) ;
                
                node_operator.addChild(new TreeNode(first_module));
                node_operator.addChild(new TreeNode(second_module));
                
                //module_to.se
                
                if(sketch.getModuleConnectionController().getConnectedModule(module_to, 1) != null){
                    
                    TreeNode node_child = new TreeNode() ;
                    node_child.addChild(node_operator);
                    addNodes(this.tree_root,node_child,sketch.getModuleConnectionController().getConnectedModule(module_to, 1) ) ;
                }
            }
            
            
        }
        
    }
    
    private void addNodes(TreeNode root,TreeNode node, Module module){
        
        node.setNodeModule(module);
        root.addChild(node);
        
        if(module.getAnchor().getTotalOutputMarkers() == 1){
            
            Module module_to = sketch.getModuleConnectionController().getConnectedModule(module, 1) ;
            
            if(module_to == null){
                
                return ;
            }
            else{
                
                if(module.getModuleID().indexOf("whil") != -1){
                    addNodes(node,new TreeNode(), module_to) ;
                }
                else{
                    addNodes(root,new TreeNode() ,module_to) ;
                }
                
            }
            
        }
        else if(module.getAnchor().getTotalOutputMarkers() == 2){
            
            Module mod_to_1 = sketch.getModuleConnectionController().getConnectedModule(module, 1) ;
            Module mod_to_2 = sketch.getModuleConnectionController().getConnectedModule(module, 2) ;
            
            if(mod_to_1 != null){
                addNodes(node, new TreeNode(),mod_to_1) ;
            }
            
            if(mod_to_2 != null){
                addNodes(root, new TreeNode(),mod_to_2) ;
            }
                
            
        }
        else if(module.getAnchor().getTotalOutputMarkers() == 3){
            
            //condition
            
            Module mod_to_1 = sketch.getModuleConnectionController().getConnectedModule(module, 1) ;
            Module mod_to_2 = sketch.getModuleConnectionController().getConnectedModule(module, 2) ;
            Module mod_to_3 = sketch.getModuleConnectionController().getConnectedModule(module, 3) ;
            
            TreeNode first_node = new TreeNode() ;
            TreeNode second_node = new TreeNode() ;
            
            node.addChild(first_node);
            node.addChild(second_node);
             
            if(mod_to_1 != null){
                addNodes(first_node, new TreeNode(),mod_to_1) ;
            }
            
            if(mod_to_2 != null){
                addNodes(second_node, new TreeNode(),mod_to_2) ;
            }
            
             if(mod_to_3 != null){
                addNodes(root, new TreeNode(),mod_to_3) ;
            }
            
        }
        
       
        
    }
   
   
    
    
    
}
