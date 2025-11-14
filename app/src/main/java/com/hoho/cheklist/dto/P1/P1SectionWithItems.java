package com.hoho.cheklist.dto.P1;

import java.util.List;

public class P1SectionWithItems {
    private P1SectionRequest section;
    private List<P1ItemRequest> items;

    private P1SectionWithItems() {}

    public static P1SectionWithItems create(P1SectionRequest section, List<P1ItemRequest> items) {
        P1SectionWithItems request = new P1SectionWithItems();

        request.section = section;
        request.items = items;

        return request;
    }

    public P1SectionRequest getSection() {
        return section;
    }

    public List<P1ItemRequest> getItems() {
        return items;
    }
}
