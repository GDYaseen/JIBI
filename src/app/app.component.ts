import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {

  constructor(private router: Router) {}
  ngOnInit(): void {
    switch (localStorage.getItem('ROLE')) {
      case 'com.jibi.back_end.models.Client':
        this.router.navigateByUrl('/client-home');
        break;
      default:
        this.router.navigateByUrl('/');
        break;
    }
  }
}
