package core.components;

import core.KenKen.KenKen;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import utils.Backup;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public final class Menu extends JFrame {
    private static OptionsPanel optionsPanel;
    private static MediaPlayer backgroundMusic;
    private static Menu instance=null;

    public static synchronized Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    private Menu() {
        URL resource = Thread.currentThread().getContextClassLoader()
                .getResource("sounds/background_sound.mp3");
        Media music = new Media(resource.toString());
        backgroundMusic = new MediaPlayer(music);
        backgroundMusic.play();
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
        optionsPanel = OptionsPanel.getInstance();
        JButton newGameButton = new JButton("nuova partita");
        JButton oldGameButton = new JButton("carica partita");
        setSize(300, 300);
        setResizable(false);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(150, 50));
        oldGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        oldGameButton.setMaximumSize(new Dimension(150, 50));
        add(Box.createRigidArea(new Dimension(0, getHeight() / 4)));
        add(newGameButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(oldGameButton);
        oldGameButton.addActionListener(e -> {
            KenKen game = null;
            try {
                String userDir = System.getProperty("user.home");
                JFileChooser chooser = new JFileChooser(userDir + "/Desktop");
                FileNameExtensionFilter knkFilter = new FileNameExtensionFilter("knk files (*.knk)", "knk");
                chooser.addChoosableFileFilter(knkFilter);
                chooser.setFileFilter(knkFilter);
                chooser.showOpenDialog(null);
                String path = chooser.getSelectedFile().getAbsolutePath();
                game = new KenKen((ArrayList<Object>) Backup.open(path));
            } catch (IOException | ClassNotFoundException ioException) {
                if (ioException instanceof FileNotFoundException) {
                    JOptionPane.showMessageDialog(null, "file non trovato");
                    return;
                }
                ioException.printStackTrace();
            } catch (NullPointerException ne) {
                JOptionPane.showMessageDialog(null, "nessun file selezionato");
                return;
            }
            optionsPanel.setClosingOperation(game);
            OptionsPanel.findSolutions(game);
            if (game.getDim() == 6) optionsPanel.setDimensions("6x6");
            game.getView().reloadDataWritten((int[][]) game.getModel().getBackupList().get(1));
            OptionsPanel.setKenKen(game);
            game.start();
            dispose();
        });
        newGameButton.addActionListener(e -> {
            KenKen kenKen = new KenKen(3);
            optionsPanel.setClosingOperation(kenKen);
            OptionsPanel.findSolutions(kenKen);
            kenKen.start();
            dispose();
        });
    }
    public void start(){
        setVisible(true);
    }
    public static void seeOptionsPanel(){
        optionsPanel.setVisible(true);
    }
    public static MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }
}
