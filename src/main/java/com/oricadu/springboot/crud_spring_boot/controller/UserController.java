//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.oricadu.springboot.crud_spring_boot.controller;


import com.oricadu.springboot.crud_spring_boot.model.User;
import com.oricadu.springboot.crud_spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    public UserController() {
    }

    @GetMapping({"/user"})
    public /*String*/ ModelAndView user(Principal principal/*,
                             ModelMap model*/) {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("user", userService.getUserByUsername(principal.getName()));
        /*model.addAttribute("user",
                userService.getUserByUsername(principal.getName()));*/
//        return "show_user";
        return modelAndView;
    }


}
