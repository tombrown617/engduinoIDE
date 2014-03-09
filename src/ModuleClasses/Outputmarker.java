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
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author shehrozebhatti
 */
public class Outputmarker extends ImageView {
    
    private double x_coordinate ;
    
    private double y_coordinate ;
    
    public Outputmarker(Image img, double x, double y, AnchorPane sketch){
        super(img) ;
        //setHeight(20);
        setStyle("-fx-background-color:transparent") ; 
        double new_x = x + 79 ;
        double new_y = y + 10 ;
        
        setX(new_x) ; 
        setY(new_y) ;
        relocate(new_x,new_y) ;
        
        this.x_coordinate = new_x ;
        this.y_coordinate = new_y ;
        
        CubicCurve curve = new CubicCurve();
            curve.setStartX(getX() + 5);
            curve.setStartY(getY() + 5);
            curve.setControlX1(getX() + 5);
            curve.setControlY1(getY() + 5);
            curve.setControlX2(getX() + 5);
            curve.setControlY2(getY() + 5);
            curve.setEndX(getX() + 5);
            curve.setEndY(getX() + 5);
            curve.setStroke(Color.WHITE);
            curve.setStrokeWidth(4);
            curve.setStrokeLineCap(StrokeLineCap.ROUND);
            curve.setFill(Color.TRANSPARENT);
            
            sketch.getChildren().add(curve) ;
        
        
        
        
        this.enableDrag(sketch, curve);
        
    }
    
    public void updateCoordinates(double x, double y){
        
        double new_x = x + 79 ;
        double new_y = y + 10 ;
        
        setX(new_x) ;
        setY(new_y) ;
        relocate(new_x, new_y) ;
        
        
    }
    
    private void enableDrag(final AnchorPane sketch, final CubicCurve curve) {
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
          setImage(new Image("graphics/output_marker.png")) ;
          
           
          
        }
      });
      setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          
            setImage(new Image("graphics/output_dragged.png")) ;
            System.out.println("Drag has been started ") ;
            
            
            double newX = mouseEvent.getX() + dragDelta.x;
            double newY = mouseEvent.getY() + dragDelta.y ;
            
            
            
             curve.setStartX(getX() + 5);
            curve.setStartY(getY() + 5);
            curve.setControlX1(getX() + 5);
            curve.setControlY1(getY() + 5);
            
            curve.setControlX2(newX);
            curve.setControlY2(newY);
            curve.setEndX(newX);
            curve.setEndY(newY);
            curve.setStroke(Color.DARKGREY);
            
           
          
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
            setImage(new Image("graphics/output_marker.png")) ;
          }
        }
      });
    }
    
    private class Delta { double x, y; }
    
            
}
