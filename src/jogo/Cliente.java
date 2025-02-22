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

            String mensagemServidor;
            while ((mensagemServidor = in.readLine()) != null) {
                System.out.println(mensagemServidor);

                if (mensagemServidor.contains("Digite 's' para jogar novamente ou 'n' para sair:")) {
                    String resposta;
                    do {
                        System.out.print("> ");
                        resposta = consoleInput.readLine().trim().toLowerCase();
                        if (!resposta.equals("s") && !resposta.equals("n")) {
                            System.out.println("Entrada inválida. Digite 's' ou 'n'.");
                        }
                    } while (!resposta.equals("s") && !resposta.equals("n"));
                
                    out.println(resposta);
                
                    if (resposta.equals("s")) {
                        String aguardando = in.readLine();
                        System.out.println(aguardando);
                    } else {
                        String saida = in.readLine();
                        System.out.println(saida);
                        System.exit(0);
                    }
                } else if (mensagemServidor.contains("Role seu dado")) {
                    System.out.println("Pressione 'y' para rolar o dado:");
                    while (!consoleInput.readLine().trim().equalsIgnoreCase("y")) {
                        System.out.println("Pressione apenas 'y' para rolar o dado:");
                        Thread.sleep(100); 
                    }
                    int roll = new Random().nextInt(6) + 1;
                    System.out.println("Você rolou: " + roll);
                    out.println(roll);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
