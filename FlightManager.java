package services;

import database.DatabaseConnection;
import models.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightManager {

    public List<Flight> getAllFlights() throws SQLException {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM flights";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Flight flight = new Flight(
                    resultSet.getString("flight_id"),
                    resultSet.getString("departure_city"),
                    resultSet.getString("arrival_city"),
                    resultSet.getString("departure_time"),
                    resultSet.getInt("available_seats")
                );
                flights.add(flight);
            }
        }
        return flights;
    }

    public Flight getFlightByID(String flightID) throws SQLException {
        String query = "SELECT * FROM flights WHERE flight_id = ?";
        Flight flight = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, flightID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                flight = new Flight(
                    resultSet.getString("flight_id"),
                    resultSet.getString("departure_city"),
                    resultSet.getString("arrival_city"),
                    resultSet.getString("departure_time"),
                    resultSet.getInt("available_seats")
                );
            }
        }
        return flight;
    }

    public void addFlight(Flight flight) throws SQLException {
        String query = "INSERT INTO flights (flight_id, departure_city, arrival_city, departure_time, available_seats) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, flight.getFlightID());
            statement.setString(2, flight.getDepartureCity());
            statement.setString(3, flight.getArrivalCity());
            statement.setString(4, flight.getDepartureTime());
            statement.setInt(5, flight.getAvailableSeats());

            statement.executeUpdate();
        }
    }
}
