import { Moment } from 'moment';
import { ISubtheme } from 'app/shared/model/subtheme.model';

export interface IActivity {
  id?: number;
  activityName?: string;
  activityDescription?: string;
  activityDuration?: number;
  activityUtility?: number;
  activityReqsId?: number;
  activityCreatedBy?: string;
  activityCreationDate?: Moment;
  activityModifiedBy?: string;
  activityModifiedDate?: Moment;
  subtheme?: ISubtheme;
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public activityName?: string,
    public activityDescription?: string,
    public activityDuration?: number,
    public activityUtility?: number,
    public activityReqsId?: number,
    public activityCreatedBy?: string,
    public activityCreationDate?: Moment,
    public activityModifiedBy?: string,
    public activityModifiedDate?: Moment,
    public subtheme?: ISubtheme
  ) {}
}
