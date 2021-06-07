package sample;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;


public class PdfMaker{

    private static DbHandler dbHandler;
    private static Connection conn;


    public static void makeCertificate(ActionEvent actionEvent,Member member,int dose) throws Exception {
        if (dose == 1) {
            String dest = "C:\\Users\\dell\\Desktop\\addingImage.pdf";
            try {
                Node source = (Node) actionEvent.getSource();
                Window stage = source.getScene().getWindow();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save file");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF doc(*.pdf)", "*.pdf"));
                fileChooser.setInitialFileName(member.getName() + " Dose1.pdf");
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    dest = file.getAbsolutePath();
                }
            } catch (Exception ignored) {
            }
            // Creating a PdfWriter
            PdfWriter writer = new PdfWriter(dest);

            // Creating a PdfDocument
            PdfDocument pdf = new PdfDocument(writer);

            // Creating a Document
            Document document = new Document(pdf);

            // Creating an ImageData object
            String imFile = "C:\\Users\\dell\\IdeaProjects\\Vaccine_CodeForCovid\\src\\images\\logo.png";
            ImageData data = ImageDataFactory.create(imFile);

            // Creating an Image object
            Image image = new Image(data);
            image.setFixedPosition(230, 730);
            image.setWidth((float) (112));
            image.setHeight((float) (80));


            // Adding image to the document
            document.add(image);

            System.out.println("Image added");

            String Head1 = "\n\n\n\nProvisional Certificate for COVID-19 Vaccination - 1st Dose";
            String Head2 = "Beneficiary Details";
            String Head3 = "Vaccination Details";

            //Beneficiary Details parameters
            String Beneficiary_Name = "Beneficiary Name:                                     ";
            String Age = "Age:                                                            ";
            String gender = "Gender:                                                       ";
            String ID_verified = "ID_verified:                                                ";
            String Unique_Health_ID = "Unique_Health_ID:                                    ";
            String Beneficiary_Refference_ID = "Beneficiary_Refference_ID:                      ";

            //Vaccination Details parameters
            String Vaccine_Name = "Vaccine_Name:                                         ";
            String Date_of_Dose = "Date_of_Dose:                                          ";
            String Next_Due_Date = "Next_Due_Date:                                       ";
            String Vaccinated_By = "Vaccinated_By:                                        "; //doc
            String Vaccinated_at = "Vaccinated_at:                                          "; //which centre


//        String para2 = "The journey commenced with a single tutorial on HTML in 2006";

            // Creating Paragraphs
            Paragraph paragraph1 = new Paragraph(Head1);
            Paragraph paragraph2 = new Paragraph(Head2);
            Paragraph paragraph3 = new Paragraph(Beneficiary_Name);
            Paragraph paragraph4 = new Paragraph(Age);
            Paragraph paragraph5 = new Paragraph(gender);
            Paragraph paragraph6 = new Paragraph(ID_verified);
            Paragraph paragraph7 = new Paragraph(Unique_Health_ID);
            Paragraph paragraph8 = new Paragraph(Beneficiary_Refference_ID);
            Paragraph paragraph9 = new Paragraph(Vaccine_Name);
            Paragraph paragraph10 = new Paragraph(Date_of_Dose);
            Paragraph paragraph11 = new Paragraph(Next_Due_Date);
            Paragraph paragraph12 = new Paragraph(Vaccinated_By);
            Paragraph paragraph13 = new Paragraph(Vaccinated_at);
            Paragraph paragraph14 = new Paragraph(Head3);
//        Paragraph paragraph2 = new Paragraph(para2);
            // Creating text object
            Text BenfName = new Text(member.getName());
            Text ag = new Text(Integer.toString(member.getAge()));
            Text gndr = new Text("");
            if (member.getGender() == 1) {
                gndr = new Text("Male");
            } else {
                gndr = new Text("Female");
            }
            Text idVerf = new Text("ID verified");
            Text uniqHealthID = new Text(member.getAadhaarNumber() + "-" + member.getRefID());
            Text BenfReffID = new Text(Integer.toString(member.getRefID()));
            Text VaccnName = new Text(member.getDose1Name());
            Text DateDose = new Text(member.getDose1date().substring(3, 5) + " " + member.getDose1date().substring(0, 3) + " 2021");
            Text nextDose = new Text("29 Aug 2021");
            Text vaccnBy = new Text("Dr Arun");
            String query = "SELECT * FROM vaccinecentres WHERE centreID = " + member.getDose1CentreID() + " ;";
            dbHandler = new DbHandler();
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);
            String vaccinationCentre = null;
            while (set.next()) {
                vaccinationCentre = set.getString("Address") + ", " + set.getString("State");
            }
            Text vaccnAt = new Text(vaccinationCentre);


            // Setting font color
            Style normal = new Style();
            PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
            normal.setFont(font).setFontSize(13);

            Style subhead = new Style();
            PdfFont medium = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
            subhead.setFont(medium).setFontSize(16);
            subhead.setFont(medium).setUnderline();


            Style code = new Style();
            PdfFont monospace = PdfFontFactory.createFont(FontConstants.COURIER);
            code.setFont(monospace);

            Style Header = new Style();
            PdfFont Bigger = PdfFontFactory.createFont(FontConstants.TIMES_BOLDITALIC);
            Header.setFont(Bigger).setFontSize(18);
            Header.setFont(Bigger).setUnderline();
            Header.setFont(Bigger);

            paragraph1.addStyle(Header);
            paragraph2.addStyle(subhead);
            paragraph14.addStyle(subhead);
            paragraph1.setTextAlignment(TextAlignment.CENTER);
            paragraph3.add(BenfName).addStyle(normal);
            paragraph4.add(ag).addStyle(normal);
            paragraph5.add(gndr).addStyle(normal);
            paragraph6.add(idVerf).addStyle(normal);
            paragraph7.add(uniqHealthID).addStyle(normal);
            paragraph8.add(BenfReffID).addStyle(normal);
            paragraph9.add(VaccnName).addStyle(normal);
            paragraph10.add(DateDose).addStyle(normal);
            paragraph11.add(nextDose).addStyle(normal);
            paragraph12.add(vaccnBy).addStyle(normal);
            paragraph13.add(vaccnAt).addStyle(normal);

            imFile = "C:\\Users\\dell\\IdeaProjects\\Vaccine_CodeForCovid\\src\\images\\modi.PNG";
            data = ImageDataFactory.create(imFile);

            // Creating an Image object
            image = new Image(data);
            image.setHeight((float) ((float) 541.0 / 2));
            image.setWidth((float) ((float) 1239.0 / 2));
            image.setFixedPosition(0, 0);

            document.add(image);

//        Paragraph p = new Paragraph();
//        p.add(new Text("The Strange Case of ").addStyle(normal));
//        p.add(new Text("Dr. Jekyll").addStyle(code));
//        p.add(new Text(" and ").addStyle(normal));
//        p.add(new Text("Mr. Hyde").addStyle(code));
//        p.add(new Text(".").addStyle(normal));
//        document.add(p);
//        paragraph1.setFontColor();


            // Adding paragraphs to document
            document.add(paragraph1);
            document.add(new Paragraph("\n"));
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph6);
            document.add(paragraph7);
            document.add(paragraph8);
            document.add(new Paragraph("\n"));
            document.add(paragraph14);
            document.add(paragraph9);
            document.add(paragraph10);
            document.add(paragraph11);
            document.add(paragraph12);
            document.add(paragraph13);

//        document.add(paragraph2);

            // Closing the document
//        font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
//        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
//        Text title = new Text("The Strange Case of Dr. Jekyll and Mr. Hyde").setFont(bold);
//        Text author = new Text("Robert Louis Stevenson").setFont(font);
//        Paragraph p2 = new Paragraph().add(title).add(" by ").add(author);
//        document.add(p2);
            document.close();
            System.out.println("Paragraph added");

        }else if(dose==2){
            String dest = "C:\\Users\\dell\\Desktop\\addingImage.pdf";
            try {
                Node source = (Node) actionEvent.getSource();
                Window stage = source.getScene().getWindow();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save file");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF doc(*.pdf)", "*.pdf"));
                fileChooser.setInitialFileName(member.getName() + " Dose2.pdf");
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    dest = file.getAbsolutePath();
                }
            } catch (Exception ignored) {
            }
            // Creating a PdfWriter
            PdfWriter writer = new PdfWriter(dest);

            // Creating a PdfDocument
            PdfDocument pdf = new PdfDocument(writer);

            // Creating a Document
            Document document = new Document(pdf);

            // Creating an ImageData object
            String imFile = "C:\\Users\\dell\\IdeaProjects\\Vaccine_CodeForCovid\\src\\images\\logo.png";
            ImageData data = ImageDataFactory.create(imFile);

            // Creating an Image object
            Image image = new Image(data);
            image.setFixedPosition(230, 730);
            image.setWidth((float) (112));
            image.setHeight((float) (80));


            // Adding image to the document
            document.add(image);

            System.out.println("Image added");

            String Head1 = "\n\n\n\nProvisional Certificate for COVID-19 Vaccination - 2nd Dose";
            String Head2 = "Beneficiary Details";
            String Head3 = "Vaccination Details";

            //Beneficiary Details parameters
            String Beneficiary_Name = "Beneficiary Name:                                     ";
            String Age = "Age:                                                            ";
            String gender = "Gender:                                                       ";
            String ID_verified = "ID_verified:                                                ";
            String Unique_Health_ID = "Unique_Health_ID:                                    ";
            String Beneficiary_Refference_ID = "Beneficiary_Refference_ID:                      ";

            //Vaccination Details parameters
            String Vaccine_Name = "Vaccine_Name:                                         ";
            String Date_of_Dose = "Date_of_Dose:                                          ";
            String Next_Due_Date = "Next_Due_Date:                                       ";
            String Vaccinated_By = "Vaccinated_By:                                        "; //doc
            String Vaccinated_at = "Vaccinated_at:                                          "; //which centre


//        String para2 = "The journey commenced with a single tutorial on HTML in 2006";

            // Creating Paragraphs
            Paragraph paragraph1 = new Paragraph(Head1);
            Paragraph paragraph2 = new Paragraph(Head2);
            Paragraph paragraph3 = new Paragraph(Beneficiary_Name);
            Paragraph paragraph4 = new Paragraph(Age);
            Paragraph paragraph5 = new Paragraph(gender);
            Paragraph paragraph6 = new Paragraph(ID_verified);
            Paragraph paragraph7 = new Paragraph(Unique_Health_ID);
            Paragraph paragraph8 = new Paragraph(Beneficiary_Refference_ID);
            Paragraph paragraph9 = new Paragraph(Vaccine_Name);
            Paragraph paragraph10 = new Paragraph(Date_of_Dose);
            Paragraph paragraph11 = new Paragraph(Next_Due_Date);
            Paragraph paragraph12 = new Paragraph(Vaccinated_By);
            Paragraph paragraph13 = new Paragraph(Vaccinated_at);
            Paragraph paragraph14 = new Paragraph(Head3);
//        Paragraph paragraph2 = new Paragraph(para2);
            // Creating text object
            Text BenfName = new Text(member.getName());
            Text ag = new Text(Integer.toString(member.getAge()));
            Text gndr = new Text("");
            if (member.getGender() == 1) {
                gndr = new Text("Male");
            } else {
                gndr = new Text("Female");
            }
            Text idVerf = new Text("ID verified");
            Text uniqHealthID = new Text(member.getAadhaarNumber() + "-" + member.getRefID());
            Text BenfReffID = new Text(Integer.toString(member.getRefID()));
            Text VaccnName = new Text(member.getDose2Name());
            Text DateDose = new Text(member.getDose2date().substring(3, 5) + " " + member.getDose2date().substring(0, 3) + " 2021");
            Text nextDose = new Text("29 Aug 2021");
            Text vaccnBy = new Text("Dr Arun");
            String query = "SELECT * FROM vaccinecentres WHERE centreID = " + member.getDose2CentreID() + " ;";
            dbHandler = new DbHandler();
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);
            String vaccinationCentre = null;
            while (set.next()) {
                vaccinationCentre = set.getString("Address") + ", " + set.getString("State");
            }
            Text vaccnAt = new Text(vaccinationCentre);


            // Setting font color
            Style normal = new Style();
            PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
            normal.setFont(font).setFontSize(13);

            Style subhead = new Style();
            PdfFont medium = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
            subhead.setFont(medium).setFontSize(16);
            subhead.setFont(medium).setUnderline();


            Style code = new Style();
            PdfFont monospace = PdfFontFactory.createFont(FontConstants.COURIER);
            code.setFont(monospace);

            Style Header = new Style();
            PdfFont Bigger = PdfFontFactory.createFont(FontConstants.TIMES_BOLDITALIC);
            Header.setFont(Bigger).setFontSize(18);
            Header.setFont(Bigger).setUnderline();
            Header.setFont(Bigger);

            paragraph1.addStyle(Header);
            paragraph2.addStyle(subhead);
            paragraph14.addStyle(subhead);
            paragraph1.setTextAlignment(TextAlignment.CENTER);
            paragraph3.add(BenfName).addStyle(normal);
            paragraph4.add(ag).addStyle(normal);
            paragraph5.add(gndr).addStyle(normal);
            paragraph6.add(idVerf).addStyle(normal);
            paragraph7.add(uniqHealthID).addStyle(normal);
            paragraph8.add(BenfReffID).addStyle(normal);
            paragraph9.add(VaccnName).addStyle(normal);
            paragraph10.add(DateDose).addStyle(normal);
            paragraph11.add(nextDose).addStyle(normal);
            paragraph12.add(vaccnBy).addStyle(normal);
            paragraph13.add(vaccnAt).addStyle(normal);

            imFile = "C:\\Users\\dell\\IdeaProjects\\Vaccine_CodeForCovid\\src\\images\\modi.PNG";
            data = ImageDataFactory.create(imFile);

            // Creating an Image object
            image = new Image(data);
            image.setHeight((float) ((float) 541.0 / 2));
            image.setWidth((float) ((float) 1239.0 / 2));
            image.setFixedPosition(0, 0);

            document.add(image);

//        Paragraph p = new Paragraph();
//        p.add(new Text("The Strange Case of ").addStyle(normal));
//        p.add(new Text("Dr. Jekyll").addStyle(code));
//        p.add(new Text(" and ").addStyle(normal));
//        p.add(new Text("Mr. Hyde").addStyle(code));
//        p.add(new Text(".").addStyle(normal));
//        document.add(p);
//        paragraph1.setFontColor();


            // Adding paragraphs to document
            document.add(paragraph1);
            document.add(new Paragraph("\n"));
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph6);
            document.add(paragraph7);
            document.add(paragraph8);
            document.add(new Paragraph("\n"));
            document.add(paragraph14);
            document.add(paragraph9);
            document.add(paragraph10);
            document.add(paragraph11);
            document.add(paragraph12);
            document.add(paragraph13);

//        document.add(paragraph2);

            // Closing the document
//        font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
//        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
//        Text title = new Text("The Strange Case of Dr. Jekyll and Mr. Hyde").setFont(bold);
//        Text author = new Text("Robert Louis Stevenson").setFont(font);
//        Paragraph p2 = new Paragraph().add(title).add(" by ").add(author);
//        document.add(p2);
            document.close();
            System.out.println("Paragraph added");
        }
    }
}