package com.hramyko.finalapp.web.controllers;

import com.hramyko.finalapp.service.impl.EmailSenderServiceImpl;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api//waiting_list")
public class WaitingListController {

    private final WaitingListService waitingListService;
    private final UserService userService;
    private final EmailSenderServiceImpl emailSenderServiceImpl;

    @Autowired
    public WaitingListController(WaitingListService waitingListService, UserService userService,
                                 EmailSenderServiceImpl emailSenderServiceImpl) {
        this.waitingListService = waitingListService;
        this.userService = userService;
        this.emailSenderServiceImpl = emailSenderServiceImpl;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user.delete')")
    public String showWaitingList() {
        return waitingListService.findAll().toString();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('user.delete')")
    public String showUserInfo(@PathVariable("id") int formId) {
        return userService.findUserById(waitingListService.getUserId(formId)).toString();
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('user.delete')")
    public String applyForm(@PathVariable("id") int formId) {
        int userId = waitingListService.getUserId(formId);
        userService.updateUserRole(userId, "TRADER");
        waitingListService.deleteUserForm(formId);
        emailSenderServiceImpl.notifyMessage(userService.findUserById(userId).getEmail());
        return "User has been added to traders";
    }

    @PostMapping("/reg_trader")
    @PreAuthorize("hasAuthority('user.read')")
    public String regTraderPage() {
        waitingListService.addUserForm();
        return "You successfully send your form";
    }
}
