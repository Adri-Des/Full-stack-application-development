import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'api/auth';

  constructor(private http: HttpClient) {}

  register(user: any): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/register`, user);
  }

  login(user: any): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/login`, user);
  }

  getProfile(): Observable<any> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<any>(`${this.apiUrl}/me`, { headers });
  }

  updateProfile(userData: any): Observable<any> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };

    return this.http.put(`${this.apiUrl}/update`, userData, { headers });
  }
}
