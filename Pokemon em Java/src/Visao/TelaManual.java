package Visao;
import java.awt.*;
import javax.swing.*;

class TelaManual extends JFrame {
public TelaManual() {
setTitle("Manual de Tipos");
setSize(500, 600);
setLocationRelativeTo(null);
setLayout(new BorderLayout());


JTextArea passo = new JTextArea();
passo.setFont(new Font("Monospaced", Font.BOLD, 14));
passo.setForeground(Color.black);
passo.setEditable(false);
passo.setLineWrap(true);
passo.setWrapStyleWord(true);


String[] linhas = new String[]{
"Formato: Atacante -> (Super contra/ Fraco contra/ Imune contra)",
"", "Normal -> (Super: nulo / Fraco: Pedra, Metal / Imune: Fantasma)",
"Fogo -> (Super: Grama, Gelo, Inseto, Metal / Fraco: Fogo, Agua, Pedra, Dragao / Imune: nulo)",
"Agua -> (Super: Fogo, Terra, Pedra / Fraco: Agua, Grama, Dragao / Imune: nulo)",
"Eletrico -> (Super: Agua, Voador / Fraco: Eletrico, Grama, Dragao / Imune: Terra)",
"Grama -> (Super: Water, Ground, Rock / Fraco: Fire, Grass, Poison, Flying, Bug, Dragon, Steel / Imune: nulo)",
"Gelo -> (Super: Grama, Terra, Voador, Dragao / Fraco: Fogo, Agua, Gelo, Metal / Imune: nulo)",
"Lutador -> (Super: Normal, Gelo, Pedra, Sombrio, Metal / Fraco: Veneno, Voador, Psiquico, Inseto, Fada / Imune: Fantasma)",
"Veneno -> (Super: Grama, Fada / Fraco: Veneno, Terra, Pedra, Fantasma / Imune: Metal)",
"Terra -> (Super: Fogo, Eletrico, Veneno, Pedra, Metal / Fraco: Grama, Inseto / Imune: Voador)",
"Voador -> (Super: Grama, Lutador, Inseto / Fraco: Eletrico, Pedra, Metal / Imune: nulo)",
"Psiquico -> (Super: Lutador, Veneno / Fraco: Psiquico, Metal / Imune: Sombrio)",
"Inseto -> (Super: Grama, Psiquico, Sombrio / Fraco: Fogo, Lutador, Veneno, Voador, Fantasma, Metal, Fada / Imune: nulo)",
"Pedra -> (Super: Fogo, Gelo, Voador, Inseto / Fraco: Lutador, Terra, Metal / Imune: nulo)",
"Fantasma -> (Super: Psiquico, Fantasma / Fraco: Sombrio / Imune: Normal, Lutador)",
"Dragao -> (Super: Dragao / Fraco: Metal / Imune: Fada)",
"Sombrio -> (Super: Psiquico, Fantasma / Fraco: Lutador, Sombrio, Fada / Imune: nulo)",
"Metal -> (Super: Gelo, Pedra, Fada / Fraco: Fogo, Agua, Eletrico, Metal / Imune: nulo)",
"Fada -> (Super: Lutador, Dragao, Sombrio / Fraco: Fogo, Veneno, Metal / Imune: nulo)\n",
"", "\nLegenda:",
"\nSuper = super efetivo (2x)",
"\nFraco = não muito efetivo (0.5x)",
"\nImune = sem efeito (0x)"
};


for (String l : linhas) passo.append(l + "");


JScrollPane scroll = new JScrollPane(passo);
scroll.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 6));


add(scroll, BorderLayout.CENTER);
setVisible(true);
}
}