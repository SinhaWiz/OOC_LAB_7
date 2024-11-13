import java.util.List;

public interface UserOperations {
    List<User>getAllUsers();
    User getUser(String userID);
}
