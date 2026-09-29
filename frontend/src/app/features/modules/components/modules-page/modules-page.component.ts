import { Component, OnInit, inject } from '@angular/core';
import { NgIf, NgFor, NgClass } from '@angular/common';
import { Router } from '@angular/router';
import { StudyService } from '@core/services/study.service';
import { TranslationService } from '@core/services/translation.service';
import { ModuleResponse, StudyProgressItem, StudyStatus } from '@shared/models/study.models';
import { ProgressBarComponent } from '@shared/components/progress-bar/progress-bar.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';

@Component({
  selector: 'app-modules-page',
  standalone: true,
  imports: [NgIf, NgFor, NgClass, ProgressBarComponent, StatusBadgeComponent],
  templateUrl: './modules-page.component.html',
  styleUrl: './modules-page.component.scss'
})
export class ModulesPageComponent implements OnInit {
  private studyService = inject(StudyService);
  private router = inject(Router);
  ts = inject(TranslationService);

  modules: ModuleResponse[] = [];
  progressMap: Map<number, StudyProgressItem> = new Map();
  expandedModules: Set<number> = new Set();
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
            // Expand first module by default
            if (this.modules.length > 0) {
              this.expandedModules.add(this.modules[0].id);
            }
          },
          error: () => {
            this.loading = false;
            // Still show modules even if progress fails
          }
        });
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  toggleModule(moduleId: number): void {
    if (this.expandedModules.has(moduleId)) {
      this.expandedModules.delete(moduleId);
    } else {
      this.expandedModules.add(moduleId);
    }
  }

  isExpanded(moduleId: number): boolean {
    return this.expandedModules.has(moduleId);
  }

  getTopicStatus(topicId: number): StudyStatus {
    return this.progressMap.get(topicId)?.status || 'NOT_STARTED';
  }

  getModuleProgress(mod: ModuleResponse): number {
    if (!mod.topics.length) return 0;
    const completed = mod.topics.filter(t => this.getTopicStatus(t.id) === 'COMPLETED').length;
    return Math.round((completed / mod.topics.length) * 100);
  }

  getModuleCompletedCount(mod: ModuleResponse): number {
    return mod.topics.filter(t => this.getTopicStatus(t.id) === 'COMPLETED').length;
  }

  getModuleTitle(mod: ModuleResponse): string {
    return this.ts.lang() === 'en' ? mod.titleEn : mod.titlePt;
  }

  getTopicTitle(topic: any): string {
    return this.ts.lang() === 'en' ? topic.titleEn : topic.titlePt;
  }

  navigateToTopic(moduleId: number, topicId: number): void {
    this.router.navigate(['/modules', moduleId, 'topics', topicId]);
  }
}
