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
      // Check if a 'refresh' query parameter is present
      if (params['refresh']) {
        this.retrieveArticles(); // Refresh the articles if requested
      }
    });

    this.retrieveArticles();
  }

  retrieveArticles() {
    // Fetches the list of articles from the service
    this.articleService.getFeed().subscribe(
      (data) => {
        this.articles = data; // Store the received articles
      },
      () => {
        alert('Erreur lors de la récupération du fil d’actualité');
      }
    );
  }

  // Updates the sort order and applies sorting
  changeSortOrder(order: 'asc' | 'desc') {
    this.sortOrder = order;
    this.sortArticles();
  }

  sortArticles() {
    // Sorts articles by their creation date according to the selected order
    this.articles.sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return this.sortOrder === 'desc' ? dateB - dateA : dateA - dateB;
    });
  }

  ArticleDetail(articleId: number) {
    // Navigates to the detail page of a specific article
    this.router.navigate(['/article', articleId]);
  }
}
