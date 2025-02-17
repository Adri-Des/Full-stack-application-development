import { Component, OnInit } from '@angular/core';
import { ArticleService, Article } from '../../services/articles.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss'],
})
export class FeedComponent implements OnInit {
  articles: Article[] = [];
  sortOrder: 'asc' | 'desc' = 'desc';

  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      if (params['refresh']) {
        this.retrieveArticles();
      }
    });

    this.retrieveArticles();
  }

  retrieveArticles() {
    this.articleService.getFeed().subscribe(
      (data) => {
        this.articles = data;
      },
      () => {
        alert('Erreur lors de la récupération du fil d’actualité');
      }
    );
  }

  changeSortOrder(order: 'asc' | 'desc') {
    this.sortOrder = order;
    this.sortArticles();
  }

  sortArticles() {
    this.articles.sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return this.sortOrder === 'desc' ? dateB - dateA : dateA - dateB;
    });
  }

  ArticleDetail(articleId: number) {
    this.router.navigate(['/article', articleId]);
  }
}
