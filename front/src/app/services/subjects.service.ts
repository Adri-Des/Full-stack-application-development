import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Subject {
  id: number;
  name: string;
}

/*export interface Subscription {
  id: number;
  subject: Subject;
}*/

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  private apiUrl = 'api/subjects';
  private apiSubscribeUrl = 'api/subscriptions';

  constructor(private http: HttpClient) {}

  // Retrieve all available subjects
  getSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.apiUrl);
  }

  // Subscribe the current user to a subject by its ID
  subscribeToSubject(subjectId: number): Observable<string> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.post<string>(
      `${this.apiSubscribeUrl}?subjectId=${subjectId}`,
      {},
      { headers }
    );
  }

  // Unsubscribe the current user from a subject by its ID
  unsubscribeFromSubject(subjectId: number): Observable<string> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.delete<string>(
      `${this.apiSubscribeUrl}?subjectId=${subjectId}`,
      { headers }
    );
  }

  // Get the list of subjects the user is currently subscribed to
  getUserSubscriptions(): Observable<any[]> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<any[]>(this.apiSubscribeUrl, { headers });
  }
}
