import { Component, OnInit, inject } from '@angular/core';
import { NgIf, NgFor, DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { StudyService } from '@core/services/study.service';
import { TranslationService } from '@core/services/translation.service';
import { DashboardData } from '@shared/models/study.models';
import { ProgressBarComponent } from '@shared/components/progress-bar/progress-bar.component';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [NgIf, NgFor, DatePipe, ProgressBarComponent, StatusBadgeComponent],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.scss'
})
export class DashboardPageComponent implements OnInit {
  private studyService = inject(StudyService);
  private router = inject(Router);
  ts = inject(TranslationService);

  dashboard: DashboardData | null = null;
  loading = true;
  error = false;

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;
    this.error = false;
    this.studyService.getDashboard().subscribe({
      next: (data) => {
        this.dashboard = data;
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  get notStartedCount(): number {
    if (!this.dashboard) return 0;
    return this.dashboard.totalTopics - this.dashboard.completedTopics - this.dashboard.inProgressTopics;
  }

  navigateToModules(): void {
    this.router.navigate(['/modules']);
  }

  getModuleTitle(mod: any): string {
    return this.ts.lang() === 'en' ? mod.titleEn : mod.titlePt;
  }

  getTopicTitle(item: any): string {
    return this.ts.lang() === 'en' ? item.topicTitleEn : item.topicTitlePt;
  }
}
