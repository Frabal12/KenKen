//design pattern {strategy, singleton}
package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Verifier {
    private VerifyStrategy strategy;
    private static Verifier instance=null;

    private Verifier(int type){
        switch (type){
            case 0: {
                strategy=new VerifyStrategyA();
                break;
            }
            case 1: {
                strategy=new VerifyStrategyB();
                break;
            }
            default: break;
        }
    }
    public static synchronized Verifier getInstance() {
        if (instance == null) {
            instance = new Verifier(0);
        }
        return instance;
    }

    public boolean execute(Integer[]val,String operation,int solution) {
        return strategy.execute(val, operation, solution);
    }

    public void setStrategy(int type) {
        switch (type){
            case 0: {
                strategy=new VerifyStrategyA();
                break;
            }
            case 1: {
                strategy=new VerifyStrategyB();
                break;
            }
            default: break;
        }
    }


    class VerifyStrategyA implements VerifyStrategy{

        @Override
        public boolean execute(Integer[]val,String operation,int solution) {
                if(operation==null && val[0]==solution)return true;
                else if (operation==null) return false;
                int temp=0;
                switch (operation){
                    case "+":
                        for (Integer integer : val) temp += integer;
                        break;
                    case "-":
                        if(val[0]-val[1]==solution|| val[1]-val[0]==solution)
                            return true;
                        break;
                    case "x":
                        temp=1;
                        for (Integer integer : val) temp *= integer;
                        break;
                    case "÷":
                        if(val[0]==0||val[1]==0)return false;
                        if(((val[0]%val[1]==0)&&(val[0]/val[1]==solution))
                                || ((val[1]%val[0]==0)&&(val[1]/val[0]==solution)))
                            return true;

                        break;
                    default: break;
                }
                return temp==solution;
        }
    }
    class VerifyStrategyB implements VerifyStrategy{

        @Override
        public boolean execute(Integer[]val,String operation,int solution){
            //order the list
            if(operation==null && val[0]==solution)return true;
            else if(operation==null)return false;
            List<Integer> tempList = Arrays.asList(val);
            Collections.sort(tempList);
            val=new Integer[tempList.size()];
            for(int i=0;i< tempList.size();i++) val[i]=tempList.get(i);

            int temp=0;
            switch (operation){
                case "+":
                    for (Integer integer : val) temp += integer;
                    break;
                case "-":
                    temp=val[0];
                    if(val.length==2)temp=val[1]-val[0];
                    break;
                case "x":
                    temp=1;
                    for (Integer integer : val) temp *= integer;
                    break;
                case "÷":
                    temp=val[0];
                    if(val.length==2)
                        temp = val[1] / val[0];
                    break;
                default: break;
            }
            return temp==solution;
        }
    }
}


