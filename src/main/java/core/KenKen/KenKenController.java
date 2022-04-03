package core.KenKen;

import core.components.Menu;
import core.components.OptionsPanel;
import core.components.WritablePanel;
import utils.Backup;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class KenKenController {
    private KenKenView view;
    private KenKenModel model;

    public KenKenController(KenKenView view, KenKenModel model){
        this.view=view;
        this.model=model;
    }

    public void begin(){
        view.colorBorders(model.getTotalCoords());
        model.setPanels(view.getPanels());
        view.writeDataPanels(model.getPanelsData());
    }
    public void initializeListeners() {
        ActionListener verifyListener = e -> model.endVerify();
        initializeMenuBar(model.getBackupList());
        view.getVerifyButton().addActionListener(verifyListener);
        WritablePanel[][] panels = view.getPanels();
        for (int i = 0; i <panels.length ; i++)
            for (int j = 0; j < panels.length; j++)
                panels[i][j].addKeyListener(new TextListener(panels[i][j],panels));
    }
    private void initializeMenuBar(ArrayList<Object> backupList){
        String userDir = System.getProperty("user.home");
        JFileChooser chooser = new JFileChooser(userDir +"/Desktop");
        FileNameExtensionFilter knkFilter = new FileNameExtensionFilter("knk files (*.knk)", "knk");
        chooser.addChoosableFileFilter(knkFilter);
        chooser.setFileFilter(knkFilter);
        chooser.setAcceptAllFileFilterUsed(false);
        if(view.getTheMenuBar().isInitialized()){
            if(view.getTheMenuBar().getSaveOption().getActionListeners().length>0)
                view.getTheMenuBar().getSaveOption().removeActionListener(view.getTheMenuBar().getSaveOption().getActionListeners()[0]);
            view.getTheMenuBar().getSaveOption().addActionListener(e -> {
                try {
                    chooser.showSaveDialog(null);
                    Backup.save(backupList,chooser.getSelectedFile().getAbsolutePath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (NullPointerException ne){
                    JOptionPane.showMessageDialog(null,"file non salvato");
                }
            });
            return;
        }
        view.getTheMenuBar().setInitialized(true);
        view.getTheMenuBar().getSaveOption().addActionListener(e -> {
            try {
                model.setGameMatrix();
                backupList.set(1,model.getGameMatrix());
                chooser.showSaveDialog(null);
                Backup.save(backupList,chooser.getSelectedFile().getAbsolutePath());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NullPointerException ne){
                JOptionPane.showMessageDialog(null,"file non salvato");
            }
        });
        view.getTheMenuBar().getLoadOption().addActionListener(e -> {
            KenKen game;
            game=null;
            try {
                chooser.showOpenDialog(null);
                game = new KenKen((ArrayList<Object>) Backup.open(chooser.getSelectedFile().getAbsolutePath()));
            } catch (IOException | ClassNotFoundException ioException) {
                if (ioException instanceof FileNotFoundException){
                    JOptionPane.showMessageDialog(null,"file non trovato");
                    return;
                }
                ioException.printStackTrace();
            } catch (NullPointerException ne){
                JOptionPane.showMessageDialog(null,"nessun file selezionato");
                return;
            }
            view.dispose();
            model = game.getModel();
            for (Frame f:Frame.getFrames()) {
                if(f instanceof JFrame)
                    f.dispose();
            }
            if (game.getDim() == 6) OptionsPanel.setDimensions("6x6");
            game.getView().reloadDataWritten((int[][]) backupList.get(1));
            OptionsPanel.setKenKen(game);
            game.start();
            OptionsPanel.findSolutions(game);
        });
        view.getTheMenuBar().getRestartOption().addActionListener(e -> {
            KenKen game = new KenKen(OptionsPanel.getKenKenDimension());
            view.dispose();
            model=game.getModel();
            for (Frame f:Frame.getFrames()) {
                if(f instanceof JFrame)
                    f.dispose();
            }
            game.start();
            OptionsPanel.findSolutions(game);
        });
        view.getTheMenuBar().getInfo().addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Il Ken Ken è un gioco di logica. \n\nLo scopo " +
                    "del gioco è quello di risolvere una tabella (3x3 o 6x6) mettendo un numero per ogni casella, \n" +
                    "ogni numero però non deve essere ripetuto ne sulle righe ne sulle colonne, inoltre devono \n" +
                    "essere presenti tutti i numeri da 1 a n (numero massimo righe/colonne).\n" +
                    "Nella tabella sono presenti dei gruppi disposti casualmente, ogni gruppo ha una scritta, ad\n" +
                    "esempio 6x, eseguendo l'operazione della scritta, nel nostro caso x, tra i numeri contenuti\n" +
                    "nel gruppo si deve ottenere il risultato riportato, da esempio 6. \n\n" +
                    "E' possibile in ogni momento verificare che i vincoli siano rispettati tramite il tasto \"verifica\"." +
                    "", "informazioni", JOptionPane.INFORMATION_MESSAGE);
        });
        view.getTheMenuBar().getSolutionsOption().addActionListener(e -> OptionsPanel.getSolutionFounder().getSolutionsWindow().setVisible(true));
        view.getTheMenuBar().getGraphicsOption().addActionListener(e -> Menu.seeOptionsPanel());
    }
    class TextListener extends KeyAdapter {
        private WritablePanel panel;
        private WritablePanel[][] panels;
        public TextListener(WritablePanel panel, WritablePanel[][] panels){
            this.panel=panel;
            this.panels=panels;
        }
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if(panel.getText().length()>0){
                panel.getToolkit().beep();
                e.consume();

            }
            if (!((c >= '0') && (c <= '9') ||
                    (c == KeyEvent.VK_BACK_SPACE) ||
                    (c == KeyEvent.VK_DELETE))) {
                panel.getToolkit().beep();
                e.consume();
            }
            if(!OptionsPanel.isSelectedVerifyButtonOn() && c != KeyEvent.VK_DELETE) {
                for (int i = 0; i < panels.length; i++) {
                    for (int j = 0; j < panels.length; j++) {
                        if (panels[i][j].getText().equals(""))
                            return;
                    }
                }
                model.endVerify();
            }
        }
    }
}
