import { Moment } from 'moment';

export interface IStudent {
  id?: number;
  studentFirstName?: string;
  studentLastName?: string;
  studentEmail?: string;
  studentPassword?: string;
  studentCredits?: string;
  studentCreatedBy?: string;
  studentCreationDate?: Moment;
  studentModifiedBy?: string;
  studentModifiedDate?: Moment;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public studentFirstName?: string,
    public studentLastName?: string,
    public studentEmail?: string,
    public studentPassword?: string,
    public studentCredits?: string,
    public studentCreatedBy?: string,
    public studentCreationDate?: Moment,
    public studentModifiedBy?: string,
    public studentModifiedDate?: Moment
  ) {}
}
