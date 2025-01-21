import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  user = { email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.user).subscribe(
      (response: any) => {
        localStorage.setItem('jwt', response.token);
        //alert('Connexion réussie');
        this.router.navigate(['subjects']);
      },
      (error) => {
        alert('Identifiants incorrects');
      }
    );
  }
}
