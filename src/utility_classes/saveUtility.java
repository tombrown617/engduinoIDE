/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import SketchClasses.Sketch;
import SketchClasses.SketchController;
import XMLFileHandling.SketchToXML;
import engduino_ide.FXMLDocumentController;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Dialogs;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


public class saveUtility extends Utility {
    
    private Stage stage ;
    
    private SketchToXML sketch_to_xml ;
    
     
    
    public saveUtility(final FXMLDocumentController doc_controller, final Stage stage, final SketchController sketch_controller){
        
        
        super("Save", doc_controller, sketch_controller) ;
        this.stage = stage ;
        this.sketch_to_xml = new SketchToXML(stage) ;
        
        doc_controller.getSketchSaveButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
       
            public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){
                            
                            if(sketch_controller.getTotalSketches() == 0){
                                Dialogs.showErrorDialog(stage, "There are no sketches to save in the main sketch window !", "No Sketches found", "Error !") ;
                            }
                            else{
                                
                                Sketch sketch_to_save = doc_controller.getActiveSketch() ;
                                
                                if(sketch_to_save.getSketchFile() != null){
                                    
                                    if(sketch_to_save.isSketchForModule()){
                                          saveDataToFile(sketch_to_save.getSketchFile(), sketch_to_save,"Module");
                                      }
                                      else{
                                          saveDataToFile(sketch_to_save.getSketchFile(), sketch_to_save,"Sketch");
                                      }
                                    
                                }
                                else{
                                    
                                    FileChooser fileChooser = new FileChooser();
                                    fileChooser.setTitle("Save Sketch - " + sketch_to_save.getName());

                                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                                    fileChooser.getExtensionFilters().add(extFilter);

                                    File file = fileChooser.showSaveDialog(stage);

                                    if (file != null) {

                                      if (!file.getPath().endsWith(".xml")) {

                                        file = new File(file.getPath() + ".xml");
                                      }
                                      
                                      if(sketch_to_save.isSketchForModule()){
                                          saveDataToFile(file, sketch_to_save,"Module");
                                      }
                                      else{
                                          saveDataToFile(file, sketch_to_save,"Sketch");
                                      }
                                      
                                      
                                    }
                                }
                             }
                        }
                        
                    }
                }
            });
        
        
        
        doc_controller.getSaveAsModule().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                fileLocationChooser() ;
                
            }
        });
        
        doc_controller.getMenuItem("export_sketch").setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                fileLocationChooser() ;
                
            }
        });
        
        
    }
    
    private void fileLocationChooser(){
        
        if(super.getSketchController().getTotalSketches() == 0){
                                Dialogs.showErrorDialog(stage, "There are no sketches to save in the main sketch window !", "No Sketches found", "Error !") ;
                   }
                   else{
                                
                      Sketch sketch_to_save = super.getDocumentController().getActiveSketch() ;
                                
                      if(sketch_to_save.getSketchFile() != null){
                             saveDataToFile(sketch_to_save.getSketchFile(),sketch_to_save, "Module") ;
                         }
                         else{
                                    
                             FileChooser fileChooser = new FileChooser();
                             fileChooser.setTitle("Save Module - " + sketch_to_save.getName());

                             FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                             fileChooser.getExtensionFilters().add(extFilter);

                            File file = fileChooser.showSaveDialog(stage);

                            if (file != null) {

                                if (!file.getPath().endsWith(".xml")) {

                                   file = new File(file.getPath() + ".xml");
                                 }

                                      saveDataToFile(file, sketch_to_save, "Module");
                                    }
                                }
                             }
        
    }
    
    public void saveDataToFile(File file, Sketch sketch,String type) {
      
      String fileName = file.getName().substring(0,file.getName().length() - 4) ;
      sketch.setName(fileName);
      String xml = this.sketch_to_xml.getXML(sketch, type) ;
      
      try {
        FileUtility.saveFile(xml, file);
        Dialogs.showInformationDialog(stage, "Your " + type+  " has been saved", type + " Saved !", ":-)");
        sketch.setSketchFile(file);
        
      } catch (Exception e) { 
        Dialogs.showErrorDialog(stage,
            "Could not save data to file:\n" + file.getPath(),
            "Could not save data", "Error", e);
      }
    }
    
    
}
