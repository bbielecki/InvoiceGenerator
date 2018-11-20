package io.spinme;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PdfGenerator {
    private static String companyName;
    private static String companyCity;
    private static String companyStreet;
    private static String companyBuilding;
    private static String companyZipCode;
    private static String companyCountry;
    private static String companyContactNumber;
    private static String companyContactEmail;
    private static String companyBankAccount;
    private static String companyBankName;
    private static String companyVatIdentifier;

    private static SimpleDateFormat invoiceNumberDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private static SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static String FILE = "SecondPdf.pdf";
    private static String FOLDER = "resources";
    private static BaseColor spinmeTheme = new BaseColor(26, 179, 148);
    private static BaseColor spinmeLightTheme = new BaseColor(233, 252, 248);
    private static BaseColor grey = new BaseColor(115, 115, 115);

    private static Font greyFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, grey);
    private static Font whiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.WHITE);
    private static Font whiteBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
    private static Font standardFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, spinmeTheme);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);


    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);


    private static InvoiceDto invoiceDto;

    private Document generatedPdf;

    public Document getResult(){
        return generatedPdf;
    }

    public PdfGenerator(InvoiceDto invoiceDto) throws IOException, DocumentException {
        this.invoiceDto = invoiceDto;
        try {
//            Properties companyData = new ConfigReader().GetConfiguration();
//            companyBankAccount = companyData.getProperty("bankAccount");
//            companyBankName = companyData.getProperty("bankName");
//            companyBuilding = companyData.getProperty("companyBuilding");
//            companyCity = companyData.getProperty("companyCity");
//            companyContactEmail = companyData.getProperty("companyContactEmail");
//            companyContactNumber = companyData.getProperty("companyContactNumber");
//            companyName = companyData.getProperty("companyName");
//            companyStreet = companyData.getProperty("companyStreet");
//            companyZipCode = companyData.getProperty("companyZipCode");
//            companyCountry = companyData.getProperty("companyCountry");
//            companyVatIdentifier = companyData.getProperty("companyVatIdentifier");

            companyBankAccount = "8745765654345678";
            companyBankName = "mBank";
            companyBuilding = "18/8";
            companyCity = "Warsaw";
            companyContactEmail = "spinme@info.com";
            companyContactNumber = "12456789";
            companyName = "SpinMe";
            companyStreet = "Konduktorska";
            companyZipCode = "00-123";
            companyCountry = "Poland";
            companyVatIdentifier = "PL97293874";


            Document document = new Document();
            File folder = new File(FOLDER);
            folder.mkdir();
            File file = new File(folder, FILE);
            file.createNewFile();

            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            AddContactData(document);
            document.add(new Paragraph(" "));
            AddOrderData(document);
            document.add(new Paragraph(" "));
            AddPaymentData(document);
            document.close();

            generatedPdf = document;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("SpinMe invoice");
        document.addSubject("Invoice for subscription");
        document.addKeywords("SpinMe, Sales, Business");
        document.addAuthor("Bartek Bielecki");
        document.addCreator("Bartek Bielecki");
    }

    private static void addTitlePage(Document document)
            throws DocumentException, IOException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Invoice\n\n\n", titleFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph("Nr: " + invoiceNumberDateFormat.format(new Date()) + "\n", smallBold));
        preface.add(new Paragraph("Date: " + standardDateFormat.format(new Date()) + "\n", smallBold));

        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setSpacingBefore(5);
        headerTable.setWidthPercentage(100f);
        headerTable.setWidths(new int[]{1,4,1});

        headerTable.addCell(configureCell(new PdfPCell(preface)));
        headerTable.addCell(configureCell(new PdfPCell(new Phrase())));

        //here the logo can be loaded in place of the empty cell
        headerTable.addCell(configureCell(new PdfPCell(new Phrase())));
//        Image logo = Image.getInstance(FOLDER + "/" + LOGO);
//        headerTable.addCell(configureCell(new PdfPCell(logo, true)));

        document.add(headerTable);

        Paragraph emptyLines = new Paragraph();
        addEmptyLine(emptyLines, 2);

        document.add(emptyLines);
    }

    private static void AddContactData(Document document) throws DocumentException {
        PdfPTable contactTable = new PdfPTable(5);
        contactTable.setSpacingBefore(5);
        contactTable.setSpacingAfter(5);
        contactTable.setWidthPercentage(100f);
        contactTable.setWidths(new int[]{5,5,1,5,5});

        //dealer
        contactTable.addCell(configureCell(new PdfPCell(new Phrase("DEALER:", greyFont))));
        contactTable.addCell(configureCell(new PdfPCell(new Phrase(companyName, standardFont))));

        //empty cell
        contactTable.addCell(addEmptyCell());

        //buyer
        contactTable.addCell(configureCell(new PdfPCell(new Phrase("BUYER:", greyFont))));
        contactTable.addCell(configureCell(new PdfPCell(new Phrase(invoiceDto.UserFirstName + " " + invoiceDto.UserLastName, standardFont))));

        //Dealer address
        contactTable.addCell(configureCell(new PdfPCell(new Phrase("ADDRESS:", greyFont))));
        contactTable.addCell(configureCell(new PdfPCell(new Phrase(companyStreet + " " + companyBuilding + "\n" + companyZipCode + " " + companyCity, standardFont))));

        //empty cell
        contactTable.addCell(addEmptyCell());

        //buyer address
        contactTable.addCell(configureCell(new PdfPCell(new Phrase("ADDRESS:", greyFont))));
        contactTable.addCell(configureCell(new PdfPCell(new Phrase(invoiceDto.Address + "\n" + invoiceDto.ZipCode + " " + invoiceDto.City, standardFont))));

        //Dealer country
        contactTable.addCell(configureCell(new PdfPCell(new Phrase("COUNTRY:", greyFont))));
        contactTable.addCell(configureCell(new PdfPCell(new Phrase(companyCountry, standardFont))));

        //empty cell
        contactTable.addCell(addEmptyCell());

        //buyer country
        contactTable.addCell(configureCell(new PdfPCell(new Phrase("COUNTRY:", greyFont))));
        contactTable.addCell(configureCell(new PdfPCell(new Phrase(invoiceDto.Country, standardFont))));

        //NIP
        contactTable.addCell(configureCell(new PdfPCell(new Phrase("VAT ID:", greyFont))));
        contactTable.addCell(configureCell(new PdfPCell(new Phrase(companyVatIdentifier, standardFont))));

        //empty cell
        contactTable.addCell(addEmptyCell());
        contactTable.addCell(addEmptyCell());
        contactTable.addCell(addEmptyCell());

        //contact number
        contactTable.addCell(configureCell(new PdfPCell(new Phrase("CONTACT NUMBER", greyFont))));
        contactTable.addCell(configureCell(new PdfPCell(new Phrase(companyContactNumber, standardFont))));

        //empty cell
        contactTable.addCell(addEmptyCell());
        contactTable.addCell(addEmptyCell());
        contactTable.addCell(addEmptyCell());

        //contact email
        contactTable.addCell(configureCellWithBottomSeparator(new PdfPCell(new Phrase("CONTACT EMAIL", greyFont))));
        contactTable.addCell(configureCellWithBottomSeparator(new PdfPCell(new Phrase(companyContactEmail, standardFont))));

        //empty cell
        contactTable.addCell(addEmptyCellWithSeparator());
        contactTable.addCell(addEmptyCellWithSeparator());
        contactTable.addCell(addEmptyCellWithSeparator());

        document.add(contactTable);
    }



    private static void AddOrderData(Document document) throws DocumentException {
        PdfPTable orederData = new PdfPTable(5);
        orederData.setSpacingBefore(5);
//        orederData.setSpacingAfter(5);
        orederData.setWidthPercentage(100f);
        orederData.setWidths(new int[]{5,5,1,5,5});
        //date of invoice
        orederData.addCell(configureCell(new PdfPCell(new Phrase("DATE OF INVOICE:", greyFont))));
        orederData.addCell(configureCell(new PdfPCell(new Phrase(standardDateFormat.format(invoiceDto.InvoiceCreatedAt), standardFont))));

        //empty cell
        orederData.addCell(addEmptyCell());

        //payment method
        orederData.addCell(configureCell(new PdfPCell(new Phrase("PAYMENT METHOD:", greyFont))));
        orederData.addCell(configureCell(new PdfPCell(new Phrase("Debit Card", standardFont))));

        //date of subscription start
        orederData.addCell(configureCell(new PdfPCell(new Phrase("SUBSCRIPTION STARTS ON:", greyFont))));
        orederData.addCell(configureCell(new PdfPCell(new Phrase(standardDateFormat.format(invoiceDto.InvoiceCreatedAt), standardFont))));

        //empty cell
        orederData.addCell(addEmptyCell());

        //buyers bank
        orederData.addCell(configureCell(new PdfPCell(new Phrase("BANK:", greyFont))));
        orederData.addCell(configureCell(new PdfPCell(new Phrase(companyBankName, standardFont))));

        //date of subscription end
        orederData.addCell(configureCell(new PdfPCell(new Phrase("SUBSCRIPTION ENDS ON", greyFont))));
        orederData.addCell(configureCell(new PdfPCell(new Phrase(standardDateFormat.format(new Date()), standardFont))));

        //empty cell
        orederData.addCell(addEmptyCell());

        //buyers account/card number
        orederData.addCell(configureCell(new PdfPCell(new Phrase("ACCOUNT NUMBER", greyFont))));
        orederData.addCell(configureCell(new PdfPCell(new Phrase( companyBankAccount, standardFont))));

        //date of payment deadline
        orederData.addCell(configureCellWithBottomSeparator(new PdfPCell(new Phrase("PAYMENT DATE", greyFont))));
        orederData.addCell(configureCellWithBottomSeparator(new PdfPCell(new Phrase(standardDateFormat.format(new Date()), standardFont))));

        //empty cell
        orederData.addCell(addEmptyCellWithSeparator());
        orederData.addCell(addEmptyCellWithSeparator());
        orederData.addCell(addEmptyCellWithSeparator());

        document.add(orederData);

    }

    private static void AddPaymentData(Document document) throws DocumentException {
        PdfPTable paymentTable = new PdfPTable(8);
        paymentTable.setSpacingBefore(5);
        paymentTable.setSpacingAfter(5);
        paymentTable.setWidthPercentage(100f);
        paymentTable.setWidths(new int[]{1,7,2,2,2,2,2,2});

        //HEADER
        paymentTable.addCell(configurePaymentHeaderCell(new PdfPCell(new Phrase("ID", smallBold))));
        paymentTable.addCell(configurePaymentHeaderCell(new PdfPCell(new Phrase("Name", smallBold))));
        paymentTable.addCell(configurePaymentHeaderCell(new PdfPCell(new Phrase("Amount", smallBold))));
        paymentTable.addCell(configurePaymentHeaderCell(new PdfPCell(new Phrase("Net price", smallBold))));
        paymentTable.addCell(configurePaymentHeaderCell(new PdfPCell(new Phrase("Net worth", smallBold))));
        paymentTable.addCell(configurePaymentHeaderCell(new PdfPCell(new Phrase("VAT rate", smallBold))));
        paymentTable.addCell(configurePaymentHeaderCell(new PdfPCell(new Phrase("VAT value", smallBold))));
        paymentTable.addCell(configurePaymentHeaderCell(new PdfPCell(new Phrase("Gross worth", smallBold))));

        //Content
        paymentTable.addCell(configureCellWithBox(new PdfPCell(new Phrase("1", standardFont))));
        paymentTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(invoiceDto.Title, standardFont))));
        paymentTable.addCell(configureCellWithBox(new PdfPCell(new Phrase("1", standardFont))));
        paymentTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(invoiceDto.AmountNet + "", standardFont))));
        paymentTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(invoiceDto.AmountNet + "", standardFont))));
        paymentTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(invoiceDto.VatRate + "", standardFont))));
        paymentTable.addCell(configureCellWithBox(new PdfPCell(new Phrase((invoiceDto.AmountGross - invoiceDto.AmountNet) + "", standardFont))));
        paymentTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(invoiceDto.AmountGross + "", standardFont))));

        document.add(paymentTable);

//        //summary
//        PdfPTable summaryTable = new PdfPTable(6);
//        summaryTable.setWidths(new int[]{1,1,1,1,1,1});
//        summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        summaryTable.setWidthPercentage(60f);
//        summaryTable.setSpacingBefore(5);
//        summaryTable.setSpacingAfter(5);
//
//        summaryTable.addCell(configureCellWithBox(new PdfPCell(new Phrase("Total:"))));
//        summaryTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(""))));
//        summaryTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(""))));
//        summaryTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(" "))));
//        summaryTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(" "))));
//        summaryTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(" "))));

//        document.add(summaryTable);


        //discount
        PdfPTable discountTable = new PdfPTable(3);
        discountTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        discountTable.setWidthPercentage(30f);
        discountTable.setSpacingBefore(5);
        discountTable.setSpacingAfter(5);

        discountTable.addCell(configureCellWithBox(new PdfPCell(new Phrase("Discount:", standardFont))));
        discountTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(invoiceDto.DiscountPercentage))));
        discountTable.addCell(configureCellWithBox(new PdfPCell(new Phrase(invoiceDto.AmountWithDiscount))));

        document.add(discountTable);

        //total
        PdfPTable totalTable = new PdfPTable(2);
        totalTable.setWidths(new int[]{1,1});
        totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalTable.setWidthPercentage(50f);
        totalTable.setSpacingBefore(5);
        totalTable.setSpacingAfter(5);

        totalTable.addCell(configurePaymentSummaryHeaderCell(new PdfPCell(new Phrase("Total to pay:", whiteBoldFont))));
        totalTable.addCell(configurePaymentSummaryHeaderCell(new PdfPCell(new Phrase(invoiceDto.AmountWithDiscount + "", whiteBoldFont))));

        document.add(totalTable);

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static PdfPCell configureCell(PdfPCell cell){
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setRowspan(2);
        return cell;
    }

    private static PdfPCell configureCellWithBottomSeparator(PdfPCell cell){
        cell = configureCell(cell);

        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(spinmeTheme);
        return cell;
    }

    private static PdfPCell configureCellWithBox(PdfPCell cell){
        cell = configureCell(cell);

        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(spinmeTheme);
        return cell;
    }

    private static PdfPCell configurePaymentHeaderCell(PdfPCell cell){

        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(spinmeLightTheme);
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(spinmeTheme);
        cell.setRowspan(5);

        return cell;
    }


    private static PdfPCell configurePaymentSummaryHeaderCell(PdfPCell cell){

        cell = configurePaymentHeaderCell(cell);

        cell.setPadding(10f);
        cell.setBackgroundColor(spinmeTheme);

        return cell;
    }

    private static PdfPCell addEmptyCell(){
        return configureCell(new PdfPCell(new Phrase("")));
    }

    private static PdfPCell addEmptyCellWithSeparator(){
        return configureCellWithBottomSeparator(new PdfPCell(new Phrase("")));
    }
}
