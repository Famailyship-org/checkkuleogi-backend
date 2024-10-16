package com.Familyship.checkkuleogi.domains.child.dto;

import lombok.Getter;

@Getter
public class CreateChildRequestDTO {
    private Long childId;
    private int[] surveys;
    private float[] surveysResult;
}
