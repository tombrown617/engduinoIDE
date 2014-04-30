/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engduino_ide;

import ModuleClasses.Inputmarker;
import ModuleClasses.MainInputMarker;
import ModuleClasses.MainOutputMarker;
import ModuleClasses.Moduleanchor;
import ModuleClasses.Outputmarker;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;


public class Beziercurve {
    
  private Outputmarker starting_anchor ;
  
  private MainInputMarker main_input ;
  
  private MainOutputMarker main_output ;
  
  private Inputmarker end_anchor;
  
  private CubicCurve curve ;
  
  private int startX ;
  
  private int startY ;
  
  private int endX ;
  
  private int endY ;
    
  public Beziercurve(CubicCurve curve, Outputmarker output_marker, Inputmarker input_marker){
      
      this.startX = (int) output_marker.getX( );
      this.startY = (int) output_marker.getY( );
      this.endX = (int) input_marker.getX( );
      this.endY = (int) input_marker.getY( );
      
      this.curve = curve ;
      this.starting_anchor = output_marker ;
      this.end_anchor = input_marker ;
      
      this.starting_anchor.doBinding(this.curve.startXProperty(), this.curve.startYProperty(), false);
      this.end_anchor.doBinding(this.curve.endXProperty(), this.curve.endYProperty(), false);
  }
  
  public Beziercurve(AnchorPane anchor_pane, Outputmarker output_marker, Inputmarker input_marker){
      
      
      
      this.startX = (int) output_marker.getX( );
      this.startY = (int) output_marker.getY( );
      this.endX = (int) input_marker.getX( );
      this.endY = (int) input_marker.getY( );
      
      this.curve = this.createStartingCurve() ;
      this.starting_anchor = output_marker ;
      this.end_anchor = input_marker ;
      
      anchor_pane.getChildren().add(curve) ;
      
      this.starting_anchor.doBinding(this.curve.startXProperty(), this.curve.startYProperty(), false);
      this.end_anchor.doBinding(this.curve.endXProperty(), this.curve.endYProperty(), false);
  }
  
  public Beziercurve(AnchorPane anchor_pane, MainInputMarker output_marker, Inputmarker input_marker){
      
      this.startX = (int) output_marker.getX( );
      this.startY = (int) output_marker.getY( );
      this.endX = (int) input_marker.getX( );
      this.endY = (int) input_marker.getY( );
      
      this.curve = this.createStartingCurve() ;
      this.main_input = output_marker ;
      this.end_anchor = input_marker ;
      
      anchor_pane.getChildren().add(curve) ;
      
      this.main_input.doBinding(this.curve.startXProperty(), this.curve.startYProperty(), false);
      this.end_anchor.doBinding(this.curve.endXProperty(), this.curve.endYProperty(), false);
  }
  
  public Beziercurve(AnchorPane anchor_pane, Outputmarker output_marker, MainOutputMarker main_output){
      
      this.startX = (int) output_marker.getX( );
      this.startY = (int) output_marker.getY( );
      this.endX = (int) main_output.getX( );
      this.endY = (int) main_output.getY( );
      
      this.curve = this.createStartingCurve() ;
      this.starting_anchor = output_marker ;
      this.main_output = main_output ;
      
      anchor_pane.getChildren().add(curve) ;
      
      this.starting_anchor.doBinding(this.curve.startXProperty(), this.curve.startYProperty(), false);
      this.main_output.doBinding(this.curve.endXProperty(), this.curve.endYProperty(), false);
  }
  
  private CubicCurve createStartingCurve() {
    
      
    CubicCurve curve = new CubicCurve();
    curve.setStartX(this.startX );
    curve.setStartY(this.startY);
    
    
    curve.setControlX1(this.startX);
    curve.setControlY1(this.startY);
    curve.setControlX2(this.endX);
    curve.setControlY2(this.endY);
    
    curve.setEndX(this.endX);
    curve.setEndY(this.endY);
    curve.setStroke(Color.DARKGREY);
    curve.setStrokeWidth(2);
    curve.setStrokeLineCap(StrokeLineCap.ROUND);
    curve.setFill(Color.TRANSPARENT);
    
    curve.setStroke(Color.BLACK);
    curve.toFront();
    
    return curve;
  }
  
  private void calculateControlX1(){
      
      if((this.startX - this.endX) < 10){
          
      }
      
  }
  
 
  
  public CubicCurve getCurve(){
      return this.curve ;
  }
  
  public Outputmarker getStartingAnchor(){
      return this.starting_anchor ;
  }
  
  public Inputmarker getEndingAnchor(){
      return this.end_anchor;
  }
  
  
    
}
