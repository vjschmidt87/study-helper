import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';
import {
  ModuleResponse, TopicDetail, DashboardData,
  StudyProgressItem, UpdateProgressRequest
} from '@shared/models/study.models';

@Injectable({ providedIn: 'root' })
export class StudyService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getModules(): Observable<ModuleResponse[]> {
    return this.http.get<ModuleResponse[]>(`${this.apiUrl}/modules`);
  }

  getModule(id: number): Observable<ModuleResponse> {
    return this.http.get<ModuleResponse>(`${this.apiUrl}/modules/${id}`);
  }

  getTopicDetail(id: number): Observable<TopicDetail> {
    return this.http.get<TopicDetail>(`${this.apiUrl}/topics/${id}`);
  }

  getDashboard(): Observable<DashboardData> {
    return this.http.get<DashboardData>(`${this.apiUrl}/progress/dashboard`);
  }

  getUserProgress(): Observable<StudyProgressItem[]> {
    return this.http.get<StudyProgressItem[]>(`${this.apiUrl}/progress`);
  }

  updateProgress(topicId: number, body: UpdateProgressRequest): Observable<StudyProgressItem> {
    return this.http.put<StudyProgressItem>(`${this.apiUrl}/progress/topics/${topicId}`, body);
  }
}
