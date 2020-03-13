import { Moment } from 'moment';
import { ITheme } from 'app/shared/model/theme.model';

export interface ISubtheme {
  id?: number;
  subthemeName?: string;
  subthemeDescription?: string;
  subthemeCreatedBy?: string;
  subthemeCreationDate?: Moment;
  subthemeModifiedBy?: string;
  subthemeModifiedDate?: Moment;
  subthemeMaxGrade?: number;
  theme?: ITheme;
}

export class Subtheme implements ISubtheme {
  constructor(
    public id?: number,
    public subthemeName?: string,
    public subthemeDescription?: string,
    public subthemeCreatedBy?: string,
    public subthemeCreationDate?: Moment,
    public subthemeModifiedBy?: string,
    public subthemeModifiedDate?: Moment,
    public subthemeMaxGrade?: number,
    public theme?: ITheme
  ) {}
}
