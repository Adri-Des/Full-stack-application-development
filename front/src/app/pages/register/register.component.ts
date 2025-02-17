/*import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  user = { username: '', email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    if (!this.user.username || !this.user.email || !this.user.password) {
      alert('Tous les champs doivent être remplis.');
      return;
    }
    this.authService.register(this.user).subscribe(
      (response: any) => {
        alert(response.message);
        this.router.navigate(['/login']);
      },
      (error) => {
        if (error.error?.error) {
          alert(`Erreur : ${error.error.error}`);
        } else {
          alert('Erreur lors de l’inscription');
        }
      }
    );
  }
}*/

import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  registerForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  register() {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe({
        next: (response: any) => {
          alert(response.message);
          this.router.navigate(['/login']);
        },
        error: (error) => {
          if (error.error?.error) {
            alert(`Erreur : ${error.error.error}`);
          }
          this.errorMessage = "Erreur lors de l'inscription.";
        },
      });
    }
  }
}
