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

  constructor(private http: HttpClient) {}

  getSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(this.apiUrl);
  }
}
