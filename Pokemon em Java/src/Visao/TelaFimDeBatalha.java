package Visao;

import Controle.*;
import Modelo.*;
import java.awt.*;
import javax.swing.*;

public class TelaFimDeBatalha extends JFrame {

    private Jogador jogador;
    private JogadorCPU cpu;
    private ControleBatalha controle;
    private MusicPlayer musica;

    // Constrói a tela de resultado da batalha
    public TelaFimDeBatalha(Jogador jogador, JogadorCPU cpu, ControleBatalha controle) {
        this.jogador = jogador;
        this.cpu = cpu;
        this.controle = controle;

        setTitle("Resultado da Batalha");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(painelPrincipal, BorderLayout.CENTER);

        JLabel lblTitulo = new JLabel("Fim da Batalha!");
        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 26));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblTitulo);

        painelPrincipal.add(Box.createVerticalStrut(15));

        JLabel lblResultado = new JLabel(gerarMensagemDeResultado());
        lblResultado.setFont(new Font("Monospaced", Font.PLAIN, 20));
        lblResultado.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblResultado);

        painelPrincipal.add(Box.createVerticalStrut(25));

        painelPrincipal.add(criarHUDFinal());

        painelPrincipal.add(Box.createVerticalStrut(35));

        JPanel painelBotoes = new JPanel(new FlowLayout());

        JButton btnReiniciar = new JButton("Jogar Novamente");
        btnReiniciar.setFont(new Font("Monospaced", Font.BOLD, 16));
        btnReiniciar.addActionListener(e -> reiniciarJogo());

        JButton btnFechar = new JButton("Encerrar Jogo");
        btnFechar.setFont(new Font("Monospaced", Font.BOLD, 16));
        btnFechar.addActionListener(e -> System.exit(0));

        painelBotoes.add(btnReiniciar);
        painelBotoes.add(btnFechar);

        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Gera o texto final baseado no resultado da batalha
    private String gerarMensagemDeResultado() {
        if (cpu.getPokemonAtivo().isCapturado()) {
            musica.parar();
            musica.tocar("/musica/vitoria.wav", false);
            return "Parabéns! Você capturou Mewtwo!";
        }

        if (cpu.temPokemonVivo()) {
            musica.tocar("/musica/derrota.wav", false);
            return "Você perdeu a batalha!";
        }

        if (jogador.temPokemonVivo()) {
            musica.parar();
            musica.tocar("/musica/vitoria.wav", false);
            return "Você venceu a batalha!";
        }

        return "Fim da batalha.";
    }

    // Monta a HUD final com o estado dos Pokémon
    private JPanel criarHUDFinal() {
        JPanel hud = new JPanel(new GridLayout(2, 1, 10, 10));

        Pokemon pJogador = jogador.getPokemonAtivo();
        String hpJ = pJogador != null ? pJogador.getHpAtual() + "/" + pJogador.getMaxHp() : "—";

        JLabel lblJogador = new JLabel(
            "Seu Pokémon: " +
            (pJogador != null ? pJogador.getNome() : "Nenhum") +
            " — HP: " + hpJ
        );
        lblJogador.setFont(new Font("Monospaced", Font.PLAIN, 18));
        hud.add(lblJogador);

        Pokemon pCPU = cpu.getPokemonAtivo();
        String hpC = pCPU != null ? pCPU.getHpAtual() + "/" + pCPU.getMaxHp() : "—";

        JLabel lblCPU = new JLabel(
            "Pokémon Inimigo: " +
            (pCPU != null ? pCPU.getNome() : "Nenhum") +
            " — HP: " + hpC
        );
        lblCPU.setFont(new Font("Monospaced", Font.PLAIN, 18));
        hud.add(lblCPU);

        return hud;
    }

    // Reinicia a batalha criando novos jogadores e uma nova instância de controle
    private void reiniciarJogo() {
        controle.fecharTelaInicial();
        dispose();
        Jogador novoJogador = new Jogador("Player");
        JogadorCPU novoCPU = new JogadorCPU();
        new ControleBatalha(novoJogador, novoCPU);
    }
}
