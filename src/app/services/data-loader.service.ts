import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DataLoaderService {
  private httpClient = inject(HttpClient);

  public loadDataWithFallback<T>(apiUrl: string, fallbackUrl: string): Observable<T> {
    return this.httpClient.get<T>(apiUrl).pipe(
      catchError(() => {
        // Attempt to load data from the fallback JSON file
        return this.httpClient.get<T>(fallbackUrl).pipe(
          catchError(() => {
            return throwError(() => new Error('Failed to load data from both API and fallback file.'));
          })
        );
      })
    );
  }
}
