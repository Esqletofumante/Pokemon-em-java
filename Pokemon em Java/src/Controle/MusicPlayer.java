package Controle;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.*;

public class MusicPlayer {

    private static Clip clip;

    public static void tocar(String caminho, boolean loop) {
        try {
            System.out.println("[MusicPlayer] Carregando via getResource: " + caminho);

            parar();

            // Carrega do .jar / pasta de resources / caminho interno
            InputStream in = MusicPlayer.class.getResourceAsStream(caminho);

            if (in == null) {
                System.out.println("ERRO: Arquivo NÃO encontrado em resources: " + caminho);
                return;
            }

            // Java exige stream com marca: usar BufferedInputStream
            BufferedInputStream buffIn = new BufferedInputStream(in);

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(buffIn);

            // Conversão opcional caso o formato não seja compatível
            AudioFormat baseFormat = audioInput.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream decoded = AudioSystem.getAudioInputStream(decodedFormat, audioInput);

            clip = AudioSystem.getClip();
            clip.open(decoded);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

            System.out.println("✔ Música iniciada!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parar() {
        try {
            if (clip != null) {
                clip.stop();
                clip.flush();
                clip.close();
                clip = null;
            }
        } catch (Exception ignored) {}
    }
}
