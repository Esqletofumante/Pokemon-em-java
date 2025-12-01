package Visao;

import Controle.*;
import Modelo.*;
import java.awt.*;
import javax.swing.*;

public class TelaLuta extends JFrame {

    private Jogador jogador;
    private ControleBatalha controle;

    private JButton botaoGolpe1;
    private JButton botaoGolpe2;
    private JButton botaoGolpe3;
    private JButton botaoGolpe4;

    public TelaLuta(Jogador jogador, ControleBatalha controle) {
        this.jogador = jogador;
        this.controle = controle;

        setTitle("Escolha um Golpe");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Pokemon ativo = jogador.getPokemonAtivo();

        // ===== BOTÃO 1 =====
        Movimento mv1 = ativo.getMovimento(0);
        botaoGolpe1 = criarBotaoDoGolpe(mv1);
        botaoGolpe1.addActionListener(e -> {
            controle.atacarJogador(0);
            dispose();
        });
        add(botaoGolpe1);

        // ===== BOTÃO 2 =====
        Movimento mv2 = ativo.getMovimento(1);
        botaoGolpe2 = criarBotaoDoGolpe(mv2);
        botaoGolpe2.addActionListener(e -> {
            controle.atacarJogador(1);
            dispose();
        });
        add(botaoGolpe2);

        // ===== BOTÃO 3 =====
        Movimento mv3 = ativo.getMovimento(2);
        botaoGolpe3 = criarBotaoDoGolpe(mv3);
        botaoGolpe3.addActionListener(e -> {
            controle.atacarJogador(2);
            dispose();
        });
        add(botaoGolpe3);

        // ===== BOTÃO 4 =====
        Movimento mv4 = ativo.getMovimento(3);
        botaoGolpe4 = criarBotaoDoGolpe(mv4);
        botaoGolpe4.addActionListener(e -> {
            controle.atacarJogador(3);
            dispose();
        });
        add(botaoGolpe4);

        setVisible(true);
    }

    private JButton criarBotaoDoGolpe(Movimento mv) {
        String texto =
            "<html><b>" + mv.getNome() + "</b><br>" +
            "Tipo: " + mv.getTipo() + "<br>" +
            "Poder: " + mv.getPoder() + "<br>" +
            "Precisão: " + (int)(mv.getPrecisao() * 100) + "%</html>";

        JButton botao = new JButton(texto);
        botao.setFont(new Font("Monospaced", Font.BOLD, 13));
        botao.setFocusPainted(false);
        return botao;
    }
}
