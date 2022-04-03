// design pattern {Prototype}
package core.components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;

public final class WritablePanel extends JPanel implements Cloneable {
    private String data;
    private String value;
    private JTextPane fixedText;
    private JTextPane writableText;
    private static WritablePanel prototype = null;

    public static synchronized WritablePanel getPrototype(){
        if ( prototype==null ) prototype = new WritablePanel();
        return prototype;
    }
    @Override
    public WritablePanel clone() {
        return new WritablePanel();
    }

    private WritablePanel(){
        data=" ";
        value="";
        Font font = new Font(Font.SERIF, Font.BOLD, 10);
        Font font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        setLayout(new OverlayLayout(this));
        fixedText = new JTextPane();
        fixedText.setEditable(false);
        writableText = new JTextPane();
        writableText.setMaximumSize(new Dimension(20,18));

        fixedText.setText(data);
        writableText.setText(value);
        fixedText.setFont(font);
        writableText.setPreferredSize(new Dimension(14,20));
        writableText.setFont(font1);
        fixedText.setAlignmentX(0.5f);
        fixedText.setAlignmentY(0.5f);
        add(writableText);
        add(fixedText);

    }


    public void setBorderByCoords(int topBorder,int leftBorder,int bottomBorder,
                          int rightBorder,Color BorderColor) {
        setBorder(BorderFactory.createMatteBorder(topBorder, leftBorder, bottomBorder, rightBorder, BorderColor));

    }
    public void setSquareBorderByCoords(int topBorder,int leftBorder,int bottomBorder,
                                  int rightBorder,Color BorderColor) {
        int topGrayBorder=1;
        int bottomGrayBorder=1;
        int leftGrayBorder=1;
        int rightGrayBorder=1;

        if(topBorder==1)topGrayBorder=0;
        if(bottomBorder==1)bottomGrayBorder=0;
        if(leftBorder==1)leftGrayBorder=0;
        if(rightBorder==1)rightGrayBorder=0;
        Border grayBorder=BorderFactory.createMatteBorder(topGrayBorder,
                leftGrayBorder, bottomGrayBorder, rightGrayBorder, Color.lightGray);

       setBorder(new CompoundBorder(grayBorder,
               BorderFactory.createMatteBorder(topBorder,
                       leftBorder, bottomBorder, rightBorder, BorderColor)));

    }

    public void setData(String data) {
        this.data = data;
        fixedText.setText(data);
    }
    public void setValue(String value) {
        this.value = value;
        writableText.setText(value);
    }
    public String getText() {
        return writableText.getText();
    }
    public void addKeyListener(KeyAdapter k){
        writableText.addKeyListener(k);
    }
    public void voidTextOn() {
        writableText.setBackground(OptionsPanel.getColorSetted());
        fixedText.setBackground(OptionsPanel.getColorSetted());
    }

    public void voidTextOff() {
        writableText.setBackground(Color.white);
        fixedText.setBackground(Color.white);
    }


}
