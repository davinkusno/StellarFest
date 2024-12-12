package controller.view.vendor.view;

import controller.event.EventController;
import javafx.collections.ObservableList;
import model.Event;
import model.user.User;

public class InvitedEventsViewController {

    public static void loadEvents(ObservableList<Event> events, User user) {
        events.clear();
        events.addAll(EventController.getForAttendee(user.getId()));
    }

}
