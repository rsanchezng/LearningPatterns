import { Moment } from 'moment';

export interface ITeacher {
  id?: number;
  teacherFirstName?: string;
  teacherLastName?: string;
  teacherEmail?: string;
  teacherPassword?: string;
  teacherCreatedBy?: string;
  teacherCreationDate?: Moment;
  teacherModifiedBy?: string;
  teacherModifiedDate?: Moment;
}

export class Teacher implements ITeacher {
  constructor(
    public id?: number,
    public teacherFirstName?: string,
    public teacherLastName?: string,
    public teacherEmail?: string,
    public teacherPassword?: string,
    public teacherCreatedBy?: string,
    public teacherCreationDate?: Moment,
    public teacherModifiedBy?: string,
    public teacherModifiedDate?: Moment
  ) {}
}
