package core.KenKen;

import core.components.OptionsPanel;
import core.components.WritablePanel;
import core.components.MenuBar;
import utils.Pair;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class KenKenView extends JFrame{

    private WritablePanel[][] panels;
    private JButton verifyButton;
    private JPanel screen;
    private MenuBar menuBar;


    public KenKenView(int dim){
        LineBorder border = new LineBorder(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(385, 430);
        setBackground(Color.white);
        setLocationRelativeTo(null);

        screen = new JPanel();
        screen.setLayout(new GridLayout(dim,dim));
        screen.setBackground(Color.black);
        screen.setSize(300,300);
        screen.setLocation(35,10);
        screen.setBorder(border);

        WritablePanel writablePanel = WritablePanel.getPrototype();
        panels=new WritablePanel[dim][dim];
        for(int i=0;i<dim;i++)
            for(int j=0;j<dim;j++){
                panels[i][j]=writablePanel.clone();
                screen.add(panels[i][j]);
            }

        verifyButton = new JButton("verifica");
        verifyButton.setSize(100,30);
        verifyButton.setLocation(135,320);
        verifyButton.setText("verifica");

        menuBar = MenuBar.getInstance();
        setJMenuBar(menuBar);
        add(verifyButton);
        add(screen);
    }


    public void colorBorders (ArrayList<ArrayList<Pair<Integer,Integer>>> coordinates){
        int top=0;
        int bottom=0;
        int left=0;
        int right=0;
        for (ArrayList<Pair<Integer,Integer>> points: coordinates) {
            for(int i=0;i<points.size();i++){
                if(!points.contains(new Pair<>(points.get(i).getX()+1,points.get(i).getY()))) bottom=1;
                if(!points.contains(new Pair<>(points.get(i).getX()-1,points.get(i).getY()))) top=1;
                if(!points.contains(new Pair<>(points.get(i).getX(),points.get(i).getY()-1))) left=1;
                if(!points.contains(new Pair<>(points.get(i).getX(),points.get(i).getY()+1))) right=1;
                if(OptionsPanel.isSelectedSquares())
                    panels[points.get(i).getX()][points.get(i).getY()].setSquareBorderByCoords(top,left,bottom,right, Color.black);
                else
                    panels[points.get(i).getX()][points.get(i).getY()].setBorderByCoords(top,left,bottom,right, Color.black);
                top=0;bottom=0;left=0;right=0;
            }
        }
        return;
    }

    public void writeDataPanels (String[][]panelsData) {
        for (int i = 0; i <panelsData.length ; i++)
            for (int j = 0; j < panelsData.length; j++)
                this.panels[i][j].setData(panelsData[i][j]);
    }
    public void reloadDataWritten(int[][]gameMatrix) {
        for (int i = 0; i <gameMatrix.length ; i++)
            for (int j = 0; j < gameMatrix.length; j++){
                if(gameMatrix[i][j]!=0)
                    this.panels[i][j].setValue(gameMatrix[i][j]+"");
            }
    }


    public JButton getVerifyButton() {
        return verifyButton;
    }

    public MenuBar getTheMenuBar() {
        return menuBar;
    }

    public WritablePanel[][] getPanels() {
        return panels;
    }
}
