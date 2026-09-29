import { Component, OnInit, inject } from '@angular/core';
import { NgIf, NgFor, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NoteService } from '@core/services/note.service';
import { TranslationService } from '@core/services/translation.service';
import { StudyNoteItem, CreateNoteRequest, UpdateNoteRequest } from '@shared/models/study.models';

@Component({
  selector: 'app-notes-page',
  standalone: true,
  imports: [NgIf, NgFor, FormsModule, DatePipe],
  templateUrl: './notes-page.component.html',
  styleUrl: './notes-page.component.scss'
})
export class NotesPageComponent implements OnInit {
  private noteService = inject(NoteService);
  ts = inject(TranslationService);

  notes: StudyNoteItem[] = [];
  loading = true;
  error = false;
  searchQuery = '';

  // Form state
  showForm = false;
  editingNote: StudyNoteItem | null = null;
  formTitle = '';
  formContent = '';
  formTopicId: number | null = null;
  saving = false;

  // Delete confirmation
  noteToDelete: StudyNoteItem | null = null;

  ngOnInit(): void {
    this.loadNotes();
  }

  loadNotes(): void {
    this.loading = true;
    this.error = false;
    const search = this.searchQuery.trim() || undefined;
    this.noteService.getNotes(undefined, search).subscribe({
      next: (notes) => {
        this.notes = notes;
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  onSearch(): void {
    this.loadNotes();
  }

  openNewNote(): void {
    this.editingNote = null;
    this.formTitle = '';
    this.formContent = '';
    this.formTopicId = null;
    this.showForm = true;
  }

  openEditNote(note: StudyNoteItem): void {
    this.editingNote = note;
    this.formTitle = note.title;
    this.formContent = note.content;
    this.formTopicId = note.topicId;
    this.showForm = true;
  }

  closeForm(): void {
    this.showForm = false;
    this.editingNote = null;
  }

  saveNote(): void {
    if (!this.formTitle.trim() || !this.formContent.trim()) return;
    this.saving = true;

    if (this.editingNote) {
      const body: UpdateNoteRequest = {
        title: this.formTitle,
        content: this.formContent
      };
      this.noteService.updateNote(this.editingNote.id, body).subscribe({
        next: () => {
          this.saving = false;
          this.closeForm();
          this.loadNotes();
        },
        error: () => {
          this.saving = false;
        }
      });
    } else {
      const body: CreateNoteRequest = {
        title: this.formTitle,
        content: this.formContent
      };
      if (this.formTopicId) {
        body.topicId = this.formTopicId;
      }
      this.noteService.createNote(body).subscribe({
        next: () => {
          this.saving = false;
          this.closeForm();
          this.loadNotes();
        },
        error: () => {
          this.saving = false;
        }
      });
    }
  }

  confirmDelete(note: StudyNoteItem): void {
    this.noteToDelete = note;
  }

  cancelDelete(): void {
    this.noteToDelete = null;
  }

  deleteNote(): void {
    if (!this.noteToDelete) return;
    this.noteService.deleteNote(this.noteToDelete.id).subscribe({
      next: () => {
        this.noteToDelete = null;
        this.loadNotes();
      },
      error: () => {
        this.noteToDelete = null;
      }
    });
  }

  getNoteTopicTitle(note: StudyNoteItem): string {
    if (!note.topicTitleEn) return this.ts.t('notes.generalNote');
    return this.ts.lang() === 'en' ? (note.topicTitleEn || '') : (note.topicTitlePt || '');
  }

  getContentPreview(content: string): string {
    return content.length > 150 ? content.substring(0, 150) + '...' : content;
  }
}
