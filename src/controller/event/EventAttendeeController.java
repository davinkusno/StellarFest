package controller.event;

import driver.Connect;
import driver.Results;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventAttendeeController {

    public static List<Long> getAttendeesForEvent(long eventId) {
        List<Long> vendorIds = new ArrayList<>();

        String query = "SELECT * FROM event_attendees WHERE event_id = ?";
        try (Results results = Connect.getInstance().executeQuery(query, eventId)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                vendorIds.add(set.getLong("user_id"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return vendorIds;
    }

    public static void addAttendee(long id, long userId) {
        String query = "INSERT INTO event_attendees (event_id, user_id) VALUES (?, ?)";
        try {
            Connect.getInstance().executeUpdate(query, id, userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeAttendee(long id, long userId) {
        String query = "DELETE FROM event_attendees WHERE event_id = ? AND user_id = ?";
        try {
            Connect.getInstance().executeUpdate(query, id, userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
