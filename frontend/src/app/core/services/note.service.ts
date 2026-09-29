import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';
import { StudyNoteItem, CreateNoteRequest, UpdateNoteRequest } from '@shared/models/study.models';

@Injectable({ providedIn: 'root' })
export class NoteService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getNotes(topicId?: number, search?: string): Observable<StudyNoteItem[]> {
    let params = new HttpParams();
    if (topicId) { params = params.set('topicId', topicId.toString()); }
    if (search) { params = params.set('search', search); }
    return this.http.get<StudyNoteItem[]>(`${this.apiUrl}/notes`, { params });
  }

  getNote(id: number): Observable<StudyNoteItem> {
    return this.http.get<StudyNoteItem>(`${this.apiUrl}/notes/${id}`);
  }

  createNote(body: CreateNoteRequest): Observable<StudyNoteItem> {
    return this.http.post<StudyNoteItem>(`${this.apiUrl}/notes`, body);
  }

  updateNote(id: number, body: UpdateNoteRequest): Observable<StudyNoteItem> {
    return this.http.put<StudyNoteItem>(`${this.apiUrl}/notes/${id}`, body);
  }

  deleteNote(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/notes/${id}`);
  }
}
