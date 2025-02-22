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

            String msgServidor;
            while ((msgServidor = in.readLine()) != null) {
                System.out.println(msgServidor);

                if (msgServidor.equals("ROLE_DADO")) {
                    System.out.print("Pressione 'y' para rolar o dado: ");
                    String input = consoleInput.readLine();
                    if ("y".equalsIgnoreCase(input)) {
                        int roll = new Random().nextInt(6) + 1;
                        System.out.println("Você rolou: " + roll);
                        out.println(roll);
                    } else {
                        System.out.println("Você não rolou o dado.");
                    }
                } else if (msgServidor.contains("Resultados") || msgServidor.contains("Parabéns") || msgServidor.contains("Você perdeu")) {
                    System.out.println(in.readLine());
                } else if (msgServidor.equals("Deseja jogar novamente? (s/n)")) {
                    System.out.print("Digite 's' para continuar ou 'n' para sair: ");
                    String input = consoleInput.readLine();
                    out.println(input);
                    if ("n".equalsIgnoreCase(input)) {
                        System.out.println("Você saiu do jogo.");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Conexão encerrada pelo servidor.");
        }
    }
}
