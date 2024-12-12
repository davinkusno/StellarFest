package controller.view.eventogranizer.view;

import controller.event.EventController;
import javafx.scene.control.TextField;
import model.Event;
import util.AlertUtil;
import util.Callable;

public class EditEventNameController {

    public static void saveEventName(Event event, TextField nameInput, Callable refreshCallable) {
        String name = nameInput.getText();

        if (name.isEmpty()) {
            AlertUtil.showError("Name is required", "Please enter the event name");
            return;
        }

        if (name.equals(event.getName())) {
            AlertUtil.showError("No changes made", "Please enter a new name to save");
            return;
        }

        event.setName(name);
        boolean updated = EventController.updateEvent(event.getName(), event.getDate(), event.getLocation(), event.getDescription(), event.getId());
        if (!updated) {
            AlertUtil.showError("Failed to update event", "An error occurred while updating the event");
            return;
        }

        AlertUtil.showInfo("Event updated", "Event name has been updated successfully", refreshCallable);
    }

}
