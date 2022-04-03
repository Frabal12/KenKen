package solutions.components;

import core.GroupOfPanels;
import core.KenKen.KenKen;
import solutions.generation.KenKenSolution;
import solutions.generation.NumbersGenerator;
import utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SolutionsWindow extends JFrame {
    private ArrayList<Integer[][]> solutionsMatrices;
    private ArrayList<KenKenSolution> solutionsKenKen;

    public SolutionsWindow(KenKen kenKen){
        setSize(300,300);
        JPanel panelToScroll = new JPanel();
        JScrollPane basePanel = new JScrollPane(panelToScroll);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        panelToScroll.setLayout(new BoxLayout(panelToScroll,BoxLayout.Y_AXIS));
        ArrayList<ArrayList<Pair<Integer,Integer>>> coordinates = kenKen.getModel().getTotalCoords();
        ArrayList<GroupOfPanels> groupsValues = kenKen.getModel().getTotalGroupsValues();
        ArrayList<ArrayList<ArrayList<Integer>>> solutions= new ArrayList<>();
        solutionsMatrices = new ArrayList<>();
        solutionsKenKen = new ArrayList<>();
        Integer[][] verifyM=new Integer[kenKen.getDim()][kenKen.getDim()];
        for (GroupOfPanels group : groupsValues)
            solutions.add(NumbersGenerator.getTotalSolutions(group.getGroupLength(),group.getOperation()
                    ,group.getGroupNumber(),kenKen.getDim()));
        SolutionButton solutionButton;
        KenKenSolution solution ;
        solve(solutions,coordinates,verifyM,-1);
        for (Integer[][] solutionsMatrix : solutionsMatrices) {
            solution = new KenKenSolution(solutionsMatrix);
            if (!solutionsKenKen.contains(solution))
                solutionsKenKen.add(solution);
        }
        for (int i = 0; i < solutionsKenKen.size(); i++) {
            final KenKenSolution kenKensolution=solutionsKenKen.get(i);
            solutionButton=new SolutionButton(solutionsKenKen.get(i),i+1);
            solutionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            solutionButton.addActionListener(e -> {
                for (Frame f:Frame.getFrames()) {
                    if(f instanceof WindowShowingSolution && kenKensolution.equals(((WindowShowingSolution) f).getKenKenSolution()))
                        f.dispose();
                }
            });
            panelToScroll.add(Box.createRigidArea(new Dimension(0,5)));
            panelToScroll.add(solutionButton);


        }
        basePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        basePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(basePanel);
    }
    private void solve(ArrayList<ArrayList<ArrayList<Integer>>> solutions,
                              ArrayList<ArrayList<Pair<Integer,Integer>>> coordinates,
                              Integer[][] verifyM, int i) {
        i++;
        int x,y;
        boolean rightWay;
        if(i>=solutions.size()){
            Integer[][] solution = Arrays.stream(verifyM).map(Integer[]::clone).toArray(Integer[][]::new);
            solutionsMatrices.add(solution);
            return;
        }
        for (int j = 0; j < solutions.get(i).size(); j++) {
            rightWay = true;
            for (int k = 0; k < solutions.get(i).get(j).size(); k++) {
                x=coordinates.get(i).get(k).getX();
                y=coordinates.get(i).get(k).getY();
                if(!verifyRC(verifyM,coordinates.get(i).get(k),solutions.get(i).get(j).get(k))){
                    rightWay=false;
                    break;
                }
                verifyM[x][y]=solutions.get(i).get(j).get(k);
            }
            if(rightWay) solve(solutions,coordinates,verifyM,i);
            for (int k = 0; k < solutions.get(i).get(j).size(); k++) {
                x = coordinates.get(i).get(k).getX();
                y = coordinates.get(i).get(k).getY();
                verifyM[x][y]=null;
            }
        }
    }
    private boolean verifyRC(Integer[][] matrix,Pair<Integer,Integer> point, Integer k){
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i][point.getY()]!=null && matrix[i][point.getY()].equals(k) && i!=point.getX()){
                return false;
            }
            if(matrix[point.getX()][i]!=null && matrix[point.getX()][i].equals(k) && i!=point.getY()){
                return false;
            }
        }
        return true;
    }
}
