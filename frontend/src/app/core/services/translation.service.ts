import { Injectable, signal, computed } from '@angular/core';
import { en } from '../i18n/en';
import { ptBr } from '../i18n/pt-br';

type Lang = 'en' | 'pt-br';

@Injectable({ providedIn: 'root' })
export class TranslationService {
  private _lang = signal<Lang>(this.getInitialLang());
  lang = this._lang.asReadonly();
  isEn = computed(() => this._lang() === 'en');

  private translations: Record<Lang, Record<string, any>> = {
    'en': en,
    'pt-br': ptBr
  };

  toggleLang(): void {
    const newLang: Lang = this._lang() === 'en' ? 'pt-br' : 'en';
    this._lang.set(newLang);
    localStorage.setItem('lang', newLang);
  }

  t(key: string): string {
    const keys = key.split('.');
    let result: any = this.translations[this._lang()];
    for (const k of keys) {
      if (result && typeof result === 'object' && k in result) {
        result = result[k];
      } else {
        return key;
      }
    }
    return typeof result === 'string' ? result : key;
  }

  private getInitialLang(): Lang {
    const stored = localStorage.getItem('lang');
    return (stored === 'en' || stored === 'pt-br') ? stored : 'en';
  }
}
