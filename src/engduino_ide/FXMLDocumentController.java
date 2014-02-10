/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sun.plugin.javascript.navig.Anchor;

/**
 *
 * @author shehrozebhatti
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    /*@FXML
    private Label loop_drag ;
    
    @FXML
    private AnchorPane pane_to_drop ;
    
    @FXML
    private ImageView chore_image ;
    
    @FXML
    private Image chore_image_url ;
    
    */
    
    @FXML
    private Button create_new_project_button ;
    
    @FXML
    private Button open_project_button ;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      
      /* loop_drag.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture
                System.out.println("onDragDetected");
                
                /* allow any transfer mode 
                Dragboard db = loop_drag.startDragAndDrop(TransferMode.ANY);
                
                /* put a string on dragboard 
                ClipboardContent content = new ClipboardContent();
                content.putString(loop_drag.getText());
                db.setContent(content);
                
                event.consume();
            }
        });
       
       
       
       
       pane_to_drop.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target 
                System.out.println("onDragOver");
                
                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data 
                if (event.getGestureSource() != pane_to_drop &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses 
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });

        pane_to_drop.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target 
                /* show to the user that it is an actual gesture target 
                if (event.getGestureSource() != pane_to_drop &&
                        event.getDragboard().hasString()) {
                    //pane_to_drop.setFill(Color.GREEN);
                }
                
                event.consume();
            }
        });

        pane_to_drop.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues 
                //pane_to_drop.setFill(Color.BLACK);
                
                event.consume();
            }
        });
        
        pane_to_drop.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped 
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it 
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //pane_to_drop.setText(db.getString());
                    
                    chore_image.setOpacity(1);
                   
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used 
                event.setDropCompleted(success);
                
                event.consume();
            }
        });

        */
        
       
       
       
        
        
    } 
    
    
    
}
