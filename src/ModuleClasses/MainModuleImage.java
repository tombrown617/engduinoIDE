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

/**
 *
 * @author shehrozebhatti
 */
public class MainModuleImage extends ImageView {
    
    private double x_coordinate ;
    
    private double y_coordinate ;
    
    public MainModuleImage(Image img_object){
        
        super(img_object) ;
        enableDrag(this.x_coordinate, this.y_coordinate);
    }
    
    private void enableDrag(double xCo, double y ) {
      final Delta dragDelta = new Delta();
      
      double temp_x ;
      double temp_y ;
      
      
      setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          // record a delta distance for the drag and drop operation.
          //dragDelta.x = getCenterX() - mouseEvent.getX();
          //dragDelta.y = getCenterY() - mouseEvent.getY();
            
            
          //dragDelta.x = getX() - mouseEvent.getX();
          //dragDelta.y = getY() - mouseEvent.getY();
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
          double newX = mouseEvent.getX()- getScene().getWidth() / 2.0 ;
          //if (newX > 0 && newX < getScene().getWidth()) {
            
              //setLayoutX(newX) ;
              //temp_x = newX ;
             //setX(newX) ;
          //}  
          double newY = mouseEvent.getY() - 150 ;
          //if (newY > 0 && newY < getScene().getHeight()) {
            //setCenterY(newY);
              setLayoutY(newY) ;
          //}  
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
            
            
            double newX = mouseEvent.getX()- getScene().getWidth() / 2.0 ;
                setLayoutX(newX) ;
             
            double newY = mouseEvent.getY() - 150 ;
                setLayoutY(newY) ;
            
            
          }
        }
      });
    }
    
    private class Delta { double x, y; }
    
    
}
