package jogo;

import java.io.*;
import java.net.*;

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
            e.printStackTrace();
        }
    }

    public void aguardarRoll() {
        try {
            roll = Integer.parseInt(in.readLine());
            System.out.println("Jogador " + idJogador + " rolou: " + roll);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
