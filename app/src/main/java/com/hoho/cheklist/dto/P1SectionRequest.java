package com.hoho.cheklist.dto;

public class P1SectionRequest {
    public long id;
    public int sectionNo;
    public String category;
    public String mainQuestion;
    public String target;
    public String description;
    public String referenceBasis;
    public int useYn;
    public int sortOrder;

    protected P1SectionRequest() {}

    public static P1SectionRequest create(
            int sectionNo, String category, String mainQuestion,
            String target, String description, String referenceBasis,
            int useYn, int sortOrder) {
        P1SectionRequest request = new P1SectionRequest();

        request.sectionNo = sectionNo;
        request.category = category;
        request.mainQuestion = mainQuestion;
        request.target = target;
        request.description = description;
        request.referenceBasis = referenceBasis;
        request.useYn = useYn;
        request.sortOrder = sortOrder;

        return request;
    }
}
