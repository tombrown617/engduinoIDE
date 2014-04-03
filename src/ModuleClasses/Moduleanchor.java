/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import FlowControlClasses.Module;
import SketchClasses.Sketch;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author shehrozebhatti
 */
public class Moduleanchor extends ImageView  {
    
     private final ContextMenu cm ;
     
     private ArrayList<Outputmarker> output_markers = new ArrayList<Outputmarker>() ;
     
     private ArrayList<Inputmarker> input_markers = new ArrayList<Inputmarker>()  ;
     
     private boolean contextMenuSelected = false ;
     
     private Sketch sketch ;
     
     private AnchorPane sketch_anchor_pane ;
     
     private Module module ;
    
    public Moduleanchor(Image img, String mod_id,double x_coordinate, double y_coordinate, AnchorPane sketch, Sketch main_sketch,Module module){
    
       super(img) ;
       setId(mod_id + "_anchor");
       setStyle("-fx-background-color:black") ;
       
       this.module = module ;
       double new_x ;
       double new_y ;
       
       this.sketch = main_sketch;
       this.sketch_anchor_pane = sketch ;
       
       Outputmarker output_mark = new Outputmarker(new Image("graphics/output_marker.png"), x_coordinate + 79 , y_coordinate + 10 , sketch,1,this) ;
       output_mark.setId(mod_id + "_output_1");
       
       
       this.output_markers.add(output_mark) ;
       
       if(mod_id.indexOf("cond_module") != -1){
           this.addOutputMarker(3, mod_id,x_coordinate, y_coordinate);
       }
       else if(mod_id.indexOf("forl_module") != -1){
           this.addOutputMarker(2, mod_id,x_coordinate, y_coordinate);
       }
       
       
       Inputmarker first_input_marker = new Inputmarker(x_coordinate - 19, y_coordinate + 14, sketch,main_sketch,1,this) ; 
       first_input_marker.setId(mod_id + "_input");
       this.input_markers.add(first_input_marker) ;
       
       if(mod_id.indexOf("andl_module") != -1 || mod_id.indexOf("xorl_module") != -1 || mod_id.indexOf("orlo_module") != -1 || 
           mod_id.indexOf("grea_module") != -1 || mod_id.indexOf("less_module") != -1 || mod_id.indexOf("equa_module") != -1  ){
           this.addInputMarker(2, mod_id, x_coordinate, y_coordinate);
       }
       
       setX(x_coordinate ) ;
       setY(y_coordinate ) ;
       relocate(x_coordinate, y_coordinate) ;
       
       enableDrag(1,this.output_markers, this.input_markers); 
               
        cm = new ContextMenu();
        final CheckMenuItem cmItem1 = new CheckMenuItem("Connect from ->");
        cmItem1.setSelected(false);
        
        final CheckMenuItem cmItem2 = new CheckMenuItem("Connect to   <-");
        cmItem2.setSelected(false);
        
        cmItem1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue ov,
            Boolean old_val, Boolean new_val) {
                
                
                
                if(new_val == true){
                    cmItem2.setDisable(true) ;
                }
                else{
                    cmItem2.setDisable(false) ;
                }
                
            }
        });
        
        cmItem2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue ov,
            Boolean old_val, Boolean new_val) {
                //node.setVisible(new_val);
                if(new_val == true){
                    cmItem1.setDisable(true) ;
                }
                else{
                    cmItem1.setDisable(false) ;
                }   
            }
        });
        
        
        cm.getItems().add(cmItem1);
        cm.getItems().add(cmItem2);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    if (MouseButton.SECONDARY.equals(e.getButton())){
                         cm.show(getScene().getRoot(), e.getScreenX(), e.getScreenY());
                    }else{
                        cm.hide(); 
                    }  
                       
                }
        });
       
       
    }
    
    public void hideContextMenu(){
        this.cm.hide(); 
    }

    // make a node movable by dragging it around with the mouse.
    private void enableDrag(final int  count, final ArrayList<Outputmarker> out, final ArrayList<Inputmarker> input_markers) {
      final Delta dragDelta = new Delta();
      setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
         
          dragDelta.x = getX() - mouseEvent.getX();
          dragDelta.y = getY() - mouseEvent.getY();
          getScene().setCursor(Cursor.MOVE);
        }
      });
      
      setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getScene().setCursor(Cursor.HAND);
        }
      });
      
      
      setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          double newX = mouseEvent.getX() + dragDelta.x;
          setX(newX) ;
           
          double newY = mouseEvent.getY() + dragDelta.y ;
          setY(newY) ;
          
          for(int i = 0; i < out.size(); i++){
            out.get(i).updateCoordinates(newX, newY);   
          }
          
          for(int i = 0; i < input_markers.size(); i++){
            input_markers.get(i).updateCoordinates(newX, newY);   
          }
          
          
        }
      });
      
      
      setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          if (!mouseEvent.isPrimaryButtonDown()) {
            getScene().setCursor(Cursor.HAND);
          }
        }
      });
      setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          if (!mouseEvent.isPrimaryButtonDown()) {
            getScene().setCursor(Cursor.DEFAULT);
          }
        }
      });
      
      
      
    }
    
    private class Delta { double x, y; }   
    
    public void doBinding(DoubleProperty x, DoubleProperty y, boolean first){
        
        if(first == true){
            x.bind(xProperty().add(100));
            y.bind(yProperty().add(15));
        }
        else{
           x.bind(xProperty().add(10));
           y.bind(yProperty().add(50)); 
        }
        
    }
    
    public Outputmarker getMarker(int number){
        
        return this.output_markers.get(number - 1) ;
    }
    
    public Inputmarker getInputMarker(int number){
        return this.input_markers.get(number - 1) ;
    }
    
    public Module getModule(){
        return this.module ;
    }
    
    public void addInputMarker(int count, String mod_id, double x,double y){
        
        if(count == 2){
            
            Inputmarker second_input_marker = new Inputmarker(x - 19, y+ 30, this.sketch_anchor_pane, this.sketch,2,this) ;
            second_input_marker.setId(mod_id + "_output_2");
            this.input_markers.add(second_input_marker) ;
            this.sketch_anchor_pane.getChildren().add(second_input_marker) ;
            
        }
        
    }
    
    public void addOutputMarker(int count, String mod_id, double x,double y){
        
        if(count == 2){
            
            Outputmarker second_output_marker = new Outputmarker(new Image("graphics/output_marker.png"), x+ 79, y+ 28.0, this.sketch_anchor_pane,2,this) ;
            second_output_marker.setId(mod_id + "_output_2");
            this.output_markers.add(second_output_marker) ;
            this.sketch_anchor_pane.getChildren().add(second_output_marker) ;
            
            
        }
        else if(count == 3){
            
            Outputmarker second_output_marker = new Outputmarker(new Image("graphics/output_marker.png"), x+ 79, y+ 28.0, this.sketch_anchor_pane,2,this) ;
            Outputmarker third_output_marker = new Outputmarker(new Image("graphics/output_marker.png"), x+ 79, y+ 46.0, this.sketch_anchor_pane,3,this) ;
            
            second_output_marker.setId(mod_id + "_output_2");
            third_output_marker.setId(mod_id + "_output_3");
            
            this.output_markers.add(second_output_marker) ;
            this.output_markers.add(third_output_marker) ;
            
            this.sketch_anchor_pane.getChildren().add(second_output_marker) ;
            this.sketch_anchor_pane.getChildren().add(third_output_marker) ;
        
            
        
        }
        
        
    }
    
    public int getTotalInputMarkers(){
        return this.input_markers.size() ;
    }
    
    public int getTotalOutputMarkers(){
        return this.output_markers.size() ;
    }
}
