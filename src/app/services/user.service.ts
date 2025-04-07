import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
    private usersUrl = `${environment.apiUrl}/users`;
    private httpClient = inject(HttpClient);
    private loginService = inject(LoginService);

    getUserId(): Observable<number> {
        const email = this.loginService.getEmail();
        if (!email) {
            return throwError(() =>
                new Error('Gebruiker niet ingelogd.'));
        }
        return this.httpClient.get<number>(`${this.usersUrl}/id/${email}`);
    }
}
