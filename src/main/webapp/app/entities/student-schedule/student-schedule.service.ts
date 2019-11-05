import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
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
      scheduleCreationDate:
        studentSchedule.scheduleCreationDate != null && studentSchedule.scheduleCreationDate.isValid()
          ? studentSchedule.scheduleCreationDate.format(DATE_FORMAT)
          : null,
      scheduleModifiedDate:
        studentSchedule.scheduleModifiedDate != null && studentSchedule.scheduleModifiedDate.isValid()
          ? studentSchedule.scheduleModifiedDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.scheduleCreationDate = res.body.scheduleCreationDate != null ? moment(res.body.scheduleCreationDate) : null;
      res.body.scheduleModifiedDate = res.body.scheduleModifiedDate != null ? moment(res.body.scheduleModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((studentSchedule: IStudentSchedule) => {
        studentSchedule.scheduleCreationDate =
          studentSchedule.scheduleCreationDate != null ? moment(studentSchedule.scheduleCreationDate) : null;
        studentSchedule.scheduleModifiedDate =
          studentSchedule.scheduleModifiedDate != null ? moment(studentSchedule.scheduleModifiedDate) : null;
      });
    }
    return res;
  }
}
