package com.web.shop.webbanhang.wishlish;

import com.web.shop.webbanhang.entity.User;
import com.web.shop.webbanhang.entity.Wishlish;
import com.web.shop.webbanhang.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WishlishController {

    @Autowired
    private WishlishService wishlishService;

    @RequestMapping("/wishlist")
    public String wishlist(Model model,
                           @AuthenticationPrincipal
                                   MyUserDetails myUserDetails){
        User user = null;
        if(myUserDetails != null) {
            user = myUserDetails.getUser();
        }
        List<Wishlish> listWishlishes = wishlishService.findByUser_UserId(user.getUserId());
        model.addAttribute("listWish",listWishlishes);
        return "home/shopping/wishlist";
    }
}
