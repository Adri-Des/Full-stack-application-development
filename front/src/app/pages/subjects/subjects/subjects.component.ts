import { Component, OnInit } from '@angular/core';
import { SubjectService, Subject } from '../../../services/subjects.service';

@Component({
  selector: 'app-subjects-list',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss'],
})
export class SubjectsComponent implements OnInit {
  subjects: Subject[] = []; // List of all available subjects
  subscribedSubjects: number[] = []; // List of subject IDs the user is subscribed to

  constructor(private subjectService: SubjectService) {}

  ngOnInit(): void {
    // Fetch all the existing subjects
    this.subjectService.getSubjects().subscribe((data) => {
      this.subjects = data; // Store the received subjects
    });

    // Fetch the list of subjects the user is subscribed to
    this.subjectService.getUserSubscriptions().subscribe((data) => {
      // Extract and store only the subject IDs from the subscription data
      this.subscribedSubjects = data.map((sub: any) => sub.subject.id);
    });
  }

  // Method to subscribe the user to a subject by its ID
  subscribe(subjectId: number) {
    this.subjectService.subscribeToSubject(subjectId).subscribe(
      () => {
        //alert('Abonnement réussi');
        this.subscribedSubjects.push(subjectId);
      },
      () => alert('Erreur lors de l’abonnement')
    );
  }

  // Method to check if a user is already subscribed to a subject
  isSubscribed(subjectId: number): boolean {
    return this.subscribedSubjects.includes(subjectId);
  }
}
