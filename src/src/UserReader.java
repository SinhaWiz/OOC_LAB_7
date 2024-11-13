import java.util.List;

public interface UserReader {
    List<User> getAllUsers();
    User getUser(String userID);
}
