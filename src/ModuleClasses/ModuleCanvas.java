/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas ;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author shehrozebhatti
 */
public class ModuleCanvas extends Canvas {
    
    public ModuleCanvas(double x, double y){
        
        super(100.0,150.0)  ;
        GraphicsContext gc = getGraphicsContext2D();
        
        Image center_item = new Image("http://i.imgur.com/CcrmxZ0.png") ;
        Image right_output = new Image("http://i.imgur.com/AdRuyOC.png") ;
        
        MainModuleImage data = new MainModuleImage(center_item) ;
        
        
        data.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
              
              getScene().setCursor(Cursor.MOVE);
            }
        });
        
        data.setOnMouseDragged(new EventHandler<MouseEvent>() {
            
            @Override public void handle(MouseEvent mouseEvent) {
              
              double newX = mouseEvent.getX()- getScene().getWidth() / 2.0 ;
             
              double newY = mouseEvent.getY() - 150 ;
              
              System.out.println("The new X is " + newX + "\nThe new y is " + newY) ;
               
            }
        });
        
        getStyleClass().add("testing_color") ;
        gc.drawImage(data.getImage(), 0, 0);
        //gc.drawImage(right_output, 90, 10);
        
        setLayoutX(x ) ;
        setLayoutY(y ) ;
        relocate(x, y) ;
        
        
        enableDrag(); 
        
    }

   
    private void enableDrag() {
      final Delta dragDelta = new Delta();
      setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          // record a delta distance for the drag and drop operation.
          //dragDelta.x = getCenterX() - mouseEvent.getX();
          //dragDelta.y = getCenterY() - mouseEvent.getY();
            
            
          dragDelta.x = getLayoutX() - mouseEvent.getX();
          dragDelta.y = getLayoutY() - mouseEvent.getY();
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
          setLayoutX(newX) ;
           
          double newY = mouseEvent.getY() + dragDelta.y ;
          setLayoutY(newY) ;
          
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
    
    
    
    
}

