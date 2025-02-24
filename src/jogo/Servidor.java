package jogo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Servidor {
    private static final int PORT = 12345;
    private static ServerSocket serverSocket;
    private static final int MAX_JOGADORES = 4;
    private static final int MIN_JOGADORES = 2;
    private static List<Jogador> jogadores = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado... Aguardando jogadores...");
        
        try {
            serverSocket = new ServerSocket(PORT); 

            while (true) {
                while (jogadores.size() < MAX_JOGADORES) {
                    Socket socket = serverSocket.accept();
                    Jogador novoJogador = new Jogador(socket, jogadores.size() + 1);
                    jogadores.add(novoJogador);
                    new Thread(novoJogador).start();
                    System.out.println("Jogador " + novoJogador.getIdJogador() + " conectado.");
                }

                if (confirmarInicio()) {
                    iniciarJogo();
                } else {
                    System.out.println("Não há jogadores suficientes. Reiniciando...");
                    jogadores.forEach(Jogador::fecharConexao);
                    jogadores.clear();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static boolean confirmarInicio() {
        System.out.println("Confirmando início com " + jogadores.size() + " jogadores...");
        Iterator<Jogador> iterator = jogadores.iterator();
        
        while (iterator.hasNext()) {
            Jogador jogador = iterator.next();
            jogador.enviarMsg("Deseja iniciar o jogo? (s/n)");
            String resposta = jogador.receberMsg();
            
            if (!"s".equalsIgnoreCase(resposta)) {
                System.out.println("Jogador " + jogador.getIdJogador() + " recusou. Desconectando...");
                jogador.enviarMsg("Você foi desconectado da partida.");
                jogador.fecharConexao();
                iterator.remove();
            }
        }
    
        for (int i = 0; i < jogadores.size(); i++) {
            jogadores.get(i).setIdJogador(i + 1);
        }
    
        System.out.println("Jogadores confirmados: " + jogadores.size());
        return jogadores.size() >= MIN_JOGADORES;
    }

    private static void iniciarJogo() {
        do {
            System.out.println("Iniciando jogo com " + jogadores.size() + " jogadores!");
            Map<Integer, Integer> resultados = new HashMap<>();
            
            for (Jogador j : jogadores) {
                j.zerarPontos();
                resultados.put(j.getIdJogador(), 0);
            }
    
            for (int rodada = 1; rodada <= 3; rodada++) {
                for (Jogador atual : jogadores) {
                    for (Jogador j : jogadores) {
                        if (j == atual) {
                            j.enviarMsg("Sua vez! Pressione 'y' para rolar.");
                        } else {
                            j.enviarMsg("Aguarde o Jogador " + atual.getIdJogador() + ".");
                        }
                    }
                    
                    atual.aguardarRoll();
                    resultados.put(atual.getIdJogador(), resultados.get(atual.getIdJogador()) + atual.getRoll());
                    
                    for (Jogador j : jogadores) {
                        j.enviarMsg("Jogador " + atual.getIdJogador() + " rolou " + atual.getRoll());
                    }
                }
            }
            
            int idVencedor = AdminJogo.calcularVencedor(resultados);
            for (Jogador j : jogadores) {
                j.enviarMsg("\n=== RESULTADO FINAL ===");
                j.enviarMsg("Pontuações: " + resultados);
                if (j.getIdJogador() == idVencedor) {
                    j.enviarMsg("VOCÊ VENCEU! \\o/");
                } else {
                    j.enviarMsg("Você perdeu. Tente novamente!");
                }
            }
        } while (aguardarNovaPartida());
    }

    private static boolean aguardarNovaPartida() {
        List<Jogador> jogadoresConfirmados = new ArrayList<>();

        for (Jogador jogador : jogadores) {
            jogador.enviarMsg("Digite 's' para jogar novamente ou 'n' para sair:");
        }

        Iterator<Jogador> iterator = jogadores.iterator();
        while (iterator.hasNext()) {
            Jogador jogador = iterator.next();
            String resposta = jogador.receberMsg();
            if ("s".equalsIgnoreCase(resposta)) {
                jogadoresConfirmados.add(jogador);
                jogador.enviarMsg("Aguardando confirmação de outros jogadores...");
            } else {
                System.out.println("Jogador " + jogador.getIdJogador() + " saiu do jogo.");
                jogador.enviarMsg("Conexão encerrada.");
                jogador.fecharConexao();
                iterator.remove();
            }
        }

        jogadores = jogadoresConfirmados;

        System.out.println("Jogadores que permaneceram: " + jogadores.size());

        try {
            while (jogadores.size() < MAX_JOGADORES) {
                System.out.println("Aguardando novos jogadores...");
                Socket socket = serverSocket.accept(); 
                Jogador novoJogador = new Jogador(socket, jogadores.size() + 1);
                jogadores.add(novoJogador);
                new Thread(novoJogador).start();
                System.out.println("Novo jogador " + novoJogador.getIdJogador() + " conectado.");
                novoJogador.enviarMsg("Aguardando outros jogadores...");
            }
        } catch (IOException e) {
            System.err.println("Erro ao aceitar nova conexão: " + e.getMessage());
            return false;
        }

        return jogadores.size() >= MIN_JOGADORES && confirmarInicio();
    }

}