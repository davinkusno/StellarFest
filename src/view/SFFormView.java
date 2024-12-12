package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.lang.reflect.InvocationTargetException;

public abstract class SFFormView extends SFView {

    public SFFormView(StageManager stageManager) {
        super(stageManager);
    }

    protected FormRow createTextField(String title, Class<? extends TextField> textFieldClass, Pane container) {
        FormRow formRow = this.createTextField(title, textFieldClass);
        container.getChildren().add(formRow.getRow());

        return formRow;
    }

    protected FormRow createTextField(String title, Class<? extends TextField> textFieldClass) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label(title);
        label.setMinWidth(100);
        row.getChildren().add(label);

        TextField textField;
        try {
            textField = textFieldClass.getDeclaredConstructor().newInstance();
            row.getChildren().add(textField);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return new FormRow(row, textField);
    }

    protected static class FormRow {
        private final HBox row;
        private final TextField textField;

        public FormRow(HBox row, TextField textField) {
            this.row = row;
            this.textField = textField;
        }

        public HBox getRow() {
            return row;
        }

        public TextField getTextField() {
            return textField;
        }
    }
}
