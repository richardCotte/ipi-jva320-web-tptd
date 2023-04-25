package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class HomeController {

    @Autowired
    SalarieAideADomicileService salarieAideADomicileService;

    @GetMapping(value = "/")
    public String home(ModelMap modelMap) throws SalarieException {

        SalarieAideADomicile salarie = new SalarieAideADomicile("Richard", LocalDate.now(), LocalDate.now(), 10, 0, 80, 5, 1);
        salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        SalarieAideADomicile salarie1 = new SalarieAideADomicile("wef", LocalDate.now(), LocalDate.now(), 10, 0, 80, 5, 1);
        salarieAideADomicileService.creerSalarieAideADomicile(salarie1);
        SalarieAideADomicile salarie2 = new SalarieAideADomicile("Ricwefwefhard", LocalDate.now(), LocalDate.now(), 10, 0, 80, 5, 1);
        salarieAideADomicileService.creerSalarieAideADomicile(salarie2);

        modelMap.put("salarieCount", salarieAideADomicileService.countSalaries());

        return "home";
    }
}
