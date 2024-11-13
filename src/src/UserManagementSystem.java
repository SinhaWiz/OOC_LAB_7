import java.util.List;
public class UserManagementSystem {
    private final FileOperations fileOperations;
    private final Authenticator authenticator;
    private final String userFile ;
    private final  String adminFile;

    public UserManagementSystem(FileOperations fileOperations, Authenticator authenticator, String userFile, String adminFile) {
        this.fileOperations = fileOperations;
        this.authenticator = authenticator;
        this.userFile = userFile;
        this.adminFile = adminFile;
    }
    public User login(String username, String password) {
        if(authenticator.authenticate(username, password)) {
           return authenticator.getAuthenticatedUser(username);
        }
        throw new RuntimeException("Authentication failed");
    }
    public UserOperations getUserOperations(User user) {
        switch (user.getUserType()) {
            case ADMIN:
                return new AdminUserOperations(fileOperations, userFile, adminFile);
            case POWER:
                return new PowerUserOperationsImpl(fileOperations, userFile);
            case REGULAR:
                return new RegularUserOperationsImpl(fileOperations, userFile);
            default:
                throw new RuntimeException("Invalid user type");
        }
    }
}
