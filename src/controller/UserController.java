package controller;

import datastore.UserDatastore;
import driver.Connect;
import driver.Results;
import enums.Role;
import model.user.User;
import model.user.impl.AdminUser;
import model.user.impl.EOUser;
import model.user.impl.GuestUser;
import model.user.impl.VendorUser;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserController {

    public static List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Results results = Connect.getInstance().executeQuery(query)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                long id = set.getLong("id");
                String email = set.getString("email");
                String username = set.getString("username");
                String role = set.getString("role");

                users.add(UserController.newUser(id, email, username, role));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public static User getOne(long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Results results = Connect.getInstance().executeQuery(query, id)) {
            ResultSet set = results.getResultSet();
            if (set.next()) {
                String email = set.getString("email");
                String username = set.getString("username");
                String role = set.getString("role");

                return UserController.newUser(id, email, username, role);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static List<User> getMany(List<Long> ids) {
        List<User> users = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return users;
        }

        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = "SELECT * FROM users WHERE id IN (" + placeholders + ")";
        try (Results results = Connect.getInstance().executeQuery(query, ids.toArray())) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                long id = set.getLong("id");
                String email = set.getString("email");
                String username = set.getString("username");
                String role = set.getString("role");

                users.add(UserController.newUser(id, email, username, role));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch Users with ids: " + ids, e);
        }

        return users;
    }

    public static User login(String email, String password) {
        return UserController.login(email, password, true);
    }

    public static User login(String email, String password, boolean saveToDatastore) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Results results = Connect.getInstance().executeQuery(query, email, password)) {
            ResultSet set = results.getResultSet();
            if (set.next()) {
                long id = set.getLong("id");
                String username = set.getString("username");
                String roleString = set.getString("role");

                User user = UserController.newUser(id, email, username, roleString);
                if (saveToDatastore) {
                    UserDatastore.getInstance().setCurrentUser(user);
                }

                return user;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static boolean updateUser(long userId, String email, String username, String password) {
        String query = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
        try {
            Connect.getInstance().executeUpdate(query, email, username, password, userId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isDuplicate(String column, String value) {
        String query = "SELECT " + column + " FROM users WHERE " + column + " = ?";
        try (Results results = Connect.getInstance().executeQuery(query, value)) {
            return results.getResultSet().next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean registerUser(String email, String username, String password, String role) {
        String query = "INSERT INTO users (email, username, password, role) VALUES (?, ?, ?, ?)";
        try {
            Connect.getInstance().executeUpdate(query, email, username, password, role);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static User newUser(long id, String email, String username, String roleString) {
        Role role = Role.getRole(roleString);
        assert role != null;

        return UserController.newUser(id, email, username, role);
    }

    private static User newUser(long id, String email, String username, Role role) {
        User user;
        switch (role) {
            case ADMIN:
                user = new AdminUser(id, email, username);
                break;
            case EVENT_ORGANIZER:
                user = new EOUser(id, email, username);
                break;
            case VENDOR:
                user = new VendorUser(id, email, username);
                break;
            case GUEST:
                user = new GuestUser(id, email, username);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
        return user;
    }
}
