import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubtheme } from 'app/shared/model/subtheme.model';

type EntityResponseType = HttpResponse<ISubtheme>;
type EntityArrayResponseType = HttpResponse<ISubtheme[]>;

@Injectable({ providedIn: 'root' })
export class SubthemeService {
  public resourceUrl = SERVER_API_URL + 'api/subthemes';

  constructor(protected http: HttpClient) {}

  create(subtheme: ISubtheme): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subtheme);
    return this.http
      .post<ISubtheme>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subtheme: ISubtheme): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subtheme);
    return this.http
      .put<ISubtheme>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISubtheme>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubtheme[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(subtheme: ISubtheme): ISubtheme {
    const copy: ISubtheme = Object.assign({}, subtheme, {
      subthemeCreationDate:
        subtheme.subthemeCreationDate != null && subtheme.subthemeCreationDate.isValid()
          ? subtheme.subthemeCreationDate.format(DATE_FORMAT)
          : null,
      subthemeModifiedDate:
        subtheme.subthemeModifiedDate != null && subtheme.subthemeModifiedDate.isValid()
          ? subtheme.subthemeModifiedDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.subthemeCreationDate = res.body.subthemeCreationDate != null ? moment(res.body.subthemeCreationDate) : null;
      res.body.subthemeModifiedDate = res.body.subthemeModifiedDate != null ? moment(res.body.subthemeModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((subtheme: ISubtheme) => {
        subtheme.subthemeCreationDate = subtheme.subthemeCreationDate != null ? moment(subtheme.subthemeCreationDate) : null;
        subtheme.subthemeModifiedDate = subtheme.subthemeModifiedDate != null ? moment(subtheme.subthemeModifiedDate) : null;
      });
    }
    return res;
  }
}
