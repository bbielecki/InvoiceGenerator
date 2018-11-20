import com.itextpdf.text.DocumentException;
import io.spinme.InvoiceDto;
import io.spinme.PdfGenerator;

import java.io.IOException;

public class Test{
    public static void main(String [] args) throws IOException, DocumentException {
        PdfGenerator pdfGenerator = new PdfGenerator(new InvoiceDto());
    }
}