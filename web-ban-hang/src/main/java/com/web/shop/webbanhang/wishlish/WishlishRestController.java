package com.web.shop.webbanhang.wishlish;

import com.web.shop.webbanhang.entity.User;
import com.web.shop.webbanhang.entity.Wishlish;
import com.web.shop.webbanhang.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WishlishRestController {

    @Autowired
    private WishlishService wishlishService;

    @PostMapping("/wishlish/add/{pid}")
    public int addProductToWishlish(@PathVariable ("pid")Long productDetailId,
                                   @AuthenticationPrincipal
                                           MyUserDetails myUserDetails) {
        User user = null;
        if(myUserDetails == null) {
            return 0;
        }
        user = myUserDetails.getUser();
        if(user == null) {
            return 0;
        }
        wishlishService.addProduct(productDetailId, user.getUserId());
        List<Wishlish> wishlishes = wishlishService.findByUser_UserId(user.getUserId());
        int quantityItem = (int) wishlishes.stream().count();
        return quantityItem;
    }

    @PostMapping("/wishlish/remove/{pid}")
    public int removeProductOfWishlish(@PathVariable("pid")Long productDetailId,
                                @AuthenticationPrincipal MyUserDetails myUserDetails) {
        User user = null;
        if(myUserDetails == null) {
            return 0;
        }
        user = myUserDetails.getUser();
        if(user == null) {
            return 0;
        }
        wishlishService.deleteByUser_UserIdAndProductDetail_ProductDetailId(user.getUserId(),productDetailId);
        List<Wishlish> wishlishes = wishlishService.findByUser_UserId(user.getUserId());
        int quantityItem = (int) wishlishes.stream().count();
        return quantityItem;
    }
}
