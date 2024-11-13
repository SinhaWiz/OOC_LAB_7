public interface AdminOperations extends UserOperations {
    void modifySystemSettings(String oldFileName, String newFileName);
    void updateUserPrivileges(String userID , UserType newType);
    void addUser(User user);
    void updateUser(User user);
}
