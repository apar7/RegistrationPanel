package com.panelbjj.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.panelbjj.dao.ClubRepository;
import com.panelbjj.dao.CompetitorRepository;
import com.panelbjj.dto.Category;
import com.panelbjj.dto.Club;
import com.panelbjj.dto.Competitor;
import com.panelbjj.dto.SpreadsheetGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping({"/admin"})
public class AdminController {

    private ClubRepository clubRepository;

    private CompetitorRepository competitorRepository;

    @Autowired
    public void setClubRepository(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Autowired
    public void setCompetitorRepository(CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }


    @RequestMapping(method = GET)
    public String adminStart(Model model) {
        Category category = new Category();
        model.addAttribute(category);
        return "adminBrackets";
    }

    @RequestMapping(method = POST)
    public String adminCategory(@ModelAttribute("category") Category category,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        List<Competitor> competitorList = competitorRepository.findCategory(category);
        redirectAttributes.addFlashAttribute(competitorList);
        redirectAttributes.addFlashAttribute(category);
        session.setAttribute("category", category);
        return "redirect:/admin/category";
    }

    @RequestMapping(method = GET, value = "/category")
    public String adminCategory() {
        return "adminCategory";
    }

    @RequestMapping(method = POST, value = "/category")
    public ModelAndView generateBrackets(ModelAndView mav,
                                         @ModelAttribute Category category) {
        List<Competitor> competitorList = competitorRepository.findCategoryToGenerate(category);
        if (competitorList.size() > 1)
            mav.setView(new SpreadsheetGenerator(competitorList, category));
        return mav;
    }

    @RequestMapping(method = POST, value = "/delete{competitorID}")
    public String delete(@PathVariable int competitorID,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        competitorRepository.deleteCompetitor(competitorID);
        Category category = (Category) session.getAttribute("category");
        List<Competitor> competitorList = competitorRepository.findCategory(category);
        redirectAttributes.addFlashAttribute(competitorList);
        redirectAttributes.addFlashAttribute(category);
        return "redirect:/admin/category";
    }




    @RequestMapping(method = POST, value = "/deletefrom{competitorID}")
    public String deleteFromCompList(@PathVariable int competitorID) {
        competitorRepository.deleteCompetitor(competitorID);
        return "redirect:/admin/zawodnicy/1";
    }

    @RequestMapping(method = GET, value = "/competitors/{pageNumber}")
    public String adminZawodnicy(@PathVariable int pageNumber,
                                 Model model) {
        List<Competitor> competitorList = competitorRepository.findAll(pageNumber);
        model.addAttribute(pageNumber);
        model.addAttribute(competitorList);
        return "adminCompetitors";
    }

    @RequestMapping(method = GET, value = "/findcompetitor")
    public String wyszukajZawodnika(@RequestParam String imie,
                                    Model model) {
        List<Competitor> competitorList = competitorRepository.findByName(imie);
        model.addAttribute("pageNumber", 1);
        model.addAttribute(competitorList);
        return "adminCompetitors";
    }

    @RequestMapping(method = GET, value = "/competitor{competitorId}")
    public String findCompetitor(@PathVariable int competitorId,
                                 Model model) {
        Competitor competitor = competitorRepository.findCompetitorById(competitorId);
        model.addAttribute(competitor);
        return "adminCompetitor";
    }

    @RequestMapping(method = POST, value = "/competitor{competitorId}")
    public String editCompetitor(@PathVariable int competitorId,
                                 @ModelAttribute Competitor competitor) {
        competitorRepository.updateCompetitor(competitor);
        return "redirect:/admin/competitor{competitorId}";
    }


    @RequestMapping(method = GET, value = "/findregistration")
    public String wyszukajZgloszenie(@RequestParam Long registrationNumber,
                                     Model model) {
        List<Competitor> competitorList = competitorRepository.findByRegNumber(registrationNumber);
        model.addAttribute(competitorList);
        model.addAttribute("pageNumber", 1);
        return "adminCompetitors";
    }


    @RequestMapping(method = GET, value = "/clubs")
    public String clubs(Model model) {
        List<Club> clubList = clubRepository.downloadClubstoEdit();
        model.addAttribute(clubList);
        Club newClub = new Club();
        model.addAttribute(newClub);
        return "adminClubs";
    }

    @RequestMapping(method = POST, value = "/clubs")
    public String clubsPost(@ModelAttribute Club klub) {
        try {
            klub.getId();
            clubRepository.updateKlub(klub);
        } catch (NullPointerException npe) {
            clubRepository.addKlub(klub);
        }
        return "redirect:/admin/clubs";
    }

    @RequestMapping(method = POST, value = "/deleteclub")
    public String clubsDelete(@ModelAttribute Club klub) {
        clubRepository.usunKlub(klub.getId());
        return "redirect:/admin/clubs";
    }


}
