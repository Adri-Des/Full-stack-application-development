import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService, Article } from '../../services/articles.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './detail-article.component.html',
  styleUrls: ['./detail-article.component.scss'],
})
export class DetailArticleComponent implements OnInit {
  article: Article | undefined; // Holds the article data once loaded
  comments: any[] = []; // Array to store comments related to the article
  newComment: string = ''; // Holds the value of the new comment to be added

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.params['id']; // Retrieves the article ID from the route parameters
    this.articleService.getArticleById(id).subscribe(
      (data) => {
        this.article = data; // Sets the article data
        this.loadComments(); // Loads comments for the article
      },
      () => alert("Erreur lors de la récupération de l'article")
    );
  }

  loadComments() {
    // Loads comments related to the current article
    if (this.article) {
      this.articleService.getComments(this.article.id).subscribe(
        (data) => (this.comments = data),
        () => alert('Erreur lors de la récupération des commentaires')
      );
    }
  }

  addComment() {
    // Adds a new comment to the article
    if (this.article) {
      this.articleService
        .addComment(this.article.id, this.newComment)
        .subscribe(
          () => {
            this.newComment = '';
            this.loadComments(); // Refreshes the comment list
          },
          () => alert('Erreur lors de l’ajout du commentaire')
        );
    }
  }
}
