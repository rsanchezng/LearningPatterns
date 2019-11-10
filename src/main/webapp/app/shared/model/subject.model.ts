import { Moment } from 'moment';
import { ITeacher } from 'app/shared/model/teacher.model';

export interface ISubject {
  id?: number;
  subjectName?: string;
  subjectDescription?: string;
  subjectCredits?: number;
  subjectCreatedBy?: string;
  subjectCreationDate?: Moment;
  subjectModifiedBy?: string;
  subjectModifiedDate?: Moment;
  teacher?: ITeacher;
}

export class Subject implements ISubject {
  constructor(
    public id?: number,
    public subjectName?: string,
    public subjectDescription?: string,
    public subjectCredits?: number,
    public subjectCreatedBy?: string,
    public subjectCreationDate?: Moment,
    public subjectModifiedBy?: string,
    public subjectModifiedDate?: Moment,
    public teacher?: ITeacher
  ) {}
}
