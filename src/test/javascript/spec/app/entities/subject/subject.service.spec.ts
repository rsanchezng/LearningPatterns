/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SubjectService } from 'app/entities/subject/subject.service';
import { ISubject, Subject } from 'app/shared/model/subject.model';

describe('Service Tests', () => {
  describe('Subject Service', () => {
    let injector: TestBed;
    let service: SubjectService;
    let httpMock: HttpTestingController;
    let elemDefault: ISubject;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SubjectService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Subject(0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            subjectCreationDate: currentDate.format(DATE_FORMAT),
            subjectModifiedDate: currentDate.format(DATE_FORMAT)
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

      it('should create a Subject', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            subjectCreationDate: currentDate.format(DATE_FORMAT),
            subjectModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            subjectCreationDate: currentDate,
            subjectModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Subject(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Subject', async () => {
        const returnedFromService = Object.assign(
          {
            subjectName: 'BBBBBB',
            subjectDescription: 'BBBBBB',
            subjectCredits: 1,
            subjectCreatedBy: 'BBBBBB',
            subjectCreationDate: currentDate.format(DATE_FORMAT),
            subjectModifiedBy: 'BBBBBB',
            subjectModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            subjectCreationDate: currentDate,
            subjectModifiedDate: currentDate
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

      it('should return a list of Subject', async () => {
        const returnedFromService = Object.assign(
          {
            subjectName: 'BBBBBB',
            subjectDescription: 'BBBBBB',
            subjectCredits: 1,
            subjectCreatedBy: 'BBBBBB',
            subjectCreationDate: currentDate.format(DATE_FORMAT),
            subjectModifiedBy: 'BBBBBB',
            subjectModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            subjectCreationDate: currentDate,
            subjectModifiedDate: currentDate
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

      it('should delete a Subject', async () => {
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
