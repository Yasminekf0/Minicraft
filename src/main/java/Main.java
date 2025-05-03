import controller.StartController;
import view.game.core.MainView;

public class Main {
    public static void main(String[] args) {

        MainView mainView = new MainView();

        new StartController(mainView);
    }
}
