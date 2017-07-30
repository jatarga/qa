package qa.dto.post;

import org.hibernate.validator.constraints.NotBlank;
import qa.utils.PostId;

import java.util.HashSet;

/**
 * Created by ozgur on 7/29/17.
 */
public class PostUpdateDTO extends PostCreateDTO {

    @PostId
    public Long postId;

}
