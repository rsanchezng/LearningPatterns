import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StudentActivityService } from 'app/entities/student-activity/student-activity.service';
import { IStudentActivity, StudentActivity } from 'app/shared/model/student-activity.model';

describe('Service Tests', () => {
  describe('StudentActivity Service', () => {
    let injector: TestBed;
    let service: StudentActivityService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudentActivity;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(StudentActivityService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new StudentActivity(0, currentDate, currentDate, 0, currentDate, currentDate, 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            activityStartDate: currentDate.format(DATE_FORMAT),
            activityEndDate: currentDate.format(DATE_FORMAT),
            studentActivityGradeDate: currentDate.format(DATE_FORMAT),
            studentActivityCreatedDate: currentDate.format(DATE_FORMAT),
            studentActivityModifiedDate: currentDate.format(DATE_FORMAT),
            studentActivityModifiedBy: currentDate.format(DATE_FORMAT)
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

      it('should create a StudentActivity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            activityStartDate: currentDate.format(DATE_FORMAT),
            activityEndDate: currentDate.format(DATE_FORMAT),
            studentActivityGradeDate: currentDate.format(DATE_FORMAT),
            studentActivityCreatedDate: currentDate.format(DATE_FORMAT),
            studentActivityModifiedDate: currentDate.format(DATE_FORMAT),
            studentActivityModifiedBy: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            activityStartDate: currentDate,
            activityEndDate: currentDate,
            studentActivityGradeDate: currentDate,
            studentActivityCreatedDate: currentDate,
            studentActivityModifiedDate: currentDate,
            studentActivityModifiedBy: currentDate
          },
          returnedFromService
        );
        service
          .create(new StudentActivity(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a StudentActivity', () => {
        const returnedFromService = Object.assign(
          {
            activityStartDate: currentDate.format(DATE_FORMAT),
            activityEndDate: currentDate.format(DATE_FORMAT),
            activityGrade: 1,
            studentActivityGradeDate: currentDate.format(DATE_FORMAT),
            studentActivityCreatedDate: currentDate.format(DATE_FORMAT),
            studentActivityCreatedBy: 'BBBBBB',
            studentActivityModifiedDate: currentDate.format(DATE_FORMAT),
            studentActivityModifiedBy: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            activityStartDate: currentDate,
            activityEndDate: currentDate,
            studentActivityGradeDate: currentDate,
            studentActivityCreatedDate: currentDate,
            studentActivityModifiedDate: currentDate,
            studentActivityModifiedBy: currentDate
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

      it('should return a list of StudentActivity', () => {
        const returnedFromService = Object.assign(
          {
            activityStartDate: currentDate.format(DATE_FORMAT),
            activityEndDate: currentDate.format(DATE_FORMAT),
            activityGrade: 1,
            studentActivityGradeDate: currentDate.format(DATE_FORMAT),
            studentActivityCreatedDate: currentDate.format(DATE_FORMAT),
            studentActivityCreatedBy: 'BBBBBB',
            studentActivityModifiedDate: currentDate.format(DATE_FORMAT),
            studentActivityModifiedBy: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            activityStartDate: currentDate,
            activityEndDate: currentDate,
            studentActivityGradeDate: currentDate,
            studentActivityCreatedDate: currentDate,
            studentActivityModifiedDate: currentDate,
            studentActivityModifiedBy: currentDate
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

      it('should delete a StudentActivity', () => {
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
