/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XMLFileHandling;

import FlowControlClasses.Constant;
import FlowControlClasses.CustomSketchModule;
import FlowControlClasses.LED;
import FlowControlClasses.Loop;
import FlowControlClasses.Module;
import FlowControlClasses.Wait;
import ModuleClasses.MainInputMarker;
import ModuleClasses.Outputmarker;
import SketchClasses.Sketch;
import SketchClasses.SketchController;
import engduino_ide.Beziercurve;
import engduino_ide.FXMLDocumentController;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;




public class ConvertToSketch {
    
    private Stage stage ;
    
    private FXMLDocumentController doc_controller ;
    
    private SketchController sketch_controller ;
    
    private CustomSketchHandler custom_sketch_controller ;
    
    private Sketch sketch ;
    
    private HashMap<String,Module> modules_parsed = new HashMap<String,Module>() ;
    
    private ArrayList<XMLConnection> xml_connections = new ArrayList<XMLConnection>() ;
    
    public ConvertToSketch(Stage stage, FXMLDocumentController doc_controller, SketchController sketch_controller){
        this.stage = stage ;
        this.doc_controller = doc_controller ;
        this.sketch_controller = sketch_controller ;
        this.custom_sketch_controller = new CustomSketchHandler(doc_controller) ;
    }
    
    
    public Sketch parseXML(Document doc, File file ){
        
        
        if(doc.getDocumentElement().getNodeName().equals("sketch") ){
           
           Sketch sketch =  sketch_controller.createSketch(doc.getDocumentElement().getAttribute("name")) ;
           this.sketch = sketch ;
           sketch_controller.addSketchToList(sketch);
           AnchorPane anchor_pane = doc_controller.createNewSketchTab(doc.getDocumentElement().getAttribute("name"));
           sketch_controller.getSketch(doc.getDocumentElement().getAttribute("name")).setSketchanchorPane(anchor_pane);
                             
           if(doc.getDocumentElement().getAttribute("full").equals("true")){
               stage.setFullScreen(true);
           } 
           
           NodeList nList = doc.getElementsByTagName("connections");
           
           for(int i = 0; i < nList.getLength(); i++){
               
               Node nNode = nList.item(i);
               
              
               if(nNode.hasChildNodes()){
                   
                   NodeList moduleList = nNode.getChildNodes() ;
                   
                   for(int j = 0; j < moduleList.getLength(); j++){
                       Node temp_node = moduleList.item(j);
                       
                       if(temp_node.getNodeName().equals("module") ){
                           
                           parseTree(temp_node,null, doc,anchor_pane,0) ;
                       }
                       else if(temp_node.getNodeName().equals("port")){
                           parseTree(temp_node,null, doc,anchor_pane,Integer.parseInt(temp_node.getAttributes().getNamedItem("number").getNodeValue())) ;
                       }
                   }
                   
               }
               
               
           }
           
           
           NodeList isolated_modules = doc.getElementsByTagName("isolated");
           
           for(int i = 0; i < isolated_modules.getLength(); i++){
               
               Node nNode = isolated_modules.item(i);
               
               if(nNode.hasChildNodes()){
                   
                   NodeList moduleList = nNode.getChildNodes() ;
                   
                   for(int j = 0; j < moduleList.getLength(); j++){
                       Node temp_node = moduleList.item(j);
                       outputModule(temp_node,sketch.getName(),anchor_pane) ;
                       
                   }
                   
               }
               
               
           }
           
           
           doc_controller.getCodeViewTextArea().setText("");
           
           try{
               doc_controller.getCodeViewTextArea().setText(sketch.getCodeViewController().getCode(true,true));
           
           }
           catch(IOException e){
               
           }
           
           this.modules_parsed.clear();
           return sketch ;
           
        }
        else if(doc.getDocumentElement().getNodeName().equals("module")){
            
            if(this.sketch_controller.getTotalSketches() == 0){
                Dialogs.showErrorDialog(stage, "Please create a new sketch or open a previously saved to to load this custom module into the sketch !", "No sketch to add the module", "Error !") ;
            }
            else{
                
                Sketch current_sketch = this.doc_controller.getActiveSketch() ;
                CustomSketchModule mod  = (CustomSketchModule) current_sketch.getModuleController().createModule("custom_module", doc_controller.getTotalModules() + 1, 200, 200, current_sketch.getSketchAnchorPane()) ;
                
                doc_controller.addModuleAnchor(mod.getAnchor());
                current_sketch.getSketchAnchorPane().getChildren().add(mod.getAnchor() ) ;
                current_sketch.getSketchAnchorPane().getChildren().add(mod.getAnchor().getMarker(1)) ;
                current_sketch.getSketchAnchorPane().getChildren().add(mod.getAnchor().getInputMarker(1)) ;
                
                
                mod.setSketch(this.createCustomModuleSketch(doc)) ;
                mod.getSketch().setSketchFile(file);
                current_sketch.getCodeViewController().addHeader(mod.getSketch().getCodeViewController().getHeaderList());
                current_sketch.getCodeViewController().setOutputArrayCode(mod.getSketch().getCodeViewController().getCodeArrayTypes());
            }
            
            
             
            this.modules_parsed.clear();
            return null ;
        }
        else{
            Dialogs.showErrorDialog(stage, "This is not a valid sketch File", "Inappropriate File Type", "Error !") ;
            this.modules_parsed.clear();
            return null ;
        }
        
         
        
    }
    
    public Sketch createCustomModuleSketch(Document doc){
        
        Sketch sketch =  sketch_controller.createSketch(doc.getDocumentElement().getAttribute("name")) ;
           this.sketch = sketch ;
           sketch_controller.addSketchToList(sketch);
           
           sketch.setSketchTab(doc_controller.createCustomTab(sketch, sketch.getName())) ;
           sketch.setSketchType(true);
           AnchorPane anchor_pane = sketch.getSketchAnchorPane() ;
           sketch_controller.getSketch(doc.getDocumentElement().getAttribute("name")).setSketchanchorPane(anchor_pane);
                             
           if(doc.getDocumentElement().getAttribute("full").equals("true")){
               stage.setFullScreen(true);
           } 
           
           NodeList nList = doc.getElementsByTagName("connections");
           
           for(int i = 0; i < nList.getLength(); i++){
               
               Node nNode = nList.item(i);
               
              
               if(nNode.hasChildNodes()){
                   
                   NodeList moduleList = nNode.getChildNodes() ;
                   
                   for(int j = 0; j < moduleList.getLength(); j++){
                       Node temp_node = moduleList.item(j);
                       
                       if(temp_node.getNodeName().equals("module") ){
                           parseTree(temp_node,null, doc,anchor_pane,0) ;
                       }
                       else if(temp_node.getNodeName().equals("port")){
                           parseTree(temp_node,null, doc,anchor_pane,Integer.parseInt(temp_node.getAttributes().getNamedItem("number").getNodeValue())) ;
                       }
                   }
                   
               }
               
               
           }
           
           
           NodeList isolated_modules = doc.getElementsByTagName("isolated");
           
           for(int i = 0; i < isolated_modules.getLength(); i++){
               
               Node nNode = isolated_modules.item(i);
               
               if(nNode.hasChildNodes()){
                   
                   NodeList moduleList = nNode.getChildNodes() ;
                   
                   for(int j = 0; j < moduleList.getLength(); j++){
                       Node temp_node = moduleList.item(j);
                       outputModule(temp_node,sketch.getName(),anchor_pane) ;
                       
                   }
               }
           }
           return sketch ;
    }
    
    
    private Module outputModule(Node temp_node,String sketch_name,AnchorPane anchor_pane){
        
        NamedNodeMap attr_list = temp_node.getAttributes() ;
        
            int x = (int) Double.parseDouble(attr_list.getNamedItem("x").getNodeValue());
            int y = (int) Double.parseDouble(attr_list.getNamedItem("y").getNodeValue()) ;
            String module_type = attr_list.getNamedItem("type").getNodeValue() ;
            
            Module mod = doc_controller.addModuleToSketch(sketch_name, x,y, anchor_pane, module_type);
            
            
            if(module_type.equals("Blink LED") ){
                LED led = (LED) mod ;
                led.setLEDNumber(temp_node.getAttributes().getNamedItem("value").getNodeValue());
            }
            else if(module_type.equals("Wait") ){
                Wait wait = (Wait) mod ;
                wait.setWaitingTime(Integer.parseInt(temp_node.getAttributes().getNamedItem("value").getNodeValue()));
                
            }
            else if(module_type.equals("Constant")){
                Constant cons = (Constant) mod ;
                cons.setValue(Integer.parseInt(temp_node.getAttributes().getNamedItem("value").getNodeValue()));
            }
            else if(module_type.equals("For Loop")){
                Loop loop = (Loop) mod ;
                loop.setLoopSymbol(temp_node.getAttributes().getNamedItem("symbol").getNodeValue());
            }
            else if(module_type.equals("custom_module")){
                
                CustomSketchModule custom_module = (CustomSketchModule) mod  ;
                this.custom_sketch_controller.createCustomSketch(attr_list.getNamedItem("file_path").getNodeValue(), mod, new ConvertToSketch(this.stage, this.doc_controller, this.sketch_controller));
                sketch.getCodeViewController().addHeader(custom_module.getSketch().getCodeViewController().getHeaderList());
                sketch.getCodeViewController().setOutputArrayCode(custom_module.getSketch().getCodeViewController().getCodeArrayTypes());
                
            }
        
        return mod ;
    }
    
    private void parseTree(Node temp_node, Module previous_module,Document doc, AnchorPane anchor_pane,int port){
        
        String sketch_name = doc.getDocumentElement().getAttribute("name") ;
            
        NamedNodeMap attr_list = temp_node.getAttributes() ;
         
        if(temp_node.hasChildNodes() && temp_node.getNodeName().equals("module")){
            
            
            Module mod = null ;
            
            if(temp_node.getAttributes().getNamedItem("connection_from").getNodeValue().equals("main")){
                
                mod = outputModule(temp_node,sketch_name,anchor_pane) ;
            
                this.modules_parsed.put(temp_node.getAttributes().getNamedItem("module_id").getNodeValue(),mod) ;
                this.xml_connections.add(new XMLConnection("main",temp_node.getAttributes().getNamedItem("module_id").getNodeValue())) ;
                
                MainInputMarker main_input = sketch.getModuleController().getMainInputMarker() ;
                Beziercurve new_final_curve = new Beziercurve(anchor_pane, main_input,mod.getAnchor().getInputMarker(1)) ;
                sketch.getModuleConnectionController().createNewConnection(main_input, mod, mod.getAnchor().getInputMarker(1).getPort(),new_final_curve);
              
            }
            else{
                
                if(this.modules_parsed.containsKey(temp_node.getAttributes().getNamedItem("module_id").getNodeValue())){
                    
                    if(this.doesXMLConnectionExist(temp_node.getAttributes().getNamedItem("connection_from").getNodeValue(), temp_node.getAttributes().getNamedItem("module_id").getNodeValue()) == false){
                        
                        this.xml_connections.add(new XMLConnection(temp_node.getAttributes().getNamedItem("connection_from").getNodeValue(),temp_node.getAttributes().getNamedItem("module_id").getNodeValue())) ;
                        
                        Outputmarker output_marker = previous_module.getAnchor().getMarker(port) ;
                        mod = this.modules_parsed.get(temp_node.getAttributes().getNamedItem("module_id").getNodeValue()) ;
                        Beziercurve new_final_curve = new Beziercurve(anchor_pane, output_marker,this.modules_parsed.get(temp_node.getAttributes().getNamedItem("module_id").getNodeValue()).getAnchor().getInputMarker(Integer.parseInt(temp_node.getAttributes().getNamedItem("connection_from_port").getNodeValue()))) ;

                        sketch.getModuleConnectionController().createNewConnection(output_marker.getModuleAnchor().getModule(), mod, output_marker.getPort(), port,new_final_curve);
                  
                    }
                    
                }
                else{
                    
                    mod = outputModule(temp_node,sketch_name,anchor_pane) ;
            
                    this.modules_parsed.put(temp_node.getAttributes().getNamedItem("module_id").getNodeValue(),mod) ;
                    
                    
                    this.xml_connections.add(new XMLConnection(temp_node.getAttributes().getNamedItem("connection_from").getNodeValue(),temp_node.getAttributes().getNamedItem("module_id").getNodeValue())) ;
                    Outputmarker output_marker = previous_module.getAnchor().getMarker(port) ;
                    Beziercurve new_final_curve = new Beziercurve(anchor_pane, output_marker,mod.getAnchor().getInputMarker(1)) ;

                    sketch.getModuleConnectionController().createNewConnection(output_marker.getModuleAnchor().getModule(), mod, output_marker.getPort(), port,new_final_curve);
                  
                }
                
            }
                 
            NodeList child_nodes = temp_node.getChildNodes() ;
            
            for(int i = 0; i < temp_node.getChildNodes().getLength(); i++){
                
                parseTree(child_nodes.item(i),mod, doc, anchor_pane,0) ;
            }
            
        }
        else if(temp_node.hasChildNodes() && temp_node.getNodeName().equals("port")){
            
            NodeList child_nodes = temp_node.getChildNodes() ;
            int port_number = Integer.parseInt(temp_node.getAttributes().getNamedItem("number").getNodeValue()) ;
            
            for(int i = 0; i < temp_node.getChildNodes().getLength(); i++){
                
                parseTree(child_nodes.item(i),previous_module, doc, anchor_pane,port_number) ;
            }
            
        }
        else if(!temp_node.hasChildNodes() && temp_node.getNodeName().equals("module")){
            
            Module mod  = null ;
            
            if(!this.modules_parsed.containsKey(temp_node.getAttributes().getNamedItem("module_id").getNodeValue())){
                
                mod = outputModule(temp_node,sketch_name,anchor_pane) ;
                
                this.modules_parsed.put(temp_node.getAttributes().getNamedItem("module_id").getNodeValue(),mod) ;
            
            }
            
            if(this.doesXMLConnectionExist(temp_node.getAttributes().getNamedItem("connection_from").getNodeValue(), temp_node.getAttributes().getNamedItem("module_id").getNodeValue()) == true){
                return ;
            }
            
            
            this.xml_connections.add(new XMLConnection(temp_node.getAttributes().getNamedItem("connection_from").getNodeValue(),temp_node.getAttributes().getNamedItem("module_id").getNodeValue())) ;
            
             Outputmarker output_marker = previous_module.getAnchor().getMarker(port) ;
             Beziercurve new_final_curve = new Beziercurve(anchor_pane, output_marker,mod.getAnchor().getInputMarker(1)) ;
             sketch.getModuleConnectionController().createNewConnection(output_marker.getModuleAnchor().getModule(), mod, output_marker.getPort(), port,new_final_curve);
                  
            
            return  ;
        }
    }
    
    private boolean doesXMLConnectionExist(String first_id, String second_id){
        
        for(int i = 0; i < this.xml_connections.size(); i++){
            
            if(this.xml_connections.get(i).getFirstModule().equals(first_id) && this.xml_connections.get(i).getSecondModule().equals(second_id) ){
                return true ;
            }
            
        }
        return false ;
    }
    
    private String getAttributeValue(String fullText){
        
      String[] parts =   fullText.split("=") ;
      return parts[1] ;
        
    }
    
}
