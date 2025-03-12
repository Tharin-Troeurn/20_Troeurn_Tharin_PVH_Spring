package com.example.__Troeurn_Tharin_PVH_Homework001_RESTAP.controller;

import com.example.__Troeurn_Tharin_PVH_Homework001_RESTAP.model.ApiResponse;
import com.example.__Troeurn_Tharin_PVH_Homework001_RESTAP.model.Ticket;
import com.example.__Troeurn_Tharin_PVH_Homework001_RESTAP.model.TicketRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    ArrayList<Ticket> tickets = new ArrayList<>();
    int id = 3;

    public TicketController(){
        tickets.add(new Ticket(1, "Sok", LocalDate.now(), "PP", "KP", 12, true, "Complated", "D1"));
        tickets.add(new Ticket(2, "Dara", LocalDate.now(), "PP", "BTB", 21, true, "Booked", "D2"));
    }

    @GetMapping
    public ArrayList<Ticket> getAllTicket(){
        return tickets;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Ticket>> createTicket(@RequestBody TicketRequest newTicket){
        Ticket ticket = new Ticket(
                id++,
                newTicket.getPassengerName(),
                newTicket.getTraveldate().now(),
                newTicket.getSourceStation(),
                newTicket.getDestinationStation(),
                newTicket.getPrice(),
                newTicket.isPaymentStatus(),
                newTicket.getTicketStatus(),
                newTicket.getSeatNumber()
        );
        tickets.add(ticket);

        ApiResponse<Ticket> apiResponse= new ApiResponse<>(
                true,
                "Ticket created successfully",
                HttpStatus.CREATED,
                ticket,
                LocalDateTime.now()

        ) ;

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> getTicketById(@PathVariable("ticket-id") Integer id){
        ArrayList<Ticket> searchTicketId = new ArrayList<>();
        for(Ticket t : tickets){
            if(t.getId() == id){
                searchTicketId.add(t);
                ApiResponse<Ticket> apiResponse = new ApiResponse<>(
                        true,
                        "Ticket retrieved successfully",
                        HttpStatus.OK,
                        t,
                        LocalDateTime.now()
                );
                return ResponseEntity.ok(apiResponse);
            }
        }
        ApiResponse<Ticket> apiResponse = new ApiResponse<>(
                false,
                "No ticket found with ID: "+ id,
                HttpStatus.NOT_FOUND,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ArrayList<Ticket>>> getTicketsByName(@RequestParam String passengerName){
            ArrayList<Ticket> searchTicketName = new ArrayList<>();
            for(Ticket t : tickets){
                if(t.getPassengerName().equalsIgnoreCase(passengerName)){
                    searchTicketName.add(t);
                }
            }

        if (!searchTicketName.isEmpty()) {
            ApiResponse<ArrayList<Ticket>> apiResponse = new ApiResponse<>(
                    true,
                    "Passenger Name searched Found.",
                    HttpStatus.OK,
                    searchTicketName,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(apiResponse);
        }
        ApiResponse<ArrayList<Ticket>> apiResponse = new ApiResponse<>(
                false,
                "Passenger Name searched not Found.",
                HttpStatus.NOT_FOUND,
                new ArrayList<>(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<ArrayList<Ticket>>> filterDateAndTicketStatus(@RequestParam String tStatus, @RequestParam LocalDate tDate){
        ArrayList<Ticket> filterTicket = new ArrayList<>();
        System.out.println(tStatus);
        System.out.println(tDate);
        for(Ticket t : tickets){
            if(t.getTicketStatus().equalsIgnoreCase(tStatus) && t.getTraveldate().equals(tDate)){
                filterTicket.add(t);
            }
        }
        if (!filterTicket.isEmpty()) {
            ApiResponse<ArrayList<Ticket>> apiResponse = new ApiResponse<>(
                    true,
                    "Tickets filtered successfully",
                    HttpStatus.OK,
                    filterTicket,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(apiResponse);
        }
        ApiResponse<ArrayList<Ticket>> apiResponse = new ApiResponse<>(
                false,
                "Tickets filtered not successfully",
                HttpStatus.NOT_FOUND,
                new ArrayList<>(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @PutMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> updateById(@PathVariable("ticket-id") Integer id, @RequestBody TicketRequest uTicket){
        for(Ticket t : tickets){
            if(t.getId() == id){
                t.setPassengerName(uTicket.getPassengerName());
                t.setTraveldate(LocalDate.now());
                t.setSourceStation(uTicket.getSourceStation());
                t.setDestinationStation(uTicket.getDestinationStation());
                t.setPrice(uTicket.getPrice());
                t.setPaymentStatus(uTicket.isPaymentStatus());
                t.setTicketStatus(uTicket.getTicketStatus());
                t.setSeatNumber(uTicket.getSeatNumber());
                ApiResponse<Ticket> apiResponse = new ApiResponse<>(
                        true,
                        "Ticket updated successfully.",
                        HttpStatus.OK,
                        t,
                        LocalDateTime.now()
                );
                return ResponseEntity.ok(apiResponse);
            }
        }
        ApiResponse<Ticket> apiResponse = new ApiResponse<>(
                false,
                "No ticket found with ID: "+ id,
                HttpStatus.NOT_FOUND,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> deleteTicket(@PathVariable("ticket-id") Integer id){
        for(Ticket t : tickets){
            if(t.getId() == id){
                tickets.remove(t);
                ApiResponse<Ticket> apiResponse = new ApiResponse<>(
                        true,
                        "Ticket deleted successfully.",
                        HttpStatus.OK,
                        null,
                        LocalDateTime.now()
                );
                return ResponseEntity.ok(apiResponse);

            }
        }
        ApiResponse<Ticket> apiResponse = new ApiResponse<>(
                false,
                "No ticket found with ID: "+ id,
                HttpStatus.NOT_FOUND,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(apiResponse);
    }


}
