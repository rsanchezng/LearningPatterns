import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentSchedule } from 'app/shared/model/student-schedule.model';

type EntityResponseType = HttpResponse<IStudentSchedule>;
type EntityArrayResponseType = HttpResponse<IStudentSchedule[]>;

@Injectable({ providedIn: 'root' })
export class StudentScheduleService {
  public resourceUrl = SERVER_API_URL + 'api/student-schedules';

  constructor(protected http: HttpClient) {}

  create(studentSchedule: IStudentSchedule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentSchedule);
    return this.http
      .post<IStudentSchedule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(studentSchedule: IStudentSchedule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentSchedule);
    return this.http
      .put<IStudentSchedule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStudentSchedule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudentSchedule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(studentSchedule: IStudentSchedule): IStudentSchedule {
    const copy: IStudentSchedule = Object.assign({}, studentSchedule, {
      studentScheduleCreationDate:
        studentSchedule.studentScheduleCreationDate != null && studentSchedule.studentScheduleCreationDate.isValid()
          ? studentSchedule.studentScheduleCreationDate.format(DATE_FORMAT)
          : null,
      studentScheduleModifiedDate:
        studentSchedule.studentScheduleModifiedDate != null && studentSchedule.studentScheduleModifiedDate.isValid()
          ? studentSchedule.studentScheduleModifiedDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.studentScheduleCreationDate =
        res.body.studentScheduleCreationDate != null ? moment(res.body.studentScheduleCreationDate) : null;
      res.body.studentScheduleModifiedDate =
        res.body.studentScheduleModifiedDate != null ? moment(res.body.studentScheduleModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((studentSchedule: IStudentSchedule) => {
        studentSchedule.studentScheduleCreationDate =
          studentSchedule.studentScheduleCreationDate != null ? moment(studentSchedule.studentScheduleCreationDate) : null;
        studentSchedule.studentScheduleModifiedDate =
          studentSchedule.studentScheduleModifiedDate != null ? moment(studentSchedule.studentScheduleModifiedDate) : null;
      });
    }
    return res;
  }
}
