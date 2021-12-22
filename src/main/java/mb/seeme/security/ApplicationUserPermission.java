package mb.seeme.security;

public enum ApplicationUserPermission {
    TERM_READ("term:read"),
    TERM_WRITE("term:write"),
    CALENDAR_READ("calendar:read"),
    TERMS_READ("terms:read");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
