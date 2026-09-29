import { Component, OnInit, inject } from '@angular/core';
import { NgIf, NgFor, NgClass } from '@angular/common';
import { StudyService } from '@core/services/study.service';
import { TranslationService } from '@core/services/translation.service';
import { ModuleResponse, StudyProgressItem, StudyStatus } from '@shared/models/study.models';
import { ProgressBarComponent } from '@shared/components/progress-bar/progress-bar.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';

@Component({
  selector: 'app-schedule-page',
  standalone: true,
  imports: [NgIf, NgFor, NgClass, ProgressBarComponent, StatusBadgeComponent],
  templateUrl: './schedule-page.component.html',
  styleUrl: './schedule-page.component.scss'
})
export class SchedulePageComponent implements OnInit {
  private studyService = inject(StudyService);
  ts = inject(TranslationService);

  modules: ModuleResponse[] = [];
  progressMap: Map<number, StudyProgressItem> = new Map();
  loading = true;
  error = false;

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    this.error = false;
    this.studyService.getModules().subscribe({
      next: (modules) => {
        this.modules = modules;
        this.studyService.getUserProgress().subscribe({
          next: (progress) => {
            progress.forEach(p => this.progressMap.set(p.topicId, p));
            this.loading = false;
          },
          error: () => {
            this.loading = false;
          }
        });
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  getModuleTitle(mod: ModuleResponse): string {
    return this.ts.lang() === 'en' ? mod.titleEn : mod.titlePt;
  }

  getTopicTitle(topic: any): string {
    return this.ts.lang() === 'en' ? topic.titleEn : topic.titlePt;
  }

  getTopicStatus(topicId: number): StudyStatus {
    return this.progressMap.get(topicId)?.status || 'NOT_STARTED';
  }

  getModuleProgress(mod: ModuleResponse): number {
    if (!mod.topics.length) return 0;
    const completed = mod.topics.filter(t => this.getTopicStatus(t.id) === 'COMPLETED').length;
    return Math.round((completed / mod.topics.length) * 100);
  }

  getModuleStatus(mod: ModuleResponse): string {
    const progress = this.getModuleProgress(mod);
    if (progress === 100) return 'completed';
    if (progress > 0) return 'current';
    const idx = this.modules.indexOf(mod);
    if (idx > 0) {
      const prevComplete = this.getModuleProgress(this.modules[idx - 1]) === 100;
      if (!prevComplete) return 'upcoming';
    }
    if (idx === 0 && progress === 0) return 'current';
    return 'upcoming';
  }
}
