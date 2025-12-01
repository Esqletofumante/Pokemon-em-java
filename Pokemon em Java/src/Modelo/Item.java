package Modelo;

public abstract class Item {

    protected String nome;
    protected String sprite;
    protected int quantidade;

    public Item(String nome, String sprite, int quantidade) {
        this.nome = nome;
        this.sprite = sprite;
        this.quantidade = quantidade;
    }

    public String getNome() { return nome; }
    public String getSprite() { return sprite; }
    public int getQuantidade() { return quantidade; }
    
    public boolean temQuantidade() {
        return quantidade > 0;
    }

    public void consumir() {
        if (quantidade > 0) quantidade--;
    }

    // Cada item implementa seu próprio efeito
    public abstract String usar(Jogador jogador, JogadorCPU cpu, int indexPokemon);
}
