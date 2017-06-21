package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job jobs = jobData.findById(id);
        model.addAttribute("jobs", jobs);


        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        //TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        //Step1= Add the jobForm
        model.addAttribute(jobForm);
        //Step2 = Get Individual items from form and add to the newJob
        if (errors.hasErrors()) {

            return "new-job";
        }


       int id1= jobForm.getEmployerId();
       int id2= jobForm.getLocationId();
       int id3= jobForm.getCoreCompetencyId();
       int id4= jobForm.getPositionTypeId();

       String aName= jobForm.getName();
       Employer anEmployer = jobData.getEmployers().findById(id1);
       Location alocation = jobData.getLocations().findById(id2);
       CoreCompetency aSkill= jobData.getCoreCompetencies().findById(id3);
       PositionType positionTypes= jobData.getPositionTypes().findById(id4);


       Job newJob= new Job();
        newJob.setName(aName);
        newJob.setEmployer(anEmployer);
        newJob.setLocation(alocation);
        newJob.setCoreCompetency(aSkill);
        newJob.setPositionType(positionTypes);



       jobData.add(newJob);
       model.addAttribute("jobs",newJob );

        return "job-detail";
    }
}
