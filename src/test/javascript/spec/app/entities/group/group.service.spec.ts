import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { GroupService } from 'app/entities/group/group.service';
import { IGroup, Group } from 'app/shared/model/group.model';

describe('Service Tests', () => {
  describe('Group Service', () => {
    let injector: TestBed;
    let service: GroupService;
    let httpMock: HttpTestingController;
    let elemDefault: IGroup;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(GroupService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Group(0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            groupCreationDate: currentDate.format(DATE_FORMAT),
            groupModifiedDate: currentDate.format(DATE_FORMAT)
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

      it('should create a Group', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            groupCreationDate: currentDate.format(DATE_FORMAT),
            groupModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            groupCreationDate: currentDate,
            groupModifiedDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Group(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Group', () => {
        const returnedFromService = Object.assign(
          {
            groupCreatedBy: 'BBBBBB',
            groupCreationDate: currentDate.format(DATE_FORMAT),
            groupModifiedBy: 'BBBBBB',
            groupModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            groupCreationDate: currentDate,
            groupModifiedDate: currentDate
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

      it('should return a list of Group', () => {
        const returnedFromService = Object.assign(
          {
            groupCreatedBy: 'BBBBBB',
            groupCreationDate: currentDate.format(DATE_FORMAT),
            groupModifiedBy: 'BBBBBB',
            groupModifiedDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            groupCreationDate: currentDate,
            groupModifiedDate: currentDate
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

      it('should delete a Group', () => {
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
