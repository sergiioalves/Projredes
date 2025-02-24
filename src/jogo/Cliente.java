package jogo;

import java.io.*;
import java.net.*;

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

                if (mensagemServidor.contains("Deseja iniciar o jogo? (s/n)")) {
                    String resposta;
                    do {
                        System.out.print("> ");
                        resposta = consoleInput.readLine().trim().toLowerCase();
                        if (!resposta.equals("s") && !resposta.equals("n")) {
                            System.out.println("Entrada inválida. Digite 's' ou 'n'.");
                        }
                    } while (!resposta.equals("s") && !resposta.equals("n"));
                
                    out.println(resposta);
                }

                else if (mensagemServidor.contains("Digite 's' para jogar novamente ou 'n' para sair:")) {
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
                }

                else if (mensagemServidor.contains("Sua vez! Pressione 'y' para rolar.")) {
                    String resposta;
                    do {
                        System.out.print("> ");
                        resposta = consoleInput.readLine().trim().toLowerCase();
                        if (!resposta.equals("y")) {
                            System.out.println("Pressione apenas 'y' para rolar o dado:");
                        }
                    } while (!resposta.equals("y"));
                    
                    out.println("y");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}