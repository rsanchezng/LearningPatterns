import { Moment } from 'moment';
import { IActivity } from 'app/shared/model/activity.model';
import { IStudentSchedule } from 'app/shared/model/student-schedule.model';

export interface IStudentActivity {
  id?: number;
  activityStartDate?: Moment;
  activityEndDate?: Moment;
  activityGrade?: number;
  studentActivityGradeDate?: Moment;
  studentActivityCreatedDate?: Moment;
  studentActivityCreatedBy?: string;
  studentActivityModifiedDate?: Moment;
  studentActivityModifiedBy?: Moment;
  activity?: IActivity;
  studentschedule?: IStudentSchedule;
}

export class StudentActivity implements IStudentActivity {
  constructor(
    public id?: number,
    public activityStartDate?: Moment,
    public activityEndDate?: Moment,
    public activityGrade?: number,
    public studentActivityGradeDate?: Moment,
    public studentActivityCreatedDate?: Moment,
    public studentActivityCreatedBy?: string,
    public studentActivityModifiedDate?: Moment,
    public studentActivityModifiedBy?: Moment,
    public activity?: IActivity,
    public studentschedule?: IStudentSchedule
  ) {}
}
