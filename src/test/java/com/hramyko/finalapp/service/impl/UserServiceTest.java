//package com.hramyko.finalapp.service.impl;
//
//import com.hramyko.finalapp.dao.CommentDao;
//import com.hramyko.finalapp.dao.UserDao;
//import com.hramyko.finalapp.entity.User;
//import com.hramyko.finalapp.service.UserService;
//import com.hramyko.finalapp.service.validator.UserValidator;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserDao userDao;
//    @Mock
//    private UserValidator userValidator;
//    @Mock
//    private CommentDao commentDao;
//    private UserService userService;
//
//    private static User user1;
//    private static User user2;
//    private static User user3;
//    private static List<User> users;
//
//    UserServiceTest() {
//    }
//
//    @BeforeEach
//    public void initUseCase(){
//        userService = new UserServiceImpl(userDao, userValidator, commentDao) {
//        };
//    }
//
//    @BeforeAll
//    public static void setUp(){
//        user1 = new User(1, "mail1@mail.ru", "password", "Ivan", "Ivanov");
//        user2 = new User(2, "mail2@mail.ru", "password", "Sergeev", "Sergei");
//        user3 = new User();
//        user3.setId(3);
//        users = List.of(user1, user2);
//    }
//
//    @Test
//    void findAllPositiveTest() {
//        when(userService.findAll()).thenReturn(users);
//
//        List<User> actualUsers = userService.findAll();
//
//        assertEquals(users, actualUsers);
//    }
//
//    @Test
//    void saveUserPositiveTest() {
//        doNothing().when(userValidator).validate(user1);
//        when(userDao.saveUser(isA(User.class))).thenReturn(user3);
//
//        User actualUser = userService.saveUser(user1);
//
//        assertEquals(actualUser.getId(), user3.getId());
//    }
//
//    @Test
//    void findUserByIdPositiveTest() {
//        final int id = 1;
//        doNothing().when(userValidator).validateId(anyInt());
//        when(userDao.findUserById(id)).thenReturn(user1);
//
//        User actualUser = userService.findUserById(id);
//
//        assertEquals(user1, actualUser);
//    }
//
//}