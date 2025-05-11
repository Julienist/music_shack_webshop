import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private currentLanguage = signal<string>('nl'); // Standaardtaal is Nederlands

  setLanguage(lang: string): void {
    this.currentLanguage.set(lang); // Update de taal
  }

  getLanguage(): string {
    return this.currentLanguage(); // Haal de huidige taal op
  }

  onLanguageChange(): string {
    return this.currentLanguage(); // Luister naar taalwijzigingen
  }
}
