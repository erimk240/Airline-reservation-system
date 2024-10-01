package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Flight;
import models.Passenger;
import models.Reservation;
import services.FlightManager;
import services.ReservationManager;

import java.sql.SQLException;
import java.util.List;

public class AirlineReservationSystem extends Application {

    private FlightManager flightManager = new FlightManager();
    private ReservationManager reservationManager = new ReservationManager();

    @Override
    public void start(Stage primaryStage) throws SQLException {
        primaryStage.setTitle("Airline Reservation System");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label flightLabel = new Label("Beschikbare vluchten:");
        GridPane.setConstraints(flightLabel, 0, 0);
        ListView<String> flightListView = new ListView<>();
        List<Flight> flights = flightManager.getAllFlights();
        for (Flight flight : flights) {
            flightListView.getItems().add(flight.getFlightID() + " - " + flight.getDepartureCity() + " naar " + flight.getArrivalCity());
        }
        GridPane.setConstraints(flightListView, 0, 1);

        Label nameLabel = new Label("Naam:");
        GridPane.setConstraints(nameLabel, 0, 2);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 2);

        Label passportLabel = new Label("Paspoortnummer:");
        GridPane.setConstraints(passportLabel, 0, 3);
        TextField passportInput = new TextField();
        GridPane.setConstraints(passportInput, 1, 3);

        Label dobLabel = new Label("Geboortedatum (YYYY-MM-DD):");
        GridPane.setConstraints(dobLabel, 0, 4);
        TextField dobInput = new TextField();
        GridPane.setConstraints(dobInput, 1, 4);

        Button bookButton = new Button("Boek vlucht");
        GridPane.setConstraints(bookButton, 1, 5);
        bookButton.setOnAction(e -> {
            String selectedFlight = flightListView.getSelectionModel().getSelectedItem();
            if (selectedFlight != null && !nameInput.getText().isEmpty() && !passportInput.getText().isEmpty() && !dobInput.getText().isEmpty()) {

                try {
                    Flight flight = flightManager.getFlightByID(selectedFlight.split(" ")[0]);
                    Passenger passenger = new Passenger(nameInput.getText(), passportInput.getText(), dobInput.getText());
                    String seatNumber = "12A"; // Simulatie van stoelnummer
                    Reservation reservation = new Reservation(flight, passenger, seatNumber);
                    reservationManager.makeReservation(reservation);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Boeking voltooid");
                    alert.setHeaderText(null);
                    alert.setContentText("Vlucht is succesvol geboekt voor " + passenger.getName() + " op stoel " + seatNumber);
                    alert.showAndWait();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Boeking mislukt");
                alert.setHeaderText(null);
                alert.setContentText("Vul alle gegevens in om de vlucht te boeken.");
                alert.showAndWait();
            }
        });

        grid.getChildren().addAll(flightLabel, flightListView, nameLabel, nameInput, passportLabel, passportInput, dobLabel, dobInput, bookButton);

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
