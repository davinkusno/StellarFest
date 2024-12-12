package controller;

import controller.event.EventController;
import driver.Connect;
import driver.Results;
import model.Event;
import model.Invitation;
import model.join.JoinField;
import model.user.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvitationController {

    public static List<Invitation> getMany(List<Long> ids) {
        List<Invitation> invitations = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return invitations;
        }

        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = "SELECT * FROM invitations WHERE id IN (" + placeholders + ")";
        try (Results results = Connect.getInstance().executeQuery(query, ids.toArray())) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                long id = set.getLong("id");
                long eventId = set.getLong("event_id");
                long userId = set.getLong("user_id");

                invitations.add(InvitationController.newInvitation(id, eventId, userId));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch Invitation with ids: " + ids, e);
        }

        return invitations;
    }

    public static List<Long> getInvitationsForEvent(long eventId) {
        List<Long> inviteIds = new ArrayList<>();

        String query = "SELECT * FROM invitations WHERE event_id = ?";
        try (Results results = Connect.getInstance().executeQuery(query, eventId)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                inviteIds.add(set.getLong("id"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return inviteIds;
    }

    public static List<Invitation> getInvitationsForUser(long userId) {
        List<Invitation> invitations = new ArrayList<>();

        String query = "SELECT * FROM invitations WHERE user_id = ?";
        try (Results results = Connect.getInstance().executeQuery(query, userId)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                long id = set.getLong("id");
                long eventId = set.getLong("event_id");

                invitations.add(InvitationController.newInvitation(id, eventId, userId));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return invitations;
    }

    public static boolean save(Invitation invitation) {
        String query = "INSERT INTO invitations (event_id, user_id) VALUES (?, ?)";
        try {
            Connect.getInstance().executeUpdate(query, invitation.getEvent().getId(), invitation.getUser().getId());
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean saveAll(List<Invitation> invitations) {
        boolean success = true;
        for (Invitation invitation : invitations) {
            success = success && save(invitation);
        }

        return success;
    }

    public static void delete(long invitationId) {
        String query = "DELETE FROM invitations WHERE id = ?";
        try {
            Connect.getInstance().executeUpdate(query, invitationId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Invitation newInvitation(long id, long eventId, long userId) {
        Invitation invitation = new Invitation(id);

        JoinField<Event> event = new JoinField<>(eventId, () -> EventController.getOne(eventId));
        invitation.setEvent(event);

        JoinField<User> user = new JoinField<>(userId, () -> UserController.getOne(userId));
        invitation.setUser(user);

        return invitation;
    }
}
