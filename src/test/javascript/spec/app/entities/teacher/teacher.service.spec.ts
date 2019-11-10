/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TeacherService } from 'app/entities/teacher/teacher.service';
import { ITeacher, Teacher } from 'app/shared/model/teacher.model';

describe('Service Tests', () => {
  describe('Teacher Service', () => {
    let injector: TestBed;
    let service: TeacherService;
    let httpMock: HttpTestingController;
    let elemDefault: ITeacher;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TeacherService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Teacher(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            teacherCreationDate: currentDate.format(DATE_FORMAT),
            teacherModifiedDate: currentDate.format(DATE_FORMAT)
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

      it('should create a Teacher', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            teacherCreationDate: currentDate.format(DATE_FORMAT),
            teacherModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            teacherCreationDate: currentDate,
            teacherModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Teacher(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Teacher', async () => {
        const returnedFromService = Object.assign(
          {
            teacherFirstName: 'BBBBBB',
            teacherLastName: 'BBBBBB',
            teacherEmail: 'BBBBBB',
            teacherPassword: 'BBBBBB',
            teacherCreatedBy: 'BBBBBB',
            teacherCreationDate: currentDate.format(DATE_FORMAT),
            teacherModifiedBy: 'BBBBBB',
            teacherModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            teacherCreationDate: currentDate,
            teacherModifiedDate: currentDate
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

      it('should return a list of Teacher', async () => {
        const returnedFromService = Object.assign(
          {
            teacherFirstName: 'BBBBBB',
            teacherLastName: 'BBBBBB',
            teacherEmail: 'BBBBBB',
            teacherPassword: 'BBBBBB',
            teacherCreatedBy: 'BBBBBB',
            teacherCreationDate: currentDate.format(DATE_FORMAT),
            teacherModifiedBy: 'BBBBBB',
            teacherModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            teacherCreationDate: currentDate,
            teacherModifiedDate: currentDate
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

      it('should delete a Teacher', async () => {
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
