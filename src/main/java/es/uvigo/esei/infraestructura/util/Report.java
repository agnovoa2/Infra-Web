package es.uvigo.esei.infraestructura.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.ejb.Singleton;
import javax.enterprise.inject.Default;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import es.uvigo.esei.infraestructura.entities.Petition;
import es.uvigo.esei.infraestructura.entities.PetitionRow;

@Default
@Singleton
public class Report {
	
	private Document document;
	private PdfWriter writer;
	private PdfContentByte canvas;
	private PdfContentByte cb;
	private BaseFont bf;
	
	public void doSolicitudePDF(Petition petition) throws DocumentException, MalformedURLException, IOException {
		doTemplate(true,petition);
		document.close();
	}
	
	public void doRetrievePDF(Petition petition) throws DocumentException, MalformedURLException, IOException {
		doTemplate(false,petition);
		document.close();
	}
	
	public void doTemplate(boolean solicitude, Petition petition) throws DocumentException, MalformedURLException, IOException{
		document = new Document();
		document.setMargins(0, 0, 0, 0);
		System.out.println(document.getPageSize().getHeight());
		System.out.println(document.getPageSize().getWidth());
		writer = PdfWriter.getInstance(document, new FileOutputStream("p.pdf"));
        document.open();
        canvas = writer.getDirectContent();
        
        //Setting logo
        Image image = Image.getInstance(Report.class.getResource("img/logo.jpg").toString());
        image.setAbsolutePosition(62, 752);
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());
        document.add(image);

        //Setting logo lines
        Rectangle rec = new Rectangle(24, 767, 53, 768);
        rec.setBorderWidth(0);
        rec.setBackgroundColor(BaseColor.BLACK);
        rec.setBorder(Rectangle.BOX);
        canvas.rectangle(rec);
        rec = new Rectangle(24, 762, 53, 763);
        rec.setBorderWidth(0);
        rec.setBackgroundColor(BaseColor.BLACK);
        rec.setBorder(Rectangle.BOX);
        canvas.rectangle(rec);
        rec = new Rectangle(125, 767, 563, 768);
        rec.setBorderWidth(0);
        rec.setBackgroundColor(BaseColor.BLACK);
        rec.setBorder(Rectangle.BOX);
        canvas.rectangle(rec);
        rec = new Rectangle(125, 762, 563, 763);
        rec.setBorderWidth(0);
        rec.setBackgroundColor(BaseColor.BLACK);
        rec.setBorder(Rectangle.BOX);
        canvas.rectangle(rec);
        
        //Setting under logo text
        cb = writer.getDirectContent();
        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.setFontAndSize(bf, 12);
        cb.beginText();
        cb.setTextMatrix(434, 742);
        cb.showText("Escola Superior de");
        cb.setTextMatrix(418, 728);
        cb.showText("Enxeñaría Informática");
        cb.endText();

        //Report tittle rectangle
        rec = new Rectangle(93, 659, 503, 674);
        rec.setBorderWidth(0);
        rec.setBackgroundColor(BaseColor.BLACK);
        rec.setBorder(Rectangle.BOX);
        canvas.rectangle(rec);
        
        //Report tittle text
        cb.beginText();
        cb.setTextMatrix(185, 663);
        cb.setColorFill(BaseColor.WHITE);
        if(solicitude)
        	cb.showText("Solicitude de consumible para impresora");
        else
        	cb.showText("Recollida de consumible para impresora");
        cb.endText();
        
        //Solicitude table
        rec = new Rectangle(94, 599, 501, 619);
        rec.setBackgroundColor(BaseColor.GRAY);
        rec.setBorder(Rectangle.BOX);
        rec.setBorderWidth(1);
        canvas.rectangle(rec);
        rec = new Rectangle(94, 581, 501, 599);
        rec.setBackgroundColor(BaseColor.WHITE);
        rec.setBorder(Rectangle.BOX);
        rec.setBorderWidth(1);
        canvas.rectangle(rec);
        rec = new Rectangle(94, 563, 501, 581);
        rec.setBackgroundColor(BaseColor.WHITE);
        rec.setBorder(Rectangle.BOX);
        rec.setBorderWidth(1);
        canvas.rectangle(rec);
        rec = new Rectangle(94, 544, 501, 563);
        rec.setBackgroundColor(BaseColor.WHITE);
        rec.setBorder(Rectangle.BOX);
        rec.setBorderWidth(1);
        canvas.rectangle(rec);
        rec = new Rectangle(372, 544, 501, 563);
        rec.setBackgroundColor(BaseColor.WHITE);
        rec.setBorder(Rectangle.BOX);
        rec.setBorderWidth(1);
        canvas.rectangle(rec);
        
        //Fill table data
        cb.beginText();
        cb.setTextMatrix(104, 605);
        cb.setColorFill(BaseColor.BLACK);
        cb.showText("Datos da impresora");
        cb.setTextMatrix(104, 585);
        cb.setColorFill(BaseColor.BLACK);
        cb.showText("Modelo: " + petition.getPrinter().getModel().getModelName());
        cb.setTextMatrix(104, 567);
        cb.setColorFill(BaseColor.BLACK);
        cb.showText("Ubicación: " + petition.getPrinter().getUbication());
        cb.setTextMatrix(104, 549);
        cb.setColorFill(BaseColor.BLACK);
        cb.showText("Solicitante: " + petition.getUser().getName() + " " + petition.getUser().getFirstSurname() + " " + petition.getUser().getSecondSurname());
        cb.setTextMatrix(378, 549);
        cb.setColorFill(BaseColor.BLACK);
        cb.showText("Inventario: " + petition.getPrinter().getInventoryNumber());
        cb.endText();
        
        //Solicitude consumables table
        rec = new Rectangle(94, 453, 501, 473);
        rec.setBackgroundColor(BaseColor.GRAY);
        rec.setBorder(Rectangle.BOX);
        rec.setBorderWidth(1);
        canvas.rectangle(rec);
        rec = new Rectangle(94, 431 - petition.getPetitionRows().size() * 14, 501, 453);
        rec.setBackgroundColor(BaseColor.WHITE);
        rec.setBorder(Rectangle.BOX);
        rec.setBorderWidth(1);
        canvas.rectangle(rec);
        
        //Fill table data
        cb.beginText();
        cb.setTextMatrix(104, 459);
        cb.setColorFill(BaseColor.BLACK);
        cb.showText("Datos da solicitude con data " + petition.getPetitionDate());
        cb.setTextMatrix(104, 437);
        cb.setColorFill(BaseColor.BLACK);
        cb.showText("Petición:");
        int i = 0;
        for (PetitionRow petitionRow : petition.getPetitionRows()) {
        	cb.setTextMatrix(112, 424 - 14 * i);
            cb.setColorFill(BaseColor.BLACK);
            cb.showText(petitionRow.getConsumable().toString() + " Cantidad: " + petitionRow.getQuantity());
            i++;
		}
        cb.endText();
	}
}
