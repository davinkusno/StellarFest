package view.eventorganizer.create;

import controller.view.eventogranizer.create.CreateEventViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.SFFormView;
import view.StageManager;

public class CreateEventView extends SFFormView {

    private TextField nameInput;
    private DatePicker dateInput;
    private TextField locationInput;
    private TextField descriptionInput;

    public CreateEventView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Create Event";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Create Event");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        formContainer.getChildren().add(titleLabel);

        FormRow nameRow = this.createTextField("Name", TextField.class, formContainer);
        this.nameInput = nameRow.getTextField();

        HBox datePicker = this.createDatePicker();
        formContainer.getChildren().add(datePicker);

        FormRow locationRow = this.createTextField("Location", TextField.class, formContainer);
        this.locationInput = locationRow.getTextField();

        FormRow descriptionRow = this.createTextField("Description", TextField.class, formContainer);
        this.descriptionInput = descriptionRow.getTextField();

        HBox buttonContainer = this.createButtonContainer();
        formContainer.getChildren().add(buttonContainer);

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);
    }

    @Override
    public void destroyView() {
        this.nameInput.clear();
        this.dateInput.setValue(null);
        this.locationInput.clear();
        this.descriptionInput.clear();
    }

    private HBox createButtonContainer() {
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        Button saveButton = this.createSaveButton();
        buttonContainer.getChildren().add(saveButton);

        Button clearButton = this.createClearButton();
        buttonContainer.getChildren().add(clearButton);

        return buttonContainer;
    }

    private Button createSaveButton() {
        Button registerButton = new Button("Save");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        registerButton.setPrefWidth(200);

        registerButton.setOnMouseClicked(e -> {
            CreateEventViewController.handleCreateEvent(this.nameInput, this.dateInput, this.locationInput, this.descriptionInput);
        });

        return registerButton;
    }

    private Button createClearButton() {
        Button registerButton = new Button("Clear");
        registerButton.setPrefWidth(200);

        registerButton.setOnMouseClicked(e -> {
            this.destroyView();
        });

        return registerButton;
    }

    private HBox createDatePicker() {
        HBox datePickerContainer = new HBox(15);
        datePickerContainer.setAlignment(Pos.CENTER);

        Label dateLabel = new Label("Date");
        datePickerContainer.getChildren().add(dateLabel);

        DatePicker datePicker = new DatePicker();
        datePickerContainer.getChildren().add(datePicker);
        this.dateInput = datePicker;

        return datePickerContainer;
    }
}
