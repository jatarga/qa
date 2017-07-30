package qa.service;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qa.domain.*;
import qa.dto.post.PostCreateDTO;
import qa.dto.post.PostUpdateDTO;
import qa.dto.post.PostVoteDTO;
import qa.dto.post.TagDTO;
import qa.exception.BadRequestException;
import qa.repository.BasePostRepository;
import qa.repository.PostRepository;
import qa.repository.PostVoteRepository;
import qa.repository.TagRepository;
import sun.jvm.hotspot.debugger.cdbg.VoidType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by ozgur on 7/29/17.
 */
@Service
public class PostVoteService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostVoteRepository postVoteRepository;

    @Autowired
    BasePostRepository basePostRepository;

    @Transactional
    public Object vote(PostVoteDTO postVoteDTO, User user) {

        BasePost post = basePostRepository.findById(postVoteDTO.postId).get();
        PostVote postVote = postVoteRepository.findByUserAndPost(user, post);
        VoteType voteTypeRequest = postVoteDTO.voteType;

        boolean deleted = false;

        if(postVote == null) {

            postVote = new PostVote();
            postVote.setUser(user);
            postVote.setPost(post);
            postVote.setVoteType(voteTypeRequest);
            postVoteRepository.save(postVote);
        }

        else {

            // already voted with same type, so delete it
            if(postVote.getVoteType() == voteTypeRequest) {
                postVoteRepository.delete(postVote);
                deleted = true;
            } else {
                postVote.setVoteType(voteTypeRequest);
            }

        }

        if(deleted == false) {
            postVoteRepository.save(postVote);
        }

        return postVoteDTO;
    }

}