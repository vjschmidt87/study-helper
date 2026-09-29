export type StudyStatus = 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED';

export type ResourceType = 'DOCUMENTATION' | 'TUTORIAL' | 'ARTICLE' | 'BOOK' | 'COURSE' | 'COMMUNITY';

export interface ModuleResponse {
  id: number;
  number: number;
  titleEn: string;
  titlePt: string;
  descriptionEn: string;
  descriptionPt: string;
  objectiveEn: string;
  objectivePt: string;
  weekStart: number;
  weekEnd: number;
  topics: TopicSummary[];
}

export interface TopicSummary {
  id: number;
  number: string;
  titleEn: string;
  titlePt: string;
  position: number;
}

export interface TopicDetail {
  id: number;
  number: string;
  titleEn: string;
  titlePt: string;
  conceptEn: string;
  conceptPt: string;
  moduleId: number;
  moduleNumber: number;
  moduleTitleEn: string;
  moduleTitlePt: string;
  resources: ResourceItem[];
  exercises: ExerciseItem[];
}

export interface ResourceItem {
  id: number;
  titleEn: string;
  titlePt: string;
  url: string | null;
  type: ResourceType;
  position: number;
}

export interface ExerciseItem {
  id: number;
  descriptionEn: string;
  descriptionPt: string;
  position: number;
}

export interface StudyProgressItem {
  id: number;
  topicId: number;
  topicNumber: string;
  topicTitleEn: string;
  topicTitlePt: string;
  moduleId: number;
  moduleNumber: number;
  status: StudyStatus;
  notes: string | null;
  startedAt: string | null;
  completedAt: string | null;
  updatedAt: string;
}

export interface StudyNoteItem {
  id: number;
  topicId: number | null;
  topicTitleEn: string | null;
  topicTitlePt: string | null;
  title: string;
  content: string;
  createdAt: string;
  updatedAt: string;
}

export interface DashboardData {
  totalTopics: number;
  completedTopics: number;
  inProgressTopics: number;
  completionPercentage: number;
  moduleProgress: ModuleProgress[];
  recentActivity: StudyProgressItem[];
}

export interface ModuleProgress {
  moduleId: number;
  moduleNumber: number;
  titleEn: string;
  titlePt: string;
  totalTopics: number;
  completedTopics: number;
  completionPercentage: number;
}

export interface UpdateProgressRequest {
  status: StudyStatus;
  notes?: string;
}

export interface CreateNoteRequest {
  title: string;
  content: string;
  topicId?: number;
}

export interface UpdateNoteRequest {
  title: string;
  content: string;
}
