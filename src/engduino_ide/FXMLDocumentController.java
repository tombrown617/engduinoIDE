/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import ModuleClasses.Moduleanchor;
import FlowControlClasses.Condition;
import FlowControlClasses.Constant;
import FlowControlClasses.LED;
import FlowControlClasses.Loop;
import FlowControlClasses.Module;
import FlowControlClasses.Wait;
import ModuleClasses.MainInputMarker;
import ModuleClasses.MainOutputMarker;
import ModuleClasses.MainOutputMarker;
import SketchClasses.Sketch;
import SketchClasses.SketchController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import sun.plugin.javascript.navig.Anchor;
import javafx.scene.shape.*;
import javafx.stage.Stage;
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
    private Button print_code_button ;
    
    @FXML
    private Button open_project_button ;
    
    @FXML
    private Button verify_button ;
    
     @FXML
    private Button upload_button ;
    
    @FXML
    private ListView flow_control_list_container ;
    
    @FXML
    private ListView led_list_container ;
    
    @FXML
    private ListView hardware_list_container ;
    
    @FXML
    private ListView all_list_container ;
    
    @FXML
    private AnchorPane sketch_1 ;
    
    @FXML
    private AnchorPane sketch_2 ;
    
    @FXML
    private Stage stage ;
    
    @FXML 
    private TabPane sketches_tab_pane ;
    
    @FXML
    private List<ImageView> image_view = new ArrayList<ImageView>() ;
    
    @FXML
    private List<Moduleanchor> anchors = new ArrayList<Moduleanchor>() ;
    
    @FXML
    private AnchorPane controls_tab_paneanchor_pane_for_controls_tab ;
    
    @FXML
    private GridPane variables_grid_pane ;
   
    
    @FXML
    private Button update_variables ;
    
    @FXML
    private TextArea code_view_text_area ;
    
    private List<Label> flow_control_labels = new ArrayList<Label>() ;
    
    private List<Label> hardware_control_labels = new ArrayList<Label>() ;
    
    private List<Label> led_control_labels = new ArrayList<Label>() ;
    
    private List<Label> all_labels = new ArrayList<Label>() ;
    
    private List<AnchorPane> sketchPanes  = new ArrayList<AnchorPane>() ;
   
    private SketchController sketch_controller ;
    
    private ArrayList<Tab> sketch_tabs = new ArrayList<Tab>() ;
    
    @FXML
    private AnchorPane main_sketch_anchor_pane ;
    
    @FXML
    private Button save_project_button ;
    
    @FXML
    private TabPane bottom_tab_pane ;
    
    @FXML
    private MenuItem save_as_module ;
    
    @FXML
    private MenuBar main_menu_bar ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
      //System.out.println("WIdth = " + main_sketch_anchor_pane.getWidth()) ;
        
      Allmodules allmoduleList = new Allmodules() ; 
      this.code_view_text_area.setEditable(false);
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
                              all_labels.add(new_label) ;
                        break ;
                    case 1 : this.hardware_control_labels.add(new_label) ;
                             all_labels.add(new_label) ;
                        break ;
                    case 2 : this.led_control_labels.add(new_label) ;
                            all_labels.add(new_label) ;
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
      
      this.all_list_container.setItems(FXCollections.observableList(all_labels));
      
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
                       else {
                           
                            //adding module 
                           
                            String sketch_name = sketch.getId().substring(0,sketch.getId().length() - 11) ;
                            
                            int x = (int) ((int) event.getSceneX() - sketch.getWidth()/2.0)  ; 
                            int y = (int) ((int) event.getSceneY() - 150)  ;
                            
                            
                            Module mod = sketchController.getSketch(sketch_name).getModuleController().createModule(db.getString(), image_views.size() + 1,x,y, sketch) ;
                            mod.getAnchor() ;
                            
                            if(sketchController.getSketch(sketch_name).getModuleController().getTotalModuleOfType(mod.getModuleType()) > 1 && mod.getModuleType().equals("For Loop")){
                                Loop loop  = (Loop) mod ;
                                
                                Loop last_loop = (Loop) sketchController.getSketch(sketch_name).getModuleController().getLastModuleOfType(mod.getModuleType()) ;
                                
                                String value = last_loop.getLoopSymbol();
                                int charValue = value.charAt(0);
                                String next = String.valueOf( (char) (charValue + 1));
                                 
                                loop.setLoopSymbol(next);
                            }
                            
                            if(mod.getModuleID().indexOf("accl") != -1 ){
                                sketchController.getSketch(sketch_name).getCodeViewController().setOutputArrayCode("accelerometer", mod);
                            }
                            else if( mod.getModuleID().indexOf("magn") != -1){
                                sketchController.getSketch(sketch_name).getCodeViewController().setOutputArrayCode("magnetometer", mod);
                            }
                            
                            image_views.add(mod.getAnchor() ) ;
                            sketch.getChildren().add(mod.getAnchor() ) ;
                            anchors_list.add(mod.getAnchor()) ;
                            
                            sketch.getChildren().add(mod.getAnchor().getMarker(1)) ;
                            sketch.getChildren().add(mod.getAnchor().getInputMarker(1)) ;
                            
                            // creating code in the code controller for the sketch
                            sketchController.getSketch(sketch_name).getCodeViewController().addHeader(mod.getHeader());
                            
                            try{
                                code_view_text_area.setText(sketchController.getSketch(sketch_name).getCodeViewController().getCode(false,true));
                            }
                            catch(IOException e){
                                
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
                            
                            success = true;
                           
                       }
                       
                    }
                    else{
                        System.out.println("its a wire") ;
                    }
                    event.setDropCompleted(success);
                    event.consume();
                }
            }); 
            
    }
    
    public Module addModuleToSketch(String sketch_name,int x, int y, AnchorPane sketch, String module_type) {
        
         Module mod = sketch_controller.getSketch(sketch_name).getModuleController().createModule(module_type, image_view.size() + 1,x,y, sketch) ;
         mod.getAnchor() ;
                            
         if(mod.getModuleID().indexOf("accl") != -1 ){
                sketch_controller.getSketch(sketch_name).getCodeViewController().setOutputArrayCode("accelerometer", mod);
          }
          else if( mod.getModuleID().indexOf("magn") != -1){
               sketch_controller.getSketch(sketch_name).getCodeViewController().setOutputArrayCode("magnetometer", mod);
          }
                            
          image_view.add(mod.getAnchor() ) ;
          sketch.getChildren().add(mod.getAnchor() ) ;
          anchors.add(mod.getAnchor()) ;
                            
          sketch.getChildren().add(mod.getAnchor().getMarker(1)) ;
          sketch.getChildren().add(mod.getAnchor().getInputMarker(1)) ;
                            
                           
          sketch_controller.getSketch(sketch_name).getCodeViewController().addHeader(mod.getHeader());
          
          try{
              code_view_text_area.setText(sketch_controller.getSketch(sketch_name).getCodeViewController().getCode(false,true));
          
          }
          catch(IOException e){
              
          }
                            
                            sketch.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {
                                    @Override public void handle(MouseEvent e) {
                                        if (MouseButton.PRIMARY.equals(e.getButton()) ){
                                           for(int i = 0; i < image_view.size(); i++){
                                               anchors.get(i).hideContextMenu();
                                           }  
                                        }  

                                    }
                            });
        
        return mod ;
    }
    
    private ArrayList<String> fillFlowControlList(){
        
        ArrayList<String> flowControlList = new ArrayList<String>() ;
        
        flowControlList.add("   IF..ELSE..") ;
        flowControlList.add("   For Loop") ;
       
        flowControlList.add("   While Loop") ;
        flowControlList.add("   Wait") ;
        flowControlList.add("   AND") ;
        flowControlList.add("   OR") ;
        //flowControlList.add("   XOR") ;
        flowControlList.add("   NOT") ;
        
        flowControlList.add("   Greater Than") ;
        flowControlList.add("   Less Than") ;
        
        flowControlList.add("   Greater Than Equals") ;
        flowControlList.add("   Less Than Equals") ;
        
        flowControlList.add("   Equals") ;
        
        flowControlList.add("   Constant") ;
        flowControlList.add("   Print") ;
        
        return flowControlList ;
        
    }
   
    private ArrayList<String> fillSensorList(){
    
        ArrayList<String> sensorList = new ArrayList<String>() ;
        
        sensorList.add("    Magnetometer Value X") ;
        sensorList.add("    Magnetometer Value Y") ;
        sensorList.add("    Magnetometer Value Z") ;
        sensorList.add("    Infrared") ;
        sensorList.add("    Accelerometer Value X") ;
        sensorList.add("    Accelerometer Value Y") ;
        sensorList.add("    Accelerometer Value Z") ;
        
        sensorList.add("    Light Sensor Value") ;
        sensorList.add("    Temperature Value in CELSIUS") ;
        sensorList.add("    Temperature Value in KELVIN") ;
        sensorList.add("    Temperature Value in FAHRENHEIT") ;
        sensorList.add("    Wait for Button Press") ;
        sensorList.add("    Check if Button Pressed") ;
        
        return sensorList ;
    }
    
    private ArrayList<String> fillLEDsList(){
       
        ArrayList<String> LEDs = new ArrayList<String>() ;
        LEDs.add("    Blink LED") ;
        LEDs.add("    Turn All LEDs OFF") ;
        LEDs.add("    Turn All LEDs BLUE") ;
        LEDs.add("    Turn All LEDs YELLOW") ;
        LEDs.add("    Turn All LEDs WHITE") ;
        LEDs.add("    Turn All LEDs GREEN") ;
        LEDs.add("    Turn All LEDs RED") ;
        
        return LEDs ;
    }
    
    public AnchorPane createNewSketchTab(String name){
        
        
        //MainOutputMarker main_output = new MainOutputMarker()  ;
        
        Tab new_tab = new Tab();
        AnchorPane tab_anchor_pane = new AnchorPane() ;
        tab_anchor_pane.setPrefHeight(466.0); 
        tab_anchor_pane.setPrefWidth(665.0);
        tab_anchor_pane.setId(name + "_anchorPane");
        
       
        
        MainOutputMarker main_output = new MainOutputMarker(this.sketches_tab_pane.getWidth() - 20,50,tab_anchor_pane,this.sketch_controller.getSketch(name) ) ; 
        MainInputMarker main_input = new MainInputMarker(0,50,tab_anchor_pane ) ;
        
        
        tab_anchor_pane.getChildren().add(main_output);
        tab_anchor_pane.getChildren().add(main_input);
        
        
        new_tab.setId(name);
        new_tab.setText(name);
        new_tab.setContent(tab_anchor_pane);
        
        this.sketch_tabs.add(new_tab) ;
        
        this.makeSketchAnchorPaneDroppable(tab_anchor_pane,this.anchors, this.image_view, this.sketch_controller);
        this.sketches_tab_pane.getTabs().add(new_tab) ;
        this.sketchPanes.add(tab_anchor_pane) ;
        
        this.sketch_controller.getSketch(name).getModuleController().setMainInputMarker(main_input) ;
        this.sketch_controller.getSketch(name).getModuleController().setMainOutputMarker(main_output);
        
        return tab_anchor_pane ;
        
    }
    
    public Tab createCustomTab(Sketch sketch,String name){
        
        
        Tab new_tab = new Tab();
        AnchorPane tab_anchor_pane = new AnchorPane() ;
        tab_anchor_pane.setPrefHeight(466.0); 
        tab_anchor_pane.setPrefWidth(665.0);
        tab_anchor_pane.setId(name + "_anchorPane");
        
       
        
        MainOutputMarker main_output = new MainOutputMarker(this.sketches_tab_pane.getWidth() - 20,50,tab_anchor_pane,this.sketch_controller.getSketch(name) ) ; 
        MainInputMarker main_input = new MainInputMarker(0,50,tab_anchor_pane ) ;
        
        
        tab_anchor_pane.getChildren().add(main_output);
        tab_anchor_pane.getChildren().add(main_input);
        
        
        new_tab.setId(name);
        new_tab.setText(name);
        new_tab.setContent(tab_anchor_pane);
        
        sketch.setSketchanchorPane(tab_anchor_pane);
        //this.sketch_tabs.add(new_tab) ;
        
        this.makeSketchAnchorPaneDroppable(tab_anchor_pane,this.anchors, this.image_view, this.sketch_controller);
        //this.sketches_tab_pane.getTabs().add(new_tab) ;
        this.sketchPanes.add(tab_anchor_pane) ;
        
        sketch.getModuleController().setMainInputMarker(main_input) ;
        sketch.getModuleController().setMainOutputMarker(main_output);
        
        return new_tab ;
        
        
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
    
    public Button getCodeVerifyButton(){
        
        return this.verify_button ;
    }
    
    public ArrayList<Tab> getAllTabs(){
        return this.sketch_tabs ;
    }
    
    public TextArea getCodeViewTextArea(){
        return this.code_view_text_area ;
    }
    
    
    public Button getUploadButton(){
        return this.upload_button ;
    }
    
    
    public void setVariablesForModule(final Module module){
        
        this.clearVariableGridPane(); 
        this.sketch_controller.setSelectedModule(module);
        
        if(module.getModuleID().indexOf("forl") != -1){
            
            final Loop loop_object = (Loop) module ;
            
            Label first_label = new Label("Increment/Decrement : ") ;
            first_label.setId("first_label");
            
            ChoiceBox first_input = new ChoiceBox() ;
            first_input.setId("first_input");
            first_input.setItems(FXCollections.observableArrayList(
                "Increment", "Decrement")
            );
            
            first_input.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
            
                public void changed(ObservableValue ov, Number value, Number new_value){
                   
                    if(value.intValue() != new_value.intValue()){
                        if(new_value.intValue() == 1){
                            loop_object.setIncrement(false);
                        }
                        else if(new_value.intValue() == 0){
                            loop_object.setIncrement(true);
                        }
                    }
                }
            
            });
            first_input.getSelectionModel().select(module.getContent());
            
           
            Label second_label = new Label("Lower Bound : ") ;
            second_label.setId("second_label");
            
            Label third_label = new Label("Upper Bound : ") ;
            third_label.setId("third_label");
            
            
            TextField second_input = new TextField(module.getStart());
            second_input.setId("second_input");
            
            TextField third_input = new TextField(module.getEnd());
            third_input.setId("third_input");
            
            
            this.variables_grid_pane.add(first_label, 0,1) ;
            this.variables_grid_pane.add(first_input, 1,1) ;
            
            this.variables_grid_pane.add(second_label, 0,2) ;
            this.variables_grid_pane.add(second_input, 1,2) ;
            
            this.variables_grid_pane.add(third_label, 0,3) ;
            this.variables_grid_pane.add(third_input, 1,3) ;
            
            
            
        }
        else if(module.getModuleID().indexOf("ledi_module") != -1){
            
            
            boolean isConnected = false ;
            
            final LED led_ob = (LED) module ;
            
            Label first_label = new Label("LED Number : ") ;
            first_label.setId("first_label");
            
           
            
            ChoiceBox first_input = new ChoiceBox() ;
            first_input.setId("first_input");
            
            final String[] LEDNumbers  ;
            
            
            try{
               
                if(this.getActiveSketch().getModuleConnectionController().isModuleConnectedFrom(module, "forl_module") == true){
                    isConnected = true ;
                }
                
            }
            catch(NullPointerException e){
                System.out.println("Null exception caugfht" ) ;
            }
            
            
             if(isConnected == true){
                
                   Loop mod = (Loop) this.getActiveSketch().getModuleConnectionController().getConnectedFromModule(module) ;
                    LEDNumbers = new String[]{mod.getLoopSymbol(), "0", "1", "2","3", "4","5", "6","7", "8","9", "10","11", "12","13", "14","15"} ;

                    first_input.setItems(FXCollections.observableArrayList(
                        mod.getLoopSymbol(), "0", "1", "2","3", "4","5", "6","7", "8","9", "10","11", "12","13", "14","15" )
                    );
                }
                else{

                    LEDNumbers = new String[]{ "0", "1", "2","3", "4","5", "6","7", "8","9", "10","11", "12","13", "14","15"} ;

                    first_input.setItems(FXCollections.observableArrayList(
                        "0", "1", "2","3", "4","5", "6","7", "8","9", "10","11", "12","13", "14","15" )
                    );

                }
                
                
                first_input.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
            
                    public void changed(ObservableValue ov, Number value, Number new_value){

                        if(value.intValue() != new_value.intValue()){
                            led_ob.setLEDNumber(LEDNumbers[new_value.intValue()]);
                        }
                    }

                });
                
            first_input.getSelectionModel().select(module.getContent());
            
            Label second_label = new Label("Color : ") ;
            second_label.setId("second_label");
            
            final String colors[] = new String[]{"BLUE" , "WHITE", "YELLOW", "GREEN","OFF","RED"} ;
            
            ChoiceBox second_input = new ChoiceBox() ;
            second_input.setId("second_input");
            second_input.setItems(FXCollections.observableArrayList(
                "BLUE" , "WHITE", "YELLOW", "GREEN","OFF","RED")
            );
            
             second_input.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
            
                public void changed(ObservableValue ov, Number value, Number new_value){
                   
                    if(value.intValue() != new_value.intValue()){
                        led_ob.setLEDColor(colors[new_value.intValue()]);
                    }
                }
            
            });
            
            
            second_input.getSelectionModel().select(module.getLEDColor());
            
            
            this.variables_grid_pane.add(first_label, 0,1) ;
            this.variables_grid_pane.add(first_input, 1,1) ;
            
            this.variables_grid_pane.add(second_label, 0,2) ;
            this.variables_grid_pane.add(second_input, 1,2) ;
            
            
        }
        else if(module.getModuleID().indexOf("wait_module") != -1){
            
            
            Label first_label = new Label("Time to Wait : ") ;
            first_label.setId("first_label");
            
            
            TextField first_input = new TextField(module.getContent());
            first_input.setId("first_input");
            
            
            this.variables_grid_pane.add(first_label, 0,1) ;
            this.variables_grid_pane.add(first_input, 1,1) ;
            
        }
        else if(module.getModuleID().indexOf("cons_module") != -1){
            
            Label first_label = new Label("Value : ") ;
            first_label.setId("first_label");
            
            
            TextField first_input = new TextField(module.getContent());
            first_input.setId("first_input");
            
            this.variables_grid_pane.add(first_label, 0,1) ;
            this.variables_grid_pane.add(first_input, 1,1) ;
            
        }
        
        
    }
    
    private void clearVariableGridPane(){
        
       this.variables_grid_pane.getChildren().clear();
        
    }
    
    public void setStage(final Stage stage){
        this.stage = stage ;
        this.setButtonEventHandlers();
        
        
    }
    
    private void setButtonEventHandlers(){
        
        this.save_as_module.setAccelerator(new KeyCodeCombination( KeyCode.S,KeyCombination.META_DOWN, KeyCombination.SHIFT_DOWN));
        this.getMenuItem("new_sketch").setAccelerator(new KeyCodeCombination( KeyCode.N,KeyCombination.META_DOWN));
        this.getMenuItem("open_sketch").setAccelerator(new KeyCodeCombination( KeyCode.O,KeyCombination.META_DOWN));
        this.getMenuItem("save_sketch").setAccelerator(new KeyCodeCombination( KeyCode.S,KeyCombination.META_DOWN));
        this.getMenuItem("print_code").setAccelerator(new KeyCodeCombination( KeyCode.P,KeyCombination.META_DOWN));
        
        
        
        
        final CheckMenuItem edit_code = (CheckMenuItem)this.getMenuItem("edit_code") ;
        edit_code.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                
                getCodeViewTextArea().setText("");
                
                if(edit_code.isSelected()){
                    
                    getCodeViewTextArea().setEditable(true);
                    
                    try {
                        getCodeViewTextArea().setText(getActiveSketch().getCodeViewController().getCode(true, false)) ;
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    getCodeViewTextArea().setEditable(false);
                    
                    try {
                        getCodeViewTextArea().setText(getActiveSketch().getCodeViewController().getCode(true, true)) ;
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }
        });
        
        //this.getMenuItem("new_sketch").setAccelerator(new KeyCodeCombination( KeyCode.N,KeyCombination.META_DOWN));
        //this.getMenuItem("new_sketch").setAccelerator(new KeyCodeCombination( KeyCode.N,KeyCombination.META_DOWN));
        //this.getMenuItem("new_sketch").setAccelerator(new KeyCodeCombination( KeyCode.N,KeyCombination.META_DOWN));
        
        
        this.getMenuItem("exit").setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
              stage.close(); 
            }
        });
        
        final String regex = "[0-9]+";
        
        update_variables.setOnMouseClicked(new EventHandler<MouseEvent>() {
       
            public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){
                           
                          if(sketch_controller.getSelectedModule() != null){
                              
                              String module_id = sketch_controller.getSelectedModule().getModuleID() ;
                              
                              if(module_id.indexOf("forl") != -1 ){
                                  
                                  
                                  
                                  TextField second_input = (TextField) getNodeByRowColumnIndex(2,1,variables_grid_pane) ;
                                  TextField third_input = (TextField) getNodeByRowColumnIndex(3,1,variables_grid_pane) ;
                                  
                                  Loop loop_ob = (Loop) sketch_controller.getSelectedModule() ;
                                          
                                  String second_value = second_input.getText();
                                  String third_value  = third_input.getText() ;
                                  
                                  if(second_value.matches(regex) == true){
                                      loop_ob.setLowerBound(Integer.parseInt(second_value));
                                  }
                                  else{
                                      Dialogs.showErrorDialog(stage, "Please  enter an integer value for the lower bound of the loop", "Incorrect lower bound type !", "Error !") ;
                                      return  ;
                                  }
                                   
                                  String loop_value = "" ;
                                  
                                  if(getActiveSketch().getModuleConnectionController().isModuleConnectedFrom(sketch_controller.getSelectedModule(), "forl_module") == true){
                                      
                                     Loop loop = (Loop) getActiveSketch().getModuleConnectionController().getConnectedFromModule(sketch_controller.getSelectedModule()) ;
                                     loop_value = loop.getLoopSymbol() ;
                                  }
                                  
                                  if(third_value.matches(regex) == true || third_value.equals(loop_value)){
                                      loop_ob.setUpperBound(third_value);
                                  }
                                  else{
                                      Dialogs.showErrorDialog(stage, "Please  enter an valid value for the upper bound of the loop", "Incorrect lower bound type !", "Error !") ;
                                      return  ;
                                  }
                                  
                              }
                              else if(module_id.indexOf("cons_module") != -1 ){
                              
                                  TextField first_input = (TextField) getNodeByRowColumnIndex(1,1,variables_grid_pane) ;
                                  
                                  Constant constant_ob = (Constant) sketch_controller.getSelectedModule() ;
                                          
                                  String second_value = first_input.getText();
                                  
                                  if(second_value.matches(regex) == true){
                                      constant_ob.setValue(Integer.parseInt(second_value));
                                  }
                                  else{
                                      Dialogs.showErrorDialog(stage, "Please  enter an integer value for the constant value", "Incorrect lower bound type !", "Error !") ;
                                      return  ;
                                  }
                                  
                                  
                              }
                              else if(module_id.indexOf("wait_module") != -1){
                              
                                  TextField first_input = (TextField) getNodeByRowColumnIndex(1,1,variables_grid_pane) ;
                                  
                                  Wait wait_ob = (Wait) sketch_controller.getSelectedModule() ;
                                          
                                  String second_value = first_input.getText();
                                  
                                  if(second_value.matches(regex) == true){
                                     wait_ob.setWaitingTime(Integer.parseInt(second_value));
                                  }
                                  else{
                                      Dialogs.showErrorDialog(stage, "Please  enter an integer value for the waiting time", "Incorrect lower bound type !", "Error !") ;
                                      return  ;
                                  }
                                  
                                  
                              }
                             
                              Dialogs.showInformationDialog(stage,"", "Module variables have been updated",  "Hurray !");
                              
                          }
                          else{
                              Dialogs.showErrorDialog(stage, "Please select a module for changing the variables", "No Module Selected !", "Error !") ;
                          }
                             
                             
                       }
                    }
                }
        });
        
        
    }
    
    
    public Sketch getActiveSketch(){
        
        Sketch sketch = null ;
        
        for(int i = 0; i < this.getAllTabs().size(); i++){
                               
            if(this.getAllTabs().get(i).isSelected()){
                                   
                sketch = sketch_controller.getSketch(this.getAllTabs().get(i).getId()) ;
                break ;
                
            }
            
        }
        
        return sketch ;
    }
    
    public Tab getActiveSketchTab(){
        
        
        for(int i = 0; i < this.getAllTabs().size(); i++){
                               
            if(this.getAllTabs().get(i).isSelected()){
                                   
               return this.getAllTabs().get(i) ;
            }
            
        }
        
        return null ;
    }
    
    
    
     public Node getNodeByRowColumnIndex(final int row,final int column,GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }
     
    public Button getSketchSaveButton(){
        return this.save_project_button ;
    } 
    
    public Button getOpenProjectButton(){
        return this.open_project_button ;
    }
    
    public MenuItem getSaveAsModule(){
        return this.save_as_module ;
    }
    
    public int getTotalModules(){
        return this.image_view.size() ;
    }
    
    public void addModuleAnchor(Moduleanchor anchor){
        this.image_view.add(anchor) ;
        this.anchors.add(anchor) ;
    }
        

    public void addTab(Tab tab){
        
       this.sketch_tabs.add(tab) ;
        
        this.sketches_tab_pane.getTabs().add(tab) ;
        
    }
    
    
    public MenuItem getMenuItem(String name){
        
        for(int i = 0; i < this.main_menu_bar.getMenus().size(); i++){
            
            for(int j = 0 ; j < this.main_menu_bar.getMenus().get(i).getItems().size(); j++){
            
                if(this.main_menu_bar.getMenus().get(i).getItems().get(j).getId().indexOf(name) != -1){
                    return this.main_menu_bar.getMenus().get(i).getItems().get(j) ;
                }
            }
        }
        
        return null ;
        
    }
    
    public Button getPrintButton(){
        return this.print_code_button ;
    }
    
    
    public TabPane getBottomTabPane(){
        return this.bottom_tab_pane ;
    }
    
}
