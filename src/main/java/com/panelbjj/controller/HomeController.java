package com.panelbjj.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.panelbjj.dao.ClubRepository;
import com.panelbjj.dto.Competitor;
import com.panelbjj.dao.CompetitorRepository;
import com.panelbjj.dto.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping({"/"})
@EnableScheduling
public class HomeController {

    private JavaMailSenderImpl mailSender;

    private ClubRepository clubRepository;

    private CompetitorRepository competitorRepository;


    @Scheduled(fixedRate = 3*60*60*1000)
    public void deleteUnconfirmedScheduled()
    {
        competitorRepository.deleteUnconfirmed(3);
        System.out.println("Deleting unconfirmed competitors.");
    }

    @Autowired
    public void setClubRepository(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = (JavaMailSenderImpl) mailSender;
    }

    @Autowired
    public void setCompetitorRepository(CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }

    @RequestMapping(method = GET)
    public String home() {
        return "index";
    }

    @RequestMapping(method = GET, value = "register")
    public String register() {
        return "registration";
    }

    @RequestMapping(method = GET, value = "contact")
    public String kontakt() {
        return "contact";
    }

    @RequestMapping(method = GET, value = "wrongEmail")
    public String wrongEmail() {
        return "wrongEmail";
    }

    @RequestMapping(method = GET, value = "form1")
    public String form1(Model model) {
        model.addAttribute("clubs", clubRepository.downloadClubList());
        Registration registration = new Registration();
        model.addAttribute("registration", registration);
        return "form1";
    }


    @RequestMapping(method = POST, value = "form1")
    public String processRegistration(@ModelAttribute("registration") Registration registration,
                                      HttpSession session) {
        try {
            registration.validate();
        } catch (Exception exc) {
            return "form1";
        }
        session.setAttribute("registrationSession", registration);
        return "redirect:/form2";
    }

    @RequestMapping(method = GET, value = "form2")
    public String form2(Model model, HttpSession session) {

        Registration registration = (Registration) session.getAttribute("registrationSession");
        model.addAttribute(registration);
        return "form2";
    }


    @RequestMapping(method = POST, value = "form2")
    public String processRegistration2(@ModelAttribute("registration") Registration registration,
                                       HttpSession session) {

        Registration registrationSession = (Registration) session.getAttribute("registrationSession");
        registrationSession.setCompetitorList(registration.getCompetitorList());
        Long time = System.currentTimeMillis();
        for (Competitor competitor : registrationSession.getCompetitorList()){

            competitor.setClub(registrationSession.getClub());
            competitor.setEnabled(false);
            competitor.setPaid(false);
            competitor.setRegistrationNumber(time);
        }
        session.setAttribute("registrationSession", registrationSession);
        return "redirect:/confirm";
    }

    @RequestMapping(method = GET, value = "confirm")
    public String form3(Model model, HttpSession session, HttpServletRequest request) {
        Registration registration = (Registration) session.getAttribute("registrationSession");
        String url = request.getRequestURL().toString() + "/"
                + registration.getCompetitorList().get(0).getRegistrationNumber();
        try {
            sendConfirmation(registration.getEmail(), url);
        } catch (MessagingException exc) {
            return "wrongEmail";
        }
        competitorRepository.persistRegistration(registration.getCompetitorList());
        model.addAttribute(registration);
        return "confirm";
    }


    @RequestMapping(value = "confirm/{regNumber}", method = GET)
    public String finalConfirm(
            @PathVariable long regNumber, Model model) {
        if (regNumber < 1000000000) return "error";
        System.out.println("Numer rejestracji: "+regNumber);
        try {
            competitorRepository.confirmZgloszenie(regNumber);
        } catch (NullPointerException exc){return "error";}
        model.addAttribute(regNumber);
        return "finished";
    }



    public void sendConfirmation(String to, String url) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailSender.getUsername());
        helper.setTo(to);
        helper.setSubject("Nowe zgłoszenie");
        helper.setText("<html><body>Kliknij w poniższy link aby potwierdzić rezerwację na Grand Prix Polski BJJ:<br>" +
                "<a href=\"" + url +
                "\">Powierdź rezerwację</a></body></html>", true);
        mailSender.send(message);
    }

}
