package Visao;

import java.awt.*;
import javax.swing.*;

public class PokemonHud extends JPanel {

    private JLabel lblNome;
    private JProgressBar barraHP;

    // Valor usado na animação
    private int hpAtualAnimado = 0;

    public PokemonHud() {
        setLayout(null);
        setOpaque(false);
    }

    /** Configura HUD na inicialização (nome e barra). */
    public void configurar(String nome, int hpAtual, int hpMaximo) {
        removeAll();
        repaint();

        lblNome = new JLabel(nome);
        lblNome.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblNome.setBounds(10, 5, 180, 25);
        add(lblNome);

        barraHP = new JProgressBar(0, 100);
        barraHP.setBounds(10, 35, 180, 18);
        barraHP.setStringPainted(true);
        add(barraHP);

        atualizarHPInstantaneo(hpAtual, hpMaximo);
    }

    /** Atualiza HP sem animação. */
    public void atualizarHPInstantaneo(int hp, int maxHp) {
        if (lblNome == null) {
            lblNome = new JLabel("");
            lblNome.setFont(new Font("Monospaced", Font.BOLD, 16));
            lblNome.setBounds(10, 5, 180, 25);
            add(lblNome);
        }

        if (barraHP == null) {
            barraHP = new JProgressBar(0, 100);
            barraHP.setBounds(10, 35, 180, 18);
            barraHP.setStringPainted(true);
            add(barraHP);
        }

        hpAtualAnimado = hp;
        int percent = (int)((hp / (double) maxHp) * 100);

        barraHP.setValue(percent);
        barraHP.setString(hp + "/" + maxHp);

        atualizarCor(percent);
        revalidate();
        repaint();
    }

    /** Atualiza apenas o nome. */
    public void setNome(String nome) {
        if (lblNome == null) {
            lblNome = new JLabel(nome);
            lblNome.setFont(new Font("Monospaced", Font.BOLD, 16));
            lblNome.setBounds(10, 5, 180, 25);
            add(lblNome);
        } else {
            lblNome.setText(nome);
        }
        revalidate();
        repaint();
    }

    /** Anima mudança de HP. */
    public void animarHP(int hpFinal, int hpMaximo) {
        if (barraHP == null) {
            atualizarHPInstantaneo(hpFinal, hpMaximo);
            return;
        }

        final int[] hpInicialRef = new int[]{hpAtualAnimado};
        Timer timer = new Timer(15, null);

        timer.addActionListener(e -> {
            if (hpInicialRef[0] > hpFinal) hpInicialRef[0]--;
            else if (hpInicialRef[0] < hpFinal) hpInicialRef[0]++;

            hpAtualAnimado = hpInicialRef[0];

            int percent = (int)((hpAtualAnimado / (double) hpMaximo) * 100);
            barraHP.setValue(percent);
            barraHP.setString(hpAtualAnimado + "/" + hpMaximo);

            atualizarCor(percent);

            if (hpInicialRef[0] == hpFinal) {
                ((Timer)e.getSource()).stop();
            }
        });

        timer.start();
    }

    /** Define cor conforme porcentagem. */
    private void atualizarCor(int percent) {
        if (barraHP == null) return;

        if (percent > 50) barraHP.setForeground(new Color(0, 200, 0));
        else if (percent > 20) barraHP.setForeground(new Color(230, 200, 0));
        else barraHP.setForeground(new Color(200, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(240, 240, 240));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }
}
