package Modelo;

import javax.swing.JOptionPane;

public class Revive extends Item {

    public Revive(int quantidade) {
        super("Revive", "revive.png", quantidade);
    }

@Override
public String usar(Jogador jogador, JogadorCPU cpu, int indexPokemon) {

    Pokemon alvo = jogador.getEquipe().get(indexPokemon);

    if (!alvo.desmaiado()) {
        JOptionPane.showMessageDialog(
            null,
            alvo.getNome() + " ainda está vivo! Não é possível usar Revive.",
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        return null;
    }

    if (!temQuantidade()) {
        JOptionPane.showMessageDialog(
            null,
            "Você não tem mais Revives!",
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        return null;
    }

    consumir();

    int hp = alvo.getMaxHp() / 2;
    alvo.setHpAtual(hp);
    alvo.setFainted(false);

    return alvo.getNome() + " foi revivido com " + hp + " de HP!";
}

}
