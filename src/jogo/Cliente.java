package jogo;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Cliente {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado ao servidor.");
            System.out.println(in.readLine());
            int roll = new Random().nextInt(6) + 1;
            System.out.println("Você rolou: " + roll);
            out.println(roll);
            System.out.println(in.readLine());
            System.out.println(in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

