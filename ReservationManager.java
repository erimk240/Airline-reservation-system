package services;

import database.DatabaseConnection;
import models.Flight;
import models.Passenger;
import models.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationManager {

    public void makeReservation(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservations (flight_id, passport_number, seat_number) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, reservation.getFlight().getFlightID());
            statement.setString(2, reservation.getPassenger().getPassportNumber());
            statement.setString(3, reservation.getSeatNumber());

            statement.executeUpdate();
        }
    }

    public void cancelReservation(String passportNumber, String flightID) throws SQLException {
        String query = "DELETE FROM reservations WHERE passport_number = ? AND flight_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, passportNumber);
            statement.setString(2, flightID);

            statement.executeUpdate();
        }
    }
}
