/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import SketchClasses.Sketch;
import SketchClasses.SketchController;
import engduino_ide.FXMLDocumentController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Dialogs;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author shehrozebhatti
 */
public class newUtility extends Utility {
    
    //private final FXMLDocumentController doc_controller ;
    
    public newUtility(final FXMLDocumentController doc_controller, final Stage stage, final SketchController sketch_controller){
        
        super("New", doc_controller, sketch_controller) ;
        //this.doc_controller = doc_controller ;
        doc_controller.getNewProjectButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 2){
                            //System.out.println("Double clicked");
                        }
                        else{
                            
                            String newName ;
                            
                            newName = Dialogs.showInputDialog(stage, "Please enter a Sketch name :", "Create a New Sketch", "New Sketch");
                            
                           
                                
                                 if(newName == null){
                                        return ;
                                  }
                                
                            
                            
                            
                           
                            
                            for(int i = 0; i < sketch_controller.getSketches().size(); i++){
                                if(sketch_controller.getSketches().get(i).getName().equals(newName)){
                                    
                                    Dialogs.showErrorDialog(stage, "Please choose a different name for a sketch, A Sketch with the name '"+newName+"' already exists !", "Cannot create a new Sketch", "Error!");
                                                  
                                    return ;
                                }
                            }
                            
                            sketch_controller.addSketchToList(sketch_controller.createSketch(newName));
                            
                            sketch_controller.getSketch(newName).setSketchanchorPane(doc_controller.createNewSketchTab(newName));
                            
                        }
                    }
                }
            });
        
        
        MenuItem new_menu = super.getDocumentController().getMenuItem("new_sketch_menu") ;
        
        new_menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                String newName = Dialogs.showInputDialog(stage, "Please enter a Sketch name :", "Create a New Sketch", "New Sketch");
                if(newName.equals("")){
                      return ;
                }
                            
                sketch_controller.addSketchToList(sketch_controller.createSketch(newName));
                sketch_controller.getSketch(newName).setSketchanchorPane(doc_controller.createNewSketchTab(newName));
                            
            }
        });
        
    }
    
    
    
}
