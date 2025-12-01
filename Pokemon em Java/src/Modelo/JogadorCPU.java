package Modelo;

public class JogadorCPU extends Jogador {

    public JogadorCPU() {
        super("Mewtwo");
        
        // Limpa qualquer equipe criada automaticamente no jogador
        getEquipe().clear();

        // Cria o único Pokémon do CPU
        Pokemon mewtwo = new Pokemon(
            "Mewtwo", "psiquico", 250, 120, 90, 130, "Mewtwo.png"
        );
        
        mewtwo.getMoves().add(new Movimento("Psíquico", 90, 1.0, "psiquico", "nulo", 0.0));
        mewtwo.getMoves().add(new Movimento("Raio", 110, 0.7, "eletrico", "paralyze", 0.3));
        mewtwo.getMoves().add(new Movimento("Aura Esfera", 90, 1.0, "lutador", "nulo", 0.0));
        mewtwo.getMoves().add(new Movimento("Raio de Gelo", 90, 1.0, "gelo", "freeze", 0.2));

        getEquipe().add(mewtwo);
    }

    // Impede criação automática de time da classe pai
    @Override
    public void criarTime() {
        // CPU não cria time apenas sobrescreve método vazio
    }

    @Override
    public Pokemon getPokemonAtivo() {
        // Mewtwo sempre é o Pokémon ativo
        return getEquipe().get(0);
    }
    // pega um movimento aleatório do poke
    public int escolherMovimento() {
        return (int)(Math.random() * getPokemonAtivo().getMoves().size());
    }
}
