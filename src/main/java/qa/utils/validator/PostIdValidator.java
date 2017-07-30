package qa.utils.validator;

import org.springframework.beans.factory.annotation.Autowired;
import qa.repository.BasePostRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by ozgur on 7/30/17.
 */
public class PostIdValidator implements ConstraintValidator<BasePostId, Long> {

    @Autowired
    BasePostRepository basePostRepository;

    @Override
    public void initialize(BasePostId basePostId) {
    }

    @Override
    public boolean isValid(Long postId, ConstraintValidatorContext context) {
        return basePostRepository.findById(postId).isPresent();
    }

}