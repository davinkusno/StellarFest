package controller.view.eventogranizer.view;

import controller.InvitationController;
import controller.UserController;
import controller.event.EventController;
import enums.Role;
import javafx.collections.ObservableList;
import model.Event;
import model.Invitation;
import model.join.JoinField;
import model.user.User;

import java.util.ArrayList;
import java.util.List;

public class EventInviterController {

    public static void loadUsers(Event event, ObservableList<User> users, Role userRole) {
        List<User> allWithRole = UserController.getAllWithRole(userRole);

        // Filter out users that are already in the event
        List<User> attendees = event.getAttendees().getValue();
        allWithRole.removeIf(user -> attendees.stream().anyMatch(attendee -> attendee.getId() == user.getId()));

        users.addAll(allWithRole);
    }

    public static void inviteUsers(Event event, List<User> checkedUsers) {
        List<Invitation> invitations = new ArrayList<>();

        for (User user : checkedUsers) {
            Invitation invitation = new Invitation(0L);

            JoinField<Event> eventJoinField = new JoinField<>(event.getId(), () -> EventController.getOne(event.getId()));
            invitation.setEvent(eventJoinField);

            JoinField<User> userJoinField = new JoinField<>(user.getId(), () -> UserController.getOne(user.getId()));
            invitation.setUser(userJoinField);

            invitations.add(invitation);
        }

        InvitationController.saveAll(invitations);
    }
}
