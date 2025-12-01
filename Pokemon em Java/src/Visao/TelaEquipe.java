package Visao;

import Controle.ControleBatalha;
import Modelo.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/** Tela de seleção da equipe. Pode ser obrigatória quando o Pokémon ativo desmaia. */
public class TelaEquipe extends JFrame {

    private Jogador jogador;
    private TelaInicial telaPrincipal;
    private ControleBatalha controle;

    private ArrayList<JButton> botoesPokemon = new ArrayList<>();

    private boolean escolhaObrigatoria;
    private boolean forcarFechamentoObrigatorio = false;

    private boolean usandoItem = false;
    private int itemSelecionadoIndex = -1;

    /** Construtor normal (não obrigatório). */
    public TelaEquipe(Jogador jogador, TelaInicial telaPrincipal, ControleBatalha controle) {
        this(jogador, telaPrincipal, controle, false);
    }

    /** Construtor com seleção obrigatória. */
    public TelaEquipe(Jogador jogador, TelaInicial telaPrincipal,
                      ControleBatalha controle, boolean obrigatoria) {

        this.jogador = jogador;
        this.telaPrincipal = telaPrincipal;
        this.controle = controle;
        this.escolhaObrigatoria = obrigatoria;

        setTitle("Equipe");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        criarBotoesEquipe();

        JButton btnVoltar = new JButton("Voltar");
        if (obrigatoria) {
            btnVoltar.setEnabled(false);
        } else {
            btnVoltar.addActionListener(e -> dispose());
        }

        add(btnVoltar);
        setVisible(true);
    }

    /** Cria os botões dos Pokémon. */
    private void criarBotoesEquipe() {
        botoesPokemon.clear();

        var equipe = jogador.getEquipe();

        for (int i = 0; i < equipe.size(); i++) {
            Pokemon p = equipe.get(i);
            JButton btn = new JButton(formatarTexto(p));
            int index = i;

            btn.addActionListener(e -> selecionarPokemon(index));

            botoesPokemon.add(btn);
            add(btn);
        }

        revalidate();
        repaint();
    }

    /** Formata o texto exibido no botão. */
    private String formatarTexto(Pokemon p) {
        return p.getNome() + " - " + p.getHpAtual() + " / " + p.getMaxHp() + " HP";
    }

    /** Lida com a seleção de um Pokémon. */
    private void selecionarPokemon(int index) {
        Pokemon ativo = jogador.getPokemonAtivo();
        Pokemon escolhido = jogador.getEquipe().get(index);

        if (usandoItem) {
            controle.confirmarUsoItem(itemSelecionadoIndex, index);
            dispose();
            return;
        }

        if (escolhido == ativo) {
            JOptionPane.showMessageDialog(this,
                "Este Pokémon já está em campo!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (escolhido.desmaiado()) {
            JOptionPane.showMessageDialog(this,
                "Este Pokémon está desmaiado e não pode lutar!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sucesso;
        if (escolhaObrigatoria) {
            sucesso = controle.trocarPokemonObrigatoria(index);
        } else {
            sucesso = controle.trocarPokemon(index);
        }

        if (sucesso) {
            if (!escolhaObrigatoria) dispose();
        }
    }

    /** Ativa o modo de uso de item. */
    public void modoItem(int indexItem) {
        usandoItem = true;
        this.itemSelecionadoIndex = indexItem;
    }

    /** Atualiza o texto dos botões. */
    public void atualizarBotoes() {
        var equipe = jogador.getEquipe();
        for (int i = 0; i < botoesPokemon.size() && i < equipe.size(); i++) {
            botoesPokemon.get(i).setText(formatarTexto(equipe.get(i)));
        }
    }

    /** Autoriza o fechamento mesmo em modo obrigatório. */
    public void fecharObrigatoriamente() {
        forcarFechamentoObrigatorio = true;
        dispose();
    }

    /** Controla quando a janela pode ser fechada. */
    @Override
    public void dispose() {
        if (escolhaObrigatoria && !forcarFechamentoObrigatorio) return;
        super.dispose();
    }
}
