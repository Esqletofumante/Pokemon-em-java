package Controle;

import Modelo.Jogador;
import Modelo.JogadorCPU;

public class Jogo {
    public static void main(String[] args) {
        Jogador a = new Jogador("Player");
        JogadorCPU b = new JogadorCPU();
        new ControleBatalha(a, b);
    }
}

