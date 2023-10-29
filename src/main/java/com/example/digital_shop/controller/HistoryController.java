package com.example.digital_shop.controller;

import com.example.digital_shop.domain.dto.HistoryDto;
import com.example.digital_shop.service.history.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/getSenderCardHistories")
    public String getSenderCardHistories(
            @RequestParam int size,
            @RequestParam int page,
            @RequestParam HistoryDto historyDto
    ){
        historyService.getSenderCardHistory(size, page,historyDto.getSender());
        return "";
    }
    @GetMapping("/getAllHistories")
    public String getAllHistories(
            @RequestParam int size,
            @RequestParam int page
    ){
        historyService.getAllHistories(size, page);
        return "";
    }

}
