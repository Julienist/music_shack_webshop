import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Login } from '../models/login.model';
import { Token } from '../models/token.model';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment.development';

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

  public authenticate(action: 'login' | 'register', login: Login): Observable<Token> {
    const endpointUrl = action === 'login' ? environment.loginUrl : environment.registerUrl;
    return this.httpClient.post<Token>(endpointUrl, login).pipe(
      tap(token => {
        if (token.token) {
          this.loggedIn = true;
          this.token = token.token;
          this.saveTokenInLocalStorage(token.token);
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
}
