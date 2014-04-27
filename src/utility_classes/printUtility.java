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
import java.util.ArrayList;
import java.util.ListIterator;
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
    public AttributedString myStyledText;
    
    private boolean firstcall;
    private int pagecount;
    private int fontsize;
    private String title;
    private double fontascent;
    private double fontheight;
    private Font font;
    private Font hfFont;
    private java.util.List<String> lines;
    private int charsPerLine;
    private int linesPerPage;
    private AffineTransform paf;

    public printUtility(final FXMLDocumentController doc_controller, final Stage stage, final SketchController sketch_controller){
        
        
        super("Print", doc_controller, sketch_controller) ;
        this.stage = stage ;
        this.sketch_to_xml = new SketchToXML(stage) ;
        
        
        doc_controller.getPrintButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
       
            public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){                            
                                    System.out.println("Hello");
                                    checkSketchExistence() ;                     
                            }
                        }                        
                    }
                
            });
        
        
        doc_controller.getMenuItem("print_code").setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
               checkSketchExistence() ;
               System.out.println("Hello");
            }
        });
    }

    
    private void checkSketchExistence(){
        
        if(super.getSketchController().getTotalSketches() == 0){
               Dialogs.showErrorDialog(stage, "There are no sketches to print in the main sketch window!", "No Sketches found", "Error!") ;
         }
         else{
                                
             Sketch sketch_to_print = super.getDocumentController().getActiveSketch() ;
                                
             
                 String code = " ";
                 
                 try {
                     code = sketch_to_print.getCodeViewController().getCode(true,false);
                 } catch (IOException ex) {
                     Logger.getLogger(printUtility.class.getName()).log(Level.SEVERE, null, ex);
                                        
                 }
                ArrayList<String> codeList = new ArrayList();
                String[] splitString = code.split("\n");
                for(String line : splitString){
                    codeList.add(line);
                }
                lines = codeList;
                 myStyledText = new AttributedString(code); 
                 title = "Engduino Sketch";
                 fontsize = 11;
                 firstcall = true;
                 pagecount = 0;
                charsPerLine = 68; // default
                linesPerPage = 50; // default
                font = null;
                paf = null;
                 printToPrinter();           
                    
         }   
        
    }

    private void adjustLines() {
            ListIterator<String> it;
            String line;
            int pos;

            it = lines.listIterator();
            while(it.hasNext()) {
                line = it.next();
                if (line.length() > charsPerLine) {
                    it.remove();
                    for(pos = 0; pos < line.length(); pos += charsPerLine) {
                        if (pos+charsPerLine > line.length()) {
                            it.add(line.substring(pos));
                        } else {
                            it.add(line.substring(pos, pos+charsPerLine));
                        }
                    }
                }
            }
        }
    
    public int getPageCount() {
            return pagecount;
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
    public int print(Graphics g,PageFormat pageFormat,int pageIndex) {
            Graphics2D g2 = (Graphics2D)g;

            if (firstcall) {
                firstcall = false;

	
		paf = new AffineTransform(pageFormat.getMatrix());
		g2.transform(paf);
		
             
                font = new Font("Monospaced", Font.PLAIN, fontsize);
                hfFont = new Font("Monospaced", Font.BOLD, fontsize);
                g2.setFont(font);

                double cw, lh, pw, ph;
                LineMetrics lm;
                Rectangle2D rx, sb;
		FontRenderContext frc = g2.getFontRenderContext();
		
                rx = font.getMaxCharBounds(frc);
                sb = font.getStringBounds("M", frc);
                cw = sb.getWidth();
                lh = rx.getHeight() ;
                fontascent = sb.getHeight();
                fontheight = lh;

                ph = pageFormat.getImageableHeight();
                pw = pageFormat.getImageableWidth();

                charsPerLine = (int)(pw / cw);
                linesPerPage = (int)(ph / lh) - 2;
                
                
                adjustLines();
            }
	    else {
		g2.transform(paf);
	    }

            if (pageIndex > (lines.size() / linesPerPage)) {
                return NO_SUCH_PAGE;
            }

            // do the printing       
            int lpos = pageIndex * linesPerPage;
            int lim = (pageIndex + 1) * linesPerPage;
            int i;
            String line;
	    String heading;
            g2.translate(pageFormat.getImageableX(), 
                         pageFormat.getImageableY());
	    heading = "Page " + (pageIndex+1) + " - " + title;
	    g2.setFont(hfFont);
	    g2.drawString(heading, 0, (int)fontascent);
	    g2.setFont(font);
            for(i = 2; lpos < lim && lpos < lines.size(); i++, lpos++) {
                line = lines.get(lpos);
                g2.drawString(line, 0, (int)((i * fontheight) + fontascent));
            }
            if (pagecount < (pageIndex + 1)) pagecount= pageIndex+1;
            return PAGE_EXISTS;
        }
  
 
    
}
    
   