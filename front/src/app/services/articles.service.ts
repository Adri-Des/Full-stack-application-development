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

  getFeed(): Observable<Article[]> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<Article[]>(this.apiUrl, { headers });
  }

  createArticle(article: {
    title: string;
    content: string;
    subjectId: number;
  }): Observable<any> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.post<any>(this.addArticleUrl, article, { headers });
  }

  getArticleById(id: number): Observable<Article> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<Article>(`${this.addArticleUrl}/${id}`, { headers });
  }

  addComment(articleId: number, content: string): Observable<any> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.post<any>(
      `${this.addArticleUrl}/${articleId}/comment`,
      content,
      { headers }
    );
  }

  getComments(articleId: number): Observable<any[]> {
    const token = localStorage.getItem('jwt');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<any[]>(`${this.addArticleUrl}/${articleId}/comment`, {
      headers,
    });
  }
}
