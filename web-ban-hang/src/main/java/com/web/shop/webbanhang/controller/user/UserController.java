package com.web.shop.webbanhang.controller.user;

import cn.apiclub.captcha.Captcha;
import com.web.shop.webbanhang.captcha.CaptchaGenerator;
import com.web.shop.webbanhang.entity.*;
import com.web.shop.webbanhang.model.BrandDto;
import com.web.shop.webbanhang.model.UserDetailDto;
import com.web.shop.webbanhang.model.UserDto;
import com.web.shop.webbanhang.security.MyUserDetails;
import com.web.shop.webbanhang.security.RoleService;
import com.web.shop.webbanhang.security.UserService;
import com.web.shop.webbanhang.service.*;
import com.web.shop.webbanhang.shoppingcart.OrderDetailService;
import com.web.shop.webbanhang.shoppingcart.OrderService;
import com.web.shop.webbanhang.wishlish.WishlishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private MemoryStorageService memoryStorageService;

    @Autowired
    private RamService ramService;

    @Autowired
    private UserService userService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private WishlishService wishlishService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("listOutstanding")
    public List<ProductDetail> listOutstanding(){
        return productDetailService.findByOutstanding(true);
    }

        @ModelAttribute("listMostRecent")
    public List<ProductDetail> listMostRecent(){
        return productDetailService.findByMostRecent(true);
    }

    @ModelAttribute("listBestseller")
    public List<ProductDetail> listBestseller(){
        return productDetailService.findByBestseller(true);
    }

    @ModelAttribute("listDiscountCurrent")
    public List<ProductDetail> listDiscountCurrent(){
        return productDetailService.findByDiscountCurrent(true);
    }

    @ModelAttribute("listBrands")
    public List<Brand> brandList(){
        return brandService.findAll();
    }

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
            amount.updateAndGet(v -> new Float((float) (v + (item.getProductDetail().getUnitPrice() - priceDiscount) * item.getQuantity())));
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

    @ModelAttribute("listProductDetails")
    public List<ProductDetail> productDetailList(){
        return productDetailService.findAll();
    }

    @RequestMapping("/logout")
    public String login(Model model){
        model.addAttribute("user",genCaptcha());
        return "home/user/login";
    }

    @RequestMapping("/login?error")
    public String loginError(Model model){
        model.addAttribute("user",genCaptcha());
        return "home/user/login";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user",genCaptcha());
        return "home/user/register";
    }

    private UserDto genCaptcha() {
        UserDto captchaSettings = new UserDto();
        Captcha captcha = CaptchaGenerator.generateCaptcha(260, 80);
        captchaSettings.setHiddenCaptcha(captcha.getAnswer());
        captchaSettings.setCaptcha("");
        captchaSettings.setRealCaptcha(CaptchaGenerator.encodeCaptchatoBinary(captcha));
        return captchaSettings;
    }

    @RequestMapping("/login")
    public String logout(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            UserDto userDto = new UserDto();
            model.addAttribute("user",userDto);
            return "home/user/login";
        }
        return "redirect:/users/home";
    }

    @RequestMapping("/home")
    public String home(){
        return "home/user/home";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user")UserDto captchaSettings
            , BindingResult result, Model model){
        if(result.hasErrors()) {
            Captcha captcha = CaptchaGenerator.generateCaptcha(260, 80);
            captchaSettings.setHiddenCaptcha(captcha.getAnswer());
            captchaSettings.setCaptcha("");
            captchaSettings.setRealCaptcha(CaptchaGenerator.encodeCaptchatoBinary(captcha));
            model.addAttribute("noneUsername","Tài khoản không được chứa khoảng trống và độ dài từ 8 kí tự trở lên.");
            model.addAttribute("user",captchaSettings);
        }

        User user = userService.findByEmail(captchaSettings.getEmail());
        if (user != null) {
            Captcha captcha = CaptchaGenerator.generateCaptcha(260, 80);
            captchaSettings.setHiddenCaptcha(captcha.getAnswer());
            captchaSettings.setCaptcha("");
            captchaSettings.setRealCaptcha(CaptchaGenerator.encodeCaptchatoBinary(captcha));
            captchaSettings.setEmail("");
            model.addAttribute("noneEmail","Email đã tồn tại.");
            model.addAttribute("user",captchaSettings);
            return "home/user/register";
        }

        User user1 = userService.findByUsername(captchaSettings.getUsername());
        if (user1 != null){
            Captcha captcha = CaptchaGenerator.generateCaptcha(260, 80);
            captchaSettings.setHiddenCaptcha(captcha.getAnswer());
            captchaSettings.setCaptcha("");
            captchaSettings.setRealCaptcha(CaptchaGenerator.encodeCaptchatoBinary(captcha));
            captchaSettings.setUsername("");
            model.addAttribute("noneUsername","Tài khoản đã tồn tại.");
            model.addAttribute("user",captchaSettings);
        }

        if(captchaSettings.getCaptcha().equals(captchaSettings.getHiddenCaptcha())){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(captchaSettings.getPassword());
            User entity = new User();
            BeanUtils.copyProperties(captchaSettings, entity);
            entity.setPassword(password);
            entity.setEnabled(true);
            entity.setAccountNonLocked(true);
            Role role = roleService.findByRoleName("User");
            entity.addRole(role);
            userService.save(entity);
            return "redirect:/users/home";
        } else {
            model.addAttribute("message","Captcha không hợp lệ.");
            Captcha captcha = CaptchaGenerator.generateCaptcha(260, 80);
            captchaSettings.setHiddenCaptcha(captcha.getAnswer());
            captchaSettings.setCaptcha("");
            captchaSettings.setRealCaptcha(CaptchaGenerator.encodeCaptchatoBinary(captcha));
            model.addAttribute("user",captchaSettings);
        }
        return "home/user/register";
    }

    @RequestMapping("/view/{productDetailId}&memory={memoryStorageId}")
    public ModelAndView productDetail(@PathVariable("productDetailId")Long productDetailId,
                                      @PathVariable("memoryStorageId")Long memoryStorageId
                                      ,ModelMap model){
        Optional<ProductDetail> opt = productDetailService.findById(productDetailId);
        if(opt.isPresent()) {
            ProductDetail entity = opt.get();
            model.addAttribute("productDetail",entity);
            model.addAttribute("productDetailId",entity.getProductDetailId());
            List<ProductDetail> listProductDetails = productDetailService.findByProduct_ProductId(entity.getProduct().getProductId());
            List<ProductDetail> listProductDetailItem = productDetailService.findProductDetailByProduct_ProductIdAndMemoryStorage_MemoryStorageId(entity.getProduct().getProductId(),memoryStorageId);
            List<MemoryStorage> memoryStorageList = new ArrayList<>();
            listProductDetails.forEach(productDetail -> {
                MemoryStorage memoryStorage = new MemoryStorage();
                memoryStorage.setMemoryStorageId(productDetail.getMemoryStorage().getMemoryStorageId());
                memoryStorage.setMemoryStorageName(productDetail.getMemoryStorage().getMemoryStorageName());
                memoryStorageList.add(memoryStorage);
            });
            HashSet<MemoryStorage> listMemory = new HashSet<>(memoryStorageList);
            model.addAttribute("listMemory",listMemory);
            model.addAttribute("listProductDetailItem",listProductDetailItem);
            model.addAttribute("memoryStorageId",memoryStorageId);
            return new ModelAndView("home/user/view-product-detail",model);
        }
        List<ProductDetail> list = productDetailService.findAll();
        model.addAttribute("listProductDetails",list);
        model.addAttribute("message", "Product is not existed");
        return new ModelAndView("forward:/admin/products/",model);
    }

    @RequestMapping("/all-product")
    public String allProduct(Model model){
        int currentPage = 1;
        Page<ProductDetail> page = productDetailService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "home/user/list-product-side-bar";
    }

    @RequestMapping("/page/{pageNumber}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage) {
        Page<ProductDetail> page = productDetailService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "home/user/list-product-side-bar";
    }

    @RequestMapping("list/{categoryId}/page/{pageNumber}")
    public String viewPageCate(Model model,
                           @PathVariable(name = "pageNumber") int currentPage,
                               @PathVariable(name = "categoryId") Long categoryId) {
        Sort sort = Sort.by("product_productName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,9,sort);
        Page<ProductDetail> page = productDetailService.findProductDetailByProduct_Category_CategoryId(categoryId,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<ProductDetail> listProductDetails = page.getContent();
        List<ProductDetail> listBrandByCateId = productDetailService.findProductDetailByProduct_Category_CategoryId(categoryId);
        model.addAttribute("listBrandByCateId",listBrandByCateId);
        model.addAttribute("namePro",categoryId);
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "home/user/list-product-side-bar-by-category";
    }

    @RequestMapping("list/{categoryId}")
    public String sortListProduct(@PathVariable("categoryId")Long categoryId,
                                  Model model){
        int currentPage = 1;
        Sort sort = Sort.by("product_productName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,9,sort);
        Page<ProductDetail> page = productDetailService.findProductDetailByProduct_Category_CategoryId(categoryId,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("namePro",categoryId);
        List<ProductDetail> listProductDetails = page.getContent();
        List<ProductDetail> listBrandByCateId = productDetailService.findProductDetailByProduct_Category_CategoryId(categoryId);
        List<Brand> listBrand = new ArrayList<>();
        listBrandByCateId.forEach(productDetail -> {
            Brand brand = new Brand();
            brand.setBrandId(productDetail.getProduct().getBrand().getBrandId());
            brand.setBrandName(productDetail.getProduct().getBrand().getBrandName());
            listBrand.add(brand);
        });
        HashSet<Brand> listBrandByCateIds = new HashSet<>(listBrand);
        model.addAttribute("listBrandByCateIds",listBrandByCateIds);
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "home/user/list-product-side-bar-by-category";
    }

    @RequestMapping("/index")
    public String index(){
        return "home/user/404";
    }

    @RequestMapping("my-account")
    public String myAccount(@AuthenticationPrincipal MyUserDetails myUserDetails,Model model){
        User user = null;
        if(myUserDetails == null) {
            return "redirect:/users/login";
        }
        user = userService.findById(myUserDetails.getUser().getUserId()).get();
        UserDetailDto dto = new UserDetailDto();
        dto.setUserId(user.getUserId());
        dto.setCity(user.getCity());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setCommune(user.getCommune());
        dto.setDistrict(user.getDistrict());
        model.addAttribute("emailUser", user.getEmail());
        model.addAttribute("image",user.getImage());
        model.addAttribute("user",dto);
        return "home/user/my-account";
    }

    @PostMapping("update-user")
    public String updateUser(@Valid @ModelAttribute("user")UserDetailDto dto, BindingResult result,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             HttpServletRequest request,
                             Model model){
        if(result.hasErrors()) {
            model.addAttribute("user",dto);
            return "home/user/my-account";
        }
        String path = request.getServletContext().getRealPath("/static/images/user");
        File f = new File(path);
        File destination = new File(f.getAbsolutePath()+"/"+imageFile.getOriginalFilename());

        if(!destination.exists()){
            try {
                Files.write(destination.toPath(),imageFile.getBytes(), StandardOpenOption.CREATE);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        User entity = userService.findById(dto.getUserId()).get();
        if (!imageFile.isEmpty()){
            entity.setImage(imageFile.getOriginalFilename());
        }
        entity.setCity(dto.getCity());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setCommune(dto.getCommune());
        entity.setDistrict(dto.getDistrict());
        userService.save(entity);
        return "redirect:/users/my-account";
    }

    @PostMapping("update-password-user")
    public String updatePassword(@AuthenticationPrincipal MyUserDetails myUserDetails,HttpServletRequest request){
        User user = null;
        if(myUserDetails == null) {
            return "redirect:/users/login";
        }
        String password = request.getParameter("password");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passEncode = encoder.encode(password);
        user = userService.findById(myUserDetails.getUser().getUserId()).get();
        user.setPassword(passEncode);
        userService.save(user);
        return "redirect:/users/my-account";
    }

    @GetMapping("/search-product")
    public String search(ModelMap model,@RequestParam(value = "productDetailName",required = false)String productDetailName){
        Page<ProductDetail> page = null;
        if(StringUtils.hasText(productDetailName)) {
            int pageNumber = 1;
            Sort sort = Sort.by("productDetailName").ascending();
            Pageable pageable = PageRequest.of(pageNumber - 1,9,sort);
            page = productDetailService.findByProductDetailNameContaining(productDetailName,pageable);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("productDetailName",productDetailName);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
        } else {
            page = productDetailService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            model.addAttribute("productDetailName",productDetailName);
            List<ProductDetail> listProductDetails = page.getContent();
            model.addAttribute("listProductDetails", listProductDetails);
            return "home/user/list-product-side-bar";
        }
        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails", listProductDetails);
         return "home/user/list-product-side-bar-search";
    }

    @RequestMapping("/page-search/{pageNumber}&productDetailName={productDetailName}")
    public String pageProductDetailName(Model model,
                                        @PathVariable(name = "pageNumber") int currentPage,
                                        @PathVariable(name = "productDetailName") String productDetailName) {

        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,9,sort);
        Page<ProductDetail> page = productDetailService.findByProductDetailNameContaining(productDetailName,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("productDetailName", productDetailName);
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);

        return "home/user/list-product-side-bar-search";
    }

    @RequestMapping("blog-list-left-sidebar")
    public String blog(){
        return "home/user/blog-list-left-sidebar";
    }

    @RequestMapping("about")
    public String about(){
        return "home/user/about";
    }
}
