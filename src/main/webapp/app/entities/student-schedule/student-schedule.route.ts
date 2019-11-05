import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentSchedule } from 'app/shared/model/student-schedule.model';
import { StudentScheduleService } from './student-schedule.service';
import { StudentScheduleComponent } from './student-schedule.component';
import { StudentScheduleDetailComponent } from './student-schedule-detail.component';
import { StudentScheduleUpdateComponent } from './student-schedule-update.component';
import { StudentScheduleDeletePopupComponent } from './student-schedule-delete-dialog.component';
import { IStudentSchedule } from 'app/shared/model/student-schedule.model';

@Injectable({ providedIn: 'root' })
export class StudentScheduleResolve implements Resolve<IStudentSchedule> {
  constructor(private service: StudentScheduleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentSchedule> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StudentSchedule>) => response.ok),
        map((studentSchedule: HttpResponse<StudentSchedule>) => studentSchedule.body)
      );
    }
    return of(new StudentSchedule());
  }
}

export const studentScheduleRoute: Routes = [
  {
    path: '',
    component: StudentScheduleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentSchedules'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StudentScheduleDetailComponent,
    resolve: {
      studentSchedule: StudentScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentSchedules'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StudentScheduleUpdateComponent,
    resolve: {
      studentSchedule: StudentScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentSchedules'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StudentScheduleUpdateComponent,
    resolve: {
      studentSchedule: StudentScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentSchedules'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const studentSchedulePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StudentScheduleDeletePopupComponent,
    resolve: {
      studentSchedule: StudentScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StudentSchedules'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
