import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Article {
  id: number;
  title: string;
  content: string;
  author: { username: string };
  subject: { name: string };
  createdAt: string;
}

/*export interface Comment{
  id:number;
  content: string;
  author: { username: string };
}*/

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private apiUrl = 'api/feed';
  private addArticleUrl = 'api/article';

  constructor(private http: HttpClient) {}

  // Fetch all articles
  getFeed(): Observable<Article[]> {
    const token = localStorage.getItem('jwt'); // Get JWT token from local storage
    const headers = { Authorization: `Bearer ${token}` }; // Include token in Authorization header
    return this.http.get<Article[]>(this.apiUrl, { headers }); // Send GET request with auth headers
  }

  // Create a new article
  createArticle(article: {
    title: string;
    content: string;
    subjectId: number;
  }): Observable<any> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.post<any>(this.addArticleUrl, article, { headers }); // POST request with auth headers and article element to create article
  }

  // Fetch a specific article by its ID
  getArticleById(id: number): Observable<Article> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<Article>(`${this.addArticleUrl}/${id}`, { headers }); // GET request for article
  }

  // Add a comment to an article
  addComment(articleId: number, content: string): Observable<any> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.post<any>(
      `${this.addArticleUrl}/${articleId}/comment`, // POST to article's comment endpoint
      content,
      { headers }
    );
  }

  // Retrieve all comments for a given article
  getComments(articleId: number): Observable<any[]> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    // GET request for article's comments
    return this.http.get<any[]>(`${this.addArticleUrl}/${articleId}/comment`, {
      headers,
    });
  }
}
