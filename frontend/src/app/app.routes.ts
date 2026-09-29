import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  {
    path: 'dashboard',
    loadComponent: () => import('./features/dashboard/components/dashboard-page/dashboard-page.component').then(m => m.DashboardPageComponent)
  },
  {
    path: 'modules',
    loadComponent: () => import('./features/modules/components/modules-page/modules-page.component').then(m => m.ModulesPageComponent)
  },
  {
    path: 'modules/:moduleId/topics/:topicId',
    loadComponent: () => import('./features/modules/components/topic-detail/topic-detail.component').then(m => m.TopicDetailComponent)
  },
  {
    path: 'schedule',
    loadComponent: () => import('./features/schedule/components/schedule-page/schedule-page.component').then(m => m.SchedulePageComponent)
  },
  {
    path: 'notes',
    loadComponent: () => import('./features/notes/components/notes-page/notes-page.component').then(m => m.NotesPageComponent)
  },
  { path: '**', redirectTo: 'dashboard' }
];
