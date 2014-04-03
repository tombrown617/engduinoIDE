/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ModuleClasses;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
    
    private CubicCurve curve ;
    
    private int number ;
    
    private Moduleanchor module_anchor ;
    
    public Outputmarker(Image img, double x, double y, AnchorPane sketch, int number, Moduleanchor module_anchor){
        
        super(img) ;
        this.number = number ;
        setStyle("-fx-background-color:transparent") ; 
        
        setX(x) ; 
        setY(y) ;
        relocate(x,y) ;
        
        this.module_anchor = module_anchor ;
        
        this.x_coordinate = x ;
        this.y_coordinate = y ;
        
        curve = new CubicCurve();
        curve.setStroke(Color.BLACK);
        curve.setStrokeWidth(2);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        curve.setFill(Color.TRANSPARENT);
        curve.toFront();
        curve.setId(getId() + "_curve");
        sketch.getChildren().add(curve) ;
        
        this.enableDrag(sketch, curve);
        
    }
    
    public void updateCoordinates(double x, double y){
        
        double new_x = x + 79 ;
        double new_y = 0 ; 
        
        if(number == 1){
             new_y = y + 10 ;
        }
        else if(number == 2){
            new_y = y + 28 ;
        }
        else if(number == 3){
            new_y = y + 46 ;
        }
        
        setX(new_x) ;
        setY(new_y) ;
        relocate(new_x, new_y) ;
        
        
    }
    
    private void enableDrag(final AnchorPane sketch, final CubicCurve curve) {
      final Delta dragDelta = new Delta();
      setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          
          dragDelta.x = getX() - mouseEvent.getX();
          dragDelta.y = getY() - mouseEvent.getY();
          getScene().setCursor(Cursor.OPEN_HAND);
        }
      });
      
      setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getScene().setCursor(Cursor.DEFAULT);
          setImage(new Image("graphics/output_marker.png")) ;
          
          
          
          curve.setStartX(getX() + 5);
          curve.setStartY(getY() + 5);
          curve.setControlX1(getX() + 5);
          curve.setControlY1(getY() + 5);
          
          curve.setControlX2(getX() + 5);
          curve.setControlY2(getY() + 5);
          curve.setEndX(getX() + 5);
          curve.setEndY(getY() + 5);
          
        }
      });
      
     setOnDragDetected(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent e) {
            
            //Dragboard db = startDragAndDrop(TransferMode.ANY);
            Clipboard db = Clipboard.getSystemClipboard();
            ClipboardContent new_content = new ClipboardContent() ;
            new_content.putString(getId()) ;
            
            db.setContent(new_content) ;
            startFullDrag();
            
            e.setDragDetect(true );
            e.consume();
        }
    });
      
      setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          
            setImage(new Image("graphics/output_dragged.png")) ;
            mouseEvent.setDragDetect(true);
            double newX = mouseEvent.getX() + dragDelta.x ;
            double newY = mouseEvent.getY() + dragDelta.y ;
            
            curve.setStartX(getX() + 5);
            curve.setStartY(getY() + 5);
            curve.setControlX1(getX() + 5);
            curve.setControlY1(getY() + 5);
            
            curve.setControlX2(newX);
            curve.setControlY2(newY);
            curve.setEndX(newX);
            curve.setEndY(newY);
            curve.setStroke(Color.BLACK);
            curve.toFront();
            //mouseEvent.setDragDetect(true);
            
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
    
    public void removeTempCurve(){
        this.curve = null ;
        this.curve.setVisible(false);
    }    
    
     public void doBinding(DoubleProperty x, DoubleProperty y, boolean first){
        
        x.bind(xProperty().add(15));
        y.bind(yProperty().add(10)); 
        
    }
     
    public CubicCurve getCurve(){
        return this.curve ;
    } 
    
    public Moduleanchor getModuleAnchor(){
        return this.module_anchor ;
    }
    
    public int getPort(){
        return this.number ;
    }
}
