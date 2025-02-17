import {
  Component,
  HostListener,
  ViewChild,
  AfterViewInit,
} from '@angular/core';
import { Router } from '@angular/router';
import { MatSidenav } from '@angular/material/sidenav';
//import { MatDrawer } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements AfterViewInit {
  showHeader = true;
  @ViewChild('sidenav') sidenav!: MatSidenav;
  //@ViewChild('sidenav') sidenav!: MatDrawer;
  isMobile = false;

  constructor(private router: Router) {
    this.checkScreenSize();
    this.router.events.subscribe(() => {
      // Cacher l'entête si l'utilisateur est sur /login ou /register
      this.showHeader = !['/login', '/register'].includes(this.router.url);
    });
  }

  ngAfterViewInit() {
    console.log('ngAfterViewInit : sidenav =', this.sidenav);
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.checkScreenSize();
  }

  checkScreenSize() {
    this.isMobile = window.innerWidth <= 768;
  }

  toggleSidenav() {
    if (this.sidenav) {
      this.sidenav.toggle();
    }
  }

  /* toggleSidenav() {
    console.log('toggleSidenav() appelé, état actuel :', this.sidenav.opened);
    if (this.sidenav) {
      this.sidenav.opened ? this.sidenav.close() : this.sidenav.open();
      console.log('Nouveau statut après toggle :', this.sidenav.opened);
    } else {
      console.error("Erreur : `sidenav` n'est pas défini !");
    }
  }*/

  closeSidenav() {
    if (this.sidenav) {
      this.sidenav.close();
    }
  }
}
