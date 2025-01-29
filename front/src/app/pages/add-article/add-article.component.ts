import { Component, OnInit } from '@angular/core';
import { ArticleService } from '../../services/articles.service';
import { SubjectService, Subject } from '../../services/subjects.service';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
})
export class AddArticleComponent implements OnInit {
  article = { title: '', content: '', subjectId: 0 };
  subjects: Subject[] = [];

  constructor(
    private articleService: ArticleService,
    private subjectService: SubjectService
  ) {}

  ngOnInit(): void {
    this.subjectService.getSubjects().subscribe(
      (data) => (this.subjects = data),
      () => alert('Erreur lors de la récupération des sujets')
    );
  }

  createArticle() {
    this.articleService.createArticle(this.article).subscribe(
      () => alert('Article publié avec succès'),
      () => alert('Erreur lors de la publication de l’article')
    );
  }
}
