package com.web.shop.webbanhang.controller.admin;

import com.web.shop.webbanhang.entity.MemoryStorage;
import com.web.shop.webbanhang.model.MemoryStorageDto;
import com.web.shop.webbanhang.model.MemoryStorageDto;
import com.web.shop.webbanhang.service.MemoryStorageService;
import com.web.shop.webbanhang.service.MemoryStorageService;

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
@RequestMapping("admin/memoryStorages")
public class MemoryStorageController {

    @Autowired
    MemoryStorageService memoryStorageService;

    @RequestMapping(value = {"/"})
    public String add(Model model){
        MemoryStorageDto dto = new MemoryStorageDto();
        dto.setIsEdit(false);
        model.addAttribute("memoryStorage", dto);

        int currentPage = 1;
        Page<MemoryStorage> page = memoryStorageService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<MemoryStorage> listMemoryStorages = page.getContent();
        model.addAttribute("listMemoryStorages",listMemoryStorages);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/memoryStorages/list";
    }

    @RequestMapping("/page/{pageNumber}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage) {

        MemoryStorageDto dto = new MemoryStorageDto();
        dto.setIsEdit(false);
        model.addAttribute("memoryStorage", dto);

        Page<MemoryStorage> page = memoryStorageService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<MemoryStorage> listMemoryStorages = page.getContent();
        model.addAttribute("listMemoryStorages",listMemoryStorages);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/memoryStorages/list";
    }

    @RequestMapping("/pagesearch/{pageNumber}&name={name}")
    public String viewPageSearch(Model model,
                                 @PathVariable(name = "pageNumber") int currentPage,
                                 @PathVariable(name = "name") String memoryStorageName
    ) {
        MemoryStorageDto dto = new MemoryStorageDto();
        dto.setIsEdit(false);
        model.addAttribute("memoryStorage", dto);

        Sort sort = Sort.by("memoryStorageId").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,5,sort);
        Page<MemoryStorage> page = memoryStorageService.findByMemoryStorageNameContaining(memoryStorageName,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("name",memoryStorageName);
        List<MemoryStorage> listMemoryStorages = page.getContent();
        model.addAttribute("listMemoryStorages",listMemoryStorages);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/memoryStorages/search-paginated";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(@Valid @ModelAttribute("memoryStorage")MemoryStorageDto dto, BindingResult result, ModelMap model){
        if(result.hasErrors()) {
            Page<MemoryStorage> list = memoryStorageService.findAll(1);
            model.addAttribute("listMemoryStorages",list);
            return "admin/memoryStorages/list";
        }
        MemoryStorage entity = new MemoryStorage();
        BeanUtils.copyProperties(dto, entity);
        memoryStorageService.save(entity);
        Page<MemoryStorage> page = memoryStorageService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<MemoryStorage> listMemoryStorages = page.getContent();
        model.addAttribute("listMemoryStorages",listMemoryStorages);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "forward:/admin/memoryStorages/";
    }

    @GetMapping("edit/{memoryStorageId}")
    public ModelAndView edit(ModelMap model,@PathVariable("memoryStorageId")Long memoryStorageId) {
        Optional<MemoryStorage> opt = memoryStorageService.findById(memoryStorageId);
        MemoryStorageDto memoryStorage = new MemoryStorageDto();
        if(opt.isPresent()) {
            MemoryStorage entity = opt.get();
            BeanUtils.copyProperties(entity, memoryStorage);
            memoryStorage.setIsEdit(true);
            model.addAttribute("memoryStorage", memoryStorage);

            Page<MemoryStorage> page = memoryStorageService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<MemoryStorage> listMemoryStorages = page.getContent();
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            model.addAttribute("listMemoryStorages",listMemoryStorages);
            return new ModelAndView("admin/memoryStorages/list",model);
        }
        Page<MemoryStorage> page = memoryStorageService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<MemoryStorage> listMemoryStorages = page.getContent();
        model.addAttribute("listMemoryStorages",listMemoryStorages);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        model.addAttribute("message", "MemoryStorage is not existed");
        return new ModelAndView("redirect:/admin/memoryStorages/",model);
    }

    @GetMapping("delete/{memoryStorageId}")
    public String delete(@PathVariable("memoryStorageId")Long memoryStorageId, Model model){
        memoryStorageService.deleteById(memoryStorageId);
        Page<MemoryStorage> page = memoryStorageService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<MemoryStorage> listMemoryStorages = page.getContent();
        model.addAttribute("listMemoryStorages",listMemoryStorages);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "redirect:/admin/memoryStorages/";
    }

    @GetMapping("/search")
    public String search(ModelMap model,@RequestParam(value = "name",required = false)String memoryStorageName){
        Page<MemoryStorage> page = null;
        if(StringUtils.hasText(memoryStorageName)) {
            int pageNumber = 1;
            Sort sort = Sort.by("memoryStorageId").ascending();
            Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);
            page = memoryStorageService.findByMemoryStorageNameContaining(memoryStorageName,pageable);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("name",memoryStorageName);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
        } else {
            page = memoryStorageService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            MemoryStorageDto dto = new MemoryStorageDto();
            dto.setIsEdit(false);
            model.addAttribute("name",memoryStorageName);
            List<MemoryStorage> listMemoryStorages = page.getContent();
            model.addAttribute("memoryStorage", dto);
            model.addAttribute("listMemoryStorages", listMemoryStorages);
            return "admin/memoryStorages/list";
        }
        MemoryStorageDto dto = new MemoryStorageDto();
        dto.setIsEdit(false);
        model.addAttribute("name",memoryStorageName);
        List<MemoryStorage> listMemoryStorages = page.getContent();
        model.addAttribute("memoryStorage", dto);
        model.addAttribute("listMemoryStorages", listMemoryStorages);
        return "admin/memoryStorages/search-paginated";
    }
}
