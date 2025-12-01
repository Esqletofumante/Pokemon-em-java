package Modelo;

import java.util.ArrayList;

public class Jogador {

    private String nome;
    private ArrayList<Pokemon> equipe;
    private ArrayList<Item> itens;
    private int indiceAtivo;

    public Jogador(String nome) {
        this.nome = nome;
        equipe = new ArrayList<>();
        itens = new ArrayList<>();
        indiceAtivo = 0;

        criarTime();
        criarItens();
    }

    public ArrayList<Pokemon> getEquipe() {
        return equipe;
    }

    public ArrayList<Item> getItens() {
        return itens;
    }

    /** Cria a equipe padrão do jogador. */
    protected void criarTime() {
        Pokemon venussaur = new Pokemon("Venusaur", "planta", 80, 100, 100, 80, "venossauro.png");
        venussaur.getMoves().add(new Movimento("Folha Navalha", 60, 1.0, "planta", "nulo", 0));
        venussaur.getMoves().add(new Movimento("Boa Noite Cinderela", 0, 0.85, "planta", "sleep", 1.0));
        venussaur.getMoves().add(new Movimento("Bomba Lodo", 90, 1.0, "veneno", "poison", 0.3));
        venussaur.getMoves().add(new Movimento("Raio Solar", 120, 0.75, "planta", "nulo", 0));

        Pokemon rapidash = new Pokemon("Rapidash", "fogo", 65, 100, 60, 140, "Rapidash.png");
        rapidash.getMoves().add(new Movimento("Lança Chamas", 90, 1.0, "fogo", "burn", 0.1));
        rapidash.getMoves().add(new Movimento("Rajada de Fogo", 110, 0.85, "fogo", "burn", 0.3));
        rapidash.getMoves().add(new Movimento("Pisotear", 65, 1.0, "normal", "nulo", 0));
        rapidash.getMoves().add(new Movimento("Inferno", 100, 0.5, "fogo", "burn", 1.0));

        Pokemon snorlax = new Pokemon("Snorlax", "normal", 160, 110, 65, 30, "Snorlax.png");
        snorlax.getMoves().add(new Movimento("Bate Corpo", 85, 1.0, "normal", "paralyze", 0.3));
        snorlax.getMoves().add(new Movimento("Terremoto", 100, 1.0, "terra", "nulo", 0.0));
        snorlax.getMoves().add(new Movimento("Mordiscar", 70, 1.0, "noturno", "nulo", 0));
        snorlax.getMoves().add(new Movimento("Hyper Raio", 150, 0.5, "normal", "nulo", 0));

        Pokemon gengar = new Pokemon("Gengar", "fantasma", 60, 130, 60, 110, "Gengar.png");
        gengar.getMoves().add(new Movimento("Bola Fantasma", 80, 1.0, "fantasma", "nulo", 0.0));
        gengar.getMoves().add(new Movimento("Pulso Sombrio", 80, 1.0, "noturno", "nulo", 0.0));
        gengar.getMoves().add(new Movimento("Bomba Lodo", 90, 1.0, "veneno", "poison", 0.3));
        gengar.getMoves().add(new Movimento("Psiquico", 90, 1.0, "psiquico", "nulo", 0));

        Pokemon machamp = new Pokemon("Machamp", "lutador", 90, 130, 85, 55, "Machamp.png");
        machamp.getMoves().add(new Movimento("Mega Soco", 80, 0.8, "normal", "paralyze", 0.3));
        machamp.getMoves().add(new Movimento("Submissão", 100, 1.0, "lutador", "nulo", 0.0));
        machamp.getMoves().add(new Movimento("Soco de Fogo", 75, 1.0, "fogo", "burn", 0.3));
        machamp.getMoves().add(new Movimento("Soco de Gelo", 75, 1.0, "gelo", "freeze", 0.3));

        Pokemon gyarados = new Pokemon("Gyarados", "agua", 95, 125, 79, 81, "Gyarados.png");
        gyarados.getMoves().add(new Movimento("Cauda de Água", 90, 0.9, "agua", "nulo", 0.0));
        gyarados.getMoves().add(new Movimento("Terremoto", 100, 1.0, "terra", "nulo", 0.0));
        gyarados.getMoves().add(new Movimento("Mordiscar", 70, 1.0, "noturno", "nulo", 0));
        gyarados.getMoves().add(new Movimento("Furacão", 130, 0.7, "voador", "nulo", 0));

        equipe.add(venussaur);
        equipe.add(rapidash);
        equipe.add(snorlax);
        equipe.add(machamp);
        equipe.add(gengar);
        equipe.add(gyarados);
    }

    /** Cria os itens iniciais. */
    private void criarItens() {
        itens.add(new Potion(5));
        itens.add(new Revive(2));
        itens.add(new Pokebola(10));
    }

    public Pokemon getPokemonAtivo() {
        return equipe.get(indiceAtivo);
    }

    /** Troca o Pokémon ativo, se possível. */
    public boolean trocarPokemon(int index) {
        if (index >= 0 &&
            index < equipe.size() &&
            !equipe.get(index).desmaiado()) {
            indiceAtivo = index;
            return true;
        }
        return false;
    }

    /** Verifica se existe um Pokémon não desmaiado. */
    public boolean temPokemonVivo() {
        for (Pokemon p : equipe)
            if (!p.desmaiado()) return true;
        return false;
    }
}
