package controller.view.eventogranizer.view;

import controller.event.EventController;
import datastore.UserDatastore;
import javafx.collections.ObservableList;
import model.Event;
import model.user.impl.EOUser;

public class ManagedEventsViewController {

    public static void loadEvents(ObservableList<Event> events) {
        EOUser currentUser = (EOUser) UserDatastore.getInstance().getCurrentUser();

        events.clear();
        events.addAll(EventController.getForEventOrganizer(currentUser.getId()));
    }

}
