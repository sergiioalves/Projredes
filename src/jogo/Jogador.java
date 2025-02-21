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

            eviarMsg("Bem-vindo, Jogador " + idJogador + "! Aguarde os outros jogadores.");
            roll = Integer.parseInt(in.readLine());
            System.out.println("Jogador " + idJogador + " rolou: " + roll);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRoll() {
        return roll;
    }

    public void eviarMsg(String msg) {
        out.println(msg);
    }

    public void fecharConexão() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

