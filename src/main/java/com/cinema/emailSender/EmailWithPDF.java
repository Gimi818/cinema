package com.cinema.emailSender;

import com.cinema.ticket.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@AllArgsConstructor
public class EmailWithPDF {
    private final JavaMailSender javaMailSender;
    private final GenerateQrCode generateQrCode;


    public void sendEmailWithPDF(String email, Ticket ticket) throws MessagingException {
        Document document = new Document();
        String logoPath = "classpath:logo2.png";
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, pdfOutputStream);

            document.open();

            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(600, 200);
            logo.setSpacingBefore(0);
            logo.setAlignment(Element.ALIGN_CENTER);

            document.add(logo);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 25, Font.NORMAL, BaseColor.BLACK);
            Paragraph filmTitle = new Paragraph(ticket.getFilmTitle(), titleFont);
            filmTitle.setAlignment(Element.ALIGN_CENTER);

            Font dataFont = new Font(Font.FontFamily.HELVETICA, 20, Font.NORMAL, BaseColor.BLACK);
            Paragraph filmData = new Paragraph("Date: " + ticket.getScreeningDate(), dataFont);
            filmData.setAlignment(Element.ALIGN_LEFT);

            Paragraph filmTime = new Paragraph("Time: " + ticket.getScreeningTime(), dataFont);
            filmTime.setAlignment(Element.ALIGN_LEFT);


            document.add(filmTitle);
            document.add(filmData);
            document.add(filmTime);
            document.add(new Paragraph("Name: " + ticket.getName()));
            document.add(new Paragraph("Room number : " + ticket.getRoomNumber()));
            document.add(new Paragraph("Row: " + ticket.getRowsNumber()));
            document.add(new Paragraph("Seat : " + ticket.getSeatInRow()));
            document.add(new Paragraph("Ticket type - " + ticket.getTicketType()));
            document.add(new Paragraph("Ticket Price: " + ticket.getTicketPrice() + " " + ticket.getCurrency().toString()));
            document.add(new Paragraph("Email : " + email));


            Image qrCodeImage = (Image) generateQrCode.createQr(email, ticket);
            qrCodeImage.setAlignment(Element.ALIGN_CENTER);
            qrCodeImage.scaleToFit(280, 280);
            document.add(qrCodeImage);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Your ticket");

        String text = "Thank you for buying a ticket!";
        helper.setText(text, true);

        byte[] pdfBytes = pdfOutputStream.toByteArray();
        helper.addAttachment("Ticket.pdf", new ByteArrayResource(pdfBytes));

        javaMailSender.send(message);
    }
}
