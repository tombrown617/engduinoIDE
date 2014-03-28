/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import SketchClasses.Sketch;
import SketchClasses.SketchController;
import engduino_ide.FXMLDocumentController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author shehrozebhatti
 */
public class uploadUtility extends Utility {
    
     //private final FXMLDocumentController doc_controller ;
    
    public uploadUtility(final FXMLDocumentController doc_controller, final Stage stage, final SketchController sketch_controller){
        
        super("Verify", doc_controller, sketch_controller) ;
        
        doc_controller.getCodeVerifyButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
       
            public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 2){
                            //System.out.println("Double clicked");
                        }
                        else{
                            
                           Sketch sketch ;
                           
                           for(int i = 0; i < doc_controller.getAllTabs().size(); i++){
                               if(doc_controller.getAllTabs().get(i).isSelected()){
                                   sketch = sketch_controller.getSketch(doc_controller.getAllTabs().get(i).getId()) ;
                                   
                                   // this get all the code for the current open sketch so add stuff to this
                                   String code = sketch.getCodeViewController().getCode(true) ;
                                   
                                  
                                   
                                   break ;
                               }
                           }
                           
                           
                            
                        }
                    }
                }
            });
        
        
    }
    
    
}
