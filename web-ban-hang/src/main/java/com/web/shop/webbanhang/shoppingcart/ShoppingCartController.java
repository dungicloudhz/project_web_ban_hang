package com.web.shop.webbanhang.shoppingcart;

import com.web.shop.webbanhang.entity.*;
import com.web.shop.webbanhang.model.OrderDto;
import com.web.shop.webbanhang.security.MyUserDetails;
import com.web.shop.webbanhang.security.UserService;
import com.web.shop.webbanhang.service.*;
import com.web.shop.webbanhang.wishlish.WishlishService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class ShoppingCartController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private MemoryStorageService memoryStorageService;

    @Autowired
    private RamService ramService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private WishlishService wishlishService;


    @ModelAttribute("listBrands")
    public List<Brand> brandList(){
        return brandService.findAll();
    }

    @ModelAttribute("listCategorySideBar")
    public List<Category> categoryList(){
        return categoryService.findAll();
    }

    @ModelAttribute("listColorSideBar")
    public List<Color> colorList(){
        return colorService.findAll();
    }

    @ModelAttribute("listMemoryStorage")
    public List<MemoryStorage> memoryStorageList(){
        return memoryStorageService.findAll();
    }
    @ModelAttribute("listRams")
    public List<Ram> ramList(){
        return ramService.findAll();
    }

    @Autowired
    private ProductDetailService productDetailService;

    @ModelAttribute("listOrderByUser")
    public List<OrderDetail> orderDetailList(@AuthenticationPrincipal MyUserDetails myUserDetails,Model model){
        User user = null;
        if(myUserDetails == null) {
            return new ArrayList<>();
        }
        user = myUserDetails.getUser();
        List<OrderDetail> orderList = orderDetailService.findByTemporaryId(user.getUserId());

        AtomicReference<Float> amount = new AtomicReference<>((float) 0);
        orderList.forEach(item -> {
            float priceDiscount = item.getProductDetail().getUnitPrice()*item.getProductDetail().getDiscount()/100;
            amount.updateAndGet(v -> (float) (v + (item.getProductDetail().getUnitPrice() - priceDiscount) * item.getQuantity()));
        });
        model.addAttribute("amount",amount);
        return orderList;
    }

    @ModelAttribute("listWishlishByUser")
    public List<Wishlish> wishlishByUser(@AuthenticationPrincipal MyUserDetails myUserDetails){
        User user = null;
        if(myUserDetails == null) {
            return new ArrayList<>();
        }
        user = myUserDetails.getUser();
        List<Wishlish> wishlishes = wishlishService.findByUser_UserId(user.getUserId());

        return wishlishes;
    }

    @ModelAttribute("listProductDetails")
    public List<ProductDetail> productDetailList(){
        return productDetailService.findAll();
    }

    @ModelAttribute("listCarts")
    public List<OrderDetail> listCartItem(Model model,
                                          @AuthenticationPrincipal MyUserDetails myUserDetails){
        User user = null;
        if(myUserDetails != null) {
            user = myUserDetails.getUser();
        }
        List<OrderDetail> cartItems = orderDetailService.listOrderDetails(user.getUserId());
        return cartItems;
    }

    @ModelAttribute("numberCartItems")
    public Integer numberCartItem(Model model,
                                  @AuthenticationPrincipal MyUserDetails myUserDetails){
        int number = 0;
        User user = null;
        if(myUserDetails != null) {
            user = myUserDetails.getUser();
        }
        List<OrderDetail> cartItems = orderDetailService.listOrderDetails(user.getUserId());
        for(OrderDetail orderDetail: cartItems){
            number++;
        }
        return number;
    }

    @GetMapping("/cart/{productDetailId}")
    public String shoppingCartNow(@PathVariable("productDetailId")Long productDetailId,
            Model model,
                               @AuthenticationPrincipal MyUserDetails myUserDetails) {
        User user = null;
        if(myUserDetails != null) {
            user = myUserDetails.getUser();
        }
        orderDetailService.addProduct(productDetailId,1,user.getUserId());
        List<OrderDetail> cartItems = orderDetailService.listOrderDetails(user.getUserId());
        model.addAttribute("listOrderDetails", cartItems);
        model.addAttribute("pageTitle", "Shopping Cart");
        return "home/shopping/cart";
    }

    @GetMapping("/cart")
    public String shoppingCart(Model model,
                               @AuthenticationPrincipal MyUserDetails myUserDetails) {
        User user = null;
        if(myUserDetails != null) {
            user = myUserDetails.getUser();
        }
        List<OrderDetail> cartItems = orderDetailService.listOrderDetails(user.getUserId());
        model.addAttribute("listOrderDetails", cartItems);
        model.addAttribute("pageTitle", "Shopping Cart");
        return "home/shopping/cart";
    }



    @RequestMapping("/checkout")
    public String checkout(Model model,
                           @AuthenticationPrincipal MyUserDetails myUserDetails){
        OrderDto dto = new OrderDto();
        User user = null;
        if(myUserDetails != null) {
            user = myUserDetails.getUser();
        }
        List<OrderDetail> cartItems = orderDetailService.listOrderDetails(user.getUserId());
        final Float[] amount = {Float.valueOf(0)};
        cartItems.forEach(item -> {
            Float priceDiscount = item.getProductDetail().getUnitPrice()*item.getProductDetail().getDiscount()/100;
            amount[0] += (item.getProductDetail().getUnitPrice() - priceDiscount) * item.getQuantity();
        });
        dto.setAmount(amount[0]);
        model.addAttribute("order",dto);
        return "home/shopping/checkout";
    }

    @PostMapping("/checkout")
    public String addChechout(@Valid @ModelAttribute("order")OrderDto dto,
            HttpServletRequest request,
                              BindingResult result, Model model,
                              @AuthenticationPrincipal MyUserDetails myUserDetails){
        if(result.hasErrors()) {
            return "home/shopping/checkout";
        }
        User user = null;
        if(myUserDetails != null) {
            user = myUserDetails.getUser();
        }
        List<OrderDetail> cartItems = orderDetailService.listOrderDetails(user.getUserId());
        orderDetailService.updateOrder(dto.getOrderId(), user.getUserId());
        float amount = 0;
        for (OrderDetail item : cartItems){
            amount += item.getQuantity()*item.getProductDetail().getUnitPrice();
        }
        String orderCode = RandomString.make(6);
        String payment = request.getParameter("payment");
        String transp = request.getParameter("transport");
        Float transport = Float.valueOf(transp);
        Order entity = new Order();
        BeanUtils.copyProperties(dto,entity);
        entity.setOrderDate(new Date());
        entity.setUser(user);
        entity.setAmount(amount+transport);
        entity.setPayment(payment);
        entity.setTransport(transport);
        entity.setOrderCode("2023"+orderCode);
        Order order1 = orderService.save(entity);
        cartItems.forEach(item -> {
            item.setOrder(order1);
            orderDetailService.save(item);
            orderDetailService.updateTemporaryId(null,order1.getOrderId());
        });
        List<OrderDetail> orderDetailListByOrder = orderDetailService.findByOrder_OrderId(order1.getOrderId());
        model.addAttribute("order",order1);
        model.addAttribute("listCarts",orderDetailListByOrder);
        model.addAttribute("message","Đặt hàng thành công");
        return "home/shopping/bill";
    }

    @RequestMapping("bill/{orderId}")
    public String bill(@PathVariable("orderId")Long orderId,Model model){
        List<OrderDetail> orderDetailListByOrder = orderDetailService.findByOrder_OrderId(orderId);
        model.addAttribute("listCarts",orderDetailListByOrder);
        Order order = orderService.findById(orderId).get();
        model.addAttribute("order",order);
        return "home/shopping/bill";
    }

    @GetMapping("/list_order")
    public String listOrder(Model model,@AuthenticationPrincipal MyUserDetails myUserDetails){
        User user = null;
        if(myUserDetails != null) {
            user = myUserDetails.getUser();
        }
        int pageNumber = 1;
        Sort sort = Sort.by("orderId").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,10,sort);
        Page<Order> page = orderService.findByUser_UserId(user.getUserId(),pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Order> listOrders = page.getContent();
        model.addAttribute("listOrders",listOrders);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",pageNumber);
        return "home/shopping/list_order";
    }

    @RequestMapping("/page/order/{pageNumber}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage,
                           @AuthenticationPrincipal MyUserDetails myUserDetails) {
        User user = null;
        if(myUserDetails != null) {
            user = myUserDetails.getUser();
        }
        Sort sort = Sort.by("orderId").ascending();
        Pageable pageable = PageRequest.of(currentPage - 1,10,sort);
        Page<Order> page = orderService.findByUser_UserId(user.getUserId(),pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Order> listOrders = page.getContent();
        model.addAttribute("listOrders",listOrders);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "home/shopping/list_order";
    }

    @GetMapping("/search/order")
    public String searchOrderCode(ModelMap model, @RequestParam(value = "orderCode",required = false)String orderCode){
        int pageNumber = 1;
        Sort sort = Sort.by("orderId").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,10,sort);
        Page<Order> page = orderService.findByOrderCodeContaining(orderCode,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Order> listOrders = page.getContent();
        model.addAttribute("listOrders",listOrders);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",pageNumber);
        return "home/shopping/list_order";
    }
}