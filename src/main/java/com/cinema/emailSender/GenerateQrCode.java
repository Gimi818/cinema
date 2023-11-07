package com.cinema.emailSender;

import com.cinema.ticket.Ticket;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import lombok.AllArgsConstructor;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@AllArgsConstructor
public class GenerateQrCode {

    public Element createQr(String email, Ticket ticket) throws BadElementException, IOException {
        String qrCodeData =
                "Name: " + ticket.getName() + "\n"
                        + "Film: " + ticket.getFilmTitle() + "\n"
                        + "Date: " + ticket.getScreeningDate() + "\n"
                        + "Time: " + ticket.getScreeningTime() + "\n"
                        + "Room number : " + ticket.getRoomNumber() + "\n"
                        + "Row: " + ticket.getRowsNumber() + "\n"
                        + "Seats: " + ticket.getSeatInRow() + "\n"
                        + "Ticket Type : " + ticket.getTicketType() + "\n"
                        + "Ticket price: " + ticket.getTicketPrice() + " " + ticket.getCurrency().toString() + "\n"
                        + "Email : " + email;
        ByteArrayOutputStream qrOutputStream = QRCode.from(qrCodeData)
                .to(ImageType.PNG)
                .stream();

        return Image.getInstance(qrOutputStream.toByteArray());
    }

}
