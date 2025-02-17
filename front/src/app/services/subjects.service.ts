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

  getSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.apiUrl);
  }

  subscribeToSubject(subjectId: number): Observable<string> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.post<string>(
      `${this.apiSubscribeUrl}?subjectId=${subjectId}`,
      {},
      { headers }
    );
  }

  unsubscribeFromSubject(subjectId: number): Observable<string> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.delete<string>(
      `${this.apiSubscribeUrl}?subjectId=${subjectId}`,
      { headers }
    );
  }

  getUserSubscriptions(): Observable<any[]> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<any[]>(this.apiSubscribeUrl, { headers });
  }
}
