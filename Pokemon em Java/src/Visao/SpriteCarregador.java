package Visao;

import java.awt.*;
import javax.swing.*;

public class SpriteCarregador {
    //classe para carregar srpites
    public static ImageIcon carregar(String nomeArquivo, int largura, int altura) {
        var url = SpriteCarregador.class.getResource("/imagens/" + nomeArquivo);
        if (url == null) {
            System.err.println("ERRO: Imagem não encontrada: " + nomeArquivo);
            return null;
        }
        ImageIcon img = new ImageIcon(url);
        Image nova = img.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(nova);
    }
}
