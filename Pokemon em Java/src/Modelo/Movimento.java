
package Modelo;
public class Movimento {
    private final String nome;
    private final int poder;          
    private final double precisao;    
    private final String tipo;
    private final String efeito;       
    private final double efeitoChance;

   public Movimento(String nome, int poder, double precisao, String tipo, String efeito, double efeitoChance) {
        this.nome = nome;
        this.poder = poder;
        this.precisao = precisao;
        this.tipo = tipo;
        this.efeito = efeito;
        this.efeitoChance = efeitoChance;
    }


    public String getNome() { return nome; }
    public int getPoder() { return poder; }
    public double getPrecisao() { return precisao; }
    public String getTipo() { return tipo; }
    public String getEfeito() { return efeito; }
    public double getEfeitoChance() { return efeitoChance; }

    //tenta aplicar o efeito no pokemon
    public void aplicarEfeito(Pokemon atacante, Pokemon alvo) {
        if (efeito.equalsIgnoreCase("nulo")) return; // sem efeito de status
        if (!alvo.getStatus().equalsIgnoreCase("nulo")) return; // já afetado

        double roll = Math.random(); // valor entre 0.0 e 1.0
        if (roll < efeitoChance) {
            alvo.setStatus(efeito);
            System.out.println(alvo.getNome() + " foi afetado por " + efeito + "!");
        }
    }
    

    
}