import { Moment } from 'moment';
import { ISubject } from 'app/shared/model/subject.model';

export interface ITheme {
  id?: number;
  themeName?: string;
  themeDescription?: string;
  themeCreatedBy?: string;
  themeCreationDate?: Moment;
  themeModifiedBy?: string;
  themeModifiedDate?: Moment;
  subject?: ISubject;
}

export class Theme implements ITheme {
  constructor(
    public id?: number,
    public themeName?: string,
    public themeDescription?: string,
    public themeCreatedBy?: string,
    public themeCreationDate?: Moment,
    public themeModifiedBy?: string,
    public themeModifiedDate?: Moment,
    public subject?: ISubject
  ) {}
}
