package jogo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Servidor {
    private static final int PORT = 12345;
    private static final int MAX_JOGADORES = 4;
    private static final int MIN_JOGADORES = 2;
    private static List<Jogador> jogadores = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado... Aguardando jogadores...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                while (jogadores.size() < MAX_JOGADORES) {
                    try {
                        Socket socket = serverSocket.accept();
                        Jogador jogador = new Jogador(socket, jogadores.size() + 1);
                        jogadores.add(jogador);
                        new Thread(jogador).start();
                        System.out.println("Jogador " + jogadores.size() + " conectado.");

                        if (jogadores.size() >= MIN_JOGADORES && confirmarInicio()) {
                            iniciarJogo();
                        }
                    } catch (IOException e) {
                        System.err.println("Erro ao aceitar conexão: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean confirmarInicio() {
        System.out.println("Verificando se jogadores desejam iniciar a partida...");
        for (Jogador jogador : jogadores) {
            jogador.enviarMsg("Temos " + jogadores.size() + " jogadores. Deseja iniciar? (s/n)");
        }

        for (Jogador jogador : jogadores) {
            String resposta = jogador.receberMsg();
            if (!"s".equalsIgnoreCase(resposta)) {
                System.out.println("Jogador " + jogador.getIdJogador() + " não deseja iniciar. Aguardando mais jogadores...");
                return false;
            }
        }
        return true;
    }

    private static void iniciarJogo() {
        System.out.println("Iniciando o jogo...");

        System.out.println("Todos os jogadores conectados. Iniciando o jogo...");
        do {
            for (Jogador jogador : jogadores) {
                jogador.zerarPontos();
            }

            Map<Integer, Integer> resultadosFinais = new HashMap<>();
            for (Jogador jogador : jogadores) {
                resultadosFinais.put(jogador.getIdJogador(), 0);
            }

            for (int rodada = 1; rodada <= 3; rodada++) {
                System.out.println("\n--- Rodada " + rodada + " ---");

                for (int i = 0; i < jogadores.size(); i++) {
                    Jogador jogadorAtual = jogadores.get(i);

                    for (Jogador jogador : jogadores) {
                        if (jogador == jogadorAtual) {
                            jogador.enviarMsg("Sua vez! Pressione 'y' para rolar o dado.");
                        } else {
                            jogador.enviarMsg("Aguarde o jogador " + jogadorAtual.getIdJogador() + " rolar o dado.");
                        }
                    }

                    jogadorAtual.aguardarRoll();
                    int total = resultadosFinais.get(jogadorAtual.getIdJogador()) + jogadorAtual.getRoll();
                    resultadosFinais.put(jogadorAtual.getIdJogador(), total);
                }

                for (Jogador jogador : jogadores) {
                    jogador.enviarMsg("Resultado da rodada: " + resultadosFinais);
                }
            }

            int idVencedor = AdminJogo.calcularVencedor(resultadosFinais);
            for (Jogador jogador : jogadores) {
                jogador.enviarMsg("\nResultados finais: " + resultadosFinais);
                jogador.enviarMsg(jogador.getIdJogador() == idVencedor ? "Parabéns! Você venceu!" : "Você perdeu.");
            }

        } while (aguardarNovaPartida());

        for (Jogador jogador : jogadores) {
            jogador.fecharConexao();
        }

        System.out.println("Jogo encerrado.");
    }

    private static boolean aguardarNovaPartida() {
        List<Jogador> jogadoresConfirmados = new ArrayList<>();

        System.out.println("\nAguardando decisão dos jogadores para nova partida...");
        for (Jogador jogador : jogadores) {
            jogador.enviarMsg("Digite 's' para jogar novamente ou 'n' para sair:");
        }

        Iterator<Jogador> iterator = jogadores.iterator();
        while (iterator.hasNext()) {
            Jogador jogador = iterator.next();
            String resposta = jogador.receberMsg();
            if ("s".equalsIgnoreCase(resposta)) {
                jogadoresConfirmados.add(jogador);
            } else {
                jogador.enviarMsg("Você saiu do jogo.");
                jogador.fecharConexao();
                iterator.remove();
            }
        }

        jogadores = jogadoresConfirmados;
        System.out.println("Jogadores que continuam: " + jogadores.size());

        while (jogadores.size() < MIN_JOGADORES) {
            System.out.println("Aguardando mais jogadores para iniciar nova partida...");
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                Socket socket = serverSocket.accept();
                Jogador novoJogador = new Jogador(socket, jogadores.size() + 1);
                jogadores.add(novoJogador);
                new Thread(novoJogador).start();
                System.out.println("Novo jogador conectado. Total de jogadores: " + jogadores.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
