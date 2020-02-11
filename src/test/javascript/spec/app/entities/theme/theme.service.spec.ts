import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ThemeService } from 'app/entities/theme/theme.service';
import { ITheme, Theme } from 'app/shared/model/theme.model';

describe('Service Tests', () => {
  describe('Theme Service', () => {
    let injector: TestBed;
    let service: ThemeService;
    let httpMock: HttpTestingController;
    let elemDefault: ITheme;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ThemeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Theme(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            themeCreationDate: currentDate.format(DATE_FORMAT),
            themeModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Theme', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            themeCreationDate: currentDate.format(DATE_FORMAT),
            themeModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            themeCreationDate: currentDate,
            themeModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Theme(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Theme', () => {
        const returnedFromService = Object.assign(
          {
            themeName: 'BBBBBB',
            themeDescription: 'BBBBBB',
            themeCreatedBy: 'BBBBBB',
            themeCreationDate: currentDate.format(DATE_FORMAT),
            themeModifiedBy: 'BBBBBB',
            themeModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            themeCreationDate: currentDate,
            themeModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Theme', () => {
        const returnedFromService = Object.assign(
          {
            themeName: 'BBBBBB',
            themeDescription: 'BBBBBB',
            themeCreatedBy: 'BBBBBB',
            themeCreationDate: currentDate.format(DATE_FORMAT),
            themeModifiedBy: 'BBBBBB',
            themeModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            themeCreationDate: currentDate,
            themeModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Theme', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
