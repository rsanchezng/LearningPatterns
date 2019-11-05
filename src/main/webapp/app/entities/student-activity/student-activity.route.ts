import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentActivity } from 'app/shared/model/student-activity.model';
import { StudentActivityService } from './student-activity.service';
import { StudentActivityComponent } from './student-activity.component';
import { StudentActivityDetailComponent } from './student-activity-detail.component';
import { StudentActivityUpdateComponent } from './student-activity-update.component';
import { StudentActivityDeletePopupComponent } from './student-activity-delete-dialog.component';
import { IStudentActivity } from 'app/shared/model/student-activity.model';

@Injectable({ providedIn: 'root' })
export class StudentActivityResolve implements Resolve<IStudentActivity> {
  constructor(private service: StudentActivityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentActivity> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StudentActivity>) => response.ok),
        map((studentActivity: HttpResponse<StudentActivity>) => studentActivity.body)
      );
    }
    return of(new StudentActivity());
  }
}

export const studentActivityRoute: Routes = [
  {
    path: '',
    component: StudentActivityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentActivities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StudentActivityDetailComponent,
    resolve: {
      studentActivity: StudentActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentActivities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StudentActivityUpdateComponent,
    resolve: {
      studentActivity: StudentActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentActivities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StudentActivityUpdateComponent,
    resolve: {
      studentActivity: StudentActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentActivities'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const studentActivityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StudentActivityDeletePopupComponent,
    resolve: {
      studentActivity: StudentActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentActivities'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
