import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStudentActivity } from 'app/shared/model/student-activity.model';

type EntityResponseType = HttpResponse<IStudentActivity>;
type EntityArrayResponseType = HttpResponse<IStudentActivity[]>;

@Injectable({ providedIn: 'root' })
export class StudentActivityService {
  public resourceUrl = SERVER_API_URL + 'api/student-activities';

  constructor(protected http: HttpClient) {}

  create(studentActivity: IStudentActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentActivity);
    return this.http
      .post<IStudentActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(studentActivity: IStudentActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentActivity);
    return this.http
      .put<IStudentActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStudentActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudentActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(studentActivity: IStudentActivity): IStudentActivity {
    const copy: IStudentActivity = Object.assign({}, studentActivity, {
      activityStartDate:
        studentActivity.activityStartDate != null && studentActivity.activityStartDate.isValid()
          ? studentActivity.activityStartDate.format(DATE_FORMAT)
          : null,
      activityEndDate:
        studentActivity.activityEndDate != null && studentActivity.activityEndDate.isValid()
          ? studentActivity.activityEndDate.format(DATE_FORMAT)
          : null,
      studentActivityGradeDate:
        studentActivity.studentActivityGradeDate != null && studentActivity.studentActivityGradeDate.isValid()
          ? studentActivity.studentActivityGradeDate.format(DATE_FORMAT)
          : null,
      studentActivityCreatedDate:
        studentActivity.studentActivityCreatedDate != null && studentActivity.studentActivityCreatedDate.isValid()
          ? studentActivity.studentActivityCreatedDate.format(DATE_FORMAT)
          : null,
      studentActivityModifiedDate:
        studentActivity.studentActivityModifiedDate != null && studentActivity.studentActivityModifiedDate.isValid()
          ? studentActivity.studentActivityModifiedDate.format(DATE_FORMAT)
          : null,
      studentActivityModifiedBy:
        studentActivity.studentActivityModifiedBy != null && studentActivity.studentActivityModifiedBy.isValid()
          ? studentActivity.studentActivityModifiedBy.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.activityStartDate = res.body.activityStartDate != null ? moment(res.body.activityStartDate) : null;
      res.body.activityEndDate = res.body.activityEndDate != null ? moment(res.body.activityEndDate) : null;
      res.body.studentActivityGradeDate = res.body.studentActivityGradeDate != null ? moment(res.body.studentActivityGradeDate) : null;
      res.body.studentActivityCreatedDate =
        res.body.studentActivityCreatedDate != null ? moment(res.body.studentActivityCreatedDate) : null;
      res.body.studentActivityModifiedDate =
        res.body.studentActivityModifiedDate != null ? moment(res.body.studentActivityModifiedDate) : null;
      res.body.studentActivityModifiedBy = res.body.studentActivityModifiedBy != null ? moment(res.body.studentActivityModifiedBy) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((studentActivity: IStudentActivity) => {
        studentActivity.activityStartDate = studentActivity.activityStartDate != null ? moment(studentActivity.activityStartDate) : null;
        studentActivity.activityEndDate = studentActivity.activityEndDate != null ? moment(studentActivity.activityEndDate) : null;
        studentActivity.studentActivityGradeDate =
          studentActivity.studentActivityGradeDate != null ? moment(studentActivity.studentActivityGradeDate) : null;
        studentActivity.studentActivityCreatedDate =
          studentActivity.studentActivityCreatedDate != null ? moment(studentActivity.studentActivityCreatedDate) : null;
        studentActivity.studentActivityModifiedDate =
          studentActivity.studentActivityModifiedDate != null ? moment(studentActivity.studentActivityModifiedDate) : null;
        studentActivity.studentActivityModifiedBy =
          studentActivity.studentActivityModifiedBy != null ? moment(studentActivity.studentActivityModifiedBy) : null;
      });
    }
    return res;
  }
}
