package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Batalha {

    // Estado geral da batalha
    private Jogador jogador;
    private JogadorCPU cpu;
    private String ultimaMensagemCpu;
    private boolean turnoDoJogador = true;
    private boolean batalhaTerminou = false;

    public Batalha(Jogador jogador, JogadorCPU cpu) {
        this.jogador = Objects.requireNonNull(jogador);
        this.cpu = Objects.requireNonNull(cpu);
    }

    // Getters básicos
    public boolean isTurnoDoJogador() { return turnoDoJogador; }
    public boolean batalhaTerminou() { return batalhaTerminou; }
    public Jogador getJogador() { return jogador; }
    public JogadorCPU getCpu() { return cpu; }
    public void setTurnoDoJogador(boolean v) { this.turnoDoJogador = v; }
    public String getUltimaMensagemCpu() { return ultimaMensagemCpu; }

    // Ataque do jogador com resolução baseada em velocidade
    public List<String> atacarMensagens(int indexMovimento) {

        List<String> todas = new ArrayList<>();

        Pokemon pJog = jogador.getPokemonAtivo();
        Pokemon pCpu = cpu.getPokemonAtivo();

        if (pJog.getVel() >= pCpu.getVel()) {

            todas.addAll(processarAtaqueMensagens(
                pJog, pCpu, pJog.getMovimento(indexMovimento)
            ));

            if (!pCpu.desmaiado()) {
                todas.addAll(processarAtaqueMensagens(
                    pCpu, pJog, pCpu.getMovimento(cpu.escolherMovimento())
                ));
            }

        } else {

            todas.addAll(processarAtaqueMensagens(
                pCpu, pJog, pCpu.getMovimento(cpu.escolherMovimento())
            ));

            if (!pJog.desmaiado()) {
                todas.addAll(processarAtaqueMensagens(
                    pJog, pCpu, pJog.getMovimento(indexMovimento)
                ));
            }
        }

        if (!batalhaTerminou
                && pJog.getHpAtual() > 0
                && pCpu.getHpAtual() > 0) {

            todas.add("O que " + pJog.getNome() + " vai fazer?");
        }

        return todas;
    }

    // Uso de item pelo jogador
    public List<String> usarItem(Item item, int indexPokemon) {

        if (!turnoDoJogador) return null;
        if (batalhaTerminou) return null;

        List<String> msgs = new ArrayList<>();

        String msgItem = item.usar(jogador, cpu, indexPokemon);
        if (msgItem == null) return null;

        msgs.add(msgItem);

        if (batalhaTerminou) return msgs;

        msgs.addAll(finalizarTurnoMensagens());
        verificarFimDaBatalha();
        return msgs;
    }

    // Troca de Pokémon com ação posterior da CPU
    public List<String> trocarPokemonComCPU(int novoIndex) {

        if (!turnoDoJogador) return null;
        if (batalhaTerminou) return null;

        boolean ok = jogador.trocarPokemon(novoIndex);
        if (!ok) return null;

        List<String> msgs = new ArrayList<>();
        msgs.add("Vai " + jogador.getPokemonAtivo().getNome() + "!");

        msgs.addAll(finalizarTurnoMensagens());
        verificarFimDaBatalha();
        return msgs;
    }

    // Troca obrigatória sem ação da CPU
    public String trocarPokemonSemCPU(int index) {
        if (!turnoDoJogador) return null;
        boolean ok = jogador.trocarPokemon(index);
        if (!ok) return null;
        verificarFimDaBatalha();
        return "Vai " + jogador.getPokemonAtivo().getNome() + "!";
    }

    // Ação da CPU (atacar)
    public String acaoCPU() {
        if (batalhaTerminou) return "Batalha já terminou.";

        Pokemon atacante = cpu.getPokemonAtivo();
        if (atacante.desmaiado()) return atacante.getNome() + " está desmaiado e não pode agir.";
        if (atacante.isCapturado()) return atacante.getNome() + " capturado";

        int mov = cpu.escolherMovimento();
        Movimento m = atacante.getMovimento(mov);

        ultimaMensagemCpu = processarAtaque(atacante, jogador.getPokemonAtivo(), m);
        return ultimaMensagemCpu;
    }

    // Processamento de ataque (versão mensagem única)
    private String processarAtaque(Pokemon atacante, Pokemon alvo, Movimento move) {
        StringBuilder sb = new StringBuilder();

        if (atacante.desmaiado()) {
            sb.append(atacante.getNome()).append(" está desmaiado e não pode atacar!");
            return sb.toString();
        }

        if (Math.random() > move.getPrecisao()) {
            sb.append(atacante.getNome())
              .append(" usou ").append(move.getNome())
              .append(" mas errou!");
            return sb.toString();
        }

        int danoFinal = calcularDanoPokemothim(atacante, alvo, move);

        int hpAntes = alvo.getHpAtual();
        alvo.setHpAtual(Math.max(0, alvo.getHpAtual() - danoFinal));

        sb.append(atacante.getNome())
          .append(" usou ").append(move.getNome())
          .append(" e causou ").append(danoFinal)
          .append(" de dano (").append(hpAntes)
          .append(" -> ").append(alvo.getHpAtual()).append(").");

        move.aplicarEfeito(atacante, alvo);

        if (alvo.getHpAtual() <= 0 && !alvo.desmaiado()) {
            alvo.setFainted(true);
            sb.append("\n").append(alvo.getNome()).append(" desmaiou!");
            verificarFimDaBatalha();
        }

        return sb.toString();
    }

    // Processamento de ataque (versão lista de mensagens)
    public List<String> processarAtaqueMensagens(Pokemon atacante, Pokemon alvo, Movimento move) {

        List<String> msgs = new ArrayList<>();

        if (atacante.isCapturado()) return msgs;

        if (atacante.desmaiado()) {
            msgs.add(atacante.getNome() + " está desmaiado e não pode atacar!");
            return msgs;
        }

        if (Math.random() > move.getPrecisao()) {
            msgs.add(atacante.getNome() + " usou " + move.getNome() + ", mas errou!");
            return msgs;
        }

        int danoFinal = calcularDanoPokemothim(atacante, alvo, move);

        int antes = alvo.getHpAtual();
        alvo.setHpAtual(Math.max(0, antes - danoFinal));

        msgs.add(atacante.getNome() + " usou " + move.getNome() +
                 " e causou " + danoFinal + " de dano!");

        double mult = TypeChart.multiplicador(move.getTipo(), alvo.getTipo());

        if (mult >= 2.0) msgs.add("É super efetivo!");
        else if (mult <= 0.5 && mult > 0) msgs.add("Não é muito efetivo…");
        else if (mult == 0) msgs.add("Não teve efeito!");

        if (alvo.getHpAtual() <= 0) {
            msgs.add(alvo.getNome() + " desmaiou!");
            alvo.setFainted(true);
            verificarFimDaBatalha();
        }

        return msgs;
    }

    // Cálculo completo de dano com tipo e STAB
    private int calcularDanoPokemothim(Pokemon atacante, Pokemon alvo, Movimento move) {

        int level = 50;
        int basePower = move.getPoder();
        int atkStat = atacante.getAtk();
        int defStat = Math.max(1, alvo.getDef());

          // ((2 * Level / 5 + 2) * basePower * atkStat / defStat) / 50 + 2
        double etapa1 = (2.0 * level / 5.0) + 2.0;
        double etapa2 = etapa1 * basePower * atkStat / defStat;
        double danoBase = (etapa2 / 50.0) + 2.0;

        double multiplicador = 1.0;

        if (move.getTipo() != null && atacante.getTipo() != null &&
            move.getTipo().equalsIgnoreCase(atacante.getTipo())) {
            multiplicador *= 1.5;
        }

        multiplicador *= TypeChart.multiplicador(move.getTipo(), alvo.getTipo());

        double randomFactor = 0.85 + (Math.random() * 0.15);

        double danoFinalDouble = danoBase * multiplicador * randomFactor;

        int danoFinal = (int) Math.floor(danoFinalDouble);
        return Math.max(danoFinal, 1);
    }

    // Turno automático da CPU após ações não-baseadas em speed
    private List<String> finalizarTurnoMensagens() {

        List<String> msgs = new ArrayList<>();

        turnoDoJogador = false;

        if (!batalhaTerminou) {

            Pokemon atacante = cpu.getPokemonAtivo();
            Movimento mov = atacante.getMovimento(cpu.escolherMovimento());

            msgs.addAll(processarAtaqueMensagens(
                atacante,
                jogador.getPokemonAtivo(),
                mov
            ));

            verificarFimDaBatalha();

            if (!batalhaTerminou && jogador.getPokemonAtivo().getHpAtual() > 0) {
                msgs.add("O que " + jogador.getPokemonAtivo().getNome() + " vai fazer?");
            }
        }

        turnoDoJogador = true;
        return msgs;
    }

    // Verifica condições de término da batalha
    private void verificarFimDaBatalha() {
        if (!jogador.temPokemonVivo() ||
            !cpu.temPokemonVivo() ||
            cpu.getPokemonAtivo().isCapturado()) {

            batalhaTerminou = true;
        }
    }
}
