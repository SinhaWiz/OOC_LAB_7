import java.util.ArrayList;
import java.util.List;

class AdminUserOperations implements AdminOperations {
    private final FileOperations fileOperations;
    private final String userFile;
    private final String adminFile;

    public AdminUserOperations(FileOperations fileOperations, String userFile, String adminFile) {
        this.fileOperations = fileOperations;
        this.userFile = userFile;
        this.adminFile = adminFile;
    }

    @Override
    public void modifySystemSettings(String oldFileName, String newFileName) {
        fileOperations.rename(oldFileName, newFileName);
    }

    @Override
    public void updateUserPrivileges(String userId, UserType newType) {
        List<String[]> userData = fileOperations.readFile(userFile);
        List<String[]> updatedData = new ArrayList<>();

        for (String[] row : userData) {
            if (row[0].equals(userId)) {
                row[4] = newType.toString();
            }
            updatedData.add(row);
        }

        fileOperations.writeFile(userFile, updatedData);
    }

    // Other AdminOperations implementations...
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        List<String[]> userData = fileOperations.readFile(userFile);

        for (String[] row : userData) {
            users.add(new User(row[0], row[1], row[2], row[3], UserType.valueOf(row[4])));
        }

        return users;
    }

    @Override
    public User getUser(String userId) {
        List<String[]> userData = fileOperations.readFile(userFile);

        for (String[] row : userData) {
            if (row[0].equals(userId)) {
                return new User(row[0], row[1], row[2], row[3], UserType.valueOf(row[4]));
            }
        }

        return null;
    }

    @Override
    public void addUser(User user) {
        List<String[]> userData = fileOperations.readFile(userFile);
        String[] newUser = {
                user.getUserID(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getUserType().toString()
        };
        userData.add(newUser);
        fileOperations.writeFile(userFile, userData);
    }

    @Override
    public void updateUser(User user) {
        List<String[]> userData = fileOperations.readFile(userFile);
        List<String[]> updatedData = new ArrayList<>();

        for (String[] row : userData) {
            if (row[0].equals(user.getUserID())){
                updatedData.add(new String[]{
                        user.getUserID(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getUserType().toString()
                });
            } else {
                updatedData.add(row);
            }
        }

        fileOperations.writeFile(userFile, updatedData);
    }
}