/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SubthemeService } from 'app/entities/subtheme/subtheme.service';
import { ISubtheme, Subtheme } from 'app/shared/model/subtheme.model';

describe('Service Tests', () => {
  describe('Subtheme Service', () => {
    let injector: TestBed;
    let service: SubthemeService;
    let httpMock: HttpTestingController;
    let elemDefault: ISubtheme;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SubthemeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Subtheme(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            subthemeCreationDate: currentDate.format(DATE_FORMAT),
            subthemeModifiedDate: currentDate.format(DATE_FORMAT)
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

      it('should create a Subtheme', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            subthemeCreationDate: currentDate.format(DATE_FORMAT),
            subthemeModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            subthemeCreationDate: currentDate,
            subthemeModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Subtheme(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Subtheme', async () => {
        const returnedFromService = Object.assign(
          {
            subthemeName: 'BBBBBB',
            subthemeDescription: 'BBBBBB',
            subthemeCreatedBy: 'BBBBBB',
            subthemeCreationDate: currentDate.format(DATE_FORMAT),
            subthemeModifiedBy: 'BBBBBB',
            subthemeModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            subthemeCreationDate: currentDate,
            subthemeModifiedDate: currentDate
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

      it('should return a list of Subtheme', async () => {
        const returnedFromService = Object.assign(
          {
            subthemeName: 'BBBBBB',
            subthemeDescription: 'BBBBBB',
            subthemeCreatedBy: 'BBBBBB',
            subthemeCreationDate: currentDate.format(DATE_FORMAT),
            subthemeModifiedBy: 'BBBBBB',
            subthemeModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            subthemeCreationDate: currentDate,
            subthemeModifiedDate: currentDate
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

      it('should delete a Subtheme', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

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
