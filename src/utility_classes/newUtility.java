/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import engduino_ide.FXMLDocumentController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author shehrozebhatti
 */
public class newUtility extends Utility {
    
    public newUtility(FXMLDocumentController doc_controller){
        super("New", doc_controller) ;
        
        doc_controller.getNewProjectButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 2){
                            System.out.println("Double clicked");
                        }
                        else{
                          //System.out.println("Signel clicked");
                          //Dialogs.showInformationDialog(stage, "I have a great message for you!","Information Dialog", "title");
                        }
                    }
                }
            });
        
    }
    
    public void createSketch(){
        
        
        
    }
    
    
    
}
