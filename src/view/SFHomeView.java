package view;

import javafx.scene.control.Button;
import util.Callable;

public abstract class SFHomeView extends SFView {

    public SFHomeView(StageManager stageManager) {
        super(stageManager);
    }

    protected Button createRedirectButton(String label, Callable callback) {
        Button button = new Button(label);
        button.setPrefWidth(200);

        button.setOnMouseClicked(e -> {
            callback.call();
        });

        return button;
    }

    @Override
    public void destroyView() {
        // No cleanup required
    }
}
