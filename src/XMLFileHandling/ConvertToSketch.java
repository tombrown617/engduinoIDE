/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XMLFileHandling;

import FlowControlClasses.Constant;
import FlowControlClasses.LED;
import FlowControlClasses.Module;
import FlowControlClasses.Wait;
import ModuleClasses.MainInputMarker;
import ModuleClasses.Outputmarker;
import SketchClasses.Sketch;
import SketchClasses.SketchController;
import engduino_ide.Beziercurve;
import engduino_ide.FXMLDocumentController;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 *
 * @author shehrozebhatti
 */
public class ConvertToSketch {
    
    private Stage stage ;
    
    private FXMLDocumentController doc_controller ;
    
    private SketchController sketch_controller ;
    
    private Sketch sketch ;
    
    public ConvertToSketch(Stage stage, FXMLDocumentController doc_controller, SketchController sketch_controller){
        this.stage = stage ;
        this.doc_controller = doc_controller ;
        this.sketch_controller = sketch_controller ;
    }
    
    
    public Sketch parseXML(Document doc){
        
        
        if(doc.getDocumentElement().getNodeName().equals("sketch") || doc.getDocumentElement().getNodeName().equals("module")){
           
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
           doc_controller.getCodeViewTextArea().setText(sketch.getCodeViewController().getCode(true));
           
           return sketch ;
           
        }
        else{
            Dialogs.showErrorDialog(stage, "This is not a valid sketch File", "Inappropriate File Type", "Error !") ;
            return null ;
        }
        
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
        
        return mod ;
    }
    
    private void parseTree(Node temp_node, Module previous_module,Document doc, AnchorPane anchor_pane,int port){
        
        
        NamedNodeMap attr_list = temp_node.getAttributes() ;
         
        if(temp_node.hasChildNodes() && temp_node.getNodeName().equals("module")){
            
            
            
            String sketch_name = doc.getDocumentElement().getAttribute("name") ;
            Module mod = outputModule(temp_node,sketch_name,anchor_pane) ;
            
            if(temp_node.getAttributes().getNamedItem("connection_from").getNodeValue().equals("main")){
                
                MainInputMarker main_input = sketch.getModuleController().getMainInputMarker() ;
                Beziercurve new_final_curve = new Beziercurve(anchor_pane, main_input,mod.getAnchor().getInputMarker(1)) ;
                sketch.getModuleConnectionController().createNewConnection(main_input, mod, mod.getAnchor().getInputMarker(1).getPort());
              
            }
            else{
                
                Outputmarker output_marker = previous_module.getAnchor().getMarker(port) ;
                Beziercurve new_final_curve = new Beziercurve(anchor_pane, output_marker,mod.getAnchor().getInputMarker(1)) ;
                  
                sketch.getModuleConnectionController().createNewConnection(output_marker.getModuleAnchor().getModule(), mod, output_marker.getPort(), port);
                  
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
            
            int x = (int) Double.parseDouble(attr_list.getNamedItem("x").getNodeValue());
            int y = (int) Double.parseDouble(attr_list.getNamedItem("y").getNodeValue()) ;
            
            
            
            Module mod = doc_controller.addModuleToSketch(doc.getDocumentElement().getAttribute("name"),x, y, anchor_pane, attr_list.getNamedItem("type").getNodeValue());
            String module_type = attr_list.getNamedItem("type").getNodeValue() ;
            
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
            
             Outputmarker output_marker = previous_module.getAnchor().getMarker(port) ;
             Beziercurve new_final_curve = new Beziercurve(anchor_pane, output_marker,mod.getAnchor().getInputMarker(1)) ;
             sketch.getModuleConnectionController().createNewConnection(output_marker.getModuleAnchor().getModule(), mod, output_marker.getPort(), port);
                  
            
            return  ;
        }
        
        
        
    }
    
    private String getAttributeValue(String fullText){
        
      String[] parts =   fullText.split("=") ;
      return parts[1] ;
        
    }
    
}
