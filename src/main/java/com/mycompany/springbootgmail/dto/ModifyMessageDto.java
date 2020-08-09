package com.mycompany.springbootgmail.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModifyMessageDto {

    public List<String> getLabelsToRemove() {
		return labelsToRemove;
	}
	public void setLabelsToRemove(List<String> labelsToRemove) {
		this.labelsToRemove = labelsToRemove;
	}
	public List<String> getLabelsToAdd() {
		return labelsToAdd;
	}
	public void setLabelsToAdd(List<String> labelsToAdd) {
		this.labelsToAdd = labelsToAdd;
	}
	private List<String> labelsToRemove;
    private List<String> labelsToAdd;

}
