package Visao;

import java.awt.*;
import javax.swing.*;

class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel(String img) {
        setLayout(null);
        var url = getClass().getResource("/imagens/" + img);
        if (url != null) {
            backgroundImage = new ImageIcon(url).getImage();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
