package view.eventorganizer.create;

import controller.view.eventogranizer.create.CreateEventViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.SFFormView;
import view.StageManager;
import view.component.TopBar;
import view.eventorganizer.EOHomeView;

public class CreateEventView extends SFFormView {

    private TextField nameInput;
    private DatePicker dateInput;
    private TextField locationInput;
    private TextArea descriptionInput;

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

        FormRowB descriptionRow = this.createTextArea("Description", formContainer);
        this.descriptionInput = descriptionRow.getTextArea();

        HBox buttonContainer = this.createButtonContainer();
        formContainer.getChildren().add(buttonContainer);

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);

        Pane topBar = TopBar.getTopBar(EOHomeView.class);
        borderPane.setTop(topBar);
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
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("Date");
        label.setMinWidth(100);
        row.getChildren().add(label);

        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(200);
        datePicker.setPrefWidth(200);
        row.getChildren().add(datePicker);
        this.dateInput = datePicker;

        return row;
    }
}
