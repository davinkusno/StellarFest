package controller.event;

import controller.InvitationController;
import controller.UserController;
import driver.Connect;
import driver.Results;
import model.Event;
import model.Invitation;
import model.join.JoinField;
import model.join.JoinFields;
import model.user.User;
import model.user.impl.EOUser;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventController {

    public static List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events";
        try (Results results = Connect.getInstance().executeQuery(query)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                long id = set.getLong("id");
                String name = set.getString("name");
                LocalDate date = set.getObject("date", LocalDate.class);
                String location = set.getString("location");
                String description = set.getString("description");

                long organizerId = set.getLong("organizer_id");

                events.add(EventController.newEvent(id, name, date, location, description, organizerId));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return events;
    }

    public static Event getOne(long id) {
        String query = "SELECT * FROM events WHERE id = ?";
        try (Results results = Connect.getInstance().executeQuery(query, id)) {
            ResultSet set = results.getResultSet();
            if (set.next()) {
                String name = set.getString("name");
                LocalDate date = set.getObject("date", LocalDate.class);
                String location = set.getString("location");
                String description = set.getString("description");

                long organizerId = set.getLong("organizer_id");

                return EventController.newEvent(id, name, date, location, description, organizerId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static List<Event> getMany(List<Long> ids) {
        List<Event> events = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return events;
        }

        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = "SELECT * FROM events WHERE id IN (" + placeholders + ")";
        try (Results results = Connect.getInstance().executeQuery(query, ids.toArray())) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                long id = set.getLong("id");
                String name = set.getString("name");
                LocalDate date = set.getObject("date", LocalDate.class);
                String location = set.getString("location");
                String description = set.getString("description");

                long organizerId = set.getLong("organizer_id");

                events.add(EventController.newEvent(id, name, date, location, description, organizerId));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return events;
    }

    private static Event newEvent(long id, String name, LocalDate date, String location, String description, long organizerId) {
        Event event = new Event(id, name, date, location, description);

        JoinField<EOUser> organizer = new JoinField<>(organizerId, () -> (EOUser) UserController.getOne(organizerId));
        event.setOrganizer(organizer);

        List<Long> invitations = InvitationController.getInvitationsForEvent(id);
        JoinFields<Invitation> invites = new JoinFields<>(invitations, () -> InvitationController.getMany(invitations));
        event.setInvitations(invites);

        List<Long> attendees = EventAttendeeController.getAttendeesForEvent(id);
        JoinFields<User> attendeeUsers = new JoinFields<>(attendees, () -> UserController.getMany(attendees));
        event.setAttendees(attendeeUsers);

        return event;
    }
}
