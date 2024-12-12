package model;

import model.join.JoinField;
import model.join.JoinFields;
import model.user.User;
import model.user.impl.EOUser;

import java.time.LocalDate;
import java.util.Objects;

public class Event {

    private final long id;
    private String name;
    private LocalDate date;
    private String location;
    private String description;

    private JoinField<EOUser> organizer;
    private JoinFields<Invitation> invitations;
    private JoinFields<User> attendees;

    public Event(long id) {
        this.id = id;
    }

    public Event(long id, String name, LocalDate date, String location, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JoinField<EOUser> getOrganizer() {
        return organizer;
    }

    public void setOrganizer(JoinField<EOUser> organizer) {
        this.organizer = organizer;
    }

    public JoinFields<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(JoinFields<Invitation> invitations) {
        this.invitations = invitations;
    }

    public JoinFields<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(JoinFields<User> attendees) {
        this.attendees = attendees;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", organizer='" + organizer.getId() + '\'' +
                ", invitations='" + invitations.getIds() + '\'' +
                ", attendees='" + attendees.getIds() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
