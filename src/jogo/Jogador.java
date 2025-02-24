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
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Erro ao configurar E/S para jogador " + idJogador + ": " + e.getMessage());
            e.printStackTrace();
            fecharConexao();
        }
    }

    public int getIdJogador() {
        return idJogador;
    }
    
    public void setIdJogador(int novo) {
        this.idJogador = novo;
    }

    public boolean estaConectado() {
        return socket != null && !socket.isClosed() && socket.isConnected();
    }

    @Override
    public void run() {
        enviarMsg("Bem-vindo, Jogador " + idJogador + "! Aguarde os outros jogadores.");
    }

    public void aguardarRoll() {
        String resposta = receberMsg();
        while (!"y".equalsIgnoreCase(resposta)) {
            enviarMsg("Sua vez! Pressione 'y' para rolar.");
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
        if (out != null) {
            out.println(msg);
        } else {
            System.err.println("Erro: PrintWriter não inicializado para jogador " + idJogador);
        }
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
        this.roll = 0;
        this.out.flush();
    }

    public void fecharConexao() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar conexão do jogador " + idJogador + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}