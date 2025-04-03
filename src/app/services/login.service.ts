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
  private role: string = '';
  private email: string = '';

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

  //  getEmail(): string | null {
  //   return this.email;
  // }

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
        }
      })
      // tap(role => {
      //   if (role.role) {
      //     this.role = role.role;
      //     this.saveRoleInLocalStorage(role.role);
      //   }
      // })
    );
  }

  private saveTokenInLocalStorage(token: string){
    localStorage.setItem('authToken', token);
  }

  private loadTokenFromLocalStorage(){
      this.token = localStorage.getItem('authToken')
  }

  // private saveRoleInLocalStorage(role: string){
  //   localStorage.setItem('role', role);
  //   console.log("role saved in localstorage: "+ role);
  // }

  // private loadRoleFromLocalStorage(){
  //   this.role = localStorage.getItem('role');
  // }

}
