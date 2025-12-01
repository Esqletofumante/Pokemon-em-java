package Visao;

import Controle.ControleBatalha;
import Controle.MusicPlayer;
import Modelo.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TelaInicial extends JFrame {

    private ControleBatalha controle;

    private Jogador j;
    private JogadorCPU cpu;

    private TelaEquipe telaEquipeAberta;
    private TelaLuta telaLutaAberta;
    private TelaMochila telaMochilaAberta;

    private MessageQueue filaMensagens;
    private MusicPlayer musica;

    private JButton btnLutar;
    private JButton btnMochila;
    private JButton btnEquipe;
    private JButton btnManual;

    private JPanel msgContainer;
    private JLabel lblMensagem;

    private JPanel fundoHUD;
    private JPanel painelBotoes;

    private BackgroundPanel painelFundo;

    private JLabel lblSpriteInimigo;
    private JLabel lblSpriteJogador;
    private PokemonHud hudInimigo;
    private PokemonHud hudJogador;

    public TelaInicial(Jogador j, JogadorCPU cpu, ControleBatalha controle) {
        this.j = j;
        this.cpu = cpu;
        this.controle = controle;

        filaMensagens = new MessageQueue(this);

        setTitle("Batalha Pokémon");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel de fundo
        painelFundo = new BackgroundPanel("PlanoDeFundo.png");
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        // HUD inferior
        fundoHUD = new JPanel(null);
        fundoHUD.setBackground(new Color(200, 200, 200));
        painelFundo.add(fundoHUD);

        // Caixa de mensagens
        msgContainer = new BordaPokemonPanel();
        msgContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

        lblMensagem = new JLabel("");
        lblMensagem.setFont(new Font("Monospaced", Font.BOLD, 20));
        msgContainer.add(lblMensagem);

        // Mensagens iniciais da batalha
        filaMensagens.adicionar("Um Mewtwo selvagem apareceu!");
        filaMensagens.adicionar("O que você vai fazer?");
        filaMensagens.iniciarExibicao();

        fundoHUD.add(msgContainer);

        // Painel de botões
        painelBotoes = new JPanel(null);
        painelBotoes.setOpaque(false);
        fundoHUD.add(painelBotoes);

        btnLutar  = new BotaoPokemon("LUTAR",   new Color(255,120,120));
        btnMochila= new BotaoPokemon("MOCHILA", new Color(255,220,120));
        btnEquipe = new BotaoPokemon("POKÉMON", new Color(180,200,255));
        btnManual = new BotaoPokemon("MANUAL",  new Color(180,255,180));

        painelBotoes.add(btnLutar);
        painelBotoes.add(btnMochila);
        painelBotoes.add(btnEquipe);
        painelBotoes.add(btnManual);

        // Ações dos botões
        btnLutar.addActionListener(e -> {
            if (telaEquipeAberta != null) telaEquipeAberta.dispose();
            if (telaLutaAberta != null) telaLutaAberta.dispose();
            if (telaMochilaAberta != null) telaMochilaAberta.dispose();
            telaLutaAberta = new TelaLuta(j, controle);
        });

        btnMochila.addActionListener(e -> {
            if (telaEquipeAberta != null) telaEquipeAberta.dispose();
            if (telaLutaAberta != null) telaLutaAberta.dispose();
            if (telaMochilaAberta != null) telaMochilaAberta.dispose();
            telaMochilaAberta = new TelaMochila(controle);
        });

        btnEquipe.addActionListener(e -> {
            if (telaEquipeAberta != null) telaEquipeAberta.dispose();
            if (telaLutaAberta != null) telaLutaAberta.dispose();
            if (telaMochilaAberta != null) telaMochilaAberta.dispose();
            telaEquipeAberta = new TelaEquipe(j, this, controle);
        });

        btnManual.addActionListener(e -> new TelaManual());

        // Sprites e HUD
        lblSpriteInimigo = new JLabel();
        lblSpriteJogador = new JLabel();
        hudInimigo = new PokemonHud();
        hudJogador = new PokemonHud();

        painelFundo.add(lblSpriteInimigo);
        painelFundo.add(lblSpriteJogador);
        painelFundo.add(hudInimigo);
        painelFundo.add(hudJogador);

        atualizarBattleHUD(j, cpu);

        // Ajusta elementos ao redimensionar
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                ajustarLayout();
            }
        });

        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        MusicPlayer.tocar("/musica/batalha.wav", true);
    }

    // Controle da fila de mensagens
    public MessageQueue getFilaMensagens() {
        return filaMensagens;
    }

    public void atualizarMensagem(String msg) {
        lblMensagem.setText(msg);
    }

    public void mostrarMensagemComDelay(String msg, Runnable depois) {
        atualizarMensagem(msg);
        javax.swing.Timer timer = new javax.swing.Timer(3000, e -> depois.run());
        timer.setRepeats(false);
        timer.start();
    }

    // Atualiza sprites e HUD
    public void atualizarBattleHUD(Jogador player, JogadorCPU inimigo) {
        if (player == null || inimigo == null) return;

        var pokePlayer = player.getPokemonAtivo();
        var pokeCPU = inimigo.getPokemonAtivo();

        if (!ControleBatalha.instanciaAtual.isInimigoCapturado()) {
            pokeCPU = inimigo.getPokemonAtivo();
            lblSpriteInimigo.setIcon(
                SpriteCarregador.carregar(pokeCPU.getSprite(), 220, 200)
            );
        }

        lblSpriteJogador.setIcon(
            SpriteCarregador.carregar(pokePlayer.getSprite(), 260, 240)
        );

        hudJogador.configurar(
            pokePlayer.getNome(),
            pokePlayer.getHpAtual(),
            pokePlayer.getMaxHp()
        );

        hudInimigo.configurar(
            pokeCPU.getNome(),
            pokeCPU.getHpAtual(),
            pokeCPU.getMaxHp()
        );
    }

    // Alteração temporária para a animação da pokébola
    public void mostrarSpritePokebola() {
        lblSpriteInimigo.setIcon(
            SpriteCarregador.carregar("pokebola.png", 120, 120)
        );
    }

    public void restaurarSpriteInimigo(JogadorCPU cpu) {
        if (ControleBatalha.instanciaAtual.isInimigoCapturado()) return;
        var pokeCPU = cpu.getPokemonAtivo();
        lblSpriteInimigo.setIcon(
            SpriteCarregador.carregar(pokeCPU.getSprite(), 220, 200)
        );
    }

    // Animações de dano
    public void tremerSprite(JLabel lbl) {
        int x = lbl.getX();
        int y = lbl.getY();

        new javax.swing.Timer(40, new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                int offset = (count % 2 == 0 ? 5 : -5);
                lbl.setLocation(x + offset, y);
                count++;
                if (count > 6) {
                    lbl.setLocation(x, y);
                    ((javax.swing.Timer)e.getSource()).stop();
                }
            }
        }).start();
    }

    public void piscarSprite(JLabel lbl) {
        new javax.swing.Timer(120, new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                lbl.setVisible(!lbl.isVisible());
                count++;
                if (count >= 6) {
                    lbl.setVisible(true);
                    ((javax.swing.Timer)e.getSource()).stop();
                }
            }
        }).start();
    }

    public void animarDanoInimigo() {
        tremerSprite(lblSpriteInimigo);
        piscarSprite(lblSpriteInimigo);
    }

    public void animarDanoJogador() {
        tremerSprite(lblSpriteJogador);
        piscarSprite(lblSpriteJogador);
    }

    // Ajuste de layout dinâmico
    private void ajustarLayout() {
        int w = getWidth();
        int h = getHeight();

        painelFundo.setBounds(0, 0, w, h);
        painelFundo.repaint();

        int hudHeight = (int)(h * 0.25);
        int hudY = h - hudHeight;

        fundoHUD.setBounds(0, hudY, w, hudHeight);

        msgContainer.setBounds(
            10, 10,
            (w / 2) - 20,
            hudHeight - 20
        );

        painelBotoes.setBounds(
            (w / 2) + 10,
            10,
            (w / 2) - 20,
            hudHeight - 20
        );

        int bw = 310;
        int bh = 52;
        int esp = 18;

        btnLutar.setBounds(20, 10, bw, bh);
        btnMochila.setBounds(20 + bw + 20, 10, bw, bh);

        btnEquipe.setBounds(20, 10 + bh + esp, bw, bh);
        btnManual.setBounds(20 + bw + 20, 10 + bh + esp, bw, bh);

        lblSpriteInimigo.setBounds(780, h - hudHeight - 360, 220, 200);
        lblSpriteJogador.setBounds(280, h - hudHeight - 200, 260, 240);

        hudInimigo.setBounds(w - 320, h - hudHeight - 430, 250, 70);
        hudJogador.setBounds(60, h - hudHeight - 260, 250, 70);

        repaint();
    }

    public JogadorCPU getCPU() {
        return cpu;
    }
}
