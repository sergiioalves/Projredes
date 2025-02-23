package jogo;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Jogador implements Runnable {
    private Socket socket;
    private int idJogador;
    private PrintWriter out;
    private BufferedReader in;
    private int roll;

    public Jogador(Socket socket, int idJogador) {
        this.socket = socket;
        this.idJogador = idJogador;
    }

    public int getIdJogador() {
        return idJogador;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            enviarMsg("Bem-vindo, Jogador " + idJogador + "! Aguarde os outros jogadores.");
        } catch (IOException e) {
            System.err.println("Erro ao configurar E/S para jogador " + idJogador + ": " + e.getMessage());
            e.printStackTrace();
            fecharConexao();
        }
    }

    public void aguardarRoll() {
        
        String resposta = receberMsg();
        while (!"y".equalsIgnoreCase(resposta)) {
            enviarMsg("Pressione apenas 'y' para rolar o dado:");
            resposta = receberMsg();
        }
    
        this.roll = rolarDado();
        System.out.println("Jogador " + idJogador + " rolou: " + this.roll);
        enviarMsg("Você rolou: " + this.roll);
    }

    private int rolarDado() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public int getRoll() {
        return roll;
    }

    public void enviarMsg(String msg) {
        out.println(msg);
    }

    public String receberMsg() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("Erro ao receber mensagem do jogador " + idJogador + ": " + e.getMessage());
            e.printStackTrace();
            fecharConexao();
            return null;
        }
    }

    public void zerarPontos() {
        roll = 0;
    }

    public void fecharConexao() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar conexão do jogador " + idJogador + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
