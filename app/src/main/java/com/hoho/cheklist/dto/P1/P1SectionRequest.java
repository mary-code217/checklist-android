package com.hoho.cheklist.dto.P1;

import org.json.JSONException;
import org.json.JSONObject;

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
            long id, int sectionNo, String category, String mainQuestion,
            String target, String description, String referenceBasis,
            int useYn, int sortOrder) {
        P1SectionRequest request = new P1SectionRequest();

        request.id = id;
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

    public JSONObject toJson() throws JSONException {
        JSONObject o = new JSONObject();

        o.put("id", this.id);
        o.put("sectionNo", this.sectionNo);
        o.put("category", this.category);
        o.put("mainQuestion", this.mainQuestion);
        o.put("target", this.target);
        o.put("description", this.description);
        o.put("referenceBasis", this.referenceBasis);
        o.put("useYn", this.useYn);
        o.put("sortOrder", this.sortOrder);

        return o;
    }

    public long getId() {
        return id;
    }

    public int getSectionNo() {
        return sectionNo;
    }

    public String getCategory() {
        return category;
    }

    public String getMainQuestion() {
        return mainQuestion;
    }

    public String getTarget() {
        return target;
    }

    public String getDescription() {
        return description;
    }

    public String getReferenceBasis() {
        return referenceBasis;
    }

    public int getUseYn() {
        return useYn;
    }

    public int getSortOrder() {
        return sortOrder;
    }
}
