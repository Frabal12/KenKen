package solutions.components;

import solutions.generation.KenKenSolution;

import javax.swing.*;

public class SolutionButton extends JButton {

    private KenKenSolution solution;
    private int solutionNumber;
    public SolutionButton (KenKenSolution solution , int solutionNumber) {
        this.solution=solution;
        this.solutionNumber=solutionNumber;
        setToolTipText(solution.toStringHTML("soluzione " + solutionNumber +": "));
        setText("soluzione "+ solutionNumber);
        addActionListener(e -> {
            WindowShowingSolution window = new WindowShowingSolution(solution);
            window.setVisible(true);
        });
    }

}
