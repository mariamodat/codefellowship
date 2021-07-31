package codefellowship.demo.Web;

import codefellowship.demo.Domain.AppUser;
import codefellowship.demo.Domain.Post;
import codefellowship.demo.infrastructure.PostRepository;
import codefellowship.demo.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;

@Controller
public class postController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;


    /**
     * get posts page
     * @return
     */
    @GetMapping("/post")
    public String getPostsPage (){
        return "postPage";
    }

    /**
     * get all posts
     * @param m
     * @return all posts by all users
     */
    @GetMapping("/gpost")
public  String getPostsPage2(Model m ){
        Iterable <Post> posts = postRepository.findAll();
        m.addAttribute("posts" , posts);
        return "feeds";
}

    /**
     * each user can post

     * @param m
     * @param body
     * @return
     */
    @PostMapping("/post")
    public RedirectView postNewPost(Model m , String body){
        AppUser userDetails = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUser user= userRepository.findByUsername(userDetails.getUsername());
        if (user !=null)
        {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Post post = new Post(body ,timestamp,user);
            postRepository.save(post);
        }
//        m.addAttribute("appUser" ,user);
//        m.addAttribute("principal" , p.getName());
        return new RedirectView("/profile");
    }
}
