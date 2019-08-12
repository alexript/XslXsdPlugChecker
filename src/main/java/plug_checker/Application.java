package plug_checker;

import plug_checker.ui.MainWindow;

public class Application {

    private static MainWindow mainwindow;

    public static void main(String[] args) {
        mainwindow = new MainWindow();
    }

    public static MainWindow getMainWindow() {
        return mainwindow;
    }
}
