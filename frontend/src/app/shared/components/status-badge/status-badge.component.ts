import { Component, Input, inject } from '@angular/core';
import { NgClass } from '@angular/common';
import { TranslationService } from '@core/services/translation.service';
import { StudyStatus } from '@shared/models/study.models';

@Component({
  selector: 'app-status-badge',
  standalone: true,
  imports: [NgClass],
  template: `
    <span class="status-badge" [ngClass]="'status-badge--' + status">
      {{ ts.t('status.' + status) }}
    </span>
  `,
  styleUrl: './status-badge.component.scss'
})
export class StatusBadgeComponent {
  @Input() status!: StudyStatus;
  ts = inject(TranslationService);
}
