package Visao;
import java.awt.*;
import javax.swing.*;

class BotaoPokemon extends JButton {

    public BotaoPokemon(String texto, Color cor) {
        super(texto);

        setBackground(cor);
        setForeground(Color.BLACK);

        setFont(new Font("Monospaced", Font.BOLD, 18));
        setFocusPainted(false);

        // borda pixelada arredondada
        setBorder(new PixelBorder());
    }
}
