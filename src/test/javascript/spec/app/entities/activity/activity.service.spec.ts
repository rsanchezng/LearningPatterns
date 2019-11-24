import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ActivityService } from 'app/entities/activity/activity.service';
import { IActivity, Activity } from 'app/shared/model/activity.model';

describe('Service Tests', () => {
  describe('Activity Service', () => {
    let injector: TestBed;
    let service: ActivityService;
    let httpMock: HttpTestingController;
    let elemDefault: IActivity;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ActivityService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Activity(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            activityCreationDate: currentDate.format(DATE_FORMAT),
            activityModifiedDate: currentDate.format(DATE_FORMAT)
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

      it('should create a Activity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            activityCreationDate: currentDate.format(DATE_FORMAT),
            activityModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            activityCreationDate: currentDate,
            activityModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Activity(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Activity', () => {
        const returnedFromService = Object.assign(
          {
            activityName: 'BBBBBB',
            activityDescription: 'BBBBBB',
            activityDuration: 1,
            activityUtility: 1,
            activityReqsId: 1,
            activityCreatedBy: 'BBBBBB',
            activityCreationDate: currentDate.format(DATE_FORMAT),
            activityModifiedBy: 'BBBBBB',
            activityModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            activityCreationDate: currentDate,
            activityModifiedDate: currentDate
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

      it('should return a list of Activity', () => {
        const returnedFromService = Object.assign(
          {
            activityName: 'BBBBBB',
            activityDescription: 'BBBBBB',
            activityDuration: 1,
            activityUtility: 1,
            activityReqsId: 1,
            activityCreatedBy: 'BBBBBB',
            activityCreationDate: currentDate.format(DATE_FORMAT),
            activityModifiedBy: 'BBBBBB',
            activityModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            activityCreationDate: currentDate,
            activityModifiedDate: currentDate
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

      it('should delete a Activity', () => {
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
