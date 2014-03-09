/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import ModuleClasses.Moduleanchor;
import ModuleClasses.Condition;
import ModuleClasses.Module;
import ModuleClasses.ModuleCanvas;
import SketchClasses.SketchController;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import sun.plugin.javascript.navig.Anchor;
import javafx.scene.shape.*;
/**
 *
 * @author shehrozebhatti
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
   
    @FXML
    private Button new_project_button ;
    
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
    
    @FXML 
    private TabPane sketches_tab_pane ;
    
    @FXML
    private List<ImageView> image_view = new ArrayList<ImageView>() ;
    
    @FXML
    private List<Moduleanchor> anchors = new ArrayList<Moduleanchor>() ;
    
     @FXML
    private List<ModuleCanvas> modules_canvas = new ArrayList<ModuleCanvas>() ;
    
    @FXML
    private AnchorPane controls_tab_paneanchor_pane_for_controls_tab ;
    
    private List<Label> flow_control_labels = new ArrayList<Label>() ;
    
    private List<Label> hardware_control_labels = new ArrayList<Label>() ;
    
    private List<Label> led_control_labels = new ArrayList<Label>() ;
    
    private List<AnchorPane> sketchPanes  = new ArrayList<AnchorPane>() ;
   
    private SketchController sketch_controller ;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
      Allmodules allmoduleList = new Allmodules() ; 
      
      int i = 0 ;
      int j = 0 ;
      
      for(i = 0; i < 3; i++){
      
          ArrayList<String> listData ;
          
           if(i == 0){
               listData = this.fillFlowControlList();
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
                        
                        Dragboard db = new_label.startDragAndDrop(TransferMode.ANY);

                        // put a string on dragboard 
                        ClipboardContent content = new ClipboardContent();
                        content.putString(new_label.getText());
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
      
    } 
    
   
    private void makeSketchAnchorPaneDroppable(final AnchorPane sketch,final List<Moduleanchor> anchors_list, final List<ImageView> image_views, final SketchController sketchController){
    
       
            sketch.setOnDragOver(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                    
                    //if (event.getGestureSource() != sketch &&
                     //       event.getDragboard().hasString()) {
                        // allow for both copying and moving, whatever user chooses 
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    //}

                    event.consume();
                }
            });
 
            sketch.setOnDragExited(new EventHandler <DragEvent>() {
                public void handle(DragEvent event) {
                    event.consume();
                }
            });
             
            sketch.setOnDragDropped(new EventHandler <DragEvent>() {
                public void handle(DragEvent event) {
                    // data dropped 
                    
                    // if there is a string data on dragboard, read it and use it 
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        
                       //System.out.println("THE db string is " + db.getString());
                       if(db.getString().contains("module")){
                           //System.out.println("onDragDropped for module size = " + image_views.size());
                           ImageView new_position ;
                           int i = 0 ;
                           
                           for(i = 0; i < image_views.size(); i++){
                              //System.out.println("The id is " + image_views.get(i).getId() ) ;
                               if(image_views.get(i).getId().equals(db.getString())){
                                   //System.out.println("data found") ; 
                                   int x = (int) ((int) event.getSceneX() - sketch.getWidth()/2.0)  ; 
                                    int y = (int) ((int) event.getSceneY() - 150)  ;
                                   image_views.get(i).relocate(x, y);
                               }
                           }
                           
                       }
                       else{
                           
                            String sketch_name = sketch.getId().substring(0,sketch.getId().length() - 11) ;
                            
                            int x = (int) ((int) event.getSceneX() - sketch.getWidth()/2.0)  ; 
                            int y = (int) ((int) event.getSceneY() - 150)  ;

                            Condition mod = sketchController.getSketch(sketch_name).getModuleController().createModule(db.getString(), image_views.size() + 1,x,y, sketch) ;
                            mod.getAnchor() ;
                            //sketch.getChildren().add(mod) ;
                            image_views.add(mod.getAnchor() ) ;
                            sketch.getChildren().add(mod.getAnchor() ) ;
                            anchors_list.add(mod.getAnchor()) ;
                            
                            sketch.getChildren().add(mod.getAnchor().getMarker()) ;
                            
                            sketch.getChildren().add(mod.getAnchor().getInputMarker()) ;
                            
                            if(anchors_list.size() == 2){
                                System.out.println("two anchors");
                                //Beziercurve new_curve = new Beziercurve(anchors_list.get(0), anchors_list.get(1)) ;
                                //sketch.getChildren().add(new_curve.getCurve()) ;
                            }
                            
                            sketch.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {
                                    @Override public void handle(MouseEvent e) {
                                        if (MouseButton.PRIMARY.equals(e.getButton()) ){
                                           for(int i = 0; i < image_views.size(); i++){
                                               anchors.get(i).hideContextMenu();
                                           }  
                                        }  

                                    }
                            });
                            
                            /*final ImageView img_view = new ImageView() ;
                            img_view.setId("new_module_" + image_views.size() + 1);
                            Image chore = new Image("http://i.imgur.com/7IKgTHk.png") ;
                            //img_view.setStyle("-fx-border-color:darkblue ; -fx-border-insets:3; -fx-border-width:1.0;");
                            img_view.setImage(chore);
                            image_views.add(img_view) ;
                            */
                            
                            /*img_view.setOnDragDetected(new EventHandler <MouseEvent>() {
                                 public void handle(MouseEvent event) {

                                     Dragboard db2 = img_view.startDragAndDrop(TransferMode.ANY);
                                     ClipboardContent content_2 = new ClipboardContent();
                                     
                                     content_2.putString(img_view.getId()) ;
                                     System.out.println("onDragDetected for module");
                                     db2.setContent(content_2);

                                     event.consume();
                                 }
                             });
                                    */
                            
                            /*sketch.getChildren().add(img_view) ;

                            int x = (int) ((int) event.getSceneX() - sketch.getWidth()/2.0)  ; 
                            int y = (int) ((int) event.getSceneY() - 150)  ;

                            img_view.relocate(x,y);*/
                            success = true;
                           
                       }
                       
                    }
                    else{
                        
                    }
                    event.setDropCompleted(success);
                    event.consume();
                }
            }); 
            
    }
    
    private ArrayList<String> fillFlowControlList(){
        
        ArrayList<String> flowControlList = new ArrayList<String>() ;
        
        flowControlList.add("   IF..ELSE..") ;
        flowControlList.add("   Switch") ;
        flowControlList.add("   For Loop") ;
        flowControlList.add("   Repeat Forever") ;
        flowControlList.add("   Repeat Once") ;
        flowControlList.add("   Wait") ;
        
        return flowControlList ;
        
    }
   
    private ArrayList<String> fillSensorList(){
    
        ArrayList<String> sensorList = new ArrayList<String>() ;
        
        sensorList.add("    Magnetometer X Value") ;
        sensorList.add("    Magnetometer Y Value") ;
        sensorList.add("    Magnetometer Z Value") ;
        sensorList.add("    Infrared") ;
        sensorList.add("    Accelerometer X Value") ;
        sensorList.add("    Accelerometer Y Value") ;
        sensorList.add("    Accelerometer Z Value") ;
        
        sensorList.add("    Light Sensor Value") ;
        sensorList.add("    Thermistor Value") ;
        sensorList.add("    Button") ;
        
        return sensorList ;
    }
    
    private ArrayList<String> fillLEDsList(){
       
        ArrayList<String> LEDs = new ArrayList<String>() ;
        
        int i = 1 ;
        for(i = 1; i <=16 ;i++){
        
             LEDs.add("  Blink LED " + i) ;
        }
        
        return LEDs ;
    }
    
    public void createNewSketchTab(String name){
        
        Tab new_tab = new Tab();
        AnchorPane tab_anchor_pane = new AnchorPane() ;
        tab_anchor_pane.setPrefHeight(466.0); 
        tab_anchor_pane.setPrefWidth(665.0);
        tab_anchor_pane.setId(name + "_anchorPane");
        new_tab.setId(name);
        new_tab.setText(name);
        new_tab.setContent(tab_anchor_pane);
        
        this.makeSketchAnchorPaneDroppable(tab_anchor_pane,this.anchors, this.image_view, this.sketch_controller);
        this.sketches_tab_pane.getTabs().add(new_tab) ;
        this.sketchPanes.add(tab_anchor_pane) ;
        //this.sketchPanes.add(new_tab) ;
        
        //Beziercurve new_curve = new Beziercurve(300,300,500,300 );
        //tab_anchor_pane.getChildren().add(new_curve.getCurve()) ;
        //tab_anchor_pane.getChildren().add(new_curve.getStartingAnchor()) ;
        //tab_anchor_pane.getChildren().add(new_curve.getEndingAnchor());
        
        //tab_anchor_pane.getChildren().add(new ModuleCanvas(this.sketchPanes.get(0).getWidth())) ;
        
        
    }
    
    public Button getNewProjectButton(){
        return this.new_project_button ;
    }
    
    public TabPane getSketchTabPane(){
        return this.sketches_tab_pane ;
    }
    
    public void save_project(){
        System.out.println("Saved");
    }
            
    public void setSketchController(SketchController sketchController){
        this.sketch_controller  = sketchController ;
    }
    
    
}
