import { Component, OnInit } from '@angular/core';
import { ArticleService, Article } from '../../services/articles.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
})
export class FeedComponent implements OnInit {
  articles: Article[] = [];

  constructor(private articleService: ArticleService) {}

  ngOnInit(): void {
    this.articleService.getFeed().subscribe(
      (data) => {
        this.articles = data;
      },
      () => {
        alert('Erreur lors de la récupération du fil d’actualité');
      }
    );
  }
}
