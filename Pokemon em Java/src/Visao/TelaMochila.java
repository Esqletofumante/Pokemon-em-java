package Visao;

import Controle.ControleBatalha;
import java.awt.*;
import javax.swing.*;

public class TelaMochila extends JFrame {

    private ControleBatalha controle;

    public TelaMochila(ControleBatalha controle) {
        this.controle = controle;

        setTitle("Mochila");
        setSize(350, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 5, 5));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Itens exibidos na mochila
        add(criarBotaoItem("pocao.png", "Poção", 0));
        add(criarBotaoItem("revive.png", "Revive", 1));
        add(criarBotaoItem("pokebola.png", "Pokébola", 2));

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> dispose());
        add(btnVoltar);

        setVisible(true);
    }

    /**
     * Cria um botão visual representando um item da mochila.
     */
    private JButton criarBotaoItem(String sprite, String nome, int indexItem) {

        JPanel painel = new JPanel(new BorderLayout());

        ImageIcon icon = SpriteCarregador.carregar(sprite, 32, 32);
        painel.add(new JLabel(icon), BorderLayout.WEST);
        painel.add(new JLabel(nome, SwingConstants.CENTER), BorderLayout.CENTER);

        // Quantidade do item
        int qtd = ControleBatalha.instanciaAtual
                .getJogador()
                .getItens()
                .get(indexItem)
                .getQuantidade();

        painel.add(new JLabel("x" + qtd), BorderLayout.EAST);

        JButton botao = new JButton();
        botao.setLayout(new BorderLayout());
        botao.add(painel, BorderLayout.CENTER);

        // Ação ao clicar: usar item e fechar tela
        botao.addActionListener(e -> {
            controle.usarItem(indexItem);
            dispose();
        });

        return botao;
    }
}
