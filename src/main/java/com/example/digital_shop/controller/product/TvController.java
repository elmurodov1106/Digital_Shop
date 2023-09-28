package com.example.digital_shop.controller.product;


import com.example.digital_shop.domain.dto.TvDto;
import com.example.digital_shop.entity.product.TvEntity;
import com.example.digital_shop.service.tv.TvService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/tv")
@RequiredArgsConstructor
public class TvController {
    private final TvService tvService;

    @GetMapping("/add")
    public String addGet(
            @RequestParam UUID userId, Model model) {
        model.addAttribute("userId", userId);
        return "TvAdd";
    }

    @PostMapping("{userId}/add")
    public String add(
            @ModelAttribute TvDto tvDto,
            @PathVariable UUID userId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model
    ) throws IOException {
        tvService.add(tvDto, userId, amount, image);
        model.addAttribute("userId", userId);
        return "SellerMenu";
    }

    @GetMapping("/get-all")
    public String getAll(
            @RequestParam int size,
            @RequestParam int page,
            Model model) {
        List<TvEntity> allTv = tvService.getAllTv(size, page);
        if (allTv.isEmpty()) {
            model.addAttribute("message", "Tv not found");
            return "index";
        }
        model.addAttribute("tv", allTv);
        return "allTv";
    }


    @GetMapping("/search")
    public String search(
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam String name,
            Model model
    ) {
        List<TvEntity> search = tvService.search(size, page, name);
        if (search.isEmpty()) {
            model.addAttribute("message", "tv not found");
            return "index";
        }
        model.addAttribute("tv", search);
        return "search";
    }

    @PutMapping("/{userId}/update")
    public String update(
            @RequestBody TvDto tvDto,
            @PathVariable UUID userId,
            @RequestParam UUID tvId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model
    ) throws IOException {
        TvEntity update = tvService.update(tvDto, userId, tvId, amount, image);
        if (update == null) {
            model.addAttribute("message", "Tv not found");
            model.addAttribute("userId", userId);
            return "SellerMenu";
        }
        model.addAttribute("userId", userId);
        model.addAttribute("message", "Tv successfully updated");
        return "SellerMenu";
    }


    @DeleteMapping("/{userId}/delete")
    public String delete(
            @PathVariable UUID userId,
            @RequestParam UUID tvId,
            Model model
    ) {
        Boolean tv = tvService.deleteById(tvId, userId);
        if (tv == null) {
            model.addAttribute("message", "Tv not found");
            model.addAttribute("userId", userId);
            return "SellerMenu";
        }
        model.addAttribute("message", "Tv successfully deleted");
        model.addAttribute("userId", userId);
        return "SellerMenu";
    }

}

