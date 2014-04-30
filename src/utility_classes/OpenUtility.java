/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import SketchClasses.Sketch;
import SketchClasses.SketchController;
import XMLFileHandling.ConvertToSketch;
import engduino_ide.FXMLDocumentController;
import java.io.File;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Dialogs;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;


public class OpenUtility extends Utility {
    
    private Stage stage ;
    
    private ConvertToSketch xml_to_sketch_parser ;
    
    public OpenUtility(final FXMLDocumentController doc_controller, final Stage stage, final SketchController sketch_controller){
        
        super("Open", doc_controller, sketch_controller) ;
        this.stage = stage ;
        this.xml_to_sketch_parser = new ConvertToSketch(stage,doc_controller, sketch_controller) ;
        
        doc_controller.getOpenProjectButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
       
            public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){
                            
                            FileChooser fileChooser = new FileChooser();

                            // Set extension filter
                            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                                    "XML files (*.xml)", "*.xml");
                            fileChooser.getExtensionFilters().add(extFilter);

                            // Show save file dialog
                            File file = fileChooser.showOpenDialog(stage);

                            if (file != null) {
                                loadDataFromFile(file);
                            }
                            
                            
                        }
                        
                    }
                }
            });
        
        
        doc_controller.getMenuItem("open_sketch").setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
               getFileChooser() ;
                
            }
        });
        
        doc_controller.getMenuItem("import_sketch").setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
               getFileChooser() ;
                
            }
        });
        
    }
    
    private void getFileChooser(){
        
        FileChooser fileChooser = new FileChooser();

                            // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
               loadDataFromFile(file);
        }
        
    }
    
     
    @SuppressWarnings("unchecked")
    public void loadDataFromFile(File file) {
      
      try {
        
          String xml = FileUtility.readFile(file);
          
          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	  Document doc = dBuilder.parse(file);
 
          doc.getDocumentElement().normalize();
          Sketch sketch = this.xml_to_sketch_parser.parseXML(doc,file);
          
          try{
              sketch.setSketchFile(file);
          }
          catch(NullPointerException e){
              
          }
          
 
	  
      } catch (Exception e) { // catches ANY exception
        Dialogs.showErrorDialog(stage,
            "Could not load data from file:\n" + file.getPath(),
            "Could not load data", "Error", e);
      }
    }

    
    
    
}
