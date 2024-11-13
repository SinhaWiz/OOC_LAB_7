public class User {
    private final String userID;
    private final String userName;
    private final String email;
    private final String password;
    private final UserType userType;
    public User(String userID, String userName, String email, String password, UserType userType) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }
}
