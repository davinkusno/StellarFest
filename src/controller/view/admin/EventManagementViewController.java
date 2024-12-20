package controller.view.admin;

import controller.event.EventController;
import driver.Connect;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Event;
import util.AlertUtil;

public class EventManagementViewController {

    public static void loadEvents(ObservableList<Event> events) {
        events.clear();
        events.addAll(EventController.getAll());
    }

    public static void handleDeleteSelectedEvent(TableView<Event> eventTable, ObservableList<Event> events) {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            AlertUtil.showError("No event selected", "Please select an event to delete");
            return;
        }

        try {
            int update = Connect.getInstance().executeUpdate("DELETE FROM events WHERE id = " + selectedEvent.getId());
            if (update <= 0) {
                AlertUtil.showError("Error deleting event", "No event was deleted");
                return;
            }

            events.remove(selectedEvent);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Error deleting event", "An error occurred while deleting the event");
        }
    }
}
