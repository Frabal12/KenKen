//design pattern {Singleton}

package core.components;

import javax.swing.*;

public final class MenuBar extends JMenuBar {

    private JMenu fileManager;
    private JMenu info;
    private static JMenu options;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem graphics;
    private JMenuItem gameInfo;
    private static JMenuItem restart;
    private static JMenuItem solutions;
    private static JMenuItem waitRestart;
    private static JMenuItem waitsolutions;
    private JProgressBar progressBar;
    private static MenuBar instance=null;
    private boolean initialized;

    private MenuBar(){
        fileManager = new JMenu("file");
        options = new JMenu("opzioni");
        info = new JMenu("info");
        save = new JMenuItem("salva");
        load = new JMenuItem("carica");
        graphics = new JMenuItem("opzioni grafiche");
        restart = new JMenuItem("riavvia");
        solutions = new JMenuItem("soluzioni");
        gameInfo = new JMenuItem("info del gioco");
        waitRestart=new JMenuItem("   ");
        waitsolutions=new JMenuItem();
        progressBar=new JProgressBar();
        progressBar.setIndeterminate(true);
        initialized=false;

        setSize(0,10);
        fileManager.add(save);
        fileManager.add(load);
        options.add(graphics);
        options.add(restart);
        options.add(solutions);
        waitsolutions.add(progressBar);
        options.add(waitRestart);
        options.add(waitsolutions);
        info.add(gameInfo);
        add(fileManager);
        add(options);
        add(info);
    }
    public static synchronized MenuBar getInstance() {
        if (instance == null) {
            instance = new MenuBar();
        }
        return instance;
    }


    public static void endSolutionSearch(){
        waitRestart.setVisible(false);
        waitsolutions.setVisible(false);
        restart.setVisible(true);
        solutions.setVisible(true);

    }
    public static void beginSolutionSearch(){
        waitRestart.setVisible(true);
        waitsolutions.setVisible(true);
        restart.setVisible(false);
        solutions.setVisible(false);

    }
    public JMenuItem getSaveOption() {
        return save;
    }

    public JMenuItem getLoadOption() {
        return load;
    }

    public JMenuItem getGraphicsOption() {
        return graphics;
    }

    public JMenuItem getRestartOption() {
        return restart;
    }

    public JMenuItem getSolutionsOption() {
        return solutions;
    }

    public JMenuItem getInfo(){return gameInfo;}

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }



}
