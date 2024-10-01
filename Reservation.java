package Airline reservation system;
package models;

public class Reservation {
    private Flight flight;
    private Passenger passenger;
    private String seatNumber;

    public Reservation(Flight flight, Passenger passenger, String seatNumber) {
        this.flight = flight;
        this.passenger = passenger;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
