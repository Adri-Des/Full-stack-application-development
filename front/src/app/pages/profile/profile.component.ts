import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { SubjectService } from 'src/app/services/subjects.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  user: any = {};
  subscriptions: any[] = [];

  constructor(
    private authService: AuthService,
    private router: Router,
    private subjectService: SubjectService
  ) {}

  ngOnInit(): void {
    this.authService.getProfile().subscribe(
      (data) => {
        this.user = data;
      },
      (error) => {
        alert('Erreur lors de la récupération du profil');
        this.logout();
      }
    );

    this.subjectService.getUserSubscriptions().subscribe(
      (data) => {
        this.subscriptions = data;
      },
      () => {
        alert('Erreur lors de la récupération des abonnements');
      }
    );
  }

  updateProfile(): void {
    const initialUser = { ...this.user };
    this.authService.updateProfile(this.user).subscribe(
      () => {
        //this.user = updatedUser;
        alert('Profil mis à jour avec succès !');
        this.logout();
      },
      (error) => {
        alert(error.error);
        this.user = initialUser;
      }
    );
  }

  unsubscribe(subjectId: number) {
    this.subjectService.unsubscribeFromSubject(subjectId).subscribe(
      () => {
        //alert('Désabonnement réussi');
        this.subscriptions = this.subscriptions.filter(
          (subscription) => subscription.subject.id !== subjectId
        );
      },
      () => alert('Erreur lors du désabonnement')
    );
  }

  logout() {
    localStorage.removeItem('jwt');
    this.router.navigate(['/login']);
  }
}
