package com.web.shop.webbanhang.shoppingcart;

import com.web.shop.webbanhang.entity.*;
import com.web.shop.webbanhang.model.OrderDto;
import com.web.shop.webbanhang.model.ProductDetailDto;
import com.web.shop.webbanhang.security.MyUserDetails;
import com.web.shop.webbanhang.service.ProductDetailService;
import com.web.shop.webbanhang.wishlish.WishlishService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:8080/**", maxAge = 3600)
public class ShoppingCartRestController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private WishlishService wishlishService;

//    @GetMapping("/listOrder")
//    public List<ProductDetailDto> listOrder( @AuthenticationPrincipal
//                                                         MyUserDetails myUserDetails){
//        User user = null;
//        if(myUserDetails == null) {
//            return new ArrayList<>();
//        }
//        user = myUserDetails.getUser();
//        List<OrderDetail> orderList = orderDetailService.findByTemporaryId(user.getUserId());
//        List<ProductDetailDto> list = new ArrayList<>();
//        orderList.forEach(item -> {
//            ProductDetailDto dto = new ProductDetailDto();
//            dto.setProductDetailId(item.getProductDetail().getProductDetailId());
//            dto.setProductDetailName(item.getProductDetail().getProductDetailName());
//            dto.setQuantity(item.getQuantity());
//            dto.setUnitPrice(item.getProductDetail().getUnitPrice());
//            dto.setDiscount(item.getProductDetail().getDiscount());
//            dto.setImage(item.getProductDetail().getImage());
//            list.add(dto);
//        });
//        return list;
//    }

    @GetMapping("/quantity/cart")
    public int quantityItemCart(@AuthenticationPrincipal
                                                MyUserDetails myUserDetails){
        User user = null;
        if(myUserDetails == null) {
            return 0;
        }
        user = myUserDetails.getUser();
        List<OrderDetail> orderList = orderDetailService.findByTemporaryId(user.getUserId());
        int quantityItem = orderList.stream().mapToInt(OrderDetail::getQuantity).sum();
        return quantityItem;
    }

    @GetMapping("/wishlish/cart")
    public int quantityItemWishlish(@AuthenticationPrincipal
                                            MyUserDetails myUserDetails){
        User user = null;
        if(myUserDetails == null) {
            return 0;
        }
        user = myUserDetails.getUser();
        List<Wishlish> wishlishes = wishlishService.findByUser_UserId(user.getUserId());
        int quantityItem = (int) wishlishes.stream().count();
        return quantityItem;
    }


    @GetMapping("/view/modal")
    public ProductDetailDto modalProductDetail(@RequestParam("productDetailId")Long productDetaiId, Model model){
        ProductDetail productDetail = productDetailService.findById(productDetaiId).get();
        List<ProductDetail> listProductDetailByProduct = productDetailService.findByProduct_ProductId(productDetail.getProduct().getProductId());
        model.addAttribute("listImage",listProductDetailByProduct);
        ProductDetailDto dto = new ProductDetailDto();
        BeanUtils.copyProperties(productDetail,dto);
        dto.setColorName(productDetail.getColor().getColorName());
        dto.setMemoryStorageName(productDetail.getMemoryStorage().getMemoryStorageName());
        dto.setImage(productDetail.getImage());
        dto.setSizeRam(productDetail.getRam().getSizeRam());
        dto.setBrand(productDetail.getProduct().getBrand().getBrandName());
        return dto;
    }

    @PostMapping("/cart/add/{pid}/{qty}")
    public int addProductToCart(@PathVariable ("pid")Long productId,@PathVariable("qty")Integer quantity,
                                   @AuthenticationPrincipal
                                   MyUserDetails myUserDetails) {
        System.out.println("addProductToCart: "+productId+" - "+quantity);
        User user = null;
        if(myUserDetails == null) {
            List<OrderDetail> orderList = orderDetailService.findByTemporaryId(user.getUserId());
            int quantityItem = orderList.stream().mapToInt(OrderDetail::getQuantity).sum();
            return quantityItem;
        }
        user = myUserDetails.getUser();
        if(user == null) {
            return 0;
        }
        Integer addedQuantity = orderDetailService.addProduct(productId, quantity, user.getUserId());

        List<OrderDetail> orderList = orderDetailService.findByTemporaryId(user.getUserId());
        int quantityItem = orderList.stream().mapToInt(OrderDetail::getQuantity).sum();
        return quantityItem;
    }

    @PostMapping("/cart/update/{pid}/{qty}")
    public String updateQuantity(@PathVariable("pid")Long productId,@PathVariable("qty")Integer quantity,
                                 @AuthenticationPrincipal MyUserDetails myUserDetails) {
        User user = null;
        if(myUserDetails == null) {
            return "You must login to add this product to your shopping cart.";
        }

        user = myUserDetails.getUser();
        if(user == null) {
            return "You must login to add this product to your shopping cart.";
        }


        float subtotal = orderDetailService.updateQuantity(productId, quantity, user.getUserId());
        List<OrderDetail> orderList = orderDetailService.findByTemporaryId(user.getUserId());
        int quantityItem = orderList.stream().mapToInt(OrderDetail::getQuantity).sum();

        return String.valueOf(subtotal);
    }

    @PostMapping("/cart/remove/{pid}")
    public int removeProduct(@PathVariable("pid")Long productId,
                             @AuthenticationPrincipal MyUserDetails myUserDetails) {
        User user = null;
        if(myUserDetails == null) {
            return 0;
        }
        user = myUserDetails.getUser();
        if(user == null) {
            return 0;
        }
        orderDetailService.removeProduct(productId, user.getUserId());
        List<OrderDetail> orderList = orderDetailService.findByTemporaryId(user.getUserId());
        int quantityItem = orderList.stream().mapToInt(OrderDetail::getQuantity).sum();
        return quantityItem;
    }
}
