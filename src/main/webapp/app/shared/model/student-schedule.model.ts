import { Moment } from 'moment';
import { IGroup } from 'app/shared/model/group.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IStudentSchedule {
  id?: number;
  scheduleCreatedBy?: string;
  scheduleCreationDate?: Moment;
  scheduleModifiedBy?: string;
  scheduleModifiedDate?: Moment;
  group?: IGroup;
  student?: IStudent;
}

export class StudentSchedule implements IStudentSchedule {
  constructor(
    public id?: number,
    public scheduleCreatedBy?: string,
    public scheduleCreationDate?: Moment,
    public scheduleModifiedBy?: string,
    public scheduleModifiedDate?: Moment,
    public group?: IGroup,
    public student?: IStudent
  ) {}
}
