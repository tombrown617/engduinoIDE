/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility_classes;

import SketchClasses.Sketch;
import SketchClasses.SketchController;
import XMLFileHandling.SketchToXML;
import engduino_ide.FXMLDocumentController;
import javafx.event.EventHandler;
import javafx.scene.control.Dialogs;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.io.IOException;
import java.text.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;

/**
 *
 * @author shehrozebhatti
 */
public class printUtility extends Utility implements Printable {
    
    private final Stage stage ;    
    private final SketchToXML sketch_to_xml ;    
    static AttributedString myStyledText = null;
    
    public printUtility(final FXMLDocumentController doc_controller, final Stage stage, final SketchController sketch_controller){
        
        
        super("Print", doc_controller, sketch_controller) ;
        this.stage = stage ;
        this.sketch_to_xml = new SketchToXML(stage) ;
        
        
        doc_controller.getPrintButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
       
            public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){                            
                            
                                    checkSketchExistence() ;                     
                            }
                        }                        
                    }
                
            });
        
        
        doc_controller.getMenuItem("print_code").setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
               checkSketchExistence() ;
                
            }
        });
    }

    
    private void checkSketchExistence(){
        
        if(super.getSketchController().getTotalSketches() == 0){
               Dialogs.showErrorDialog(stage, "There are no sketches to print in the main sketch window!", "No Sketches found", "Error!") ;
         }
         else{
                                
             Sketch sketch_to_print = super.getDocumentController().getActiveSketch() ;
                                
             if(sketch_to_print.getSketchFile() != null){
                 String code = " ";
                 try {
                     code = sketch_to_print.getCodeViewController().getCode(true,false);
                 } catch (IOException ex) {
                     Logger.getLogger(printUtility.class.getName()).log(Level.SEVERE, null, ex);
                                        
                 }
                 myStyledText = new AttributedString(code); 
                 printToPrinter();           
              }                       
         }   
        
    }

    public void printToPrinter() {

        
        System.out.println("Printing ....") ;
        PrinterJob printerJob = PrinterJob.getPrinterJob(); 
        Book book = new Book();        
        book.append(this, new PageFormat());        
        printerJob.setPageable(book); 
        boolean doPrint = printerJob.printDialog(); 
        if (doPrint) {
            try {
                printerJob.print();
            } catch (PrinterException ex) {
                System.err.println("Error occurred while trying to Print: "
                        + ex);
            }
        }
    }
 

    @Override
    public int print(Graphics g, PageFormat format, int pageIndex) {
 
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.translate(format.getImageableX(), format.getImageableY());
        graphics2d.setPaint(Color.black);
        Point2D.Float pen = new Point2D.Float();
        AttributedCharacterIterator charIterator = myStyledText.getIterator();
        LineBreakMeasurer measurer = new LineBreakMeasurer(charIterator,
                graphics2d.getFontRenderContext());
        float wrappingWidth = (float) format.getImageableWidth();
        while (measurer.getPosition() < charIterator.getEndIndex()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            pen.y += layout.getAscent();
            float dx = layout.isLeftToRight() ? 0 : (wrappingWidth - layout
                    .getAdvance());
            layout.draw(graphics2d, pen.x + dx, pen.y);
            pen.y += layout.getDescent() + layout.getLeading();
        }
        return Printable.PAGE_EXISTS;
    }
 
    
}
    
   