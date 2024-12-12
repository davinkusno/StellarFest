package view.eventorganizer.view;

import controller.view.eventogranizer.view.EditEventNameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import util.Callable;

public class EditEventName {

    private final Event event;
    private final Callable onComplete;
    private final Stage stage;

    private TextField nameInput;

    public EditEventName(Event event, Callable onComplete) {
        this.event = event;
        this.onComplete = onComplete;

        this.stage = new Stage();
        stage.setTitle("Edit Event Name");

        this.prepareView();
    }

    public void show() {
        stage.show();
    }

    private void prepareView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        HBox nameField = this.createNameField();
        container.getChildren().add(nameField);

        HBox buttonContainer = this.createButtonContainer();
        container.getChildren().add(buttonContainer);

        Scene scene = new Scene(container, 800, 400);
        stage.setScene(scene);
    }

    private HBox createButtonContainer() {
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));

        Button saveButton = this.createSaveButton();
        buttonContainer.getChildren().add(saveButton);

        Button clearButton = this.createClearButton();
        buttonContainer.getChildren().add(clearButton);

        return buttonContainer;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        saveButton.setPrefWidth(200);

        saveButton.setOnMouseClicked(e -> {
            EditEventNameController.saveEventName(this.event, this.nameInput, () -> {
                this.nameInput.clear();
                this.onComplete.call();
                stage.close();
            });
        });

        return saveButton;
    }

    private Button createClearButton() {
        Button registerButton = new Button("Clear");
        registerButton.setPrefWidth(200);

        registerButton.setOnMouseClicked(e -> {
            this.nameInput.clear();
        });

        return registerButton;
    }

    private HBox createNameField() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("Name");
        label.setMinWidth(100);
        row.getChildren().add(label);

        TextField textField = new TextField();
        textField.setPrefWidth(200);
        textField.setText(this.event.getName());
        row.getChildren().add(textField);
        this.nameInput = textField;

        return row;
    }
}
