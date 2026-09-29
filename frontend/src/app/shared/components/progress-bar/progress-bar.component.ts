import { Component, Input } from '@angular/core';
import { NgIf, DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-progress-bar',
  standalone: true,
  imports: [NgIf, DecimalPipe],
  template: `
    <div class="progress-bar" [style.height]="height">
      <div class="progress-bar__fill" [style.width.%]="percentage"></div>
    </div>
    <span class="progress-bar__label" *ngIf="showLabel">{{ percentage | number:'1.0-0' }}%</span>
  `,
  styleUrl: './progress-bar.component.scss'
})
export class ProgressBarComponent {
  @Input() percentage: number = 0;
  @Input() height: string = '8px';
  @Input() showLabel: boolean = false;
}
