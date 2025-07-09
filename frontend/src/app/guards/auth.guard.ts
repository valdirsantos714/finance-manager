import { CanActivateFn, Router } from '@angular/router';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);
  const publicRoutes = ['/login', '/register'];

  if (publicRoutes.includes(state.url)) {
    return true;
  }

  if (isPlatformBrowser(platformId)) {
    const cookies = document.cookie.split(';');
    let token = null;
    for (let i = 0; i < cookies.length; i++) {
      let cookie = cookies[i].trim();
      if (cookie.startsWith('jwt=')) {
        token = cookie.substring(4);
        break;
      }
    }

    if (token) {
      return true;
    }
  }

  router.navigate(['/login']);
  return false;
};
