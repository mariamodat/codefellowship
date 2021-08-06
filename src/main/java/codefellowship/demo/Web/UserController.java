package codefellowship.demo.Web;

import codefellowship.demo.Domain.AppUser;
import codefellowship.demo.Domain.Post;
import codefellowship.demo.infrastructure.PostRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Timestamp;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    /**
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
     * get specific user
     * @param m
     * @param p
     * @param id
     * @return user html page
     */
    @GetMapping("/users/{id}")
    public String getSingleAppUserPage(Model m, Principal p, @PathVariable Long id) {
//        long ID = Long.parseLong(id);
        AppUser appUser = userRepository.getById(id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser loggedUser = userRepository.findByUsername(p.getName());
        if (loggedUser.getFollowers().contains(appUser)){
            m.addAttribute("showButton" , false);
        }
        else { m.addAttribute("showButton" , true);}
        m.addAttribute("appUser", appUser);
        m.addAttribute("principal", p.getName());

        return "user";
    }

    /**
     * this retrieves the profile
     *
     * @param model model
     * @return progile page
     */
    @GetMapping("/profile")
    public String getProfilePage( Principal p ,Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = userRepository.findByUsername(p.getName());
        model.addAttribute("counter" ,appUser.getFollowers().size());
        model.addAttribute("appUser", appUser);
        model.addAttribute("followings" ,appUser.getFollowers() );
        model.addAttribute("principal", p.getName());

        return "profile";
    }

    /**
     * to create a new user Post method
     *
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
        System.out.println(">>>>>>>>>>>>>>>>>>>"+ username + "   "+ password);
        String hashedpwd = encoder.encode(password);

        AppUser newUser = new AppUser(username, hashedpwd, firstname, lastname, dateOfBirth, bio);
        userRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return new RedirectView("/profile");
    }

    /**
     * rendering all users
     * @param m
     * @param p
     * @return html page of all users without the principal
     */
    @GetMapping("/users")
        public String getAllUsers(Model m  , Principal p ){
        AppUser user = userRepository.findByUsername(p.getName());
        Iterable<AppUser> allUsers= userRepository.findAll();
            List <AppUser> userWithoutMe = new ArrayList<>();
            userWithoutMe.addAll((Collection<? extends AppUser>) allUsers);
            userWithoutMe.remove(user);


            m.addAttribute("followings" , user.getFollowers());
            m.addAttribute("users" , userWithoutMe);

        return  "/Home";
        }


    /**
     * get following for each user
     * @param p
     * @param m
     * @return html page of followings
     */
    @GetMapping("/follow")
    public  String getUserFollowings(Principal p, Model m)
    {
        AppUser appUser = userRepository.findByUsername(p.getName());
        Set  <AppUser> followers = appUser.getFollowers();

        m.addAttribute("following" ,followers);
        m.addAttribute("appUser" , appUser);
        return "followers";
    }

    /**
     * follow users
     * @param id
     * @param p
     * @return home page with following users
     */
    @PostMapping ("/follow/{id}")
    public  RedirectView followUsersById(@PathVariable Long id , Principal p , Model m){
        AppUser loggedUser = userRepository.findByUsername(p.getName());
        AppUser userToFollow = userRepository.getById(id);
         Set <AppUser> followings = loggedUser.getFollowers();
         followings.add(userToFollow);

        userRepository.save(loggedUser);
        System.out.println(">>>>>>>>>>>>>>>>>>>Followers<<<<<<<<<<<<<<<<<" );
    loggedUser.getFollowers().forEach(System.out::println);

        return  new RedirectView("/users");
    }


    /**
     * unfollow to the users
     * @param id
     * @param p
     * @return home page
     */
    @PostMapping ("/unfollow/{id}")
    public  RedirectView unfollowUsersById(@PathVariable Long id , Principal p , Model m ){
        AppUser loggedUser = userRepository.findByUsername(p.getName());
        AppUser userToFollow = userRepository.getById(id);
        loggedUser.getFollowers().remove(userToFollow);
        userRepository.save(loggedUser);

        return  new RedirectView("/users");
    }


}


