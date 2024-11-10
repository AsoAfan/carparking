package org.example;

import org.example.app.enums.PRIORITY;
import org.example.app.models.*;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class ClientSide {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void register(String username, String email, String phoneNumber, String password, String plateNumber,
                         String parkingLotName, String spotNumber, String entryTime, String exitTime, double fee)
            throws IOException {

        // Send user details to the server
        out.println(username);
        out.println(email);
        out.println(phoneNumber);
        out.println(password);
        out.println(plateNumber);

        // Send parking details to the server
        out.println(parkingLotName);
        out.println(spotNumber);
        out.println(entryTime);
        out.println(exitTime);
        out.println(fee);

        String response = in.readLine();
        System.out.println("Server response: " + response);
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ClientSide client = new ClientSide();

        try {
            client.connect("127.0.0.1", 12345);
            System.out.println("Please register to park your car.");

            // User Registration
            String username;
            while (true) {
                System.out.print("Enter your username: ");
                username = scanner.nextLine();
                if (!isUsernameExists(username)) {
                    break;
                } else {
                    System.out.println("Username already exists. Please choose a different username.");
                }
            }

            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            // Phone Number Validation
            String phoneNumber;
            while (true) {
                System.out.print("Enter your phone number (only numbers): ");
                phoneNumber = scanner.nextLine();
                if (User.isPhoneNumberValid(phoneNumber)) {
                    break;
                } else {
                    System.out.println("Invalid phone number. Try again.");
                }
            }

            // Password Validation
            String password;
            while (true) {
                System.out.print("Enter your password (at least 8 characters, one uppercase letter, and one number): ");
                password = scanner.nextLine();
                if (User.isPasswordValid(password)) {
                    break;
                } else {
                    System.out.println("Invalid password. Please try again.");
                }
            }

            User user = new User(username, email, phoneNumber, password);
            System.out.println("User registration successful!");

            // Car Registration
            System.out.print("Enter your car's plate number: ");
            String plateNumber = scanner.nextLine();

            Car car = new Car(plateNumber, user);
            System.out.println("Car registration successful!");

            // Choose Parking Option
            ParkingLot vipLot = new ParkingLot("First Floor", 100, PRIORITY.VIP);
            ParkingLot normalLot = new ParkingLot("Under Floor", 100, PRIORITY.NORMAL);
            ParkingLot reserveLot = new ParkingLot("Reservation Park", 100, null);

            System.out.println("Choose parking option:");
            System.out.println("1. Park in VIP Lot $5 per hour.");
            System.out.println("2. Park in Normal Lot $3 per hour.");
            System.out.println("3. Reserve a spot $100 per month.");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            ParkingLot selectedParkingLot;
            if (choice == 1) {
                selectedParkingLot = vipLot;
            } else if (choice == 2) {
                selectedParkingLot = normalLot;
            } else {
                selectedParkingLot = reserveLot;
            }

            // Input Entry and Exit Times
            System.out.print("Enter entry/start time (yyyy-MM-dd HH:mm): ");
            String entryTimeStr = scanner.nextLine();
            Date entryTime;
            try {
                entryTime = dateFormat.parse(entryTimeStr);
            } catch (ParseException e) {
                System.out.println("Invalid entry time format. Please use 'yyyy-MM-dd HH:mm'.");
                return;
            }

            System.out.print("Enter expected exit/end time (yyyy-MM-dd HH:mm): ");
            String exitTimeStr = scanner.nextLine();
            Date expectedExitTime;
            try {
                expectedExitTime = dateFormat.parse(exitTimeStr);
            } catch (ParseException e) {
                System.out.println("Invalid exit time format. Please use 'yyyy-MM-dd HH:mm'.");
                return;
            }

            // Calculate Parking Fee
            Spot availableSpot = SpotManager.findAvailableSpot(selectedParkingLot);
            if (availableSpot != null) {
                double fee;
                if (choice == 1 || choice == 2) {
                    fee = calculateParkingFee(entryTime, expectedExitTime, selectedParkingLot);
                } else {
                    fee = 100;
                }

                System.out.println("Parking Fee: $" + fee);

                // Register and send all data to the server
                client.register(username, email, phoneNumber, password, plateNumber,
                        selectedParkingLot.getName(), String.valueOf(availableSpot.getSpotNumber()),
                        dateFormat.format(entryTime), dateFormat.format(expectedExitTime), fee);

                availableSpot.setAvailable(false); // Mark the spot as taken
            } else {
                System.out.println("Sorry, no available spots at the moment.");
            }

            client.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Check if the username already exists in the file
    private static boolean isUsernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader("C:\\Users\\PC\\OneDrive\\Desktop\\UserData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Username: " + username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data file.");
            e.printStackTrace();
        }
        return false;
    }

    private static double calculateParkingFee(Date entryTime, Date exitTime, ParkingLot parkingLot) {
        long durationInMillis = exitTime.getTime() - entryTime.getTime();
        long durationInHours = durationInMillis / (1000 * 60 * 60);
        double rate = parkingLot.getPriority() == PRIORITY.VIP ? 5.0 : 3.0;
        return durationInHours * rate;
    }
}