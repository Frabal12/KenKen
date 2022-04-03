package solutions.generation;

import core.KenKen.KenKen;
import core.components.MenuBar;
import solutions.components.SolutionsWindow;

public class SolutionFounder extends Thread {
    private SolutionsWindow solutionsWindow;
    private KenKen kenKen;

    public SolutionFounder(KenKen kenKen){
        this.kenKen=kenKen;
    }

    @Override
    public void run() {
        super.run();
        MenuBar.beginSolutionSearch();
        solutionsWindow=new SolutionsWindow(kenKen);
        MenuBar.endSolutionSearch();
    }

    public SolutionsWindow getSolutionsWindow() {
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return solutionsWindow;
    }
}
