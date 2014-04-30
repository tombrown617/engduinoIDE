/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CodeView;

import FlowControlClasses.CustomSketchModule;
import FlowControlClasses.Module;
import ModuleClasses.ModuleConnection;
import SketchClasses.Sketch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javafx.scene.control.Dialogs;


public class CodeViewController {
    
    private ArrayList<String> header_files ;
    
    private String code = "" ;
    
    private final String main_function_body = "" ;
    
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
    
    
    public void addHeader(ArrayList<String> header_list){
        
        for(int i = 0; i < header_list.size(); i++){
             
            if(!this.header_files.contains(header_list.get(i)) && !(header_list.get(i).equals(""))){
                 this.header_files.add(header_list.get(i)) ;
            }
        }
        
       
    }
    

    public String getCode(boolean full,boolean lineNumber) throws IOException{

    
        if(full == false){
           
            if(lineNumber == true){
                try{
                     return addLineNumber(this.getHeaders() + "\n" + this.getSetupCode() + "\n" + this.getMainBodyCode()) ;
                }
                catch(IOException e){
                    return this.getHeaders() + "\n" + this.getSetupCode() + "\n" + this.getMainBodyCode();
                }
            }
            else{
             return this.getHeaders() + "\n" + this.getSetupCode() + "\n" + this.getMainBodyCode();   
            }
            
            
        }
        else{
            
            if(lineNumber == true){
                try{
                    return addLineNumber(this.getHeaders() + "\n" + this.getSetupCode() + "\n" + this.getMainBodyFullCode()) ;
                }
                catch(IOException e){
                    return this.getHeaders() + "\n" + this.getSetupCode() + "\n" + this.getMainBodyFullCode() ;
                }
            }
            else{
                return this.getHeaders() + "\n" + this.getSetupCode() + "\n" + this.getMainBodyFullCode() ;
            }
            
            
            
            
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
    
    public List getHeadersList(){
        return header_files;
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
    

    public void setOutputArrayCode(ArrayList<String> output_array_code){
        
        for(int i = 0; i < output_array_code.size(); i++){
            
            if(output_array_code.get(i).equals("accelerometer") || output_array_code.get(i).equals("magnetometer")){
                
                if(!this.array_code_types.contains(output_array_code.get(i))){
                    
                    array_code_types.add(output_array_code.get(i)) ;
                    
                    if(output_array_code.get(i).equals("accelerometer")){
                        this.array_code.add("    float accelerations[3]; \n" + "    EngduinoAccelerometer.xyz(accelerations); ") ;
                    }
                    else if(output_array_code.get(i).equals("magnetometer")){
                        this.array_code.add("    float magneticField[3];\n" + "    EngduinoMagnetometer.xyz(magneticField); ") ;
                    }
                    
                }
            }
            
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
    
    
    private void traverseTree(TreeNode node ,boolean line_break){
        
        if(!node.hasChildNode()){
            
            if(node.getNodeModule().getModuleType().equals("custom_module")){
                
                CustomSketchModule custom_module = (CustomSketchModule) node.getNodeModule() ;
                
                this.code += custom_module.getSketch().getCodeViewController().getChildNodeCode() ;
            }
            else{
                
                this.code +=  node.getNodeModule().getModuleCode() ;
                if(line_break == true){        
                    this.code += "\n" ;
                }
                
            }
            
            
            return ;
        }
        else {
            
            
                
            if(node.getNodeModule() != null && !node.getNodeModule().getModuleType().equals("custom_module")){
                   
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
            else if(node.getNodeModule() != null && node.getNodeModule().getModuleType().equals("custom_module")){
                
                CustomSketchModule custom_module = (CustomSketchModule) node.getNodeModule() ;
                this.code += custom_module.getSketch().getCodeViewController().getChildNodeCode() ;
            }
            
            
            
           for(int i = 0; i < node.getTotalChildren();i++){
                traverseTree(node.getChild(i),true) ;
           }
            
        }
        
    }
    
    
    
    private void makeTree(TreeNode node, ArrayList<ModuleConnection> branch){
        
        
        ArrayList<ModuleConnection> module_connection = sketch.getModuleConnectionController().getMainConnection() ;
        
        if(module_connection.size() == 1){
            addNodes(this.tree_root,new TreeNode(), module_connection.get(0).getTo()) ;
        }
        else if (module_connection.size() == 0){
            
            Dialogs.showErrorDialog(sketch.getMainGUIController().getStage(), "Unable to generate code because no module is connected to the main Input Port", "Cannot generate Code", "Error!");
                                                   
        }
        else if(module_connection.size() == 2){
            
            
            
            Module first_module = module_connection.get(0).getTo() ;
            Module second_module = module_connection.get(1).getTo() ;
            
            if(sketch.getModuleConnectionController().getConnectedModule(first_module, 1) == sketch.getModuleConnectionController().getConnectedModule(second_module, 1)){
                
                Module module_to = sketch.getModuleConnectionController().getConnectedModule(first_module, 1) ;
                TreeNode node_operator = new TreeNode(module_to) ;
                
                node_operator.addChild(new TreeNode(first_module));
                node_operator.addChild(new TreeNode(second_module));
                
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
    
    
    public ArrayList<String> getHeaderList(){
        return this.header_files ;
    }
   
    
    public ArrayList<String> getCodeArrayTypes(){
        return this.array_code_types ;
    }
   
    private String addLineNumber(String code) throws IOException{
        
        String output = "" ;
        BufferedReader bufReader = new BufferedReader(new StringReader(code));
    
        String line=null;
        int lineNumber = 1 ;
        while( (line=bufReader.readLine()) != null )
        {
           output += lineNumber + "  :  " + line + "\n" ;
           lineNumber++ ;
        }
        
        
        return output ;
    }
    
    
    
}
