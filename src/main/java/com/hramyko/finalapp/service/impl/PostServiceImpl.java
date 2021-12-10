package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.dao.CommentDao;
import com.hramyko.finalapp.dao.PostDao;
import com.hramyko.finalapp.entity.Post;
import com.hramyko.finalapp.service.GameObjectService;
import com.hramyko.finalapp.service.PostService;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.validator.PostValidator;
import com.hramyko.finalapp.service.validator.UserValidator;
import com.hramyko.finalapp.service.parser.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final UserValidator userValidator;
    private final PostDao postDao;
    private final GameObjectService gameObjectService;
    private final PostValidator postValidator;
    private final CommentDao commentDao;
    private final UserService userService;

    @Autowired
    public PostServiceImpl(UserValidator userValidator, PostDao postDao,
                           GameObjectService gameObjectDao, PostValidator postValidator,
                           CommentDao commentDao, UserService userService) {
        this.userValidator = userValidator;
        this.postDao = postDao;
        this.gameObjectService = gameObjectDao;
        this.postValidator = postValidator;
        this.commentDao = commentDao;
        this.userService = userService;
    }

    @Override
    public List<Post> findUserPosts(int idUser) {
        userValidator.validateId(idUser);
        List<Post> posts = postDao.findUserPosts(idUser);
        for (Post post : posts) {
            post.setComments(commentDao.findAllCommentsOfPost(post.getId()));
        }
        return posts;
    }

    @Override
    public List<Post> findUserPosts() {
        int idUser = userService.currentUser().getId();
        userValidator.validateId(idUser);
        List<Post> posts = postDao.findUserPosts(idUser);
        for (Post post : posts) {
            post.setComments(commentDao.findAllCommentsOfPost(post.getId()));
        }
        return posts;
    }

    @Override
    public void createPost(String jsonString) {
        Post post = (Post) JsonParser.getObjectFromJson(jsonString, Post.class.getName());
        if (post == null) {
            throw new RuntimeException("Error of creating post");
        } else {
            postValidator.validate(post);
            if (gameObjectService.userHasGameObj(post.getIdGameObject(), userService.currentUser().getId())) {
                postDao.createPost(post);
            }
        }
    }

    @Override
    public void deletePost(int id, int idUser) {
        postValidator.validateId(id);
        if (gameObjectService.userHasGameObj(postDao.findPostById(id).getIdGameObject(), idUser)) {
            postDao.deletePost(id);
        }
    }

    @Override
    public void updatePost(int id, String jsonString) {
        Post post = (Post) JsonParser.getObjectFromJson(jsonString, Post.class.getName());
        if (post == null) {
            throw new RuntimeException("Error of creating post");
        } else {
            postValidator.validateId(id);
            postValidator.validatePrice(post.getPrice());
            if (gameObjectService.userHasGameObj(postDao.findPostById(id).getIdGameObject(),
                    userService.currentUser().getId())) {
                postDao.updatePost(id, post);
            }
        }
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = postDao.findAll();
        for (Post post : posts) {
            post.setComments(commentDao.findAllCommentsOfPost(post.getId()));
        }
        return posts;
    }
}
