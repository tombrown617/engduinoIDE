/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XMLFileHandling;

import FlowControlClasses.CustomSketchModule;
import FlowControlClasses.Module;
import SketchClasses.Sketch;
import engduino_ide.FXMLDocumentController;
import java.io.File;
import javafx.scene.control.Dialogs;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import utility_classes.FileUtility;


public class CustomSketchHandler {
    
    private FXMLDocumentController gui_controller ;
    
    //private ConvertToSketch sketchConverter ;
    
    public CustomSketchHandler(FXMLDocumentController gui_controller){
        this.gui_controller = gui_controller ;
        //this.sketchConverter = convertToSketch ;
    }
    
    public CustomSketchHandler(String filePath, Module module){
        
        
    }
    
    public void createCustomSketch(String filePath, Module module, ConvertToSketch sketch_converter){
        
        File file = new File(filePath) ;
        CustomSketchModule custom_module_sketch = (CustomSketchModule) module ;
        
        
       try {
        
          String xml = FileUtility.readFile(file);
          
          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	  Document doc = dBuilder.parse(file);
 
          doc.getDocumentElement().normalize();
          //Sketch sketch = this.sketchConverter.parseXML(doc,file);
          
          
          custom_module_sketch.setSketch(sketch_converter.createCustomModuleSketch(doc)) ;
          custom_module_sketch.getSketch().setSketchFile(file);
                
          
          
          try{
              custom_module_sketch.getSketch().setSketchFile(file);
          }
          catch(NullPointerException e){
              
          }
          
 
	  
      } catch (Exception e) { // catches ANY exception
            
      }
        
        
    }
    
    
    
}
