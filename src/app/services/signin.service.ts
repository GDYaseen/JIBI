import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import jwt_decode from 'jwt-decode';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SigninService {
  roleAs: any;
  isLogin = false;
  private token = '';
  private jwtToken$ = new BehaviorSubject<string>(this.token);
  private AUTH_URL = `${environment.AUTH_URL}/client/login`;

  constructor(
    private http: HttpClient,
    private router: Router,
    private toast: ToastrService
  ) {
    const fetchedToken = localStorage.getItem('act');
    if (fetchedToken) {
      this.token = fetchedToken;
      this.jwtToken$.next(this.token);
    }
  }

  get jwtUserToken(): Observable<string> {
    return this.jwtToken$.asObservable();
  }

  login(identifiant: string, password: string) {
    this.http.post(`${this.AUTH_URL}`, { login:identifiant, password }).subscribe(
      //@ts-ignore
      (res: {  'user':any, 'token': string }) => {
        localStorage.setItem('phone', res.user?.phoneNumber)
        this.token = res['token'];
        if (this.token) {
          this.toast
            .success('Login successful, Working on it...', '', {
              timeOut: 700,
              positionClass: 'toast-top-center',
            })
            .onHidden.subscribe(() => {
              this.jwtToken$.next(this.token);
              const decryptedResponse: any = jwt_decode(res['token']);
              localStorage.setItem('act', this.token);
              localStorage.setItem('ROLE', 'com.jibi.back_end.models.Client');

              localStorage.setItem('STATE', 'true');
            this.router.navigateByUrl('/client-home').then();
          });
        }
      },
      (error) => {
        this.toast.error('Authentification failed!', '', { timeOut: 2000 });
      }
    ),
      (error: any) => {
        this.toast.error('Authentification failed!', '', { timeOut: 2000 });
      };
  }

  logout() {
    this.token = '';
    this.jwtToken$.next(this.token);
    this.toast
      .success('logged out successfully', '', { timeOut: 700 })
      .onHidden.subscribe(() => {
        localStorage.removeItem('act');
        localStorage.removeItem('ROLE');
        localStorage.clear();
        localStorage.setItem('STATE', 'false');
        this.router.navigateByUrl('/').then();
      });
    return '';
  }

  getRole() {
    this.roleAs = localStorage.getItem('ROLE');
    return this.roleAs;
  }
}
