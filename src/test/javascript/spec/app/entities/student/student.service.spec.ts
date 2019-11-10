/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StudentService } from 'app/entities/student/student.service';
import { IStudent, Student } from 'app/shared/model/student.model';

describe('Service Tests', () => {
  describe('Student Service', () => {
    let injector: TestBed;
    let service: StudentService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudent;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(StudentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Student(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            studentCreationDate: currentDate.format(DATE_FORMAT),
            studentModifiedDate: currentDate.format(DATE_FORMAT)
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

      it('should create a Student', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            studentCreationDate: currentDate.format(DATE_FORMAT),
            studentModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            studentCreationDate: currentDate,
            studentModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Student(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Student', async () => {
        const returnedFromService = Object.assign(
          {
            studentFirstName: 'BBBBBB',
            studentLastName: 'BBBBBB',
            studentEmail: 'BBBBBB',
            studentPassword: 'BBBBBB',
            studentCredits: 'BBBBBB',
            studentCreatedBy: 'BBBBBB',
            studentCreationDate: currentDate.format(DATE_FORMAT),
            studentModifiedBy: 'BBBBBB',
            studentModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            studentCreationDate: currentDate,
            studentModifiedDate: currentDate
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

      it('should return a list of Student', async () => {
        const returnedFromService = Object.assign(
          {
            studentFirstName: 'BBBBBB',
            studentLastName: 'BBBBBB',
            studentEmail: 'BBBBBB',
            studentPassword: 'BBBBBB',
            studentCredits: 'BBBBBB',
            studentCreatedBy: 'BBBBBB',
            studentCreationDate: currentDate.format(DATE_FORMAT),
            studentModifiedBy: 'BBBBBB',
            studentModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            studentCreationDate: currentDate,
            studentModifiedDate: currentDate
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

      it('should delete a Student', async () => {
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
