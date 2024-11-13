import java.util.ArrayList;
import java.util.List;

class PowerUserOperationsImpl implements PowerUserOperations {
    private final FileOperations fileOperations;
    private final String userFile;

    public PowerUserOperationsImpl(FileOperations fileOperations, String userFile) {
        this.fileOperations = fileOperations;
        this.userFile = userFile;
    }

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
        if (user.getUserType() == UserType.ADMIN) {
            throw new RuntimeException("Power users cannot add admin users");
        }
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
}