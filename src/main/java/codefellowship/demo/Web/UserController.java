package codefellowship.demo.Web;

import codefellowship.demo.Domain.AppUser;
import codefellowship.demo.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
      @Autowired
      BCryptPasswordEncoder encoder;

    /**
     *
     * @return signup page
     */
    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    /**
     * this retrieves the profile
     * @param model model
     * @return progile page
     */
    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails);

        return "profile";
    }

    /**
     * to create a new user Post method
     * @param username
     * @param password
     * @param firstname
     * @param lastname
     * @param dateOfBirth
     * @param bio
     * @return redirects the user to the home page
     * @throws ParseException
     */
    @PostMapping("/usercreate")
    public RedirectView createUser(String username, String password, String firstname, String lastname, String dateOfBirth, String bio) throws ParseException {
        String hashedpwd = encoder.encode(password);
        Date DOB = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirth);
        AppUser newUser = new AppUser(username,hashedpwd, firstname, lastname, DOB, bio);
        userRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/");
    }
}
