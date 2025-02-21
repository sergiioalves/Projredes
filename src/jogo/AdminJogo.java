package jogo;

import java.util.Map;

public class AdminJogo {
    public static int calcularVencedor(Map<Integer, Integer> resultados) {
        int maxRoll = 0;
        int idVencedor = -1;

        for (Map.Entry<Integer, Integer> entry : resultados.entrySet()) {
            if (entry.getValue() > maxRoll) {
                maxRoll = entry.getValue();
                idVencedor = entry.getKey();
            }
        }
        return idVencedor;
    }
}

