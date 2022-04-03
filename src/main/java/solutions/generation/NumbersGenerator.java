package solutions.generation;

import utils.Verifier;

import java.util.ArrayList;
import java.util.Arrays;

public class NumbersGenerator{
    private static ArrayList<ArrayList<Integer>> totalSolutions;
    private static int solution;
    private static Verifier verifier;

    public static ArrayList<ArrayList<Integer>> getTotalSolutions(int numbers, String operation, int sol,int sizeMatrix) {
        Integer[] val=new Integer[numbers];
        Arrays.fill(val, 0);
        totalSolutions=new ArrayList<>();
        solution=sol;
        verifier=Verifier.getInstance();
        solve(operation,val,0,sizeMatrix);
        return totalSolutions;
    }

    private static void solve(String operation, Integer[]val, int count, int sizeMatrix){
        for(int i=1; i<sizeMatrix+1; i++){
            if(verifier.execute(val,operation,solution)) {
                ArrayList<Integer> solution=new ArrayList<>(Arrays.asList(val));
                if(!totalSolutions.contains(solution)) totalSolutions.add(solution);
            }
            if(count<val.length){
                val[count]=i;
                solve(operation,val,count+1,sizeMatrix);
            }
        }

    }
}