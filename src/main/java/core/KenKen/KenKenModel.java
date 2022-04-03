package core.KenKen;

import core.GroupOfPanels;
import core.components.OptionsPanel;
import core.components.WritablePanel;
import org.apache.commons.collections4.iterators.PermutationIterator;
import utils.Pair;
import utils.Verifier;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.stream.IntStream;

public class KenKenModel{
    private int[][] beginMatrix;
    private int[][] gameMatrix;
    private String[][] panelsData;
    private boolean correctionEnabled;
    private WritablePanel[][] panels;
    private ArrayList<Pair<Integer, Integer>> coordinates;
    private ArrayList<Integer> groupsToRemove;
    private ArrayList<Integer> groupsLengths;
    private ArrayList<ArrayList<Pair<Integer, Integer>>> totalCoords;
    private ArrayList<ArrayList<Pair<Integer, Integer>>> toOrder;
    private ArrayList<ArrayList<Integer>> totalGroups;
    private ArrayList<GroupOfPanels> totalGroupsValues;
    private ArrayList<Object> backupList;
    private int dim;

    @SuppressWarnings("unchecked")
    public KenKenModel(ArrayList<Object> backupList) {
        this.backupList=backupList;
        beginMatrix = (int[][]) backupList.get(0);
        gameMatrix = (int[][]) backupList.get(1);
        panelsData = (String[][]) backupList.get(2);
        totalGroupsValues = (ArrayList<GroupOfPanels>) backupList.get(3);
        totalCoords = (ArrayList<ArrayList<Pair<Integer, Integer>>>) backupList.get(4);
        coordinates = new ArrayList<>();
        toOrder = new ArrayList<>();
        dim = beginMatrix.length;
    }

    public KenKenModel(int dim) {
        this.dim = dim;
        beginMatrix = initInterface();
        gameMatrix = new int[dim][dim];
        groupsLengths = new ArrayList<>();
        groupsToRemove = new ArrayList<>();
        totalGroups = new ArrayList<>();
        totalGroupsValues = new ArrayList<>();
        coordinates = new ArrayList<>();
        toOrder = new ArrayList<>();
        Random random = new Random();
        Pair<Integer, Integer> point;
        correctionEnabled = true;
        panelsData=new String[dim][dim];

        //create a random list of coordinates and initialize gameMatrix
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {
                point = new Pair<>(i, j);
                coordinates.add(point);
                gameMatrix[i][j] = 0;
            }
        Collections.shuffle(coordinates);

        //create a list of random lengths for the groups
        int tempDim = dim * dim;
        totalCoords = new ArrayList<>();
        int count;
        while (tempDim > 0) {
            count = random.nextInt(dim) + 1;
            if (tempDim - count < 0) count = tempDim;
            groupsLengths.add(count);
            tempDim -= count;
        }

        //set the groups
        setGame();
        for (int i = 0; i < totalCoords.size(); i++) {
            if (!verifyAdjacent(totalCoords.get(i))) {
                toOrder.add(totalCoords.get(i));
                groupsToRemove.add(groupsLengths.get(i));
            }
        }

        for (int i = 0; i < toOrder.size(); i++) {
            totalCoords.remove(toOrder.get(i));
            groupsLengths.remove(groupsToRemove.get(i));
            for (Pair<Integer, Integer> p : toOrder.get(i)) {
                coordinates.add(p);
            }
        }
        toOrder = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> temp;
        while (coordinates.size() > 0) {
            temp = new ArrayList<>();
            temp.add(coordinates.get(0));
            coordinates.remove(0);
            for (Pair<Integer, Integer> coord : coordinates) {
                temp.add(coord);
                if (!verifyAdjacent(temp)) temp.remove(coord);
                if (temp.size() > 5) break;
            }
            for (Pair<Integer, Integer> p : temp)
                coordinates.remove(p);
            toOrder.add(temp);
        }
        for (int i = 0; i < toOrder.size(); i++) {
            totalCoords.add(toOrder.get(i));
            groupsLengths.add(toOrder.get(i).size());
        }
        ArrayList<Integer> group;
        int[] numbers;
        GroupOfPanels g;
        int x, y;
        for (ArrayList<Pair<Integer, Integer>> points : totalCoords) {
            x = points.get(0).getX();
            y = points.get(0).getY();
            group = new ArrayList<>();
            for (Pair<Integer, Integer> p : points)
                group.add(beginMatrix[p.getX()][p.getY()]);
            numbers = new int[group.size()];
            for (int i = 0; i < group.size(); i++)
                numbers[i] = group.get(i);
            g = new GroupOfPanels(numbers);
            for (Pair<Integer, Integer> p : points) {
                if (p.getX() < x || p.getY() < y) {
                    x = p.getX();
                    y = p.getY();
                }
            }
            panelsData[x][y]=g.getCompletedString();
            totalGroups.add(group);
            totalGroupsValues.add(g);

        }
        backupList = new ArrayList<>();
        backupList.add(beginMatrix);
        backupList.add(gameMatrix);
        backupList.add(panelsData);
        backupList.add(totalGroupsValues);
        backupList.add(totalCoords);
    }
    private void setGame(){
        ArrayList<Pair<Integer,Integer>>toRemove;
        Pair<Integer,Integer> point;
        int count;
        Random random = new Random();
        for(Integer ints : groupsLengths){
            count=ints;
            toRemove= new ArrayList<>();
            point = coordinates.get(0);
            toRemove.add(point);
            count--;
            while(count>0){
                int chose=random.nextInt(4);
                switch (chose) {
                    case 0:
                        if(point.getX()<dim-1)
                            point = new Pair<>(point.getX() + 1, point.getY());
                        break;
                    case 1:
                        if(point.getX()>0)
                            point = new Pair<>(point.getX() - 1, point.getY());
                        break;
                    case 2:
                        if(point.getY()<dim-1)
                            point = new Pair<>(point.getX(), point.getY() + 1);
                        break;
                    case 3:
                        if(point.getY()>0)
                            point = new Pair<>(point.getX(), point.getY() - 1);
                        break;
                }
                if (!toRemove.contains(point) && coordinates.contains(point)) {
                    toRemove.add(point);
                    count--;
                }
            }
            totalCoords.add(toRemove);
            for(Pair<Integer,Integer> p: toRemove){
                coordinates.remove(p);
            }
        }
    }
    private int[][] initInterface() {
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Integer> numbersTemp = new ArrayList<>();
        int[][] beginMatrix = new int[dim][dim];
        int count = 1;
        boolean verify = true;
        for(int i=0;i<beginMatrix.length;i++){
            numbers.add(i+1);
            numbersTemp.add(i+1);
        }
        PermutationIterator<Integer> pi = new PermutationIterator<>(numbers);
        Collections.shuffle(numbersTemp);
        for (int i = 0; i < numbersTemp.size(); i++) beginMatrix[0][i] = numbersTemp.get(i);
        while (count<beginMatrix.length) {
            if(pi.hasNext())numbersTemp= (ArrayList<Integer>) pi.next();
            else break;
            for (int i = 0; i < count; i++)
                for (int j = 0; j < beginMatrix.length ; j++)
                    if (beginMatrix[i][j] == numbersTemp.get(j)) {
                        verify = false;
                        break;
                    }
            if(verify) {
                for (int i = 0; i < beginMatrix[count].length; i++) beginMatrix[count][i] = numbersTemp.get(i);
                count++;
            }
            verify=true;
        }

        return beginMatrix;
    }
    public boolean verify(){
        boolean verify=true;
        for(int i=0;i<panels.length;i++) {
            for (int j = 0; j < panels.length; j++) {
                if (panels[i][j].getText().matches("[1-"+dim+"]"))
                    gameMatrix[i][j] = Integer.parseInt(panels[i][j].getText());
            }
        }
        if(!verifySums()){
            verify=false;
            JOptionPane.showMessageDialog(null, "Ci sono dei numeri ripetuti!");
        }
        if(!verifyGroups()){
            verify=false;
            JOptionPane.showMessageDialog(null, "i gruppi non sono completati correttamente!");
        }
        gameMatrix=new int[dim][dim];
        return verify;
    }

    private boolean verifySums(){
        ArrayList<Integer> temp;
        int[] normal;
        int[] noDup;
        for(int i = 0;i<gameMatrix.length;i++) {
            temp = new ArrayList<>();
            for (int j = 0; j < gameMatrix.length; j++) if (gameMatrix[i][j] != 0) temp.add(gameMatrix[i][j]);
            normal=new int[temp.size()];
            for(int z=0;z<temp.size();z++) normal[z]=temp.get(z);
            noDup = IntStream.of(normal).distinct().toArray();
            if (noDup.length != normal.length) return false;

            temp = new ArrayList<>();
            for (int j = 0; j < gameMatrix.length; j++) if (gameMatrix[j][i] != 0) temp.add(gameMatrix[j][i]);
            normal=new int[temp.size()];
            for(int z=0;z<temp.size();z++) normal[z]=temp.get(z);
            noDup = IntStream.of(normal).distinct().toArray();
            if (noDup.length != normal.length) return false;
        }
        return true;
    }
    private boolean verifyGroups(){
        ArrayList<Integer> temp;
        String operation;
        int groupNumber;
        for(int i=0;i<totalCoords.size();i++)
            for (Pair<Integer, Integer> point : totalCoords.get(i))
                if (gameMatrix[point.getX()][point.getY()] == 0 && correctionEnabled) {
                    panels[point.getX()][point.getY()].setBackground(OptionsPanel.getColorSetted());
                    panels[point.getX()][point.getY()].voidTextOn();
                }

        for(int i=0;i<totalCoords.size();i++){
            temp=new ArrayList<>();
            operation=totalGroupsValues.get(i).getOperation();
            groupNumber=totalGroupsValues.get(i).getGroupNumber();
            for(Pair<Integer,Integer> point : totalCoords.get(i)){
                if(gameMatrix[point.getX()][point.getY()]==0 && correctionEnabled){
                    panels[point.getX()][point.getY()].setBackground(OptionsPanel.getColorSetted());
                }
            }
            for(Pair<Integer,Integer> point : totalCoords.get(i)){
                temp.add(gameMatrix[point.getX()][point.getY()]);
                if(gameMatrix[point.getX()][point.getY()]==0 && correctionEnabled){
                    panels[point.getX()][point.getY()].setBackground(OptionsPanel.getColorSetted());
                }
            }

            if(temp.contains(0)){
                JOptionPane.showMessageDialog(null,"delle caselle sono ancora vuote, o contengono caratteri errati");
                return false;
            }
            Integer[] arr = new Integer[temp.size()];
            arr = temp.toArray(arr);

            Verifier verifier = Verifier.getInstance();
            verifier.setStrategy(1);
            if(!verifier.execute(arr,operation,groupNumber))return false;
        }
        return true;
    }

    private boolean verifyAdjacent (ArrayList<Pair<Integer,Integer>> arrayToVerify){
        ArrayList<Pair<Integer, Integer>> temp = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> toAdd = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> points = new ArrayList<>(arrayToVerify);
        if(points.size()==1)return true;
        boolean verify= false;
        Pair<Integer, Integer> point = points.remove(0);
        temp.add(point);
        while(points.size()>0) {
            verify=false;
            for (Pair<Integer, Integer> p1 : temp){
                for (Pair<Integer, Integer> p2 : points)
                    if (((p1.getX() + 1 == p2.getX() || p1.getX() - 1 == p2.getX()) ||
                            p1.getY() + 1 == p2.getY() || p1.getY() - 1 == p2.getY()) &&
                            !((!p1.getX().equals(p2.getX())) && !p1.getY().equals(p2.getY()))) {
                        toAdd.add(p2);
                        verify=true;
                    }
            }
            for (Pair<Integer, Integer> p : toAdd){
                points.remove(p);
                temp.add(p);
            }
            if (!verify) return false;
        }

        return verify;
    }


    public ArrayList<GroupOfPanels> getTotalGroupsValues() {
        return totalGroupsValues;
    }
    public ArrayList<ArrayList<Pair<Integer, Integer>>> getTotalCoords() {
        return totalCoords;
    }
    public void setPanels(WritablePanel[][] panels) {
        this.panels = panels;
    }
    public void setGameMatrix(){
        for (int i = 0; i < panelsData.length; i++)
            for (int j = 0; j < panelsData.length; j++)
                if(!panels[i][j].getText().equals(""))
                    gameMatrix[i][j]=Integer.parseInt(panels[i][j].getText());
    }

    public int[][] getGameMatrix() {
        return gameMatrix;
    }

    public ArrayList<Object> getBackupList() {
        return backupList;
    }
    public String[][] getPanelsData() {
        return panelsData;
    }

    public void endVerify() {
        correctionEnabled = OptionsPanel.isSelectedRedSelector();
        if (verify())
            JOptionPane.showMessageDialog(null, "Hai completato il gioco!!!!!");
        for (int i = 0; i < totalCoords.size(); i++) {
            for (Pair<Integer, Integer> p : totalCoords.get(i))
                if (gameMatrix[p.getX()][p.getY()] == 0 && correctionEnabled) {
                    panels[p.getX()][p.getY()].setBackground(Color.WHITE);
                    panels[p.getX()][p.getY()].voidTextOff();
                }
        }
    }
}
