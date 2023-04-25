package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class SalarieController {

    @Autowired
    SalarieAideADomicileService salarieAideADomicileService;

    private final String hostLink = "http://localhost:8080";
    private final String createSalarieLink = "/salaries";
    private final String saveUpdateSalarieLink = hostLink + createSalarieLink + "/aide";
    private final String salarieListLink = createSalarieLink + "/list";
    private final String listHtml = "list";
    private final String detailSalarieHtml = "detail_Salarie";

    @GetMapping(value = "/salaries/{id}")
    public String salarieDetails(ModelMap modelMap, @PathVariable Long id) {
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        modelMap.put("salarieCount", salarieAideADomicileService.countSalaries());
        if (salarie != null) {
            modelMap.put("idLink", saveUpdateSalarieLink + "/" + id);
            modelMap.put("id", salarie.getId());
            modelMap.put("nom", salarie.getNom());
            modelMap.put("moisEnCours", salarie.getMoisEnCours());
            modelMap.put("moisDebutContrat", salarie.getMoisDebutContrat());
            modelMap.put("joursTravaillesAnneeNMoins1", salarie.getJoursTravaillesAnneeNMoins1());
            modelMap.put("congesPayesAcquisAnneeNMoins1", salarie.getCongesPayesAcquisAnneeNMoins1());
            modelMap.put("congesPayesPrisAnneeNMoins1", salarie.getCongesPayesPrisAnneeNMoins1());
            modelMap.put("joursTravaillesAnneeN", salarie.getJoursTravaillesAnneeN());
            modelMap.put("congesPayesAcquisAnneeN", salarie.getCongesPayesAcquisAnneeN());
        } else {
            return "redirect:" + createSalarieLink;
        }
        return detailSalarieHtml;
    }

    @GetMapping(value = "/salaries")
    public String createSalarie(ModelMap modelMap) {
        modelMap.put("salarieCount", salarieAideADomicileService.countSalaries());
        modelMap.put("idLink", saveUpdateSalarieLink);
        return detailSalarieHtml;
    }

    @GetMapping(value = "/salaries/list")
    public String listSalarie(ModelMap modelMap) {
        List<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries();
        modelMap.put("salaries", salaries);
        modelMap.put("salarieCount", salarieAideADomicileService.countSalaries());
        return listHtml;
    }

    @GetMapping(value = "/salaries/{id}/delete")
    public String deleteSalarie(@PathVariable Long id) throws SalarieException {
        salarieAideADomicileService.deleteSalarieAideADomicile(id);
        return "redirect:" + salarieListLink;
    }

    @PostMapping(value = {"/salaries/aide", "/salaries/aide/{id}"})
    public String saveOrCreateSalarie(@PathVariable Optional<Long> id,
                                      @RequestParam("nom") String nom,
                                      @RequestParam("moisDebutContrat") LocalDate moisDebutContrat,
                                      @RequestParam("moisEnCours") LocalDate moisEnCours,
                                      @RequestParam("joursTravaillesAnneeN") String joursTravaillesAnneeNStr,
                                      @RequestParam("congesPayesAcquisAnneeN") String congesPayesAcquisAnneeNStr,
                                      @RequestParam("joursTravaillesAnneeNMoins1") String joursTravaillesAnneeNMoins1Str,
                                      @RequestParam("congesPayesAcquisAnneeNMoins1") String congesPayesAcquisAnneeNMoins1Str,
                                      @RequestParam("congesPayesPrisAnneeNMoins1") String congesPayesPrisAnneeNMoins1Str) throws SalarieException {

        double joursTravaillesAnneeN = Double.parseDouble(joursTravaillesAnneeNStr);
        double congesPayesAcquisAnneeN = Double.parseDouble(congesPayesAcquisAnneeNStr);
        double joursTravaillesAnneeNMoins1 = Double.parseDouble(joursTravaillesAnneeNMoins1Str);
        double congesPayesAcquisAnneeNMoins1 = Double.parseDouble(congesPayesAcquisAnneeNMoins1Str);
        double congesPayesPrisAnneeNMoins1 = Double.parseDouble(congesPayesPrisAnneeNMoins1Str);

        SalarieAideADomicile salarie = new SalarieAideADomicile(nom, moisDebutContrat, moisEnCours, joursTravaillesAnneeN,
                congesPayesAcquisAnneeN, joursTravaillesAnneeNMoins1, congesPayesAcquisAnneeNMoins1, congesPayesPrisAnneeNMoins1);

        if (id.isPresent()) {
            salarie.setId(id.get());
            salarieAideADomicileService.updateSalarieAideADomicile(salarie);
        } else {
            salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        }
        return "redirect:" + salarieListLink;
    }
}
