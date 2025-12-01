package Visao;
import java.util.*;

public class MessageQueue {

    private Queue<String> fila = new LinkedList<>();
    private TelaInicial tela;
    private Runnable onMensagemMostrada;

public void setOnMensagemMostrada(Runnable r) {
    this.onMensagemMostrada = r;
}


    public MessageQueue(TelaInicial tela) {
        this.tela = tela;
    }

    public void adicionar(String msg) {
        fila.add(msg);
    }

    public void adicionarTodas(List<String> msgs) {
        fila.addAll(msgs);
    }

   public void exibirProxima() {
    if (fila.isEmpty()) return;

    String msg = fila.poll();
    tela.atualizarMensagem(msg);

    // dispara o callback A CADA MENSAGEM
    if (onMensagemMostrada != null)
        onMensagemMostrada.run();

    javax.swing.Timer timer = new javax.swing.Timer(2500, e -> exibirProxima());
    timer.setRepeats(false);
    timer.start();
}


    public void iniciarExibicao() {
        exibirProxima();
    }
}

