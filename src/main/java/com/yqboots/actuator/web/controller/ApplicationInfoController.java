package com.yqboots.actuator.web.controller;

import com.yqboots.actuator.core.ApplicationInfo;
import com.yqboots.actuator.core.repository.ApplicationInfoRepository;
import com.yqboots.web.support.WebKeys;
import com.yqboots.web.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/actuator/application")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class ApplicationInfoController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/actuator/application";
    private static final String VIEW_HOME = "actuator/application/index";
    private static final String VIEW_FORM = "actuator/application/form";

    @Autowired
    private ApplicationInfoRepository applicationInfoRepository;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable,
                       final ModelMap model) {
        model.addAttribute(WebKeys.PAGE, applicationInfoRepository.findAll(pageable));
        return VIEW_HOME;
    }

    @RequestMapping(params = {WebKeys.ACTION_NEW}, method = RequestMethod.GET)
    public String preAdd(final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, new ApplicationInfo());
        return VIEW_FORM;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_UPDATE}, method = RequestMethod.GET)
    public String preUpdate(@RequestParam final Long id, final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, applicationInfoRepository.findOne(id));
        return VIEW_FORM;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(WebKeys.MODEL) final ApplicationInfo domain,
                         final BindingResult bindingResult,
                         final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }

        applicationInfoRepository.save(domain);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam final Long id, final ModelMap model) {
        applicationInfoRepository.delete(id);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
