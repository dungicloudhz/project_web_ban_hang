package com.web.shop.webbanhang.controller.admin;

import com.web.shop.webbanhang.entity.Category;
import com.web.shop.webbanhang.model.BrandDto;
import com.web.shop.webbanhang.model.CategoryDto;
import com.web.shop.webbanhang.service.CategoryService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = {"/"})
    public String add(Model model){
        CategoryDto dto = new CategoryDto();
        dto.setIsEdit(false);
        model.addAttribute("category", dto);

        int currentPage = 1;
        Page<Category> page = categoryService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Category> listCategories = page.getContent();
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/categories/list";
    }

    @RequestMapping("/page/{pageNumber}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage) {

        CategoryDto dto = new CategoryDto();
        dto.setIsEdit(false);
        model.addAttribute("category", dto);

        Page<Category> page = categoryService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Category> listCategories = page.getContent();
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/categories/list";
    }

    @RequestMapping("/pagesearch/{pageNumber}&name={name}")
    public String viewPageSearch(Model model,
                                 @PathVariable(name = "pageNumber") int currentPage,
                                 @PathVariable(name = "name") String brandName
    ) {
        CategoryDto dto = new CategoryDto();
        dto.setIsEdit(false);
        model.addAttribute("category", dto);

        Sort sort = Sort.by("brandName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,5,sort);
        Page<Category> page = categoryService.findByNameContaining(brandName,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("name",brandName);
        List<Category> listCategories = page.getContent();
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/categories/search-paginated";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(@Valid @ModelAttribute("category")CategoryDto dto, BindingResult result, ModelMap model){
        if(result.hasErrors()) {
            model.addAttribute("category", dto);
            Page<Category> page = categoryService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Category> listCategories = page.getContent();
            model.addAttribute("listCategories",listCategories);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            return "admin/categories/list";
        }
        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);
        categoryService.save(entity);
        Page<Category> page = categoryService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Category> listCategories = page.getContent();
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "forward:/admin/categories/";
    }

    @GetMapping("edit/{categoryId}")
    public ModelAndView edit(ModelMap model,@PathVariable("categoryId")Long categoryId) {
        Optional<Category> opt = categoryService.findById(categoryId);
        CategoryDto category = new CategoryDto();
        if(opt.isPresent()) {
            Category entity = opt.get();
            BeanUtils.copyProperties(entity, category);
            category.setIsEdit(true);
            model.addAttribute("category", category);

            Page<Category> page = categoryService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Category> listCategories = page.getContent();
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            model.addAttribute("listCategories",listCategories);
            return new ModelAndView("admin/categories/list",model);
        }
        Page<Category> page = categoryService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Category> listCategories = page.getContent();
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        model.addAttribute("message", "Category is not existed");
        return new ModelAndView("redirect:/admin/categories/",model);
    }

    @GetMapping("delete/{categoryId}")
    public String delete(@PathVariable("categoryId")Long categoryId, Model model){
        categoryService.deleteById(categoryId);
        Page<Category> page = categoryService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Category> listCategories = page.getContent();
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "redirect:/admin/categories/";
    }

    @GetMapping("/search")
    public String search(ModelMap model,@RequestParam(value = "name",required = false)String name){
        Page<Category> page = null;
        if(StringUtils.hasText(name)) {
            int pageNumber = 1;
            Sort sort = Sort.by("name").ascending();
            Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);
            page = categoryService.findByNameContaining(name,pageable);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("name",name);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
        } else {
            page = categoryService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            CategoryDto dto = new CategoryDto();
            dto.setIsEdit(false);
            model.addAttribute("name",name);
            List<Category> listCategories = page.getContent();
            model.addAttribute("category", dto);
            model.addAttribute("listCategories", listCategories);
            return "admin/categories/list";
        }
        CategoryDto dto = new CategoryDto();
        dto.setIsEdit(false);
        model.addAttribute("name",name);
        List<Category> listCategories = page.getContent();
        model.addAttribute("category", dto);
        model.addAttribute("listCategories", listCategories);
        return "admin/categories/search-paginated";
    }
}
