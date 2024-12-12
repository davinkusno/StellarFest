package controller.view.eventogranizer.create;

import controller.event.EventController;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Event;
import model.user.impl.EOUser;
import util.AlertUtil;
import view.StageManager;
import view.eventorganizer.EOHomeView;

import java.time.LocalDate;

public class CreateEventViewController {

    public static void handleCreateEvent(EOUser user, TextField nameInput, DatePicker dateInput, TextField locationInput, TextArea descriptionInput) {
        String name = nameInput.getText();
        LocalDate date = dateInput.getValue();
        String location = locationInput.getText();
        String description = descriptionInput.getText();

        if (name.isEmpty()) {
            AlertUtil.showError("Name is required", "Please enter the event name");
            return;
        }

        String dateString = date.toString();
        if (dateString.isEmpty()) {
            AlertUtil.showError("Date is required", "Please select the event date");
            return;
        }

        // Date must be in the future
        if (!isFutureDate(date)) {
            AlertUtil.showError("Invalid date", "Please select a future date");
            return;
        }

        if (location.isEmpty()) {
            AlertUtil.showError("Location is required", "Please enter the event location");
            return;
        }

        if (location.length() < 5) {
            AlertUtil.showError("Location is too short", "Please enter a location with at least 5 characters");
            return;
        }

        if (description.isEmpty()) {
            AlertUtil.showError("Description is required", "Please enter the event description");
            return;
        }

        if (description.length() > 200) {
            AlertUtil.showError("Description is too long", "Please enter a description with at most 200 characters");
            return;
        }

        boolean saved = saveEvent(user, name, date, location, description);
        if (!saved) {
            AlertUtil.showError("Event creation failed", "An error occurred while creating the event");
            return;
        }

        AlertUtil.showInfo("Event created", "The event has been created successfully", () -> {
            StageManager.getInstance().switchScene(EOHomeView.class);
        });
    }

    private static boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    private static boolean saveEvent(EOUser user, String name, LocalDate date, String location, String description) {
        Event event = EventController.newEvent(0, name, date, location, description, user.getId());
        return EventController.save(event);
    }

}
