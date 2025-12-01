
package Modelo;
import java.util.ArrayList;


public class Pokemon{
    private final String nome; 
    private final String tipo;
    private final int atk;
    private final int def;
    private final int vel;
    private final int maxHp;
    private final String Sprite;
    private int hpAtual; 
    private String status; 
    private final ArrayList<Movimento> moves;
    private boolean fainted; 
    private boolean capturado = false;


    public Pokemon(String nome, String tipo, int hp, int atk, int def, int vel, String Sprite){
        this.nome = nome;
        this.tipo = tipo;
        this.maxHp = hp;
        this.hpAtual = hp;
        this.atk = atk;
        this.def = def; 
        this.vel = vel;
        this.moves = new ArrayList<>();
        this.fainted = false; 
        this.status = "nulo";
        this.Sprite = Sprite;
    }

     public String getNome() { return nome;}

    public String getTipo() {return tipo;}

    public int getHpAtual() {return hpAtual;}

    public void setHpAtual(int v) {this.hpAtual = v; }

    public int getMaxHp() {return maxHp;}

    public int getAtk() {return atk;}

    public int getDef() {return def;}

    public int getVel() {return vel;}

    public ArrayList<Movimento> getMoves() {return moves;}

    public Movimento getMovimento(int index) {
        return moves.get(index);
    }

    public boolean desmaiado() {return fainted;}

    public void setFainted(boolean v){ this.fainted = v;}

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getSprite() { return Sprite;}

    
    
    public void setCapturado(boolean c) { this.capturado = c; }
    public boolean isCapturado() { return capturado; }


    public boolean estaVivo() {
        return !fainted;
    }

 
   
}