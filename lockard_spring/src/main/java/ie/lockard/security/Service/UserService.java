package ie.lockard.security.Service;

import ie.lockard.security.DataUtils;
import ie.lockard.security.Domain.LockardAdminsDAO;
import ie.lockard.security.Domain.LockardUsersDAO;
import ie.lockard.security.Repository.AdminRepository;
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
    private AdminRepository adminRepository;


    @Autowired
    public UserService(UserRepository repository,
                       AdminRepository adminRepository) {
        this.repository = repository;
        this.adminRepository = adminRepository;
    }

    //-----------------------------------------------------------------------------
    //                               USER REPOSITORY
    //-----------------------------------------------------------------------------

    public Boolean authenticateByUser(LockardUsersDAO user, String password){
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

    public LockardUsersDAO findByEmail(String email){
        for (LockardUsersDAO usersDAO : findAllUsers())
            if (usersDAO.getEmail().equals(email))
                return usersDAO;
        return null;
    }

    public void upsert(LockardUsersDAO usersDAO){
        //TODO Fix method..not adding the userID.. @William
        LockardAdminsDAO adminsDAO = new LockardAdminsDAO();
        adminsDAO.setUserid(usersDAO.getId());
        adminRepository.save(adminsDAO);
        repository.save(usersDAO);
    }

    public void remove(LockardUsersDAO usersDAO){
        remove(usersDAO.getId());
    }

    public void remove(Long userID){
        repository.delete(userID);
        for (LockardAdminsDAO admins : adminRepository.findAll())
            if (admins.getUserid() == userID)
                adminRepository.delete(admins.getId());
    }

    public boolean authenticateByEmail(String email, String password){
        return authenticateByUser(findByEmail(email), password);
    }

    public boolean authenticateByToken(String token, String password){
        return authenticateByUser(findByToken(token), password);
    }

    public DatabaseStatus registerUser(String firstName,
                                       String lastName,
                                       String username,
                                       String email,
                                       String password){
        if (findByEmail(email) != null)
            return DatabaseStatus.EMAIL_USED;

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
            return DatabaseStatus.FAILED;
        }


        // Check if in database
        return (findByEmail(email)!=null) ?
                DatabaseStatus.SUCCESSFUL :
                DatabaseStatus.FAILED;

        //--------------Registration End---------------
    }

    //TODO: Add any necessary methods you think are needed @ALL
}
