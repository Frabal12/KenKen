//design pattern mvc {model-view-controller}

package core.KenKen;

import java.util.ArrayList;

public class KenKen {
    private KenKenModel model;
    private KenKenView view;

    private int dim;

    public KenKen(int dim){
        this.dim=dim;
        model = new KenKenModel(dim);
        view = new KenKenView(dim);
        KenKenController controller= new KenKenController(view,model);
        controller.begin();
        controller.initializeListeners();
    }
    public KenKen (ArrayList<Object> backupList){
        this.dim=((int[][])backupList.get(0)).length;
        model = new KenKenModel(backupList);
        view = new KenKenView(dim);
        KenKenController controller= new KenKenController(view,model);
        controller.begin();
        controller.initializeListeners();
    }
    public KenKenModel getModel() {
        return model;
    }
    public KenKenView getView() {
        return view;
    }
    public void start(){
        view.setVisible(true);
    }
    public void dispose(){
        view.dispose();
    }
    public int getDim() {
        return dim;
    }

}
