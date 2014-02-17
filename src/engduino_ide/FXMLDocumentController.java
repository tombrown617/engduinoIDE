/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
    private ListView flow_control_list_container ;
    
    @FXML
    private ListView led_list_container ;
    
    @FXML
    private ListView hardware_list_container ;
    
    @FXML
    private AnchorPane sketch_1 ;
    
    @FXML
    private AnchorPane sketch_2 ;
    
    private List<Label> flow_control_labels = new ArrayList<Label>() ;
    
    private List<Label> hardware_control_labels = new ArrayList<Label>() ;
    
    private List<Label> led_control_labels = new ArrayList<Label>() ;
    
    private List<AnchorPane> sketchPanes  = new ArrayList<AnchorPane>() ;
   
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
      Allmodules allmoduleList = new Allmodules() ; 
      
      int i = 0 ;
      int j = 0 ;
      
      for(i = 0; i < 3; i++){
      
          ArrayList<String> listData ;
          
           if(i == 0){
               listData = this.fillFlowControlList() ;
           }
           else if(i == 1){
               listData = this.fillSensorList() ;
           }
           else{
               listData = this.fillLEDsList() ;
           }
            
          try{
              for(j = 0; j < listData.size(); j++){
                
                final Label new_label = new Label(listData.get(j)) ;
                new_label.setId(listData.get(j));
                
                new_label.setOnDragDetected(new EventHandler <MouseEvent>() {
                    public void handle(MouseEvent event) {
                        //drag was detected, start drag-and-drop gesture
                        

                        // allow any transfer mode 
                        Dragboard db = new_label.startDragAndDrop(TransferMode.ANY);

                        // put a string on dragboard 
                        ClipboardContent content = new ClipboardContent();
                        content.putString(new_label.getText());
                        System.out.println("onDragDetected " + new_label.getText());
                        db.setContent(content);

                        event.consume();
                    }
                });
                
                switch(i){
                    case 0 :  flow_control_labels.add(new_label) ;
                        break ;
                    case 1 : this.hardware_control_labels.add(new_label) ;
                        break ;
                    case 2 : this.led_control_labels.add(new_label) ;
                        break ;
                    default :
                        break ;
                }
                
                
              }
          }
          catch(NullPointerException e){
              System.out.println("Empty List") ;
          }
          
      }
        
     
      flow_control_list_container.setItems(FXCollections.observableList(flow_control_labels));
      hardware_list_container.setItems(FXCollections.observableList(hardware_control_labels));
      this.led_list_container.setItems(FXCollections.observableList(led_control_labels)) ;
      
      /* 
       
       
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
        
       
       this.makeSketchAnchorPaneDroppable(); 
       
        
        
    } 
    
    private void makeSketchAnchorPaneDroppable(){
    
        this.sketchPanes.add(sketch_1) ;
        this.sketchPanes.add(sketch_2) ;
        int i = 0 ;
        for(i = 0; i < this.sketchPanes.size() ; i++){
        
            final AnchorPane sketch  = this.sketch_1 ;
            
            sketch.setOnDragOver(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                    // data is dragged over the target 
                    System.out.println("onDragOver");

                    // accept it only if it is  not dragged from the same node 
                     // and if it has a string data 
                    if (event.getGestureSource() != sketch &&
                            event.getDragboard().hasString()) {
                        // allow for both copying and moving, whatever user chooses 
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }

                    event.consume();
                }
            });
            
            
             sketch.setOnDragExited(new EventHandler <DragEvent>() {
                public void handle(DragEvent event) {
                    // mouse moved away, remove the graphical cues 
                    //sketch.setFill(Color.BLACK);

                    event.consume();
                }
            });
             
            sketch.setOnDragDropped(new EventHandler <DragEvent>() {
                public void handle(DragEvent event) {
                    // data dropped 
                    System.out.println("onDragDropped");
                    // if there is a string data on dragboard, read it and use it 
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        //pane_to_drop.setText(db.getString());

                        //chore_image.setOpacity(1);
                        HBox box = new HBox();

                        ImageView img_view = new ImageView() ;
                        Image chore = new Image("http://i.imgur.com/7IKgTHk.png") ;
                        img_view.setImage(chore);
                       //box.getChildren().add(img_view) ;
                       sketch.getChildren().add(img_view) ;
                        success = true;
                    }
                    // let the source know whether the string was successfully 
                     // transferred and used 
                    event.setDropCompleted(success);

                    event.consume();
                }
            }); 
            

            
                
        }
            
    }
    
    private ArrayList<String> fillFlowControlList(){
        
        ArrayList<String> flowControlList = new ArrayList<String>() ;
        
        flowControlList.add("IF..ELSE..") ;
        flowControlList.add("Switch") ;
        flowControlList.add("For Loop") ;
        flowControlList.add("Repeat Forever") ;
        flowControlList.add("Repeat Once") ;
        flowControlList.add("Wait") ;
        
        return flowControlList ;
        
    }
    
    private ArrayList<String> fillSensorList(){
    
        ArrayList<String> sensorList = new ArrayList<String>() ;
        
        sensorList.add("Magnetometer X Value") ;
        sensorList.add("Magnetometer Y Value") ;
        sensorList.add("Magnetometer Z Value") ;
        sensorList.add("Infrared") ;
        sensorList.add("Accelerometer X Value") ;
        sensorList.add("Accelerometer Y Value") ;
        sensorList.add("Accelerometer Z Value") ;
        
        sensorList.add("Light Sensor Value") ;
        sensorList.add("Thermistor Value") ;
        sensorList.add("Button") ;
        
        return sensorList ;
    }
    
    private ArrayList<String> fillLEDsList(){
       
        ArrayList<String> LEDs = new ArrayList<String>() ;
        
        int i = 1 ;
        for(i = 1; i <=16 ;i++){
        
             LEDs.add("Blink LED " + i) ;
        }
        
        return LEDs ;
    }
    
    
    
}
