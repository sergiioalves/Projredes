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
            String input;
            while ((input = in.readLine()) != null) {
                if (input.matches("\\d+")) {
                    roll = Integer.parseInt(input);
                    System.out.println("Jogador " + idJogador + " rolou: " + roll);
                    break;
                }
            }
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

    public void fecharConexão() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
