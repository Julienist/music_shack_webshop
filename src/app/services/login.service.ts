import { HttpClient } from '@angular/common/http';
import {EventEmitter, inject, Injectable} from '@angular/core';
import { Login } from '../models/login.model';
import { Token } from '../models/token.model';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment.development';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private httpClient = inject(HttpClient);
  private loggedIn: boolean = false;
  private token: string | null = null;
  private role: string = '';
  private email: string = '';
  private route = inject(Router);
  public loginEvent = new EventEmitter<void>();

  public isLoggedIn(): boolean {
    return this.loggedIn;
  }

  public getToken() {
    return this.token;
  }

  public getRole() {
    return this.role;
  }

  public getEmail() {
    return localStorage.getItem('email');
  }

  constructor() {
    this.loadTokenFromLocalStorage()
    if(this.token != null){
      this.loggedIn = true
    }
  }

  public authenticate(action: 'login' | 'register', login: Login): Observable<Token> {
    const endpointUrl = action === 'login' ? environment.apiUrl+'/auth/login' : environment.apiUrl+'/auth/register';
    return this.httpClient.post<Token>(endpointUrl, login).pipe(
      tap(response => {
        if (response.token) {
          this.loggedIn = true;
          this.token = response.token;
          this.role = response.role;
          // this.email = response.email;
          localStorage.setItem('email', login.email);
          this.saveTokenInLocalStorage(response.token);
          this.loginEvent.emit();
        }
      })
    );
  }

  private saveTokenInLocalStorage(token: string){
    localStorage.setItem('authToken', token);
  }

  private loadTokenFromLocalStorage(){
      this.token = localStorage.getItem('authToken')
  }

  public logout() {
    this.loggedIn = false;
    this.token = null;
    localStorage.removeItem('authToken');
    localStorage.removeItem('email');
    localStorage.removeItem('pendingOrder');
    this.route.navigate(['/producten/AlleProducten']);
  }

  // private saveRoleInLocalStorage(role: string){
  //   localStorage.setItem('role', role);
  //   console.log("role saved in localstorage: "+ role);
  // }

  // private loadRoleFromLocalStorage(){
  //   this.role = localStorage.getItem('role');
  // }

}
