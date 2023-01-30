package com.web.shop.webbanhang.controller.admin;


import com.web.shop.webbanhang.entity.*;
import com.web.shop.webbanhang.model.BrandDto;
import com.web.shop.webbanhang.model.ProductDetailDto;
import com.web.shop.webbanhang.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/productDetails")
public class ProductDetailController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private MemoryStorageService memoryStorageService;
    @Autowired
    private RamService ramService;

    @ModelAttribute("listProducts")
    public List<Product> productList(){
        return productService.findAll();
    }

    @ModelAttribute("listColors")
    public List<Color> colorList(){
        return colorService.findAll();
    }

    @ModelAttribute("listMemoryStorages")
    public List<MemoryStorage> memoryStorageList(){
        return memoryStorageService.findAll();
    }

    @ModelAttribute("listRams")
    public List<Ram> ramList(){
        return ramService.findAll();
    }

    @RequestMapping("/add")
    public String add(Model model){
        ProductDetailDto dto = new ProductDetailDto();
        dto.setIsEdit(false);
        model.addAttribute("productDetail",dto);
        return "admin/productDetails/add-product-detail";
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
        return "admin/productDetails/list-product-detail";
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(@Valid @ModelAttribute("productDetail")ProductDetailDto dto,
                                     BindingResult result,
                                     @RequestParam("imageFile")MultipartFile imageFile
            ,HttpServletRequest request
            , ModelMap model) throws IOException {
        if(result.hasErrors()) {
            return new ModelAndView("admin/productDetails/add-product-detail");
        }

        String path = request.getServletContext().getRealPath("/static/images");
        File f = new File(path);
        File destination = new File(f.getAbsolutePath()+"/"+imageFile.getOriginalFilename());

        if(!destination.exists()){
            try {
                Files.write(destination.toPath(),imageFile.getBytes(),StandardOpenOption.CREATE);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        if (imageFile.isEmpty()){
            ProductDetail entity = productDetailService.findById(dto.getProductDetailId()).get();
            Product product = productService.findById(dto.getProductId()).get();
            entity.setProduct(product);
            Color color = new Color();
            color.setColorId(dto.getColorId());
            MemoryStorage memoryStorage = new MemoryStorage();
            memoryStorage.setMemoryStorageId(dto.getMemoryStorageId());
            entity.setColor(color);
            entity.setMemoryStorage(memoryStorage);
            entity.setProductDetailName(product.getProductName());
            productDetailService.save(entity);
        } else {
            dto.setImage(imageFile.getOriginalFilename());
            ProductDetail entity = new ProductDetail();
            BeanUtils.copyProperties(dto, entity);
            Product product = productService.findById(dto.getProductId()).get();
            entity.setProduct(product);
            Color color = new Color();
            color.setColorId(dto.getColorId());
            MemoryStorage memoryStorage = new MemoryStorage();
            memoryStorage.setMemoryStorageId(dto.getMemoryStorageId());
            Ram ram = new Ram();
            ram.setRamId(dto.getRamId());
            entity.setRam(ram);
            entity.setColor(color);
            entity.setMemoryStorage(memoryStorage);
            entity.setProductDetailName(product.getProductName());
            entity.setImage(imageFile.getOriginalFilename());
            entity.setOutstanding(false);
            entity.setBestseller(false);
            entity.setDiscountCurrent(false);
            entity.setMostRecent(false);
            productDetailService.save(entity);
        }
        return new ModelAndView("redirect:/admin/productDetails/",model);
    }

    @RequestMapping("/")
    public String listProductDetail(Model model){
        int currentPage = 1;
        Page<ProductDetail> page = productDetailService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/productDetails/list-product-detail";
    }

    @RequestMapping("/listoutstanding")
    public String listProductDetailOutstanding(Model model){
        int currentPage = 1;
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,10,sort);
        Page<ProductDetail> page = productDetailService.findByOutstanding(true,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "admin/productDetails/list-product-detail-outstanding";
    }

    @RequestMapping("/listoutstanding/page/{pageNumber}")
    public String viewPageOutstanding(Model model,
                           @PathVariable(name = "pageNumber") int currentPage) {
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,10,sort);
        Page<ProductDetail> page = productDetailService.findByOutstanding(true,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/productDetails/list-product-detail-outstanding";
    }

    @RequestMapping("/listmostRecent")
    public String listProductDetailMostRecent(Model model){
        int currentPage = 1;
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,10,sort);
        Page<ProductDetail> page = productDetailService.findByMostRecent(true,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "admin/productDetails/list-product-detail-mostrecent";
    }

    @RequestMapping("/listmostRecent/page/{pageNumber}")
    public String viewPageMostRecent(Model model,
                                      @PathVariable(name = "pageNumber") int currentPage) {
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,10,sort);
        Page<ProductDetail> page = productDetailService.findByMostRecent(true,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/productDetails/list-product-detail-mostrecent";
    }

    @RequestMapping("/listbestseller")
    public String listProductDetailBestseller(Model model){
        int currentPage = 1;
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,10,sort);
        Page<ProductDetail> page = productDetailService.findByBestseller(true,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "admin/productDetails/list-product-detail-bestseller";
    }

    @RequestMapping("/listbestseller/page/{pageNumber}")
    public String viewPageMostBestseller(Model model,
                                     @PathVariable(name = "pageNumber") int currentPage) {
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,10,sort);
        Page<ProductDetail> page = productDetailService.findByBestseller(true,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/productDetails/list-product-detail-bestseller";
    }

    @RequestMapping("/listdiscountCurrent")
    public String listProductDetailDiscountCurrent(Model model){
        int currentPage = 1;
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,10,sort);
        Page<ProductDetail> page = productDetailService.findByDiscountCurrent(true,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "admin/productDetails/list-product-detail-discountCurrent";
    }

    @RequestMapping("/listdiscountCurrent/page/{pageNumber}")
    public String viewPageMostDiscountCurrent(Model model,
                                         @PathVariable(name = "pageNumber") int currentPage) {
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,10,sort);
        Page<ProductDetail> page = productDetailService.findByDiscountCurrent(true,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/productDetails/list-product-detail-discountCurrent";
    }

    @RequestMapping("productNameId/{productId}/page/{pageNumber}")
    public String viewListPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage,
                           @PathVariable(name = "productId") Long productId) {
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,5,sort);
        Page<ProductDetail> page = productDetailService.findByProduct_ProductId(productId,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("namePro",productId);

        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/productDetails/list-product-detail-page";
    }

    @RequestMapping("productNameId/{productId}")
    public String viewListProduct(Model model,
                           @PathVariable(name = "productId") Long productId) {
        int currentPage = 1;
        Sort sort = Sort.by("productDetailName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,5,sort);
        Page<ProductDetail> page = productDetailService.findByProduct_ProductId(productId,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("namePro",productId);
        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("listProductDetails",listProductDetails);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/productDetails/list-product-detail-page";
    }

    @RequestMapping("/grid")
    public String listGrid(Model model){
        return "home/shop-4-column";
    }

    @RequestMapping("/list-grid")
    public String listListGrid(Model model){
        return "home/shop-left-sidebar";
    }

    @RequestMapping("/list-4-grid")
    public String listP(Model model){
        List<ProductDetail> listProductDetails = productDetailService.findAll();
        model.addAttribute("listProductDetails",listProductDetails);
        return "admin/productDetails/list-4-grid";
    }

    @GetMapping("edit/{productDetailId}")
    public ModelAndView edit(ModelMap model, @PathVariable("productDetailId")Long productDetailId) {
        Optional<ProductDetail> opt = productDetailService.findById(productDetailId);
        ProductDetailDto productDetail = new ProductDetailDto();
        if(opt.isPresent()) {
            ProductDetail entity = opt.get();
            BeanUtils.copyProperties(entity, productDetail);
            productDetail.setIsEdit(true);
            productDetail.setProductId(entity.getProduct().getProductId());
            productDetail.setColorId(entity.getColor().getColorId());
            List<Product> list = productService.findAll();
            model.addAttribute("listProducts",list);
            List<Color> listColor = colorService.findAll();
            model.addAttribute("listColors",listColor);
            model.addAttribute("productDetail",productDetail);
            return new ModelAndView("admin/productDetails/add-product-detail",model);
        }
        List<ProductDetail> list = productDetailService.findAll();
        model.addAttribute("listProductDetails",list);
        model.addAttribute("message", "Product is not existed");
        return new ModelAndView("forward:/admin/productDetails/",model);
    }

    @GetMapping("delete/{productDetailId}")
    public String delete(@PathVariable("productDetailId")Long productDetailId, Model model){
        productDetailService.deleteById(productDetailId);
        List<ProductDetail> list = productDetailService.findAll();
        model.addAttribute("listProducts",list);
        return "redirect:/admin/productDetails/";
    }

    @GetMapping("view/{productDetailId}")
    public ModelAndView view(ModelMap model, @PathVariable("productDetailId")Long productDetailId) {
        Optional<ProductDetail> opt = productDetailService.findById(productDetailId);
        ProductDetailDto productDetail = new ProductDetailDto();
        if(opt.isPresent()) {
            ProductDetail entity = opt.get();
            BeanUtils.copyProperties(entity, productDetail);
            productDetail.setIsEdit(true);
            productDetail.setProductId(entity.getProduct().getProductId());
            productDetail.setColorId(entity.getColor().getColorId());
            List<Product> list = productService.findAll();
            model.addAttribute("listProducts",list);
            List<Color> listColor = colorService.findAll();
            model.addAttribute("listColors",listColor);
            model.addAttribute("productDetail",productDetail);
            return new ModelAndView("home/user/view-product-detail",model);
        }
        List<ProductDetail> list = productDetailService.findAll();
        model.addAttribute("listProductDetails",list);
        model.addAttribute("message", "Product is not existed");
        return new ModelAndView("forward:/admin/productDetails/",model);
    }

    @RequestMapping("outstanding/{productDetailId}")
    public String addStanding(@PathVariable("productDetailId")Long productDetailId){
        ProductDetail productDetail = productDetailService.findById(productDetailId).get();
        if (productDetail.getOutstanding() == false){
            productDetail.setOutstanding(true);
            productDetailService.save(productDetail);
        } else {
            productDetail.setOutstanding(false);
            productDetailService.save(productDetail);
        }
        return "redirect:/admin/productDetails/";
    }

    @RequestMapping("outstandings/{productDetailId}")
    public String addStandings(@PathVariable("productDetailId")Long productDetailId){
        ProductDetail productDetail = productDetailService.findById(productDetailId).get();
            productDetail.setOutstanding(false);
            productDetailService.save(productDetail);
        return "redirect:/admin/productDetails/listoutstanding";
    }

    @RequestMapping("mostcurrent/{productDetailId}")
    public String addNewCurrent(@PathVariable("productDetailId")Long productDetailId){
        ProductDetail productDetail = productDetailService.findById(productDetailId).get();
        if (productDetail.getMostRecent() == false){
            productDetail.setMostRecent(true);
            productDetailService.save(productDetail);
        } else {
            productDetail.setMostRecent(false);
            productDetailService.save(productDetail);
        }
        return "redirect:/admin/productDetails/";
    }

    @RequestMapping("mostcurrents/{productDetailId}")
    public String addNewCurrents(@PathVariable("productDetailId")Long productDetailId){
        ProductDetail productDetail = productDetailService.findById(productDetailId).get();

            productDetail.setMostRecent(false);
            productDetailService.save(productDetail);
        return "redirect:/admin/productDetails/listmostRecent";
    }

    @RequestMapping("bestseller/{productDetailId}")
    public String addBestseller(@PathVariable("productDetailId")Long productDetailId){
        ProductDetail productDetail = productDetailService.findById(productDetailId).get();
        if (productDetail.getBestseller() == false){
            productDetail.setBestseller(true);
            productDetailService.save(productDetail);
        } else {
            productDetail.setBestseller(false);
            productDetailService.save(productDetail);
        }
        return "redirect:/admin/productDetails/";
    }

    @RequestMapping("bestsellers/{productDetailId}")
    public String addBestsellers(@PathVariable("productDetailId")Long productDetailId){
        ProductDetail productDetail = productDetailService.findById(productDetailId).get();
            productDetail.setBestseller(false);
            productDetailService.save(productDetail);
        return "redirect:/admin/productDetails/listbestseller";
    }

    @RequestMapping("discountCurrent/{productDetailId}")
    public String addDiscountCurrent(@PathVariable("productDetailId")Long productDetailId){
        ProductDetail productDetail = productDetailService.findById(productDetailId).get();
        if (productDetail.getDiscountCurrent() == false){
            productDetail.setDiscountCurrent(true);
            productDetailService.save(productDetail);
        } else {
            productDetail.setDiscountCurrent(false);
            productDetailService.save(productDetail);
        }
        return "redirect:/admin/productDetails/";
    }

    @RequestMapping("discountCurrents/{productDetailId}")
    public String addDiscountCurrents(@PathVariable("productDetailId")Long productDetailId){
        ProductDetail productDetail = productDetailService.findById(productDetailId).get();
            productDetail.setDiscountCurrent(false);
            productDetailService.save(productDetail);
        return "redirect:/admin/productDetails/listdiscountCurrent";
    }

    @GetMapping("/search")
    public String search(ModelMap model,@RequestParam(value = "productDetailName",required = false)String productDetailName){
        Page<ProductDetail> page = null;
        if(StringUtils.hasText(productDetailName)) {
            int pageNumber = 1;
            Sort sort = Sort.by("productDetailName").ascending();
            Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);
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
            BrandDto dto = new BrandDto();
            dto.setIsEdit(false);
            model.addAttribute("productDetailName",productDetailName);
            List<ProductDetail> listProductDetails = page.getContent();
            model.addAttribute("brand", dto);
            model.addAttribute("listProductDetails", listProductDetails);
            return "admin/productDetails/list-product-detail";
        }
        BrandDto dto = new BrandDto();
        dto.setIsEdit(false);
        model.addAttribute("productDetailName",productDetailName);
        List<ProductDetail> listProductDetails = page.getContent();
        model.addAttribute("brand", dto);
        model.addAttribute("listProductDetails", listProductDetails);
        return "admin/productDetails/list-product-detail-page";
    }
}