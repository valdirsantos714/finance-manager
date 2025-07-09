import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const publicRoutes = ['/login', '/register'];

  if (publicRoutes.includes(state.url)) {
    return true;
  }

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
  } else {
    router.navigate(['/login']);
    return false;
  }
};
