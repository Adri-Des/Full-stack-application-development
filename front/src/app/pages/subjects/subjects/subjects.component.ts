import { Component, OnInit } from '@angular/core';
import { SubjectService, Subject } from '../../../services/subjects.service';

@Component({
  selector: 'app-subjects-list',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss'],
})
export class SubjectsComponent implements OnInit {
  subjects: Subject[] = [];
  subscribedSubjects: number[] = [];

  constructor(private subjectService: SubjectService) {}

  ngOnInit(): void {
    this.subjectService.getSubjects().subscribe((data) => {
      this.subjects = data;
    });

    this.subjectService.getUserSubscriptions().subscribe((data) => {
      this.subscribedSubjects = data.map((sub: any) => sub.subject.id);
    });
  }

  subscribe(subjectId: number) {
    this.subjectService.subscribeToSubject(subjectId).subscribe(
      () => {
        //alert('Abonnement réussi');
        this.subscribedSubjects.push(subjectId);
      },
      () => alert('Erreur lors de l’abonnement')
    );
  }

  isSubscribed(subjectId: number): boolean {
    return this.subscribedSubjects.includes(subjectId);
  }
}
