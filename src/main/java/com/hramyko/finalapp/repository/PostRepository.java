package com.hramyko.finalapp.repository;

import com.hramyko.finalapp.entity.GameObject;
import com.hramyko.finalapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByGameObjectIn(List<GameObject> gameObjects);
}
