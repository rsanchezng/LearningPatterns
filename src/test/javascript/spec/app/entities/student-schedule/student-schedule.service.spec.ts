/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StudentScheduleService } from 'app/entities/student-schedule/student-schedule.service';
import { IStudentSchedule, StudentSchedule } from 'app/shared/model/student-schedule.model';

describe('Service Tests', () => {
  describe('StudentSchedule Service', () => {
    let injector: TestBed;
    let service: StudentScheduleService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudentSchedule;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(StudentScheduleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new StudentSchedule(0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            studentScheduleCreationDate: currentDate.format(DATE_FORMAT),
            studentScheduleModifiedDate: currentDate.format(DATE_FORMAT)
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

      it('should create a StudentSchedule', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            studentScheduleCreationDate: currentDate.format(DATE_FORMAT),
            studentScheduleModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            studentScheduleCreationDate: currentDate,
            studentScheduleModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new StudentSchedule(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a StudentSchedule', async () => {
        const returnedFromService = Object.assign(
          {
            studentScheduleCreatedBy: 'BBBBBB',
            studentScheduleCreationDate: currentDate.format(DATE_FORMAT),
            studentScheduleModifiedBy: 'BBBBBB',
            studentScheduleModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            studentScheduleCreationDate: currentDate,
            studentScheduleModifiedDate: currentDate
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

      it('should return a list of StudentSchedule', async () => {
        const returnedFromService = Object.assign(
          {
            studentScheduleCreatedBy: 'BBBBBB',
            studentScheduleCreationDate: currentDate.format(DATE_FORMAT),
            studentScheduleModifiedBy: 'BBBBBB',
            studentScheduleModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            studentScheduleCreationDate: currentDate,
            studentScheduleModifiedDate: currentDate
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

      it('should delete a StudentSchedule', async () => {
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
