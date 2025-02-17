import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService, Article } from '../../services/articles.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './detail-article.component.html',
  styleUrls: ['./detail-article.component.scss'],
})
export class DetailArticleComponent implements OnInit {
  article: Article | undefined;
  comments: any[] = [];
  newComment: string = '';

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.articleService.getArticleById(id).subscribe(
      (data) => {
        this.article = data;
        this.loadComments();
      },
      () => alert("Erreur lors de la récupération de l'article")
    );
  }

  loadComments() {
    if (this.article) {
      this.articleService.getComments(this.article.id).subscribe(
        (
          data //{
        ) => (this.comments = data),
        //console.log('Commentaires récupérés:', this.comments);
        //},
        () => alert('Erreur lors de la récupération des commentaires')
      );
    }
  }

  addComment() {
    if (this.article) {
      this.articleService
        .addComment(this.article.id, this.newComment)
        .subscribe(
          () => {
            this.newComment = '';
            this.loadComments();
          },
          () => alert('Erreur lors de l’ajout du commentaire')
        );
    }
  }
}
