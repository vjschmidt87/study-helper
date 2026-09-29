package com.portfolio.studyhelper.entity;

import com.portfolio.studyhelper.enums.ResourceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(length = 200)
    private String titleEn;

    @Column(length = 200)
    private String titlePt;

    @Column(length = 500)
    private String url;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    private int position;
}
