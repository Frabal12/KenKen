package solutions.components;
import solutions.generation.KenKenSolution;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class WindowShowingSolution extends JFrame {

    private KenKenSolution kenKenSolution;

    @Override
    public int hashCode() {
        return Objects.hash(kenKenSolution);
    }

    public WindowShowingSolution(KenKenSolution solution){
        kenKenSolution=solution;
        Integer[][] matrix = solution.getGameMatrix();
        setSize(300,300);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(solution.getDimension(), solution.getDimension()));
        NumberBox numberBox = NumberBox.getPrototype();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++) {
                numberBox=numberBox.clone();
                numberBox.setText(matrix[i][j]+"");
                add(numberBox);
            }
    }
    public KenKenSolution getKenKenSolution() {
        return kenKenSolution;
    }
}
