package mainGUI;

import controller.KNNController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import model.KNNModel;
import view.KNNView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Eric and Adrián");
        TabPane viewsInTabs =  new TabPane();

        KNNView knnView = new KNNView();
        KNNController knnController = new KNNController();
        KNNModel knnModel = new KNNModel();

        knnView.setController(knnController);
        knnView.setModel(knnModel);
        knnController.setModel(knnModel);
        knnModel.setView(knnView);

        Tab knnTab = new Tab("KNN", knnView.setUp(primaryStage));
        viewsInTabs.getTabs().add(knnTab);

        Scene primaryScene = new Scene(viewsInTabs);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
