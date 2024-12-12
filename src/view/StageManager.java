package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class StageManager {

    private static volatile StageManager instance;

    private final Stage primaryStage;
    private final Map<String, SFView> sfViewMap;
    private final Map<Scene, SFView> sceneViewMap;

    public StageManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.sfViewMap = new HashMap<>();
        this.sceneViewMap = new HashMap<>();

        instance = this;
    }

    public static StageManager getInstance() {
        return instance;
    }

    public void addScene(SFView scene) {
        sfViewMap.put(scene.getViewName(), scene);
        sceneViewMap.put(scene.getScene(), scene);
    }

    public void switchScene(Class<? extends SFView> viewClass) {
        String viewName = SFView.getViewNameOf(viewClass);
        switchScene(viewName);
    }

    public void switchScene(String name) {
        SFView sfView = sfViewMap.get(name);
        if (sfView == null) {
            throw new IllegalArgumentException("No scene with name " + name);
        }

        Scene scene = primaryStage.getScene();
        if (sceneViewMap.containsKey(scene)) {
            sceneViewMap.get(scene).destroyView();
        }

        if (sfView instanceof Refreshable) {
            ((Refreshable) sfView).refreshData();
        }

        primaryStage.setScene(sfView.getScene());
        primaryStage.setTitle(sfView.getWindowTitle());
        primaryStage.show();
    }

    public SceneFactory getSceneFactory() {
        return new SceneFactory();
    }

    public static class SceneFactory {
        private int width = 1000;
        private int height = 800;

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
