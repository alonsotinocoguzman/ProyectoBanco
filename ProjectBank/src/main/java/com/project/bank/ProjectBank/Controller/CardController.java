package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Model.Service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class CardController {
    private final CardService cardService;
}
