package Controle;

import Modelo.*;
import Visao.*;
import java.util.List;

// Controla a interação entre as telas e a lógica da batalha. 
public class ControleBatalha {

    private Jogador jogador;
    private JogadorCPU cpu;
    private Batalha batalha;
    private TelaInicial tela;
    public static ControleBatalha instanciaAtual;

    private boolean inimigoCapturado = false;
    private TelaEquipe equipeObrigatoriaInstancia = null;

    public boolean isInimigoCapturado() { return inimigoCapturado; }

    public ControleBatalha(Jogador jogador, JogadorCPU cpu) {
        this.jogador = jogador;
        this.cpu = cpu;
        this.batalha = new Batalha(jogador, cpu);
        ControleBatalha.instanciaAtual = this;

        this.tela = new TelaInicial(jogador, cpu, this);
        tela.atualizarBattleHUD(jogador, cpu);
    }

    public void fecharTelaInicial() {
        if (tela != null) {
            tela.dispose();
            tela = null;
        }
    }

    // Ação de atacar (com mensagens, HUD e animações). 
    public void atacarJogador(int index) {
        List<String> msgs = batalha.atacarMensagens(index);
        if (msgs == null || msgs.isEmpty()) return;

        tela.getFilaMensagens().adicionarTodas(msgs);
        tela.animarDanoInimigo();

        if (msgs.stream().anyMatch(s ->
                s.contains("atacou você") || s.contains("sofreu dano"))) {
            tela.animarDanoJogador();
        }

        tela.getFilaMensagens().iniciarExibicao();

        javax.swing.Timer t = new javax.swing.Timer(2000 * msgs.size(), e -> {
            tela.atualizarBattleHUD(jogador, cpu);
            checarFimDaBatalha();
            if (batalha.batalhaTerminou()) return;

            if (jogador.getPokemonAtivo().desmaiado()) {
                abrirTelaEquipeObrigatoria();
            }
        });

        t.setRepeats(false);
        t.start();
    }

    // Troca voluntária do jogador. 
    public boolean trocarPokemon(int index) {
        List<String> msgs = batalha.trocarPokemonComCPU(index);
        if (msgs == null || msgs.isEmpty()) {
            tela.atualizarMensagem("Não foi possível trocar o Pokémon!");
            return false;
        }

        tela.atualizarBattleHUD(jogador, cpu);
        tela.getFilaMensagens().adicionarTodas(msgs);

        if (msgs.stream().anyMatch(s ->
                s.contains("atacou você") || s.contains("sofreu dano"))) {
            tela.animarDanoJogador();
        }

        tela.getFilaMensagens().iniciarExibicao();

        javax.swing.Timer t = new javax.swing.Timer(2000 * msgs.size(), e -> {
            tela.atualizarBattleHUD(jogador, cpu);
            checarFimDaBatalha();
            if (batalha.batalhaTerminou()) return;

            if (jogador.getPokemonAtivo().desmaiado()) {
                abrirTelaEquipeObrigatoria();
            }
        });

        t.setRepeats(false);
        t.start();
        return true;
    }

    // Troca obrigatória quando o Pokémon do jogador desmaia. 
    public boolean trocarPokemonObrigatoria(int index) {
        String msg = batalha.trocarPokemonSemCPU(index);
        if (msg == null) {
            tela.atualizarMensagem("Não foi possível trocar o Pokémon!");
            return false;
        }

        tela.atualizarBattleHUD(jogador, cpu);

        tela.mostrarMensagemComDelay(msg, () -> {
            tela.atualizarBattleHUD(jogador, cpu);
            batalha.setTurnoDoJogador(true);

            checarFimDaBatalha();
            if (batalha.batalhaTerminou()) return;

            if (equipeObrigatoriaInstancia != null) {
                equipeObrigatoriaInstancia.fecharObrigatoriamente();
            }
        });

        return true;
    }

    // Abre a tela de resultado ao fim da batalha. 
    private void abrirTelaDeResultado() {
        new TelaFimDeBatalha(jogador, cpu, this);
    }

    // Verifica se a batalha acabou e abre a tela final. 
    private void checarFimDaBatalha() {
        if (batalha.batalhaTerminou()) {
            abrirTelaDeResultado();
        }
    }

    // Abre a tela de troca obrigatória. 
    private void abrirTelaEquipeObrigatoria() {
        if (equipeObrigatoriaInstancia != null && equipeObrigatoriaInstancia.isShowing()) return;
        equipeObrigatoriaInstancia = new TelaEquipe(jogador, tela, this, true);
    }

    // Usa item (Pokébola ou item normal). 
    public void usarItem(int indexItem) {

        Item item = jogador.getItens().get(indexItem);

        if (item.getQuantidade() <= 0) {
            tela.atualizarMensagem("Você não possui mais este item!");
            return;
        }

        // Uso direto de Pokébolas
        if (item instanceof Pokebola) {

            List<String> msgs = batalha.usarItem(item, 0);
            if (msgs == null || msgs.isEmpty()) return;

            boolean capturado = msgs.get(0).contains("foi capturado");

            tela.atualizarMensagem("Você jogou uma Pokébola!");
            tela.mostrarSpritePokebola();
            tela.repaint();
            tela.revalidate();

            javax.swing.Timer delay = new javax.swing.Timer(1800, e -> {

                if (capturado) {
                    inimigoCapturado = true;
                } else {
                    tela.restaurarSpriteInimigo(cpu);
                }

                tela.getFilaMensagens().adicionarTodas(msgs);
                tela.getFilaMensagens().iniciarExibicao();

                javax.swing.Timer t = new javax.swing.Timer(2000 * msgs.size(), ev -> {
                    tela.atualizarBattleHUD(jogador, cpu);
                    checarFimDaBatalha();
                    if (jogador.getPokemonAtivo().desmaiado()) {
                        abrirTelaEquipeObrigatoria();
                    }
                });

                t.setRepeats(false);
                t.start();
            });

            delay.setRepeats(false);
            delay.start();
            return;
        }

        // Itens normais exigem escolha de Pokémon
        equipeObrigatoriaInstancia = new TelaEquipe(jogador, tela, this, false);
        equipeObrigatoriaInstancia.modoItem(indexItem);
    }

    // Confirma o uso do item em um Pokémon específico. 
    public void confirmarUsoItem(int indexItem, int indexPokemon) {

        Item item = jogador.getItens().get(indexItem);
        List<String> msgs = batalha.usarItem(item, indexPokemon);
        if (msgs == null || msgs.isEmpty()) return;

        tela.getFilaMensagens().adicionarTodas(msgs);

        if (msgs.stream().anyMatch(s ->
                s.contains("atacou você") || s.contains("sofreu dano"))) {
            tela.animarDanoJogador();
        }

        tela.getFilaMensagens().iniciarExibicao();

        javax.swing.Timer t = new javax.swing.Timer(2000 * msgs.size(), e -> {
            tela.atualizarBattleHUD(jogador, cpu);
            checarFimDaBatalha();
            if (batalha.batalhaTerminou()) return;

            if (jogador.getPokemonAtivo().desmaiado()) {
                abrirTelaEquipeObrigatoria();
            }
        });

        t.setRepeats(false);
        t.start();

        equipeObrigatoriaInstancia.fecharObrigatoriamente();
    }

    // Getters
    public Jogador getJogador() { return jogador; }
    public Batalha getBatalha() { return batalha; }
}
