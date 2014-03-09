/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

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
     
     private Outputmarker output_marker ;
     
     private Inputmarker input_marker ;
     
     private boolean contextMenuSelected = false ;
    
     /*public Moduleanchor(Color color, DoubleProperty x, DoubleProperty y) {
      super(x.get(), y.get(), 10);
      System.out.println("anchor made x = " + x.get() + " y = " + y.get());
      setFill(color.deriveColor(1, 1, 1, 0.5));
      setStroke(color);
      setStrokeWidth(2);
      setStrokeType(StrokeType.OUTSIDE);

      x.bind(centerXProperty());
      y.bind(centerYProperty());
      enableDrag();
    }
     */
    public Moduleanchor(Image img, int number,DoubleProperty x, DoubleProperty y, boolean start){
    
       super(img) ;
       setId("new_module_testing_" + number);
       
       double new_x ;
       double new_y ;
       
       x.bind(xProperty());
       y.bind(yProperty());
       
       if(start == true){
           new_x = x.get() - 90 ;
           new_y = y.get() - 20 ;
       }
       else{
           new_x = x.get() - 20 ;
           new_y = y.get() - 20 ;
       }
       
       relocate(new_x, new_y) ;
       //setX(new_x) ;
       //setY(new_y) ;
       
       //
       
        
       //enableDrag();
       
        cm = new ContextMenu();
        MenuItem cmItem1 = new MenuItem("Connect from ->");
        MenuItem cmItem2 = new MenuItem("Connect to <-");
        
        cmItem1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                //content.putImage(pic.getImage());
                clipboard.setContent(content);
            }
        });

        cm.getItems().add(cmItem1);
        cm.getItems().add(cmItem2);
        
        addEventHandler(MouseEvent.MOUSE_CLICKED,
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
    
    public Moduleanchor(Image img, int number,double x_coordinate, double y_coordinate, AnchorPane sketch){
    
       super(img) ;
       setId("new_module_" + number);
       setStyle("-fx-background-color:black") ;
       
       double new_x ;
       double new_y ;
       
       //x.bind(xProperty());
       //y.bind(yProperty());
       
       this.output_marker = new Outputmarker(new Image("graphics/output_marker.png"), x_coordinate, y_coordinate, sketch) ;
       this.input_marker = new Inputmarker(x_coordinate, y_coordinate, sketch) ; 
       
       setX(x_coordinate ) ;
       setY(y_coordinate ) ;
       relocate(x_coordinate, y_coordinate) ;
       
       enableDrag(this.output_marker, this.input_marker); 
               
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
    private void enableDrag(final Outputmarker out, final Inputmarker input_marker) {
      final Delta dragDelta = new Delta();
      setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          // record a delta distance for the drag and drop operation.
          //dragDelta.x = getCenterX() - mouseEvent.getX();
          //dragDelta.y = getCenterY() - mouseEvent.getY();
            
            
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
          
          out.updateCoordinates(newX, newY);
          input_marker.updateCoordinates(newX, newY);
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
    
    public Outputmarker getMarker(){
        return this.output_marker ;
    }
    
    public Inputmarker getInputMarker(){
        return this.input_marker ;
    }
}
