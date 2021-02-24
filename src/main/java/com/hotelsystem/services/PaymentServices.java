package com.hotelsystem.services;

import com.hotelsystem.models.Booking;
import com.hotelsystem.models.Payment;
import com.hotelsystem.repository.PaymentRepository;
import com.hotelsystem.utils.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
public class PaymentServices {

    @Autowired
    PaymentRepository paymentRepository;

    public boolean createAndSavePayment(String cardNumber, String cvv, String holder, String month_exp, String year_exp, Booking booking){
        Payment payment = new Payment();
        try{
            LocalDate ld = LocalDate.now();
            String ldString = String.valueOf(ld);
            payment.setCreated_at(new DateParser().parseDate(ldString));

            payment.setHolder(holder);
            payment.setCard_number(cardNumber);
            payment.setMonth_exp(month_exp);
            payment.setYear_exp(year_exp);
            payment.setCvv(cvv);

            if(cardNumber.charAt(0) == '4'){
                payment.setCard("Visa");
            } else if (cardNumber.charAt(0) == '3'){
                if(cardNumber.charAt(1) == '4' || cardNumber.charAt(1) == '7'){
                    payment.setCard("American Express");
                }
            } else if (cardNumber.charAt(0) == '5'){
                if(cardNumber.charAt(1) == '1' || cardNumber.charAt(1) == '2' || cardNumber.charAt(1) == '3' || cardNumber.charAt(1) == '4' || cardNumber.charAt(1) == '5'){
                    payment.setCard("Master Card");
                }
            } else payment.setCard("Otra");             //Si solamente acepta esos tipos, hay que retornar error en la tarjeta y retornar la misma pagina

            payment.setBooking(booking);

            paymentRepository.save(payment);
            return true;

        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
