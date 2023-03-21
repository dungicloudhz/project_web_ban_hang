package com.web.shop.webbanhang.controller.admin;

import com.web.shop.webbanhang.entity.Brand;
import com.web.shop.webbanhang.entity.Category;
import com.web.shop.webbanhang.entity.Product;
import com.web.shop.webbanhang.entity.ProductDetail;
import com.web.shop.webbanhang.model.BrandDto;
import com.web.shop.webbanhang.model.ProductDto;
import com.web.shop.webbanhang.service.BrandService;
import com.web.shop.webbanhang.service.CategoryService;
import com.web.shop.webbanhang.service.ProductDetailService;
import com.web.shop.webbanhang.service.ProductService;
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
@RequestMapping("admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;


    @ModelAttribute("listCategories")
    public List<Category> listCategories(){
        return categoryService.findAll();
    }

    @ModelAttribute("listBrands")
    public List<Brand> listBrands(){
        return brandService.findAll();
    }

    @RequestMapping(value = {"/"})
    public String add(Model model){
        ProductDto dto = new ProductDto();
        dto.setIsEdit(false);
        List<Product> list = productService.findAll();
        model.addAttribute("listProducts",list);
        model.addAttribute("product", dto);

        int currentPage = 1;
        Page<Product> page = productService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Product> listProducts = page.getContent();
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/products/list";
    }

    @RequestMapping("/page/{pageNumber}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage) {
        ProductDto dto = new ProductDto();
        dto.setIsEdit(false);
        model.addAttribute("product", dto);

        Page<Product> page = productService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Product> listProducts = page.getContent();
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/products/list";
    }

    @RequestMapping("/pagesearch/{pageNumber}&productName={productName}")
    public String viewPageSearch(Model model,
                                 @PathVariable(name = "pageNumber") int currentPage,
                                 @PathVariable(name = "productName") String productName
    ) {
        ProductDto dto = new ProductDto();
        dto.setIsEdit(false);
        model.addAttribute("product", dto);

        Sort sort = Sort.by("productName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,5,sort);
        Page<Product> page = productService.findByProductNameContaining(productName,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("productName",productName);
        List<Product> listProducts = page.getContent();
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/products/search-paginated";
    }


    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(@Valid @ModelAttribute("product")ProductDto dto, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile
            , HttpServletRequest request
            , ModelMap model) throws IOException {
        if(result.hasErrors()) {
            Page<Product> page = productService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Product> listProducts = page.getContent();
            model.addAttribute("listProducts",listProducts);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            return "admin/products/list";
        }
        String path = request.getServletContext().getRealPath("/static/images/products");
        File f = new File(path);
        File destination = new File(f.getAbsolutePath()+"/"+imageFile.getOriginalFilename());

        if(!destination.exists()){
            try {
                Files.write(destination.toPath(),imageFile.getBytes(), StandardOpenOption.CREATE);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        if (imageFile.isEmpty()){
            Product entity = productService.findById(dto.getProductId()).get();
            entity.setProductName(dto.getProductName());
            entity.setDescription(dto.getDescription());
            entity.setStatus(dto.getStatus());
            Category category = new Category();
            category.setCategoryId(dto.getCategoryId());
            entity.setCategory(category);
            Brand brand = new Brand();
            brand.setBrandId(dto.getBrandId());
            entity.setBrand(brand);
            productService.save(entity);
        } else {
            dto.setImage(imageFile.getOriginalFilename());
            Product entity = new Product();
            BeanUtils.copyProperties(dto, entity);
            Category category = new Category();
            category.setCategoryId(dto.getCategoryId());
            entity.setCategory(category);
            Brand brand = new Brand();
            brand.setBrandId(dto.getBrandId());
            entity.setBrand(brand);
            entity.setImage(imageFile.getOriginalFilename());
            productService.save(entity);
        }


        Page<Product> page = productService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Product> listProducts = page.getContent();
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "redirect:/admin/products/";
    }

    @GetMapping("edit/{productId}")
    public ModelAndView edit(ModelMap model, @PathVariable("productId")Long productId) {

        Optional<Product> opt = productService.findById(productId);
        ProductDto product = new ProductDto();
        if(opt.isPresent()) {
            Product entity = opt.get();
            BeanUtils.copyProperties(entity, product);
            product.setIsEdit(true);
            product.setCategoryId(entity.getCategory().getCategoryId());
            product.setBrandId(entity.getBrand().getBrandId());
            model.addAttribute("product", product);

            Page<Product> page = productService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Product> listProducts = page.getContent();
            model.addAttribute("listProducts",listProducts);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            return new ModelAndView("admin/products/list",model);
        }
        Page<Product> page = productService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Product> listProducts = page.getContent();
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        model.addAttribute("message", "Product is not existed");
        return new ModelAndView("redirect:/admin/products/",model);
    }

    @GetMapping("delete/{productId}")
    public String delete(@PathVariable("productId")Long productId, Model model){
        productService.deleteById(productId);
        Page<Product> page = productService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Product> listProducts = page.getContent();
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "redirect:/admin/products/";
    }

    @GetMapping("/search")
    public String search(ModelMap model,@RequestParam(value = "productName",required = false)String productName){
        Page<Product> page = null;
        if(StringUtils.hasText(productName)) {
            int pageNumber = 1;
            Sort sort = Sort.by("productName").ascending();
            Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);
            page = productService.findByProductNameContaining(productName,pageable);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("productName",productName);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
        } else {
            page = productService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            ProductDto dto = new ProductDto();
            dto.setIsEdit(false);
            model.addAttribute("name",productName);
            List<Product> listProducts = page.getContent();
            model.addAttribute("product", dto);
            model.addAttribute("listProducts", listProducts);
            return "admin/products/list";
        }
        ProductDto dto = new ProductDto();
        dto.setIsEdit(false);
        model.addAttribute("name",productName);
        List<Product> listProducts = page.getContent();
        model.addAttribute("product", dto);
        model.addAttribute("listProducts", listProducts);
        return "admin/products/search-paginated";
    }

}