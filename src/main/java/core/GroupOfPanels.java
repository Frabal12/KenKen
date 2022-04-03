package core;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class GroupOfPanels implements Serializable {

    @Serial
    private static final long serialVersionUID = -7399247755404090600L;
    private int groupLength;
    private String completedString;
    private int groupNumber;
    private String operation;

    public GroupOfPanels(int[] numbers){
        completedString=createOperation(numbers);
    }


    private String createOperation(int[] numbers) {
        Arrays.sort(numbers);
        Random random = new Random();
        groupNumber=0;
        String completeString;
        completeString = "";
        groupLength=numbers.length;
        if(numbers.length>2)
            switch (random.nextInt(2)) {
                case 0:
                    for(int i=0;i<numbers.length;i++) groupNumber+=numbers[i];
                    completeString = completeString + "+";
                    operation="+";
                    break;
                case 1:
                    groupNumber=1;
                    for(int i=0;i<numbers.length;i++) groupNumber*=numbers[i];
                    completeString = completeString + "x";
                    operation="x";
                    break;
                default:
                    break;
            }
        else
            switch (random.nextInt(4)) {
                case 0:
                    if(numbers.length==1){
                        groupNumber=numbers[0];
                        break;
                    }
                    for(int i=0;i<numbers.length;i++)
                        groupNumber+=numbers[i];
                    completeString = completeString + "+";
                    operation="+";
                    break;
                case 1:
                    if(numbers.length==1){
                        groupNumber=numbers[0];
                        break;
                    }
                    groupNumber=1;
                    for(int i=0;i<numbers.length;i++)
                        groupNumber*=numbers[i];
                    completeString = completeString + "x";
                    operation="x";
                    break;
                case 2:
                    groupNumber=numbers[0];
                    if(numbers.length==2 && numbers[1]%numbers[0]==0) {
                        groupNumber = numbers[1] / numbers[0];
                        completeString = completeString + "÷";
                        operation="÷";
                    }
                    else if(numbers.length==2 && numbers[1]%numbers[0]!=0){
                        groupNumber=numbers[1]*numbers[0];
                        completeString = completeString + "x";
                        operation="x";
                    }
                    break;
                case 3:
                    groupNumber=numbers[0];
                    if(numbers.length==2) {
                        groupNumber = numbers[1] - numbers[0];
                        completeString = completeString + "-";
                        operation="-";
                    }
                    break;
                default:
                    break;
            }

        completeString=groupNumber+completeString;
        return completeString;
    }


    @Override
    public String toString() {
        return "core.GroupOfPanels{" +
                "groupLength=" + groupLength +
                ", completedString='" + completedString + '\'' +
                '}';
    }


    public int getGroupLength() {
        return groupLength;
    }
    public String getCompletedString() {
        return completedString;
    }
    public String getOperation() {
        return operation;
    }
    public int getGroupNumber() {
        return groupNumber;
    }
}

