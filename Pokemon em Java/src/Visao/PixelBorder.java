package Visao;
import java.awt.*;
import javax.swing.border.AbstractBorder;

public class PixelBorder extends AbstractBorder {

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4)); // borda grossa estilo DS

        int arc = 18; // arredondamento leve estilo HGSS

        g2.drawRoundRect(
                x + 2, 
                y + 2, 
                w - 6, 
                h - 6,
                arc, arc
        );

        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(10,10,10,10);
    }
}
