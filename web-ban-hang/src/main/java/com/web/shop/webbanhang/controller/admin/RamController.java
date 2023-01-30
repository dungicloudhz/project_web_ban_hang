package com.web.shop.webbanhang.controller.admin;

import com.web.shop.webbanhang.entity.Ram;
import com.web.shop.webbanhang.model.RamDto;
import com.web.shop.webbanhang.service.RamService;
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
@RequestMapping("admin/rams")
public class RamController {

    @Autowired
    RamService ramService;

    @RequestMapping(value = {"/"})
    public String add(Model model){
        RamDto dto = new RamDto();
        dto.setIsEdit(false);
        model.addAttribute("ram", dto);

        int currentPage = 1;
        Page<Ram> page = ramService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Ram> listRams = page.getContent();
        model.addAttribute("listRams",listRams);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/rams/list";
    }

    @RequestMapping("/page/{pageNumber}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage) {

        RamDto dto = new RamDto();
        dto.setIsEdit(false);
        model.addAttribute("ram", dto);

        Page<Ram> page = ramService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Ram> listRams = page.getContent();
        model.addAttribute("listRams",listRams);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/rams/list";
    }

    // @RequestMapping("/pagesearch/{pageNumber}&name={name}")
    // public String viewPageSearch(Model model,
    //                              @PathVariable(name = "pageNumber") int currentPage,
    //                              @PathVariable(name = "name") String ramName
    // ) {
    //     RamDto dto = new RamDto();
    //     dto.setIsEdit(false);
    //     model.addAttribute("ram", dto);

    //     Sort sort = Sort.by("ramId").ascending();
    //     Pageable pageable = PageRequest.of(currentPage-1,5,sort);
    //     Page<Ram> page = ramService.findByRamNameContaining(ramName,pageable);
    //     long totalItems = page.getTotalElements();
    //     int totalPages = page.getTotalPages();

    //     model.addAttribute("name",ramName);
    //     List<Ram> listRams = page.getContent();
    //     model.addAttribute("listRams",listRams);
    //     model.addAttribute("totalItems",totalItems);
    //     model.addAttribute("totalPages",totalPages);
    //     model.addAttribute("currentPage",currentPage);
    //     return "admin/rams/search-paginated";
    // }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(@Valid @ModelAttribute("ram")RamDto dto, BindingResult result, ModelMap model){
        if(result.hasErrors()) {
            Page<Ram> list = ramService.findAll(1);
            model.addAttribute("listRams",list);
            return "admin/rams/list";
        }
        Ram entity = new Ram();
        BeanUtils.copyProperties(dto, entity);
        ramService.save(entity);
        Page<Ram> page = ramService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Ram> listRams = page.getContent();
        model.addAttribute("listRams",listRams);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "forward:/admin/rams/";
    }

    @GetMapping("edit/{ramId}")
    public ModelAndView edit(ModelMap model,@PathVariable("ramId")Long ramId) {
        Optional<Ram> opt = ramService.findById(ramId);
        RamDto ram = new RamDto();
        if(opt.isPresent()) {
            Ram entity = opt.get();
            BeanUtils.copyProperties(entity, ram);
            ram.setIsEdit(true);
            model.addAttribute("ram", ram);

            Page<Ram> page = ramService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Ram> listRams = page.getContent();
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            model.addAttribute("listRams",listRams);
            return new ModelAndView("admin/rams/list",model);
        }
        Page<Ram> page = ramService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Ram> listRams = page.getContent();
        model.addAttribute("listRams",listRams);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        model.addAttribute("message", "Ram is not existed");
        return new ModelAndView("redirect:/admin/rams/",model);
    }

    @GetMapping("delete/{ramId}")
    public String delete(@PathVariable("ramId")Long ramId, Model model){
        ramService.deleteById(ramId);
        Page<Ram> page = ramService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Ram> listRams = page.getContent();
        model.addAttribute("listRams",listRams);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "redirect:/admin/rams/";
    }

    // @GetMapping("/search")
    // public String search(ModelMap model,@RequestParam(value = "name",required = false)String ramName){
    //     Page<Ram> page = null;
    //     if(StringUtils.hasText(ramName)) {
    //         int pageNumber = 1;
    //         Sort sort = Sort.by("ramId").ascending();
    //         Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);
    //         page = ramService.findByRamNameContaining(ramName,pageable);
    //         long totalItems = page.getTotalElements();
    //         int totalPages = page.getTotalPages();
    //         model.addAttribute("name",ramName);
    //         model.addAttribute("totalItems",totalItems);
    //         model.addAttribute("totalPages",totalPages);
    //         model.addAttribute("currentPage",1);
    //     } else {
    //         page = ramService.findAll(1);
    //         long totalItems = page.getTotalElements();
    //         int totalPages = page.getTotalPages();
    //         model.addAttribute("totalItems",totalItems);
    //         model.addAttribute("totalPages",totalPages);
    //         model.addAttribute("currentPage",1);
    //         RamDto dto = new RamDto();
    //         dto.setIsEdit(false);
    //         model.addAttribute("name",ramName);
    //         List<Ram> listRams = page.getContent();
    //         model.addAttribute("ram", dto);
    //         model.addAttribute("listRams", listRams);
    //         return "admin/rams/list";
    //     }
    //     RamDto dto = new RamDto();
    //     dto.setIsEdit(false);
    //     model.addAttribute("name",ramName);
    //     List<Ram> listRams = page.getContent();
    //     model.addAttribute("ram", dto);
    //     model.addAttribute("listRams", listRams);
    //     return "admin/rams/search-paginated";
    // }
}
