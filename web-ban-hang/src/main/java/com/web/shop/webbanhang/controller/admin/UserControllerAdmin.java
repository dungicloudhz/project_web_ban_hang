package com.web.shop.webbanhang.controller.admin;

import com.web.shop.webbanhang.entity.Brand;
import com.web.shop.webbanhang.entity.Role;
import com.web.shop.webbanhang.entity.User;
import com.web.shop.webbanhang.model.BrandDto;
import com.web.shop.webbanhang.model.UserDetailDto;
import com.web.shop.webbanhang.security.MyUserDetails;
import com.web.shop.webbanhang.security.RoleService;
import com.web.shop.webbanhang.security.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/user")
public class UserControllerAdmin {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @ModelAttribute("listRole")
    public List<Role> roleList(){
        return roleService.findAll();
    }

    @RequestMapping("")
    public String listUser(Model model){
        int currentPage = 1;
        Page<User> page = userService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<User> listUsers = page.getContent();
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("listUsers",listUsers);
        return "admin/products/list-user";
    }

    @RequestMapping("/page/{pageNumber}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage) {
        Page<User> page = userService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<User> listUsers = page.getContent();
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("listUsers",listUsers);
        return "admin/products/list-user";
    }

    @GetMapping("/search")
    public String search(ModelMap model, @RequestParam(value = "username",required = false)String username){
        Page<User> page = null;
        if(StringUtils.hasText(username)) {
            int pageNumber = 1;
            Sort sort = Sort.by("userId").ascending();
            Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);
            page = userService.findByUsernameContaining(username,pageable);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
        } else {
            model.addAttribute("username",username);
            page = userService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<User> listUsers = page.getContent();
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            model.addAttribute("listUsers",listUsers);
            return "admin/products/list-user-page";
        }
        model.addAttribute("username",username);
        List<User> listUsers = page.getContent();
        model.addAttribute("listUsers",listUsers);
        return "admin/products/list-user-page";
    }

    @RequestMapping("/pagesearch/{pageNumber}&username={username}")
    public String viewPageSearch(Model model,
                                 @PathVariable(name = "pageNumber") int currentPage,
                                 @PathVariable(name = "username") String username
    ) {
        Sort sort = Sort.by("userId").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,5,sort);
        Page<User> page = userService.findByUsernameContaining(username,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("username",username);
        List<User> listUsers = page.getContent();
        model.addAttribute("listUsers",listUsers);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/products/list-user-page";
    }


    @RequestMapping("my-account/{userId}")
    public String myAccount(@PathVariable("userId")Long userId, Model model){
        User user = userService.findById(userId).get();
        model.addAttribute("emailUser", user.getEmail());
        model.addAttribute("image",user.getImage());
        model.addAttribute("user",user);
        return "home/user/my-account-role";
    }

    @PostMapping("update-user")
    public String updateUser(@ModelAttribute("user")User user,Model model){
        User user1 = userService.findById(user.getUserId()).get();
        if (user.getPassword() == null || user.getUsername() == null || user.getImage() == null){
            user.setPassword(user1.getPassword());
            user.setUsername(user1.getUsername());
            user.setImage(user1.getImage());
        }
        User user2 = new User();
        BeanUtils.copyProperties(user,user2);
        user2.setEnabled(true);
        user2.setAccountNonLocked(true);
        userService.save(user2);
        model.addAttribute("image",user2.getImage());
        model.addAttribute("user",user2);
        return "redirect:/admin/user";
    }
}
