package core;

import core.components.Menu;
import javafx.application.Platform;

public class Main {
    public static void main(String... Args){
        Platform.startup(()->{
            Menu menu = Menu.getInstance();
            menu.start();
        });
    }
}

