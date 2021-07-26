package codefellowship.demo.infrastructure;

import codefellowship.demo.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post ,Long> {

}
