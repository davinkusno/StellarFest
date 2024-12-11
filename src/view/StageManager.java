package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class StageManager {

    private static volatile StageManager instance;

    private final Stage primaryStage;
    private final Map<String, SFView> sceneMap;

    private SFView previousView;

    public StageManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.sceneMap = new HashMap<>();

        instance = this;
    }

    public static StageManager getInstance() {
        return instance;
    }

    public void addScene(SFView scene) {
        sceneMap.put(scene.getViewName(), scene);
    }

    public void switchScene(Class<? extends SFView> viewClass) {
        String viewName = SFView.getViewNameOf(viewClass);
        switchScene(viewName);
    }

    public void switchScene(String name) {
        SFView sfView = sceneMap.get(name);
        if (sfView == null) {
            throw new IllegalArgumentException("No scene with name " + name);
        }

        if (sfView instanceof Refreshable) {
            ((Refreshable) sfView).refreshData();
        }

        previousView = sfView;
        primaryStage.setScene(sfView.getScene());
        primaryStage.setTitle(sfView.getWindowTitle());
        primaryStage.show();
    }

    public SceneFactory getSceneFactory() {
        return new SceneFactory();
    }

    public SFView getPreviousView() {
        return previousView;
    }

    public static class SceneFactory {
        private int width = 800;
        private int height = 600;

        private SceneFactory() {
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Scene createScene(Pane root) {
            return new Scene(root, width, height);
        }
    }

}
