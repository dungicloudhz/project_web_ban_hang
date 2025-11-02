package com.web.shop.webbanhang.controller.admin;

import com.web.shop.webbanhang.entity.Brand;
import com.web.shop.webbanhang.entity.User;
import com.web.shop.webbanhang.model.BrandDto;
import com.web.shop.webbanhang.security.UserService;
import com.web.shop.webbanhang.service.BrandService;
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
@RequestMapping("admin/brands")
public class BrandController {

    @Autowired
    BrandService brandService;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/"})
    public String add(Model model){
        BrandDto dto = new BrandDto();
        dto.setIsEdit(false);
        model.addAttribute("brand", dto);

        int currentPage = 1;
        Page<Brand> page = brandService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Brand> listBrands = page.getContent();
        model.addAttribute("listBrands",listBrands);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/brands/list";
    }

    @RequestMapping("/page/{pageNumber}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage) {

        BrandDto dto = new BrandDto();
        dto.setIsEdit(false);
        model.addAttribute("brand", dto);

        Page<Brand> page = brandService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Brand> listBrands = page.getContent();
        model.addAttribute("listBrands",listBrands);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/brands/list";
    }

    @RequestMapping("/pagesearch/{pageNumber}&brandName={brandName}")
    public String viewPageSearch(Model model,
                           @PathVariable(name = "pageNumber") int currentPage,
                                 @PathVariable(name = "brandName") String brandName
            ) {
        BrandDto dto = new BrandDto();
        dto.setIsEdit(false);
        model.addAttribute("brand", dto);

        Sort sort = Sort.by("brandName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,5,sort);
        Page<Brand> page = brandService.findByBrandNameContaining(brandName,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("name",brandName);
        List<Brand> listBrands = page.getContent();
        model.addAttribute("listBrands",listBrands);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/brands/search-paginated";
    }

    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public String saveOrUpdate(@Valid @ModelAttribute("brand")BrandDto dto, BindingResult result,
                               @RequestParam("imageFile") MultipartFile imageFile
            , HttpServletRequest request
            , ModelMap model) throws IOException {
        if(result.hasErrors()) {
            Page<Brand> page = brandService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Brand> listBrands = page.getContent();
            model.addAttribute("listBrands",listBrands);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            return "admin/brands/list";
        }

        String path = request.getServletContext().getRealPath("/static/images/brands");
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
            Brand entity = dto.getBrandId() != null ? brandService.findById(dto.getBrandId()).get() : new Brand();
            entity.setBrandName(dto.getBrandName());
            entity.setDescription(dto.getDescription());
            brandService.save(entity);
        } else {
            dto.setImage(imageFile.getOriginalFilename());
            Brand entity = dto.getBrandId() != null ? brandService.findById(dto.getBrandId()).get() : new Brand();
            BeanUtils.copyProperties(dto, entity);
            brandService.save(entity);
        }
        Page<Brand> page = brandService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Brand> listBrands = page.getContent();
        model.addAttribute("listBrands",listBrands);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "forward:/admin/brands/";
    }

    @GetMapping("edit/{brandId}")
    public ModelAndView edit(ModelMap model,@PathVariable("brandId")Long brandId) {

        Optional<Brand> opt = brandService.findById(brandId);
        BrandDto brand = new BrandDto();
        if(opt.isPresent()) {
            Brand entity = opt.get();

            BeanUtils.copyProperties(entity, brand);
            brand.setIsEdit(true);
            model.addAttribute("brand", brand);
            Page<Brand> page = brandService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Brand> listBrands = page.getContent();
            model.addAttribute("listBrands",listBrands);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            return new ModelAndView("admin/brands/list",model);
        }
        Page<Brand> page = brandService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Brand> listBrands = page.getContent();
        model.addAttribute("listBrands",listBrands);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        model.addAttribute("message", "Brand is not existed");
        return new ModelAndView("redirect:/admin/brands/",model);
    }

    @GetMapping("delete/{brandId}")
    public String delete(@PathVariable("brandId")Long brandId, Model model){
        brandService.deleteById(brandId);
        Page<Brand> page = brandService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Brand> listBrands = page.getContent();
        model.addAttribute("listBrands",listBrands);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        model.addAttribute("listBrands",listBrands);
        return "redirect:/admin/brands/";
    }

     @GetMapping("/search")
     public String search(ModelMap model,@RequestParam(value = "brandName",required = false)String brandName){
         Page<Brand> page = null;
         if(StringUtils.hasText(brandName)) {
             int pageNumber = 1;
             Sort sort = Sort.by("brandName").ascending();
             Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);
             page = brandService.findByBrandNameContaining(brandName,pageable);
             long totalItems = page.getTotalElements();
             int totalPages = page.getTotalPages();
             model.addAttribute("brandName",brandName);
             model.addAttribute("totalItems",totalItems);
             model.addAttribute("totalPages",totalPages);
             model.addAttribute("currentPage",1);
         } else {
             page = brandService.findAll(1);
             long totalItems = page.getTotalElements();
             int totalPages = page.getTotalPages();
             model.addAttribute("totalItems",totalItems);
             model.addAttribute("totalPages",totalPages);
             model.addAttribute("currentPage",1);
             BrandDto dto = new BrandDto();
             dto.setIsEdit(false);
             model.addAttribute("name",brandName);
             List<Brand> listBrands = page.getContent();
             model.addAttribute("brand", dto);
             model.addAttribute("listBrands", listBrands);
             return "admin/brands/list";
         }
         BrandDto dto = new BrandDto();
         dto.setIsEdit(false);
         model.addAttribute("name",brandName);
         List<Brand> listBrands = page.getContent();
         model.addAttribute("brand", dto);
         model.addAttribute("listBrands", listBrands);
         return "admin/brands/search-paginated";
     }
}
