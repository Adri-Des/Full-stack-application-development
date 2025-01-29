import { Component, OnInit } from '@angular/core';
import { SubjectService, Subject } from '../../../services/subjects.service';

@Component({
  selector: 'app-subjects-list',
  templateUrl: './subjects.component.html',
  //styleUrls: ['./subjects.component.css'],
})
export class SubjectsComponent implements OnInit {
  subjects: Subject[] = [];

  constructor(private subjectService: SubjectService) {}

  ngOnInit(): void {
    this.subjectService.getSubjects().subscribe((data) => {
      this.subjects = data;
    });
  }

  subscribe(subjectId: number) {
    this.subjectService.subscribeToSubject(subjectId).subscribe(
      () => alert('Abonnement réussi'),
      () => alert('Erreur lors de l’abonnement')
    );
  }

  unsubscribe(subjectId: number) {
    this.subjectService.unsubscribeFromSubject(subjectId).subscribe(
      () => alert('Désabonnement réussi'),
      () => alert('Erreur lors du désabonnement')
    );
  }
}
