import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'api/auth';

  constructor(private http: HttpClient) {}

  // Send a registration request with the user's data
  register(user: any): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/register`, user);
  }

  // Send a login request with the user's data
  login(user: any): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/login`, user);
  }

  // Fetch the currently logged-in user's profile using the JWT token
  getProfile(): Observable<any> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<any>(`${this.apiUrl}/me`, { headers });
  }

  // Send a request to update the user's profile data
  updateProfile(userData: any): Observable<any> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };

    return this.http.put(`${this.apiUrl}/update`, userData, { headers });
  }
}
