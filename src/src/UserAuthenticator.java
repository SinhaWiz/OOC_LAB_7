import java.util.List;

public class UserAuthenticator implements Authenticator {
    private final String userFileName;
    private final String adminFileName;
    private final FileOperations fileOperations;
    public UserAuthenticator(String userFileName, String adminFileName, FileOperations fileOperations) {
        this.userFileName = userFileName;
        this.adminFileName = adminFileName;
        this.fileOperations = fileOperations;
    }
    @Override
    public boolean authenticate(String username, String password) {
        User user = getAuthenticatedUser(username);
        return (user != null)&&(user.getPassword().equals(password));
    }

    @Override
    public User getAuthenticatedUser(String username) {
        List<String[]> adminData = fileOperations.readFile(userFileName);
        for(String[] row : adminData){
            if(row[1].equals(username)){
                return new User(row[0] , row[1] , row[2],row[3],UserType.valueOf(row[4]));
            }
        }
        List<String[]> userData = fileOperations.readFile(adminFileName);
        for(String[] row : userData){
            if(row[1].equals(username)){
                return new User(row[0] , row[1] , row[2],row[3],UserType.ADMIN);
            }
        }
        return null;
    }

}
