package com.example.__Troeurn_Tharin_PVH_Homework001_RESTAP.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TicketRequest {
    private String passengerName;
    private LocalDate traveldate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private boolean paymentStatus;
    private String ticketStatus;
    private String seatNumber;
}
