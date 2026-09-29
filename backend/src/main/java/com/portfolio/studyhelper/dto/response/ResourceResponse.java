package com.portfolio.studyhelper.dto.response;

import com.portfolio.studyhelper.entity.Resource;

public record ResourceResponse(
    Long id,
    String titleEn,
    String titlePt,
    String url,
    String type,
    int position
) {
    public static ResourceResponse from(Resource r) {
        return new ResourceResponse(r.getId(), r.getTitleEn(), r.getTitlePt(), r.getUrl(), r.getType().name(), r.getPosition());
    }
}
