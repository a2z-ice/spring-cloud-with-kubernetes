package org.example;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class PasswordProtectPDF {
    public static void main(String[] args) {

        System.out.println("Start encryption...");
        // Set the input PDF file name and password

        if(args.length < 2) {
            throw new RuntimeException("the first argument will be the file name without space and second argument will be the password");
        }
        String inputFile = args[0];
        String password = args[1];

        try {
            // Create a new PDF reader object to read the existing PDF file
            PdfReader reader = new PdfReader(inputFile);

            // Create a new PDF stamper object to set the password for the PDF file
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("locked_"+inputFile));

            // Set the password for the PDF file
            stamper.setEncryption(password.getBytes(), password.getBytes(),
                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);

            // Close the stamper object
            stamper.close();

            // Close the reader object
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Encryption done!!");
    }
}
