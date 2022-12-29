package ru.instamart.kraken.service.testit.model.autotest;

import ru.instamart.kraken.service.testit.Utils;
import ru.testit.client.model.AutoTestModel;
import ru.testit.client.model.AutoTestPutModel;

public class AutotestPut extends AutoTestPutModel {

    public AutotestPut(final AutoTestModel autoTestModel) {
        this.setId(autoTestModel.getId());
        this.setProjectId(autoTestModel.getProjectId());
        this.setExternalId(autoTestModel.getExternalId());
        this.setDescription(autoTestModel.getDescription());
        this.setName(autoTestModel.getName());
        this.setClassname(autoTestModel.getClassname());
        this.setNamespace(autoTestModel.getNamespace());
        this.setTitle(autoTestModel.getTitle());
        this.setLinks(autoTestModel.getLinks());
        this.setSteps(autoTestModel.getSteps());
        this.setLabels(Utils.labelsConvert(autoTestModel.getLabels()));
        this.setSetup(autoTestModel.getSetup());
        this.setTeardown(autoTestModel.getTeardown());
    }
}
