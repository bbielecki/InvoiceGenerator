package io.spinme;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTrigger-Java&code={your function key}
     * 2. curl "{your host}/api/HttpTrigger-Java?name=HTTP%20Query&code={your function key}"
     * Function Key is not needed when running locally, to invoke HttpTrigger deployed to Azure, see here(https://docs.microsoft.com/en-us/azure/azure-functions/functions-bindings-http-webhook#authorization-keys) on how to get function key for your app.
     */
    @FunctionName("GenerateInvoice")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
//        String json = request.getBody().orElse("");
//        context.getLogger().info("Received String: " + json);
//        Gson gson = new Gson();
//        InvoiceDto invoice = gson.fromJson(json, InvoiceDto.class);
//        context.getLogger().info("invoice was created. Sample data: vat rate = " + invoice.AmountNet);
        InvoiceDto invoice = new InvoiceDto();

        Document pdf;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if(invoice == null){
            invoice = new InvoiceDto();
//            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Request doesn't contain proper data.").build();
        }

        try {
            PdfGenerator generator = new PdfGenerator(invoice);
            pdf = generator.getResult();

            if(pdf != null){
                PdfWriter.getInstance(pdf, baos);
            }

        } catch (IOException | DocumentException e) {
            context.getLogger().info(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occured.").build();
        }

        HttpResponseMessage.Builder repsonse = request.createResponseBuilder(HttpStatus.ACCEPTED).body("Ok, created.");
        HttpResponseMessage message = repsonse
                .header("Content-Type", "application/pdf")
                .header("Expires", "0")
                .header("Cache-Control", "must-revalidate, post-check=0, pre-check=0")
                .header("Pragma", "public")
                .body(baos.toByteArray())

                .build();

        return message;
    }
}
