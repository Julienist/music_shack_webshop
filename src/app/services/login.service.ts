import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Login } from '../models/login.model';
import { Token } from '../models/token.model';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private httpClient = inject(HttpClient);
  private loggedIn: boolean = false;
  private token: string | null = null;

  public isLoggedIn() {
    return this.loggedIn;
  }

  public getToken(){
    return this.token;
  }

  constructor() {
    this.loadTokenFromLocalStorage()
    if(this.token != null){
      this.loggedIn = true
    }
  }

  public login(login: Login) {
    const subscription = this.httpClient.post<Token>(
      'http://localhost:8080/api/auth/login',
      login
    ).pipe(
      tap(token => {
        if (token.token) {
          this.loggedIn = true;
          this.token = token.token;
          this.saveTokenInLocalStorage(token.token)
        }
      })
    );
    return subscription
  }

  private saveTokenInLocalStorage(token: string){
    localStorage.setItem('authToken', token);
  }

  private loadTokenFromLocalStorage(){
      this.token = localStorage.getItem('authToken')
  }
}
