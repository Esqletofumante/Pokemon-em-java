package Modelo;

import javax.swing.JOptionPane;

public class Potion extends Item {

    public Potion(int quantidade) {
        super("Poção", "potion.png", quantidade);
    }

    @Override
public String usar(Jogador jogador, JogadorCPU cpu, int indexPokemon) {

    Pokemon alvo = jogador.getEquipe().get(indexPokemon);

    if (alvo.desmaiado()) {
        JOptionPane.showMessageDialog(
            null,
            alvo.getNome() + " está desmaiado e não pode ser curado!",
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        return null;
    }

    if (alvo.getHpAtual() == alvo.getMaxHp()) {
        JOptionPane.showMessageDialog(
            null,
            alvo.getNome() + " já está com a vida cheia!",
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        return null;
    }

    if (!temQuantidade()) {
        JOptionPane.showMessageDialog(
            null,
            "Você não tem mais Poções!",
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        return null;
    }

    consumir();

    int cura = (int)(alvo.getMaxHp() * 0.30);
    alvo.setHpAtual(Math.min(alvo.getHpAtual() + cura, alvo.getMaxHp()));

    return alvo.getNome() + " recuperou " + cura + " de HP!";
}

}
