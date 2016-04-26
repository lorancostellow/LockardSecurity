package ie.lockard.security;

import ie.lockard.security.Domain.LockardUsersDAO;
import ie.lockard.security.Repository.HouseRepository;
import ie.lockard.security.Service.HouseService;
import ie.lockard.security.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LockardApplication.class)
@WebAppConfiguration
public class LockardApplicationTests {

	@Autowired
	private UserService userService;

    @Autowired
	private HouseService houseService;

    @Test
	public void contextLoads() {
		System.out.println(houseService.findAll());
        System.out.println(
                userService.registerUser(
                        "Dylan",
                        "Coss",
                        "McSwaggins",
                        "dylancoss1@gmail.com",
                        "_password")
        );
	}

}
