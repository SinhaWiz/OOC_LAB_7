import java.util.ArrayList;
import java.util.List;

class RegularUserOperationsImpl implements RegularUserOperations {
    private final FileOperations fileOperations;
    private final String userFile;

    public RegularUserOperationsImpl(FileOperations fileOperations, String userFile) {
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
}