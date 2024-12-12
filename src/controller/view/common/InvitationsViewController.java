package controller.view.common;

import controller.InvitationController;
import javafx.collections.ObservableList;
import model.Event;
import model.Invitation;
import model.user.User;

public class InvitationsViewController {

    public static void loadInvitations(ObservableList<Invitation> invitations, User user) {
        invitations.clear();
        invitations.addAll(InvitationController.getInvitationsForUser(user.getId()));
    }

    public static void acceptInvitation(Invitation invitation, User user) {
        Event event = invitation.getEvent().getValue();
        event.getAttendees().add(user);
    }

    public static void deleteInvitation(Invitation invitation) {
        InvitationController.delete(invitation.getId());
    }

}
