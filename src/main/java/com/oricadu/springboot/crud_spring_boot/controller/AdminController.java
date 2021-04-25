package com.oricadu.springboot.crud_spring_boot.controller;


import com.oricadu.springboot.crud_spring_boot.model.Role;
import com.oricadu.springboot.crud_spring_boot.model.User;
import com.oricadu.springboot.crud_spring_boot.service.RoleService;
import com.oricadu.springboot.crud_spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder bCrypt;

    public AdminController() {
    }

    @GetMapping({"/"})
    public ModelAndView printUsers(Principal principal) {
        User addUser = new User();
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.getUsers())
                .addObject("user", userService.getUserByUsername(principal.getName()))
                .addObject("allRoles", roleService.getListRoles())
                .addObject("modified_user", addUser);
//                .addObject("forward_request", this.edit());
        return modelAndView;

    }

    @GetMapping({"/{id}/edit_user"})
    public ModelAndView edit(@PathVariable("id") long id) {
        User user = userService.get(id);

        ModelAndView modelAndView = new ModelAndView("edit_user");
        modelAndView.addObject("user", user)
                .addObject("userRoles", user.getRoles());
        return modelAndView;

    }

    @PostMapping({"/users/{id}"})
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id, ModelMap model) {
        user.setPass(bCrypt.encode(user.getPass()));
        model.addAttribute("updatedUser", userService.update(id, user));
        return "redirect:/admin/";
    }

    @GetMapping({"/{id}/remove_user"})
    public String removeUser(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.remove(id));
        return "redirect:/admin/";
    }


    @GetMapping({"/add_user"})
    public ModelAndView add(@ModelAttribute("modified_user") User user) {
        ModelAndView modelAndView = new ModelAndView("/add_user");
        modelAndView.addObject("allRoles", roleService.getListRoles());
        return modelAndView;
    }



    @PostMapping({"/users"})
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("roles") List<Role> roles,
                           ModelMap model) {
        user.setPass(bCrypt.encode(user.getPass()));
        this.userService.add(user);
        return "redirect:/admin/";
    }

    @GetMapping({"/{id}"})
    public String show(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute(userService.get(id));
        return "show_user";
    }


    @GetMapping({"/get_by_email"})
    public String getByEmail(ModelMap model) {
        model.addAttribute(userService.getUserByUsername("email@mail.ru"));
        return "show_user";
    }


}
