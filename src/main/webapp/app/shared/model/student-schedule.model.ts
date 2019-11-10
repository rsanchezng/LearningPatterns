import { Moment } from 'moment';
import { IGroup } from 'app/shared/model/group.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IStudentSchedule {
  id?: number;
  studentScheduleCreatedBy?: string;
  studentScheduleCreationDate?: Moment;
  studentScheduleModifiedBy?: string;
  studentScheduleModifiedDate?: Moment;
  group?: IGroup;
  student?: IStudent;
}

export class StudentSchedule implements IStudentSchedule {
  constructor(
    public id?: number,
    public studentScheduleCreatedBy?: string,
    public studentScheduleCreationDate?: Moment,
    public studentScheduleModifiedBy?: string,
    public studentScheduleModifiedDate?: Moment,
    public group?: IGroup,
    public student?: IStudent
  ) {}
}
