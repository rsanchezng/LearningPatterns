import { Moment } from 'moment';
import { ISubject } from 'app/shared/model/subject.model';
import { ITeacher } from 'app/shared/model/teacher.model';

export interface IGroup {
  id?: number;
  groupCreatedBy?: string;
  groupCreationDate?: Moment;
  groupModifiedBy?: string;
  groupModifiedDate?: Moment;
  subject?: ISubject;
  teacher?: ITeacher;
}

export class Group implements IGroup {
  constructor(
    public id?: number,
    public groupCreatedBy?: string,
    public groupCreationDate?: Moment,
    public groupModifiedBy?: string,
    public groupModifiedDate?: Moment,
    public subject?: ISubject,
    public teacher?: ITeacher
  ) {}
}
