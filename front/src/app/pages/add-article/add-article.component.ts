import { Component, OnInit } from '@angular/core';
import { ArticleService } from '../../services/articles.service';
import { SubjectService, Subject } from '../../services/subjects.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.scss'],
})
export class AddArticleComponent implements OnInit {
  article = { title: '', content: '', subjectId: 0 };
  // Array to store the list of available subjects
  subjects: Subject[] = [];

  constructor(
    private articleService: ArticleService,
    private subjectService: SubjectService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Retrieve the list of subjects from the SubjectService
    this.subjectService.getSubjects().subscribe(
      (data) => (this.subjects = data), // Store the subjects
      () => alert('Erreur lors de la récupération des sujets')
    );
  }

  // Method to create and submit a new article
  createArticle() {
    this.articleService.createArticle(this.article).subscribe(
      () => {
        // On success, show confirmation and redirect to 'feed' page with refresh parameter to display the new article
        alert('Article publié avec succès'),
          this.router.navigate(['feed'], { queryParams: { refresh: 'true' } });
      },
      () => alert('Erreur lors de la publication de l’article')
    );
  }
}
