package ie.lockard.security.Service;

import ie.lockard.security.DataUtils;
import ie.lockard.security.Domain.LockardUsersDAO;
import ie.lockard.security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    //-----------------------------------------------------------------------------
    //                               USER REPOSITORY
    //-----------------------------------------------------------------------------


    public Boolean authticateByUser(LockardUsersDAO user, String password){
        LockardUsersDAO usersDAO = findByEmail(user.getEmail());
        return usersDAO != null && (DataUtils.checkHash(usersDAO.getPassword(),
                DataUtils.getHash(password)));
    }
    public Iterable<LockardUsersDAO> findAllUsers() {
        return repository.findAll();
    }

    public LockardUsersDAO findById(long userID){
        return repository.findOne(userID);
    }

    public LockardUsersDAO findByToken(String token) {
        for (LockardUsersDAO usersDAO : findAllUsers())
            if (usersDAO.getToken().equals(token))
                return usersDAO;
        return null;
    }

    public LockardUsersDAO findByUsername(String username){
        for (LockardUsersDAO usersDAO : findAllUsers())
            if (usersDAO.getUsername().equals(username))
                return usersDAO;
        return null;
    }

    public LockardUsersDAO findByEmail(String email){
        for (LockardUsersDAO usersDAO : findAllUsers())
            if (usersDAO.getEmail().equals(email))
                return usersDAO;
        return null;
    }

    public void upsert(LockardUsersDAO usersDAO){
        repository.save(usersDAO);
    }

    public boolean authenticateByEmail(String email, String password){
        return authticateByUser(findByEmail(email), password);
    }

    public boolean authenticateByToken(String token, String password){
        return authticateByUser(findByToken(token), password);
    }

    public RegistrationStatus registerUser(String firstName,
                                           String lastName,
                                           String username,
                                           String email,
                                           String password){
        if (findByEmail(email) != null)
            return RegistrationStatus.EMAIL_USED;

        //--------------Create UserDAO----------------
        LockardUsersDAO usersDAO = new LockardUsersDAO();
        usersDAO.setId(0); // 0 adds to the database
        usersDAO.setFirstName(firstName);
        usersDAO.setLastName(lastName);
        usersDAO.setUsername(username);
        usersDAO.setEmail(email);

        // Auto Generated Stuff
        usersDAO.setPassword(DataUtils.getHash(password));
        usersDAO.setToken(DataUtils.getToken());
        usersDAO.setDate(Timestamp.valueOf(LocalDateTime.now()));
        try {
            upsert(usersDAO);
        } catch (Exception e){
            e.printStackTrace();
            return RegistrationStatus.FAILED;
        }


        // Check if in database
        return (findByEmail(email)!=null) ?
                RegistrationStatus.SUCCESSFULL:
                RegistrationStatus.FAILED;

        //--------------Registration End---------------
    }
}
