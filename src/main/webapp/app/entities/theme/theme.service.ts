import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITheme } from 'app/shared/model/theme.model';

type EntityResponseType = HttpResponse<ITheme>;
type EntityArrayResponseType = HttpResponse<ITheme[]>;

@Injectable({ providedIn: 'root' })
export class ThemeService {
  public resourceUrl = SERVER_API_URL + 'api/themes';

  constructor(protected http: HttpClient) {}

  create(theme: ITheme): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(theme);
    return this.http
      .post<ITheme>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(theme: ITheme): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(theme);
    return this.http
      .put<ITheme>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITheme>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITheme[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(theme: ITheme): ITheme {
    const copy: ITheme = Object.assign({}, theme, {
      themeCreationDate:
        theme.themeCreationDate != null && theme.themeCreationDate.isValid() ? theme.themeCreationDate.format(DATE_FORMAT) : null,
      themeModifiedDate:
        theme.themeModifiedDate != null && theme.themeModifiedDate.isValid() ? theme.themeModifiedDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.themeCreationDate = res.body.themeCreationDate != null ? moment(res.body.themeCreationDate) : null;
      res.body.themeModifiedDate = res.body.themeModifiedDate != null ? moment(res.body.themeModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((theme: ITheme) => {
        theme.themeCreationDate = theme.themeCreationDate != null ? moment(theme.themeCreationDate) : null;
        theme.themeModifiedDate = theme.themeModifiedDate != null ? moment(theme.themeModifiedDate) : null;
      });
    }
    return res;
  }
}
