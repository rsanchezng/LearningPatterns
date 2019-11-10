import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudent } from 'app/shared/model/student.model';

type EntityResponseType = HttpResponse<IStudent>;
type EntityArrayResponseType = HttpResponse<IStudent[]>;

@Injectable({ providedIn: 'root' })
export class StudentService {
  public resourceUrl = SERVER_API_URL + 'api/students';

  constructor(protected http: HttpClient) {}

  create(student: IStudent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(student);
    return this.http
      .post<IStudent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(student: IStudent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(student);
    return this.http
      .put<IStudent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStudent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(student: IStudent): IStudent {
    const copy: IStudent = Object.assign({}, student, {
      studentCreationDate:
        student.studentCreationDate != null && student.studentCreationDate.isValid()
          ? student.studentCreationDate.format(DATE_FORMAT)
          : null,
      studentModifiedDate:
        student.studentModifiedDate != null && student.studentModifiedDate.isValid()
          ? student.studentModifiedDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.studentCreationDate = res.body.studentCreationDate != null ? moment(res.body.studentCreationDate) : null;
      res.body.studentModifiedDate = res.body.studentModifiedDate != null ? moment(res.body.studentModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((student: IStudent) => {
        student.studentCreationDate = student.studentCreationDate != null ? moment(student.studentCreationDate) : null;
        student.studentModifiedDate = student.studentModifiedDate != null ? moment(student.studentModifiedDate) : null;
      });
    }
    return res;
  }
}
