package com.web.shop.webbanhang.controller.admin;

        import com.web.shop.webbanhang.entity.Brand;
        import com.web.shop.webbanhang.entity.Color;
        import com.web.shop.webbanhang.model.BrandDto;
        import com.web.shop.webbanhang.model.ColorDto;
        import com.web.shop.webbanhang.service.ColorService;
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
        import java.util.stream.Collectors;
        import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/colors")
public class ColorController {

    @Autowired
    ColorService colorService;

    @RequestMapping(value = {"/"})
    public String add(Model model){
        ColorDto dto = new ColorDto();
        dto.setIsEdit(false);
        model.addAttribute("color", dto);
        int currentPage = 1;

        Page<Color> page = colorService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Color> listColors = page.getContent();
        model.addAttribute("listColors",listColors);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/colors/list";
    }

    @RequestMapping("/page/{pageNumber}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int currentPage) {

        ColorDto dto = new ColorDto();
        dto.setIsEdit(false);
        model.addAttribute("color", dto);

        Page<Color> page = colorService.findAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Color> listColors = page.getContent();
        model.addAttribute("listColors",listColors);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/colors/list";
    }

    @RequestMapping("/pagesearch/{pageNumber}&brandName={colorName}")
    public String viewPageSearch(Model model,
                                 @PathVariable(name = "pageNumber") int currentPage,
                                 @PathVariable(name = "colorName") String colorName
    ) {
        BrandDto dto = new BrandDto();
        dto.setIsEdit(false);
        model.addAttribute("color", dto);

        Sort sort = Sort.by("colorName").ascending();
        Pageable pageable = PageRequest.of(currentPage-1,5,sort);
        Page<Color> page = colorService.findByColorNameContaining(colorName,pageable);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("name",colorName);
        List<Color> listColors = page.getContent();
        model.addAttribute("listColors",listColors);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "admin/colors/search-paginated";
    }


    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(@Valid @ModelAttribute("color")ColorDto dto, BindingResult result
            , ModelMap model) throws IOException {
        if(result.hasErrors()) {
            Page<Color> page = colorService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Color> listColors = page.getContent();
            model.addAttribute("listColors",listColors);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            return "admin/colors/list";
        }

            Color entity = new Color();
            BeanUtils.copyProperties(dto, entity);
            colorService.save(entity);

        Page<Color> page = colorService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Color> listColors = page.getContent();
        model.addAttribute("listColors",listColors);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "forward:/admin/colors/";
    }

    @GetMapping("edit/{colorId}")
    public ModelAndView edit(ModelMap model, @PathVariable("colorId")Long colorId) {
        Optional<Color> opt = colorService.findById(colorId);
        ColorDto color = new ColorDto();
        if(opt.isPresent()) {
            Color entity = opt.get();
            BeanUtils.copyProperties(entity, color);
            color.setIsEdit(true);
            model.addAttribute("color", color);

            Page<Color> page = colorService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Color> listColors = page.getContent();
            model.addAttribute("listColors",listColors);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            return new ModelAndView("admin/colors/list",model);
        }
        Page<Color> page = colorService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Color> listColors = page.getContent();
        model.addAttribute("listColors",listColors);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        model.addAttribute("message", "Color is not existed");
        return new ModelAndView("redirect:/admin/colors/",model);
    }

    @GetMapping("delete/{colorId}")
    public String delete(@PathVariable("colorId")Long colorId, Model model){
        colorService.deleteById(colorId);

        Page<Color> page = colorService.findAll(1);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Color> listColors = page.getContent();
        model.addAttribute("listColors",listColors);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",1);
        return "redirect:/admin/colors/";
    }

    @GetMapping("/search")
    public String search(ModelMap model,@RequestParam(value = "colorName",required = false)String colorName){
        Page<Color> page = null;
        if(StringUtils.hasText(colorName)) {
            int pageNumber = 1;
            Sort sort = Sort.by("colorName").ascending();
            Pageable pageable = PageRequest.of(pageNumber - 1,5,sort);
            page = colorService.findByColorNameContaining(colorName,pageable);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("colorName",colorName);
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
        } else {
            page = colorService.findAll(1);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("totalItems",totalItems);
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            ColorDto dto = new ColorDto();
            dto.setIsEdit(false);
            model.addAttribute("name",colorName);
            List<Color> listColors = page.getContent();
            model.addAttribute("color", dto);
            model.addAttribute("listColors", listColors);
            return "admin/colors/list";
        }
        ColorDto dto = new ColorDto();
        dto.setIsEdit(false);
        model.addAttribute("name",colorName);
        List<Color> listColors = page.getContent();
        model.addAttribute("color", dto);
        model.addAttribute("listColors", listColors);
        return "admin/colors/search-paginated";
    }
}