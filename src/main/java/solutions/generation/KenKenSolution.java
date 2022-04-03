package solutions.generation;

import java.util.Arrays;

public class KenKenSolution {

    private Integer[][] gameMatrix;
    public KenKenSolution(Integer[][] gameMatrix){
        this.gameMatrix=gameMatrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KenKenSolution value)) return false;
        return Arrays.deepEquals(gameMatrix, value.gameMatrix);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i <gameMatrix.length; i++) {
            for (int j = 0; j < gameMatrix.length; j++) {
                sb.append(" "+gameMatrix[i][j]+" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public String toStringHTML() {
        return toStringHTML(null);
    }

    public String toStringHTML(String beginText) {
        String begin = "<html><font face=\"sansserif\" color=\"black\">";
        String end = "</font></html>";
        StringBuilder sb=new StringBuilder();
        sb.append(begin);
        if(beginText!=null) sb.append(beginText + "&nbsp;" + "<br>");
        for (Integer[] matrix : gameMatrix) {
            for (int j = 0; j < gameMatrix.length; j++) {
                sb.append("&nbsp;&nbsp;" + matrix[j] + "&nbsp;&nbsp;");
            }
            sb.append("<br>");
        }
        sb.append(end);
        return sb.toString();
    }


    @Override
    public int hashCode() {
        return Arrays.deepHashCode(gameMatrix);
    }
    public int getDimension(){
        return gameMatrix.length;
    }
    public Integer[][] getGameMatrix() {
        return gameMatrix;
    }
}
