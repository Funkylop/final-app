package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.GameObject;
import com.hramyko.finalapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByGameObjectIn(List<GameObject> gameObjects);
}
