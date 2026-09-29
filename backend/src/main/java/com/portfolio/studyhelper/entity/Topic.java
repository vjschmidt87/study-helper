package com.portfolio.studyhelper.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @Column(length = 10)
    private String number;

    @Column(length = 200)
    private String titleEn;

    @Column(length = 200)
    private String titlePt;

    @Column(columnDefinition = "TEXT")
    private String conceptEn;

    @Column(columnDefinition = "TEXT")
    private String conceptPt;

    private int position;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    @OrderBy("position ASC")
    private List<Resource> resources;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    @OrderBy("position ASC")
    private List<Exercise> exercises;
}
