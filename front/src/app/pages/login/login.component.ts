import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    // Initialize the login form with validators
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  login() {
    if (this.loginForm.valid) {
      // Proceed only if the form is valid
      this.authService.login(this.loginForm.value).subscribe({
        next: (response: any) => {
          // Save the JWT token to local storage
          localStorage.setItem('jwt', response.token);
          this.router.navigate(['subjects']);
        },
        error: (err) => {
          this.errorMessage = 'Identifiants incorrects.';
        },
      });
    }
  }
}
