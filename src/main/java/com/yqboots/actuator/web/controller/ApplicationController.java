/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.actuator.web.controller;

import com.yqboots.actuator.web.ApplicationManager;
import com.yqboots.actuator.web.form.ApplicationForm;
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

/**
 * Controller for {@link com.yqboots.actuator.core.Application}.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/actuator/application")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class ApplicationController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/actuator/application";
    private static final String VIEW_HOME = "actuator/application/index";
    private static final String VIEW_FORM = "actuator/application/form";
    private static final String VIEW_DETAILS = "actuator/application/details";

    @Autowired
    private ApplicationManager applicationManager;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable,
                       final ModelMap model) {
        model.addAttribute(WebKeys.PAGE, applicationManager.getApplications(searchForm.getCriterion(), pageable));
        return VIEW_HOME;
    }

    @RequestMapping(params = {WebKeys.ACTION_NEW}, method = RequestMethod.GET)
    public String preAdd(final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, new ApplicationForm());
        return VIEW_FORM;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_UPDATE}, method = RequestMethod.GET)
    public String preUpdate(@RequestParam final Long id, final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, applicationManager.getApplication(id));
        return VIEW_FORM;
    }

    @RequestMapping(params = {WebKeys.ID, "action=details"}, method = RequestMethod.GET)
    public String preDetails(@RequestParam final Long id, final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, applicationManager.getApplicationDetails(id));
        return VIEW_DETAILS;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(WebKeys.MODEL) final ApplicationForm domain,
                         final BindingResult bindingResult,
                         final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }

        applicationManager.updateApplication(domain);

        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam final Long id, final ModelMap model) {
        applicationManager.removeApplication(id);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
