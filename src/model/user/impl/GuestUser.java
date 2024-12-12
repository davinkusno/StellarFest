package model.user.impl;

import model.user.User;
import view.SFView;
import view.guest.GuestHomeView;

public class GuestUser extends User {
    public GuestUser(long id) {
        super(id);
    }

    public GuestUser(long id, String email, String username) {
        super(id, email, username);
    }

    @Override
    public Class<? extends SFView> getHomeView() {
        return GuestHomeView.class;
    }
}
