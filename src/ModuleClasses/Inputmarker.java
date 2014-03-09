/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shehrozebhatti
 */
public class Inputmarker extends ImageView {
    
    private double x_coordinate ;
    
    private double y_coordinate ;
    
    public Inputmarker(double x, double y, AnchorPane sketch){
        
        setImage(new Image("graphics/input_marker.png")) ;
        
        double new_x = x - 19 ;
        double new_y = y + 14 ;
        
        setX(new_x) ; 
        setY(new_y) ;
        relocate(new_x,new_y) ;
        
        this.x_coordinate = new_x ;
        this.y_coordinate = new_y ;
        this.enableDrag();
        
    }
    
    public void updateCoordinates(double x, double y){
        
        double new_x = x - 19 ;
        double new_y = y + 14 ;
        
        setX(new_x) ;
        setY(new_y) ;
        relocate(new_x, new_y) ;
        
        
    }
    
    private void enableDrag() {
      final Delta dragDelta = new Delta();
      setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          // record a delta distance for the drag and drop operation.
          //dragDelta.x = getCenterX() - mouseEvent.getX();
          //dragDelta.y = getCenterY() - mouseEvent.getY();
            
            
          dragDelta.x = getX() - mouseEvent.getX();
          dragDelta.y = getY() - mouseEvent.getY();
          getScene().setCursor(Cursor.OPEN_HAND);
        }
      });
      setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getScene().setCursor(Cursor.DEFAULT);
           setImage(new Image("graphics/input_marker.png")) ;
        }
      });
      
      setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          if (!mouseEvent.isPrimaryButtonDown()) {
            getScene().setCursor(Cursor.HAND);
            setImage(new Image("graphics/input_mouse_over.png")) ;
            System.out.println("On input marker ");
          }
        }
      });
      
      setOnMouseDragOver(new EventHandler<MouseEvent>(){
          
         @Override public void handle(MouseEvent mouseEvent) {
          
            getScene().setCursor(Cursor.HAND);
            setImage(new Image("graphics/input_mouse_over.png")) ;
            System.out.println("On fucking input marker ");
          
        }
          
      }) ;
      setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          if (!mouseEvent.isPrimaryButtonDown()) {
            getScene().setCursor(Cursor.DEFAULT);
             setImage(new Image("graphics/input_marker.png")) ;
          }
        }
      });
    }
    
    private class Delta { double x, y; }
    
            
}
