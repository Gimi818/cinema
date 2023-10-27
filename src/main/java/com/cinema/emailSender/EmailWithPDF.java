package com.cinema.emailSender;

import com.cinema.ticket.Ticket;
import com.itextpdf.text.*;
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

    public Element createQr(String email, Ticket ticket) throws BadElementException, IOException {
        String qrCodeData =
                "Imie i Nazwisko: " + ticket.getName() + "\n" +
                        "Film: " + ticket.getFilmTitle() + "\n"
                        + "Data: " + ticket.getScreeningDate() + "\n"
                        + "Godzina: " + ticket.getScreeningTime() + "\n"
                        + "Cena biletu: " + ticket.getTicketPrice() + " zl" + "\n"
                        + "Kupujący : " + email;
        ByteArrayOutputStream qrOutputStream = QRCode.from(qrCodeData)
                .to(ImageType.PNG)
                .stream();

        return Image.getInstance(qrOutputStream.toByteArray());
    }


    public void sendEmailWithPDF(String email, Ticket ticket) throws MessagingException {
        Document document = new Document();
        String logoPath = "classpath:logo2.png";
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, pdfOutputStream);
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
            Paragraph filmData = new Paragraph("Data: " + ticket.getScreeningDate(), dataFont);
            filmData.setAlignment(Element.ALIGN_LEFT);

            Paragraph filmTime = new Paragraph("Godzina: " + ticket.getScreeningTime(), dataFont);
            filmTime.setAlignment(Element.ALIGN_LEFT);


            document.add(filmTitle);
            document.add(filmData);
            document.add(filmTime);
            document.add(new Paragraph("Imie i Nazwisko: " + ticket.getName()));
            document.add(new Paragraph("Rzad: 12"));
            document.add(new Paragraph("Miejsce : 15"));
            document.add(new Paragraph("Rodzaj biletu - "+ ticket.getTicketType()));
            document.add(new Paragraph("Cena biletu: " + ticket.getTicketPrice() + " zl"));
            document.add(new Paragraph("Kupujący : " + email));


            Image qrCodeImage = (Image) createQr(email, ticket);
            qrCodeImage.setAlignment(Element.ALIGN_CENTER);
            qrCodeImage.scaleToFit(300, 300);
            document.add(qrCodeImage);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String logoPath3 = "classpath:logo2.PNG";
        helper.setTo(email);
        helper.setSubject("Twój bilet");

        String text = "Dziękujemy za zakup biletu!";
        helper.setText(text, true);

        byte[] pdfBytes = pdfOutputStream.toByteArray();
        helper.addAttachment("Bilet.pdf", new ByteArrayResource(pdfBytes));

        javaMailSender.send(message);
    }
}
