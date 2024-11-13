public interface Authenticator {
    public boolean authenticate(String username , String password);
    User getAuthenticatedUser(String username);
}
