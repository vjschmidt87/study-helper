package com.portfolio.studyhelper.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "modules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    @Column(length = 200)
    private String titleEn;

    @Column(length = 200)
    private String titlePt;

    @Column(columnDefinition = "TEXT")
    private String descriptionEn;

    @Column(columnDefinition = "TEXT")
    private String descriptionPt;

    @Column(columnDefinition = "TEXT")
    private String objectiveEn;

    @Column(columnDefinition = "TEXT")
    private String objectivePt;

    private int weekStart;

    private int weekEnd;

    private int position;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("position ASC")
    private List<Topic> topics;
}
