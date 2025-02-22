package jogo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Servidor {
    private static final int PORT = 12345; 
    private static final int MAX_JOGADORES = 4; 
    private static List<Jogador> jogadores = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado... Aguardando jogadores...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (jogadores.size() < MAX_JOGADORES) {
                Socket socket = serverSocket.accept();
                Jogador jogador = new Jogador(socket, jogadores.size() + 1);
                jogadores.add(jogador);
                new Thread(jogador).start();
                System.out.println("Jogador " + jogadores.size() + " conectado.");
            }

            while (jogadores.size() >= 2) {
                iniciarJogo();
                jogadores = perguntarNovaRodada();
            }

            if (jogadores.size() < 2) {
                for (Jogador jogador : jogadores) {
                    jogador.enviarMsg("Jogadores insuficientes para continuar. O jogo será encerrado.");
                    jogador.fecharConexão();
                }
            }
            
            System.out.println("Todos os jogadores saíram. Servidor encerrado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void iniciarJogo() {
        System.out.println("Todos os jogadores conectados. Iniciando o jogo...");

        for (Jogador jogador : jogadores) {
            jogador.enviarMsg("Jogo iniciado! Role seu dado.");
            jogador.enviarMsg("ROLE_DADO");
        }

        Map<Integer, Integer> results = new HashMap<>();
        for (Jogador jogador : jogadores) {
            jogador.aguardarRoll();
            results.put(jogador.getIdJogador(), jogador.getRoll());
        }

        int idVencedor = AdminJogo.calcularVencedor(results);
        for (Jogador jogador : jogadores) {
            jogador.enviarMsg("Resultados: " + results.toString());
            jogador.enviarMsg(jogador.getIdJogador() == idVencedor ? "Parabéns! Você venceu!" : "Você perdeu.");
        }

        System.out.println("Jogo finalizado.");
    }

    private static List<Jogador> perguntarNovaRodada() {
        List<Jogador> jogadoresRestantes = new ArrayList<>();
        for (Jogador jogador : jogadores) {
            jogador.enviarMsg("Deseja jogar novamente? (s/n)");
            if (jogador.aguardarContinuar()) {
                jogadoresRestantes.add(jogador);
            } else {
                jogador.fecharConexão();
            }
        }
        return jogadoresRestantes;
    }
}
