import org.junit.jupiter.api.*;
import java.io.*;
import java.io.FileWriter;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class UserManagementSystemTests {
    private static final String TEST_USER_FILE = "test_users.csv";
    private static final String TEST_ADMIN_FILE = "test_admin.csv";
    private FileOperations fileOperations;
    private UserManagementSystem system;
    private Authenticator authenticator;

    @BeforeEach
    void setUp() throws IOException {

        createTestUserFile();
        createTestAdminFile();

        fileOperations = new CSVfileHandler();
        authenticator = new UserAuthenticator( TEST_USER_FILE, TEST_ADMIN_FILE,fileOperations);
        system = new UserManagementSystem(fileOperations, authenticator, TEST_USER_FILE, TEST_ADMIN_FILE);
    }

    @AfterEach
    void tearDown() {
        // Clean up test files
        new File(TEST_USER_FILE).delete();
        new File(TEST_ADMIN_FILE).delete();
    }

    private void createTestUserFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_USER_FILE))) {
            // Header
            writer.write("UserID,Username,Email,Password,UserType\n");
            // Test users
            writer.write("1,regularUser,regular@test.com,pass123,REGULAR\n");
            writer.write("2,powerUser,power@test.com,pass123,POWER\n");
        }
    }

    private void createTestAdminFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_ADMIN_FILE))) {
            // Header
            writer.write("UserID,Username,Email,Password\n");
            // Admin user
            writer.write("3,adminUser,admin@test.com,pass123\n");
        }
    }

    @Test
    void testRegularUserLogin() {
        User user = system.login("regularUser", "pass123");
        assertNotNull(user);
        assertEquals("regularUser", user.getUserName());
        assertEquals(UserType.REGULAR, user.getUserType());
    }

    @Test
    void testRegularUserOperations() {
        User regularUser = system.login("regularUser", "pass123");
        UserOperations operations = system.getUserOperations(regularUser);


        List<User> allUsers = operations.getAllUsers();
        assertFalse(allUsers.isEmpty());
        assertEquals(2, allUsers.size());


        User user = operations.getUser("1");
        assertNotNull(user);
        assertEquals("regularUser", user.getUserName());
    }

    @Test
    void testPowerUserOperations() {
        User powerUser = system.login("powerUser", "pass123");
        PowerUserOperations operations = (PowerUserOperations) system.getUserOperations(powerUser);


        User newUser = new User("4", "newUser", "new@test.com", "pass123", UserType.REGULAR);
        operations.addUser(newUser);


        User addedUser = operations.getUser("4");
        assertNotNull(addedUser);
        assertEquals("newUser", addedUser.getUserName());
    }

    @Test
    void testAdminOperations() {
        User adminUser = system.login("adminUser", "pass123");
        AdminOperations operations = (AdminOperations) system.getUserOperations(adminUser);


        operations.updateUserPrivileges("1", UserType.POWER);
        User updatedUser = operations.getUser("1");
        assertEquals(UserType.POWER, updatedUser.getUserType());


        User newUser = new User("5", "adminCreated", "admincreated@test.com", "pass123", UserType.REGULAR);
        operations.addUser(newUser);


        User addedUser = operations.getUser("5");
        assertNotNull(addedUser);
        assertEquals("adminCreated", addedUser.getUserName());
    }

    @Test
    void testInvalidLogin() {
        assertThrows(RuntimeException.class, () -> {
            system.login("regularUser", "wrongPassword");
        });
    }

    @Test
    void testFileOperations() {
        FileOperations fileOps = new CSVfileHandler();

        List<String[]> userData = fileOps.readFile(TEST_USER_FILE);
        assertFalse(userData.isEmpty());

        List<String[]> newData = new ArrayList<>(userData);
        newData.add(new String[]{"6", "testUser", "test@test.com", "pass123", "REGULAR"});
        fileOps.writeFile(TEST_USER_FILE, newData);

        List<String[]> updatedData = fileOps.readFile(TEST_USER_FILE);
        assertEquals(newData.size(), updatedData.size());
        assertEquals("testUser", updatedData.get(updatedData.size() - 1)[1]);
    }

    @Test
    void testUserAttributes() {
        User user = new User("1", "testUser", "test@test.com", "pass123", UserType.REGULAR);
        assertEquals("1", user.getUserID());
        assertEquals("testUser", user.getUserName());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("pass123", user.getPassword());
        assertEquals(UserType.REGULAR, user.getUserType());
    }
}