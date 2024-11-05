package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    private ServerSocket serverSocket;

    public ServerSide(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Read user details from the client
                String username = in.readLine();
                String email = in.readLine();
                String phoneNumber = in.readLine();
                String password = in.readLine();
                String plateNumber = in.readLine();

                // Read parking details from the client
                String parkingLotName = in.readLine();
                String spotNumber = in.readLine();
                String entryTime = in.readLine();
                String exitTime = in.readLine();
                String fee = in.readLine();

                // Save data to a file on the server
                try (FileWriter writer = new FileWriter("C:\\Users\\PC\\OneDrive\\Desktop\\UserData.txt", true)) {
                    // Save user data
                    writer.write("User Registration:\n");
                    writer.write("Username: " + username + "\n");
                    writer.write("Email: " + email + "\n");
                    writer.write("Phone Number: " + phoneNumber + "\n");
                    writer.write("---------------\n");

                    // Save car registration data
                    writer.write("Car Registration:\n");
                    writer.write("Plate Number: " + plateNumber + "\n");
                    writer.write("---------------\n");

                    // Save parking data
                    writer.write("Parking Details:\n");
                    writer.write("Parking Lot: " + parkingLotName + "\n");
                    writer.write("Spot Number: " + spotNumber + "\n");
                    writer.write("Entry Time: " + entryTime + "\n");
                    writer.write("Expected Exit Time: " + exitTime + "\n");
                    writer.write("Parking Fee: $" + fee + "\n");
                    writer.write("===============\n\n\n");

                    System.out.println("All registration and parking details saved to file successfully.");
                } catch (IOException e) {
                    System.out.println("An error occurred while saving data to the file.");
                    e.printStackTrace();
                }

                out.println("Registration and parking details saved successfully.");
                out.flush(); // Ensure the response is sent immediately

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServerSide server = new ServerSide(12345);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
