import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { NgIf, NgFor, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StudyService } from '@core/services/study.service';
import { TranslationService } from '@core/services/translation.service';
import { TopicDetail, StudyProgressItem, StudyStatus } from '@shared/models/study.models';
import { StatusBadgeComponent } from '@shared/components/status-badge/status-badge.component';

@Component({
  selector: 'app-topic-detail',
  standalone: true,
  imports: [NgIf, NgFor, NgClass, FormsModule, RouterLink, StatusBadgeComponent],
  templateUrl: './topic-detail.component.html',
  styleUrl: './topic-detail.component.scss'
})
export class TopicDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private studyService = inject(StudyService);
  ts = inject(TranslationService);

  topic: TopicDetail | null = null;
  progress: StudyProgressItem | null = null;
  currentStatus: StudyStatus = 'NOT_STARTED';
  notes = '';
  loading = true;
  error = false;
  saving = false;

  ngOnInit(): void {
    const topicId = Number(this.route.snapshot.paramMap.get('topicId'));
    if (topicId) {
      this.loadTopic(topicId);
    }
  }

  loadTopic(topicId: number): void {
    this.loading = true;
    this.studyService.getTopicDetail(topicId).subscribe({
      next: (topic) => {
        this.topic = topic;
        this.studyService.getUserProgress().subscribe({
          next: (progressList) => {
            this.progress = progressList.find(p => p.topicId === topicId) || null;
            if (this.progress) {
              this.currentStatus = this.progress.status;
              this.notes = this.progress.notes || '';
            }
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

  updateStatus(status: StudyStatus): void {
    if (!this.topic || this.saving) return;
    this.saving = true;
    this.studyService.updateProgress(this.topic.id, { status, notes: this.notes }).subscribe({
      next: (updated) => {
        this.progress = updated;
        this.currentStatus = updated.status;
        this.saving = false;
      },
      error: () => {
        this.saving = false;
      }
    });
  }

  saveNotes(): void {
    if (!this.topic || this.saving) return;
    this.saving = true;
    this.studyService.updateProgress(this.topic.id, { status: this.currentStatus, notes: this.notes }).subscribe({
      next: (updated) => {
        this.progress = updated;
        this.saving = false;
      },
      error: () => {
        this.saving = false;
      }
    });
  }

  getTopicTitle(): string {
    if (!this.topic) return '';
    return this.ts.lang() === 'en' ? this.topic.titleEn : this.topic.titlePt;
  }

  getModuleTitle(): string {
    if (!this.topic) return '';
    return this.ts.lang() === 'en' ? this.topic.moduleTitleEn : this.topic.moduleTitlePt;
  }

  getConcept(): string {
    if (!this.topic) return '';
    return this.ts.lang() === 'en' ? this.topic.conceptEn : this.topic.conceptPt;
  }

  getResourceTitle(res: any): string {
    return this.ts.lang() === 'en' ? res.titleEn : res.titlePt;
  }

  getExerciseDesc(ex: any): string {
    return this.ts.lang() === 'en' ? ex.descriptionEn : ex.descriptionPt;
  }

  goBack(): void {
    this.router.navigate(['/modules']);
  }

  statuses: StudyStatus[] = ['NOT_STARTED', 'IN_PROGRESS', 'COMPLETED'];
}
