package com.hotelsystem.services;

import com.hotelsystem.models.Booking;
import com.hotelsystem.models.Cancellation;
import com.hotelsystem.repository.CancellationRepository;
import com.hotelsystem.utils.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class CancellationServices {

    @Autowired
    private CancellationRepository cancellationRepository;

    @Autowired
    private BookingServices bookingServices;

    private DateParser dateParser;

    public boolean checkCancellationAvailable(Date check_in){
        try {
            java.util.Date check_in_fix = new java.util.Date(check_in.getTime());
            LocalDate check_inLD = check_in_fix.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            LocalDate todayLd = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Long days = DAYS.between(todayLd, check_inLD);
            System.out.println(days);

            if (days >= 2) {
                return true;
            } else{
                System.out.println("Dias no mayor a 2");
                return false;
            }
        } catch (Exception e){
            System.out.println("Excepcion generada al calcular los dias");
            return false;
        }
    }

    public Booking findBookingById(String id_booking){
        Booking booking = new Booking();

        Long idBookingLong = Long.parseLong(id_booking);

        Optional<Booking> optionalBooking = bookingServices.findBookingById(idBookingLong);

        booking = optionalBooking.get();
        return booking;
    }

    @Transactional
    public boolean createCancellation(Booking booking) throws ParseException {
        try {
            Cancellation cancellation = new Cancellation();
            cancellation.setBooking(booking);

            //Created_at
            LocalDate ld = LocalDate.now();
            String ldString = String.valueOf(ld);
            cancellation.setCreated_at(new DateParser().parseDate(ldString));

            cancellationRepository.save(cancellation);

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String costWithdraw(String id_booking){
        Booking booking = findBookingById(id_booking);

        if(booking.getFree_cancellation()){
            return "Booking cancelled. 100% of the value has been refunded: "+booking.getCost();
        } else {
            double ochentaPC= booking.getCost()*0.8;
            return "Booking cancelled. 80% of the value has been refunded: "+ochentaPC;
        }
    }
}
