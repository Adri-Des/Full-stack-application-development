import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Subject {
  id: number;
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  private apiUrl = 'api/subjects';
  private apiSubscibeUrl = 'api/subscriptions';

  constructor(private http: HttpClient) {}

  getSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.apiUrl);
  }

  subscribeToSubject(subjectId: number): Observable<string> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.post<string>(
      `${this.apiSubscibeUrl}?subjectId=${subjectId}`,
      {},
      { headers }
    );
  }

  unsubscribeFromSubject(subjectId: number): Observable<string> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.delete<string>(
      `${this.apiSubscibeUrl}?subjectId=${subjectId}`,
      { headers }
    );
  }
}
