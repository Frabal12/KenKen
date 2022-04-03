// design pattern {Prototype}

package solutions.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public final class NumberBox extends JTextField implements Cloneable{

    private static NumberBox prototype = null;

    public static synchronized NumberBox getPrototype(){
        if ( prototype==null ) prototype = new NumberBox();
        return prototype;
    }

    @Override
    public NumberBox clone() {
        return new NumberBox();
    }
    private NumberBox (){
        LineBorder border = new LineBorder(Color.BLACK);
        Font font = new Font("SansSerif", Font.BOLD, 20);
        setBackground(Color.white);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(font);
        setBorder(border);
        setEditable(false);
    }

    @Override
    public void setText(String t) {
        super.setText(t);
    }
}
