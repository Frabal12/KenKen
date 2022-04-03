// design pattern {Singleton}

package core.components;

import core.KenKen.KenKen;
import solutions.generation.SolutionFounder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;


public final class OptionsPanel extends JFrame {

    private static JCheckBox redSelector;
    private static JCheckBox squares;
    private static JCheckBox verifyButtonOn;
    private static JComboBox<String> dimensions;
    private static JComboBox<String> colors;
    private static JSlider musicBar;
    private static SolutionFounder solutionFounder;
    private static OptionsPanel instance = null;
    private static KenKen kenKen;
    private static String oldDimension;
    private static HashMap<String,Color> colorsName;


    private OptionsPanel(){
        JLabel[] optionTexts = new JLabel[3];
        colorsName=new HashMap<>();
        colorsName.put("rosso", Color.red);
        colorsName.put("rosa", Color.pink);
        colorsName.put("verde", Color.green);
        colorsName.put("giallo", Color.yellow);
        colorsName.put("azzurro", Color.CYAN);
        optionTexts[0]=new JLabel("opzioni");
        optionTexts[1]=new JLabel("seleziona la grandezza del Ken Ken e il colore delle caselle errate: ");
        optionTexts[2]=new JLabel("volume: ");
        String[] sizes ={"3x3","6x6"};
        oldDimension=sizes[0];
        redSelector = new JCheckBox("segna le caselle vuote/scritte erroneamente");
        verifyButtonOn = new JCheckBox("attiva/disattiva il bottone della verifica");
        squares = new JCheckBox("attiva/disattiva quadrettatura dello schermo");
        musicBar = new JSlider(0,10);
        dimensions = new JComboBox<>(sizes);
        String[] temp=colorsName.keySet().toArray(new String[0]);
        colors = new JComboBox<>(temp);
        setSize(300,300);
        setResizable(false);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        add(optionTexts[0]);
        optionTexts[0].setAlignmentX(Component.LEFT_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0,20)));
        optionTexts[2].setAlignmentX(Component.LEFT_ALIGNMENT);
        add(optionTexts[2]);
        add(Box.createRigidArea(new Dimension(0,10)));
        musicBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        musicBar.addChangeListener(e -> Menu.getBackgroundMusic().setVolume((float)musicBar.getValue()/10));
        add(musicBar);
        add(Box.createRigidArea(new Dimension(0,20)));
        redSelector.setAlignmentX(Component.LEFT_ALIGNMENT);
        redSelector.setSelected(true);
        add(redSelector);
        verifyButtonOn.setAlignmentX(Component.LEFT_ALIGNMENT);
        verifyButtonOn.setSelected(true);
        add(verifyButtonOn);
        squares.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(squares);
        add(Box.createRigidArea(new Dimension(0,getHeight()/8)));

        add(optionTexts[1]);
        optionTexts[1].setAlignmentX(Component.LEFT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0,10)));
        dimensions.setAlignmentX(Component.LEFT_ALIGNMENT);
        dimensions.setMaximumSize(new Dimension(80,80));
        dimensions.setBackground(Color.white);
        add(dimensions);
        colors.setAlignmentX(Component.LEFT_ALIGNMENT);
        colors.setMaximumSize(new Dimension(80,80));
        colors.setBackground(Color.white);
        colors.setSelectedItem("rosso");
        add(colors);
        add(Box.createRigidArea(new Dimension(0,getHeight()/4)));

        pack();
    }
    public static synchronized OptionsPanel getInstance(){
        if(instance==null){
            instance=new OptionsPanel();
        }
        return instance;
    }
    public void setClosingOperation (KenKen kenKen){
        WindowListener listener = new WindowCloser(kenKen);
        addWindowListener(listener);
    }
    public static boolean isSelectedRedSelector(){
        return redSelector.isSelected();
    }
    public static boolean isSelectedVerifyButtonOn(){
        return verifyButtonOn.isSelected();
    }
    public static boolean isSelectedSquares(){
        return squares.isSelected();
    }
    public static Color getColorSetted(){
        return colorsName.get((String)colors.getSelectedItem());
    }

    public static int getKenKenDimension(){
        if(dimensions.getSelectedItem().toString().equals("3x3"))
            return 3;
        else if(dimensions.getSelectedItem().toString().equals("6x6"))
            return 6;
        else return -1;
    }
    class WindowCloser extends WindowAdapter{
        public WindowCloser(KenKen kenKen ){
            OptionsPanel.kenKen=kenKen;

        }
        @Override
        public void windowClosing(WindowEvent w) {
            if(!oldDimension.equals(dimensions.getSelectedItem().toString())) {
                kenKen.dispose();
                for (Frame f:Frame.getFrames()) {
                    if(f instanceof JFrame)
                        f.dispose();
                }
                KenKen game = new KenKen(OptionsPanel.getKenKenDimension());
                OptionsPanel.kenKen = game;
                game.start();
                oldDimension=dimensions.getSelectedItem().toString();
                findSolutions(game);
            }
            kenKen.getView().colorBorders(kenKen.getModel().getTotalCoords());
            kenKen.getView().getVerifyButton().setVisible(verifyButtonOn.isSelected());
        }
    }
    public static void findSolutions(KenKen kenKen){
        solutionFounder = new SolutionFounder(kenKen);
        solutionFounder.start();
    }

    public static SolutionFounder getSolutionFounder() {
        return solutionFounder;
    }

    public static void setDimensions(String dim) {
        dimensions.setSelectedItem(dim);
        oldDimension=dim;
    }

    public static void setKenKen(KenKen kenKen) {
        OptionsPanel.kenKen = kenKen;
    }
}
