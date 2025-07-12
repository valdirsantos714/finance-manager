import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor() { }

  decodeToken(token: string): string {
    return jwtDecode(token);
  }

  getTokenFromCookie(): string {
    const cookieName = "jwt";
    const cookies = document.cookie.split(';');
    for (const cookie of cookies) {
      const [name, value] = cookie.trim().split('=');
      if (name === cookieName) {
        return decodeURIComponent(value);
      }
    }
    return '';
  }
}
