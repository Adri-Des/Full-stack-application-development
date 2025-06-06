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
    private fb: FormBuilder, // FormBuilder service to create reactive forms
    private authService: AuthService,
    private router: Router // Router service for navigation
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required], // Username is required
      email: ['', [Validators.required, Validators.email]], // Email is required and must be a valid format
      password: ['', Validators.required], // Password is required
    });
  }

  register() {
    // Proceed only if the form is valid
    if (this.registerForm.valid) {
      // Call the AuthService to register the user
      this.authService.register(this.registerForm.value).subscribe({
        next: (response: any) => {
          // On success, show a success message and navigate to login page
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
