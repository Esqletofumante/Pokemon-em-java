package Modelo;

import javax.swing.JOptionPane;

public class Pokebola extends Item {

    public Pokebola(int quantidade) {
        super("Pokébola", "pokebola.png", quantidade);
    }

    @Override
public String usar(Jogador jogador, JogadorCPU cpu, int indexPokemon) {

    if (!temQuantidade()) {
        JOptionPane.showMessageDialog(
            null,
            "Você não tem mais Pokébolas!",
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        return null;
    }

    consumir();

    Pokemon inimigo = cpu.getPokemonAtivo();

    double hpRatio = (double) inimigo.getHpAtual() / inimigo.getMaxHp();
    double chance = 0.3 * (1 - hpRatio);

    if (Math.random() < chance) {
        inimigo.setCapturado(true);
        return inimigo.getNome() + " foi capturado!";
    } else {
        return inimigo.getNome() + " escapou da Pokébola!";
    }
}

}
