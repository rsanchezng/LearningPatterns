import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Subtheme } from 'app/shared/model/subtheme.model';
import { SubthemeService } from './subtheme.service';
import { SubthemeComponent } from './subtheme.component';
import { SubthemeDetailComponent } from './subtheme-detail.component';
import { SubthemeUpdateComponent } from './subtheme-update.component';
import { SubthemeDeletePopupComponent } from './subtheme-delete-dialog.component';
import { ISubtheme } from 'app/shared/model/subtheme.model';

@Injectable({ providedIn: 'root' })
export class SubthemeResolve implements Resolve<ISubtheme> {
  constructor(private service: SubthemeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISubtheme> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Subtheme>) => response.ok),
        map((subtheme: HttpResponse<Subtheme>) => subtheme.body)
      );
    }
    return of(new Subtheme());
  }
}

export const subthemeRoute: Routes = [
  {
    path: '',
    component: SubthemeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subthemes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubthemeDetailComponent,
    resolve: {
      subtheme: SubthemeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subthemes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubthemeUpdateComponent,
    resolve: {
      subtheme: SubthemeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subthemes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubthemeUpdateComponent,
    resolve: {
      subtheme: SubthemeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subthemes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const subthemePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SubthemeDeletePopupComponent,
    resolve: {
      subtheme: SubthemeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subthemes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
