package Visao;
import java.awt.*;
import javax.swing.*;


class BordaPokemonPanel extends JPanel {

    public BordaPokemonPanel() {

        setFont(new Font("Monospaced", Font.BOLD, 18));
        setBackground(new Color(245,245,245));

        // agora a borda é pixelada arredondada igual aos botões
        setBorder(new PixelBorder());
    }

    @Override
    public boolean isOpaque() {
        return true;
    }
}
