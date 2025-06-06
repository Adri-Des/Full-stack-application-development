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
    // Fetch the user's profile data
    this.authService.getProfile().subscribe(
      (data) => {
        this.user = data;
      },
      (error) => {
        alert('Erreur lors de la récupération du profil');
        // Log out the user if profile retrieval fails
        this.logout();
      }
    );

    // Fetch the user's current subscriptions
    this.subjectService.getUserSubscriptions().subscribe(
      (data) => {
        this.subscriptions = data;
      },
      () => {
        alert('Erreur lors de la récupération des abonnements');
      }
    );
  }

  // Method to update the user's profile information
  updateProfile(): void {
    // Keep a copy in case the update fails
    const initialUser = { ...this.user };
    this.authService.updateProfile(this.user).subscribe(
      () => {
        //this.user = updatedUser;
        alert('Profil mis à jour avec succès !');
        // On success, force logout to refresh session/token
        this.logout();
      },
      (error) => {
        alert(error.error);
        this.user = initialUser;
      }
    );
  }

  // Method to unsubscribe from a specific subject by ID
  unsubscribe(subjectId: number) {
    this.subjectService.unsubscribeFromSubject(subjectId).subscribe(
      () => {
        //alert('Désabonnement réussi');
        // Remove the unsubscribed subject from the local list
        this.subscriptions = this.subscriptions.filter(
          (subscription) => subscription.subject.id !== subjectId
        );
      },
      () => alert('Erreur lors du désabonnement')
    );
  }

  // Method to log out the user by clearing the JWT and redirecting to login page
  logout() {
    localStorage.removeItem('jwt');
    this.router.navigate(['/login']);
  }
}
